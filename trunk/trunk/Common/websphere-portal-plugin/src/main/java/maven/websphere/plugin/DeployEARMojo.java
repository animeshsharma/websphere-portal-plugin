package maven.websphere.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;
import maven.websphere.plugin.utils.FileHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * The deploy-ear task enables you to install a new application into a WebSphere
 * Server or Cell.This task is a wrapper for the AdminApp.install() command of
 * the wsadmin tool. Refer to the wsadmin documentation for information on the
 * valid options available during application installation.
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Build and Deploy EAR Application <br/>
 * 
 * 
 * <i>mvn clean package websphere:deploy-ear -Dserver=was_box1
 * -DwasHome=C:\IBM\WebSphere\AppServer</i> <br/>
 * 
 * 
 * 2. Deploy an existing EAR Application <br/>
 * 
 * 
 * <i>mvn clean websphere:deploy-ear -Dserver=was_box1
 * -DwasHome=C:\IBM\WebSphere\AppServer
 * -DinstallableApp=C:\dist\BusinessDomainServices.ear<o :p></o:p></i> <br/>
 * 
 * 
 * 3. Deploy an EAR Application from Nexus <br/>
 * 
 * 
 * <i>mvn clean websphere:deploy-ear -Dserver=was_box1
 * -DwasHome=C:\IBM\WebSphere\AppServer -DinstallableApp
 * =http://stype-nexus:8081/nexus/content/repositories/releases
 * /com/xyz/BusinessDomainServices /9.34.87/BusinessDomainServices-9.34.87.ear
 * -DappName=BusinessDomainServices</i> <br/>
 * 
 * 
 * 
 * <br/>
 * <br/>
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */

@Mojo(name = "deploy-ear", threadSafe = true)
public class DeployEARMojo extends AbstractWSAntMojo {
	/**
	 * Name of the application, default value is file name or artifact name if
	 * deploy the application from Nexus repository *
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "appName")
	private String appName;

	/**
	 * Application File for installation
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "installableApp", defaultValue = "${project.build.directory}/${project.build.finalName}.${project.packaging}")
	private String installableApp;

	private File localFile;

	private static final String FTL_APPNAME = "appName";

	private static final String FTL_PROFILE = "profile";

	private static final String FTL_NODE = "node";
	private static final String FTL_CELL = "cell";
	private static final String FTL_INSTALLABLEAPP = "installableApp";
	private static final String FTL_WAS_CONNTYPE = "was_conntype";
	private static final String FTL_WAS_HOST = "was_host";
	private static final String FTL_WAS_PORT = "was_port";
	private static final String FTL_WAS_USERNAME = "was_username";
	private static final String FTL_WAS_PASSWORD = "was_password";

	public String getAppName() {
		if (StringUtils.isBlank(appName)) {
			appName = FileHelper.guessFileName(installableApp);
			int dotIdx = StringUtils.lastIndexOf(appName, ".");
			if (dotIdx > 0) {
				appName = StringUtils.substring(appName, 0, dotIdx);
			}
		}

		return appName;

	}

	@Override
	protected ScriptTemplatePair getScriptTemplatePair() {
		return ScriptTemplatePair.DEPLOY_EAR;
	}

	@Override
	protected Map<Object, Object> getWSAntScriptNameValuePairs()
			throws MojoExecutionException {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();

		// Download installable file to temp file or prepare local file
		localFile = prepareLocalFile(installableApp);
		nameValuePairs.put(FTL_INSTALLABLEAPP, localFile.getPath());
		nameValuePairs.put(FTL_WAS_CONNTYPE, getWas().getConntype());
		nameValuePairs.put(FTL_WAS_HOST, getWas().getHost());
		nameValuePairs.put(FTL_WAS_PORT, getWas().getPort());
		nameValuePairs.put(FTL_WAS_USERNAME, getWas().getWasUsername());
		nameValuePairs.put(FTL_WAS_PASSWORD, getWas().getWasPassword());
		nameValuePairs.put(FTL_PROFILE, getWas().getProfile());
		nameValuePairs.put(FTL_NODE, getWas().getNode());
		nameValuePairs.put(FTL_CELL, getWas().getCell());

		nameValuePairs.put(FTL_APPNAME, getAppName());

		return nameValuePairs;
	}

	@Override
	protected void validate() throws MojoExecutionException {
		if (StringUtils.isBlank(getServer()))
			throw new MojoExecutionException(
					"Please specify the target server by -Dserver=xxxx");
	}
}
