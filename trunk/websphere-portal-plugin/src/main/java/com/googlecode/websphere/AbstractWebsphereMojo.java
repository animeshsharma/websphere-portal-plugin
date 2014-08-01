package com.googlecode.websphere;

import static com.googlecode.websphere.utils.Constants.SLASH;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.apache.maven.wagon.Wagon;
import org.codehaus.mojo.wagon.shared.WagonUtils;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.resource.PlexusResource;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.ResourceNotFoundException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcher;

import com.googlecode.websphere.cli.ErrorConsumer;
import com.googlecode.websphere.cli.InfoConsumer;
import com.googlecode.websphere.model.WAS;
import com.googlecode.websphere.utils.Constants;
import com.googlecode.websphere.utils.FileHelper;
import com.googlecode.websphere.utils.FtpWrapper;
import com.googlecode.websphere.utils.ResourceUtils;

/**
 * Accommodate MAVEN attribute, and common method which needs MAVEN attributes
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
public abstract class AbstractWebsphereMojo extends AbstractMojo {
	/**
	 * WebSphere server profile id, which is configured in Maven Settings.xml
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "server", required = true)
	private String server;

	private WAS was;

	/**
	 * maven websphere plugin working mode, options of remote/local,
	 * default-value is remote
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "mode", defaultValue = "remote")
	private String mode;

	/**
	 * MAVEN Project.
	 */
	@Component
	protected MavenProject project;

	/**
	 * The current user system settings for use in Maven.
	 * 
	 */
	@Component
	protected Settings settings;

	/**
	 * When this plugin requires Maven 3.0 as minimum, this component can be
	 * removed and o.a.m.s.c.SettingsDecrypter be used instead.
	 */
	@Component
	protected SecDispatcher secDispatcher;

	@Component
	protected ResourceManager resourceManager;

	@Component
	protected WagonManager wagonManager;

	@Component
	private PlexusContainer plexusContainer;

	protected File workingDirectory;

	protected final Log log = getLog();

	protected boolean isTmpFile;

	protected Wagon createWagon(String serverName, String url)
			throws MojoExecutionException {
		try {
			return WagonUtils.createWagon(serverName, url, wagonManager,
					settings, getLog());
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Unable to create a Wagon instance for " + url, e);
		}
	}

	protected abstract void doExecute() throws MojoExecutionException,
			MojoFailureException;

	private void evalMojo(AbstractWebsphereMojo targetMojo) {
		targetMojo.setProject(project);
		targetMojo.setResourceManager(resourceManager);
		targetMojo.setSecDispatcher(secDispatcher);
		targetMojo.setSettings(settings);
		targetMojo.setWagonManager(wagonManager);
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		init();
		doExecute();
	}

	protected void executeCommandLine(Commandline cli)
			throws MojoExecutionException {
		try {
			getLog().info("Execute Commandline: " + cli.toString());
			int returncode = CommandLineUtils.executeCommandLine(cli,
					new InfoConsumer(getLog()), new ErrorConsumer(getLog()));

			if (0 != returncode) {
				throw new MojoExecutionException(
						"Exceute command line returns error code:["
								+ returncode + "] " + cli.toString());
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException("Fail to execute command line:"
					+ cli.toString(), e);
		}
	}

	private void extractAssets() throws MojoExecutionException {
		File assetsDir = getAssetDirectory();
		if (!assetsDir.exists()) {
			try {
				FileUtils.forceMkdir(assetsDir);

				PlexusResource shared = resourceManager.getResource("shared");
				ResourceUtils.copyJarResourceToDirectory(resourceManager,
						shared.getURL().toString(), assetsDir.getPath());

				if(null!=getWas()){
					PlexusResource asset = resourceManager.getResource(getWas()
							.getVersion() + SLASH + getMode());
					ResourceUtils.copyJarResourceToDirectory(resourceManager, asset
							.getURL().toString(), assetsDir.getPath());
				}
			} catch (ResourceNotFoundException e) {
				getLog().error(e);
				throw new MojoExecutionException(
						String.format(
								"The WebSphere Version and Mode specified is not found! wpsVerion:%s, mode:%s",
								getWas().getVersion(), getMode()));
			} catch (Exception e) {
				getLog().error(e);
				throw new MojoExecutionException(String.format(
						"Failed to copy assets to working folder[%s]!",
						assetsDir));
			}
		}
	}

	public File getAssetDirectory() {
		return new File(workingDirectory + File.separator + Constants.DIR_ASSET);
	}

	protected InputStream getEnvAssetAsStream(final String assetName)
			throws MojoExecutionException {
		InputStream fis = null;
		File assetDir = getAssetDirectory();
		try {
			fis = FileHelper.findSingleFileAsStream(assetDir, assetName);
		} catch (FileNotFoundException e) {
			getLog().error(e);
			throw new MojoExecutionException(String.format(
					"Can't locate file:%s/%s", assetDir, assetName));
		}

		return fis;
	}

	protected File getEnvironmentAssetDirectory() {

		return workingDirectory;
	};

	public String getMode() {
		return mode;
	}

	public PlexusContainer getPlexusContainer() {
		return plexusContainer;
	}

	public MavenProject getProject() {
		return project;
	}

	public String getServer() {
		return server;
	}

	protected final Server getServer(String server) {
		return settings.getServer(server);
	}

	@SuppressWarnings("unchecked")
	protected <T extends AbstractWebsphereMojo> T getSiblingPluginMojo(
			String goal) {
		T p = null;
		String hint = null;

		// locate plugin artifact
		Set<Artifact> plugins = getProject().getPluginArtifacts();
		Artifact plugin = null;
		for (Artifact item : plugins) {
			if (Constants.MAVEN_WEBSPHERE_PLUGIN.equals(item.getArtifactId())) {
				plugin = item;
				break;
			}
		}

		// construct mojo hint
		if (null != plugin) {
			hint = plugin.getGroupId() + ":" + plugin.getArtifactId() + ":"
					+ plugin.getBaseVersion() + ":" + goal;
		}

		if (StringUtils.isBlank(hint)) {
			Plugin pmp = null;

			List<Plugin> pmPlugins = getProject().getPluginManagement()
					.getPlugins();
			for (Plugin item : pmPlugins) {
				if (Constants.MAVEN_WEBSPHERE_PLUGIN.equals(item
						.getArtifactId())) {
					pmp = item;
					break;
				}
			}

			if (null != pmp) {
				hint = pmp.getGroupId() + ":" + pmp.getArtifactId() + ":"
						+ pmp.getVersion() + ":" + goal;
			}

		}

		try {
			p = (T) plexusContainer.lookup(org.apache.maven.plugin.Mojo.class,
					hint);
		} catch (ComponentLookupException e) {
			e.printStackTrace();
		}

		evalMojo(p);
		return p;
	}

	public WAS getWas() {
		return was;
	}

	// public String getWpsVersion() {
	// return wpsVersion;
	// }

	private void init() throws MojoExecutionException {
		prepareWorkingDirectory();
		if (null == was && StringUtils.isNotBlank(server)) {
			was = new WAS(getServer(server));
		}
		extractAssets();
	}

	public void setServer(String server) {
		this.server = server;
	}

	protected File prepareLocalFile(String installableApp)
			throws MojoExecutionException {
		File localFile = null;
		getLog().info("Retrieve file:" + installableApp);
		try {
			localFile = new File(installableApp);
			if (!localFile.exists()) {
				localFile = File.createTempFile(
						Constants.MAVEN_WEBSPHERE_PLUGIN,
						Constants.TEMP_FILE_SUFFIX);
				String releaseServerName = getProject()
						.getDistributionManagement().getRepository().getId();
				String url = StringUtils.substring(installableApp, 0,
						installableApp.lastIndexOf(SLASH));
				String fileName = StringUtils.remove(installableApp, url
						+ SLASH);
				Wagon getWagon = createWagon(releaseServerName, url);
				getWagon.get(fileName, localFile);
				isTmpFile = true;
			}
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Can't resolve installableApp file:" + installableApp, e);
		}
		return localFile;
	}

	private void prepareWorkingDirectory() throws MojoExecutionException {
		workingDirectory = new File(project.getBuild().getDirectory()
				+ File.separator + Constants.MAVEN_WEBSPHERE_PLUGIN);
		try {
			if (!workingDirectory.exists()) {
				FileUtils.forceMkdir(workingDirectory);
			}
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Fail to prepare working directory:"
							+ workingDirectory.getPath(), e);
		}
	}

	private void publicReadPermission(WAS was, String remoteFile)
			throws MojoExecutionException {
		FtpWrapper fw = new FtpWrapper(was);
		String[] ss = StringUtils.split(remoteFile, Constants.SLASH);
		String file = Constants.SLASH;
		for (String s : ss) {
			file = file + s + Constants.SLASH;
			fw.setPermission(file, "755");
		}
	}

	protected void putToFTP(WAS was, File localFile, String remoteFile)
			throws MojoExecutionException {
		try {
			Wagon wagon = createWagon(was.getServerName(), was.getFtpURL());
			getLog().info("Uploading: [" + localFile + "] to " + remoteFile);
			wagon.put(localFile, remoteFile);
			// Set permission
			publicReadPermission(was, remoteFile);

		} catch (Exception e) {
			throw new MojoExecutionException("Fail to upload file to FTP:"
					+ was.getServerName(), e);
		}
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public void setSecDispatcher(SecDispatcher secDispatcher) {
		this.secDispatcher = secDispatcher;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void setWagonManager(WagonManager wagonManager) {
		this.wagonManager = wagonManager;
	}
}
