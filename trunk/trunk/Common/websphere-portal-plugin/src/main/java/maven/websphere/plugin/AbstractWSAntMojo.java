package maven.websphere.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;
import maven.websphere.plugin.model.WAS;
import maven.websphere.plugin.utils.FileHelper;
import maven.websphere.plugin.utils.FreemarkerHelper;
import maven.websphere.plugin.utils.OS;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.Commandline;

public abstract class AbstractWSAntMojo extends AbstractWebsphereMojo {

	/**
	 * WebSphere server profile id, which is configured in Maven Settings.xml
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "server", required = true)
	private String server;

	/**
	 * The wasHome attribute is where contains the location of the WebSphere
	 * Installation Directory(from client side).
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "wasHome", required = true)
	private String wasHome;

	private WAS was;

	private File wasAntScript;

	private static final String FTL_WAS_HOME = "was_home";

	private static final String FTL_WSANT_BUILD_FILE = "wsant_build_file";

	@Override
	protected void doExecute() throws MojoExecutionException,
			MojoFailureException {
		validate();
		init();
		wasAntScript = generateWASAntScript();
		Commandline cli = generateCommandline();
		executeCommandLine(cli);
	}

	private Commandline generateCommandline() throws MojoExecutionException {
		Commandline cli = new Commandline();
		ScriptTemplatePair pair = OS.isFamilyWindows() || OS.isFamilyWin9x() ? ScriptTemplatePair.WSANT_CMD_WIN
				: ScriptTemplatePair.WSANT_CMD_UNIX;
		File executable = new File(workingDirectory + File.separator
				+ pair.getScript());
		InputStream template = getEnvAssetAsStream(pair.getTemplate());
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();
		String wasHome = StringUtils.isNotBlank(getWasHome()) ? getWasHome()
				: getWas().getWasHome();
		nameValuePairs.put(FTL_WAS_HOME, wasHome);
		nameValuePairs.put(FTL_WSANT_BUILD_FILE, wasAntScript.getPath());
		try {
			FreemarkerHelper.process(template, nameValuePairs,
					new OutputStreamWriter(new FileOutputStream(executable)));
			executable.setExecutable(true, true);
			cli.setExecutable(executable.getCanonicalPath());
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Unable to create XML Access executable", e);
		} finally {
			if (null != template) {
				try {
					template.close();
				} catch (IOException e) {
					getLog().error(e);
				}
			}
		}
		return cli;
	}

	private File generateWASAntScript() throws MojoExecutionException {
		File script = null;
		Writer writer = null;
		InputStream template = null;
		try {
			String scriptFtl = getWSAntScriptTemplate();
			script = new File(getWSAntScript());
			writer = new OutputStreamWriter(new FileOutputStream(script));
			template = FileHelper.findSingleFileAsStream(getAssetDirectory(),
					scriptFtl);
			Map<Object, Object> nameValuePairs = getWSAntScriptNameValuePairs();
			FreemarkerHelper.process(template, nameValuePairs, writer);
			log.info("Generating WSAnt Script:" + script.getCanonicalPath());
			log.debug(IOUtils.toString(new FileInputStream(script)));
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Unable to generate local ant script file", e);
		} finally {
			try {
				if (null != writer) {
					writer.close();
				}
				if (null != template) {
					template.close();
				}
			} catch (IOException e2) {
				getLog().error(e2);
			}
		}
		return script;
	}

	protected abstract ScriptTemplatePair getScriptTemplatePair();

	public String getServer() {
		return server;
	}

	public WAS getWas() {
		return was;
	}

	public String getWasHome() {
		return wasHome;
	}

	private String getWSAntScript() {
		return workingDirectory.getAbsoluteFile() + File.separator
				+ getScriptTemplatePair().getScript();
	}

	protected abstract Map<Object, Object> getWSAntScriptNameValuePairs()
			throws MojoExecutionException;

	private String getWSAntScriptTemplate() {
		return getScriptTemplatePair().getTemplate();
	}

	protected void init() throws MojoExecutionException {
		if (null == was && StringUtils.isNotBlank(server)) {
			was = new WAS(getServer(server));
		}
	}

	protected abstract void validate() throws MojoExecutionException;

}
