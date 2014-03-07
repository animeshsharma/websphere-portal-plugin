package maven.websphere.plugin;

import static maven.websphere.plugin.utils.Constants.XMLACCESS_CLI_UNIX;
import static maven.websphere.plugin.utils.Constants.XMLACCESS_CLI_WIN;
import static maven.websphere.plugin.utils.Constants.XMLACCESS_EXECUTABLE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;
import maven.websphere.plugin.model.WPS;
import maven.websphere.plugin.model.XMLAccessNameValuePairs;
import maven.websphere.plugin.utils.Constants;
import maven.websphere.plugin.utils.FreemarkerHelper;
import maven.websphere.plugin.utils.OS;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.Commandline;

/**
 * Super class of XMLAccess related goals, define the work follow of XMLAccess
 * execution
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public abstract class AbstractXMLAccessMojo extends AbstractWebsphereMojo {
	/**
	 * Websphere Portal server name to perform the xmlacess access, configured
	 * in Settings.xml
	 * 
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "server", required = true)
	private String server;

	private WPS wps;

	/**
	 * Do clean up jobs
	 * 
	 * @since 1.0.3
	 */
	protected void cleanUp() throws MojoExecutionException {

	}

	@Override
	public void doExecute() throws MojoExecutionException, MojoFailureException {

		File envAssets = getEnvironmentAssetDirectory();
		log.debug(envAssets.getPath());
		try {
			init();
			preAction();
			// prepareXMLAccessRuntimes();
			generateXMLAccessScript();
			Commandline cli = generateXMLAccessCli();
			executeCommandLine(cli);
			postAction();
		} finally {
			try {
				log.info("XMLAccess output file generated at:"
						+ getXMLAccessOutputFile().getCanonicalPath());
				log.debug(IOUtils.toString(new FileInputStream(
						getXMLAccessOutputFile())));
			} catch (IOException e) {
				// do nothing
			}

			cleanUp();
		}
	};

	private Commandline generateXMLAccessCli() throws MojoExecutionException {
		Commandline cli = new Commandline();
		try {
			cli.setWorkingDirectory(workingDirectory);

			InputStream template = getEnvAssetAsStream(OS.isFamilyWindows()
					|| OS.isFamilyWin9x() ? XMLACCESS_CLI_WIN
					: XMLACCESS_CLI_UNIX);
			File executable = new File(workingDirectory + File.separator
					+ XMLACCESS_EXECUTABLE);
			Map<Object, Object> nameValuePairs = XMLAccessNameValuePairs
					.asMap(this);

			Map<Object, Object> customNameValuePairs = getCustomXMLAccessCliNameValuePairs();
			if (null != customNameValuePairs) {
				nameValuePairs.putAll(customNameValuePairs);
			}
			FreemarkerHelper.process(template, nameValuePairs,
					new OutputStreamWriter(new FileOutputStream(executable)));
			executable.setExecutable(true, true);
			cli.setExecutable(executable.getCanonicalPath());
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Unable to create XML Access executable", e);
		}

		return cli;
	}

	private void generateXMLAccessScript() throws MojoExecutionException {
		if (null == getXMLAccessScriptPair()) {
			// Skip generate XML access script part if no XMLAccessScriptPair
			// type specified.
			return;
		}
		try {
			String scriptFtl = getXMLAccessScriptTemplate();
			String script = getXMLAccessScript();

			Writer writer = new OutputStreamWriter(new FileOutputStream(script));
			InputStream template = getEnvAssetAsStream(scriptFtl);
			Map<Object, Object> nameValuePairs = getXMLAccessScriptNameValuePairs();
			FreemarkerHelper.process(template, nameValuePairs, writer);
			log.info("Generating XMLAccess Script:" + script);
			log.debug(IOUtils.toString(new FileInputStream(script)));
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(
					"Fail to generate the xmlaccess scripts", e);
		}
	}

	/**
	 * To add or replace freemarker NameValuePairs in xmlacess batch file
	 * 
	 * @since 1.0.3
	 */
	protected Map<Object, Object> getCustomXMLAccessCliNameValuePairs() {
		return null;
	}

	public WPS getWps() {
		return wps;
	}

	public File getXMLAccessOutputFile() {
		File outputFile = null;
		try {
			outputFile = new File(workingDirectory.getCanonicalPath()
					+ File.separator + Constants.XMLACCESS_OUTPUT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFile;
	}

	public String getXMLAccessScript() {
		return workingDirectory.getAbsoluteFile() + File.separator
				+ getXMLAccessScriptPair().getScript();
	}

	protected abstract Map<Object, Object> getXMLAccessScriptNameValuePairs()
			throws MojoExecutionException;

	protected abstract ScriptTemplatePair getXMLAccessScriptPair();

	private String getXMLAccessScriptTemplate() {
		return getXMLAccessScriptPair().getTemplate();
	}

	private void init() {
		if (null == wps) {
			wps = new WPS(getServer(server));
		}
	}

	protected void postAction() throws MojoExecutionException,
			MojoFailureException {

	}

	protected void preAction() throws MojoExecutionException,
			MojoFailureException {

	}

	public void setServer(String server) {
		this.server = server;
	}

}
