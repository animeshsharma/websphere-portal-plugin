package com.googlecode.websphere;

import static com.googlecode.websphere.utils.Constants.SLASH;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.googlecode.websphere.model.ScriptTemplatePair;
import com.googlecode.websphere.utils.Constants;
import com.googlecode.websphere.utils.FileHelper;
import com.googlecode.websphere.utils.FtpWrapper;
import com.googlecode.websphere.utils.PortletWarInfoExtractor;

/**
 * 
 * 
 * The deploy-portlet task enables you to install portlet into a WebSphere
 * Portal Server. This task is a wrapper for the XMLAccess command of the
 * WebSphere Portal. Refer to the WebSphere Portal documentation for more
 * information. <br>
 * 
 * 
 * <br>
 * <br>
 * 
 * 
 * <b>Usages:</b> <br>
 * 
 * 
 * 1. Build and deploy a portlet <br>
 * 
 * 
 * <i>mvn clean package websphere:deploy-portlet -Dserver=was_box1</i> <br>
 * 
 * 
 * 2. Deploy an existing portlet: <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-portlet -Dserver=was_box1
 * -DinstallableApp=C:/dist/portlet1.war</i> <br>
 * 
 * 
 * 3. Deploy portlet with customer uniqueNames <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-portlet -Dserver=was_box1
 * -DinstallableApp=C:/dist/portlet1.war -DuniqueNames=com.xyz.plt1</i> <br>
 * 
 * 
 * 4. Deploy portlet from Nexus <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-portlet -Dserver=was_box1 -DinstallableApp
 * =http://stype-nexus:8081/nexus/content/repositories/releases
 * /com/xyz/ptl1/9.34.87/ptl1-9.34.87.war</i> <br>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
@Mojo(name = "deploy-portlet", threadSafe = true)
public class DeployPortletMojo extends AbstractXMLAccessMojo implements
		DeployMojoIface {
	private static final String FTL_INSTALLABLEAPP = "installableApp";
	private static final String FTL_PORTLET_UNIQUE_NAME = "unique_name";

	/**
	 * Path of installable application
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "installableApp", defaultValue = "${project.build.directory}/${project.build.finalName}.${project.packaging}")
	private String installableApp;

	/**
	 * Page unique name or Portlet uid or url mapping lable
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName")
	private String uniqueName;

	private File localFile;

	private String remoteFile;

	@Override
	protected void cleanUp() throws MojoExecutionException {
		// Cleanup FTP artifacts
		if (null != remoteFile) {
			getLog().info("Remove ftp temp file:" + remoteFile);
			FtpWrapper ftp = new FtpWrapper(getWas());
			ftp.removeDirectory(StringUtils.substring(remoteFile, 0,
					StringUtils.lastIndexOf(remoteFile, SLASH)));
		}
		// Cleanup local temp file
		if (isTmpFile) {
			getLog().info("Remove temp file:" + localFile.getPath());
			FileUtils.deleteQuietly(localFile);
		}
	}

	private String createTempRemoteFile() {
		String fileName = null;

		if (StringUtils.isNotBlank(installableApp)) {
			fileName = FileHelper.guessFileName(installableApp);
		} else {
			fileName = getProject().getArtifactId() + "."
					+ getProject().getPackaging();
		}

		return getWas().createTmpDirInUserhome() + fileName;
	}

	public String getInstallableApp() {
		return installableApp;
	}

	private String getInstallableAppPath() throws MojoExecutionException {
		String path = null;
		if (StringUtils.equals(getMode(), Constants.MODE_REMOTE)) {
			// Upload Portlet war to FTP
			remoteFile = createTempRemoteFile();
			putToFTP(getWas(), localFile, remoteFile);
			path = "file://"+remoteFile;
		} else {
			try {
				path = localFile.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				throw new MojoExecutionException(
						"Installable File not found : " + localFile.getPath(),
						e);
			}
		}
		return path;
	}

	private Properties getPortletInfoProperties() {
		InputStream in = null;
		Properties prop = new Properties();
		try {
			prop.load(FileHelper.findSingleFileAsStream(getAssetDirectory(),
					Constants.PROP_FILE_PORTLET_INFO));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return prop;
	}

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs()
			throws MojoExecutionException {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();

		// Download installable file to temp file or prepare local file
		localFile = prepareLocalFile(installableApp);
		nameValuePairs.put(FTL_INSTALLABLEAPP, getInstallableAppPath());

		if (StringUtils.isNotBlank(uniqueName)) {
			nameValuePairs.put(FTL_PORTLET_UNIQUE_NAME, uniqueName);
		}
		nameValuePairs.putAll(PortletWarInfoExtractor.getInfo(localFile,
				getPortletInfoProperties()));

		return nameValuePairs;
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		return ScriptTemplatePair.DEPLOY_PORTLET;
	}

	@Override
	public void setInstallableApp(String installableApp) {
		this.installableApp = installableApp;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

}
