package com.googlecode.websphere;

import static com.googlecode.websphere.utils.Constants.COMMA;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * 
 * The deploy-multi-portlet task enables you to install multiable portlets into
 * a WebSphere Portal Server. This task is a wrapper for the XMLAccess command
 * of the WebSphere Portal. Refer to the WebSphere Portal documentation for more
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
 * 1. Deploy multi portlets: <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-multi-portlet -Dserver=was_box1
 * -DinstallableApps=C:/dist/portlet1.war,C:/dist/portlet2.war</i> <br>
 * 
 * 
 * 2. Deploy multi portlets with customer uniqueNames, with comma seperated
 * uniqueNames args: <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-multi-portlet -Dserver=was_box1
 * -DinstallableApps=C:/dist/portlet1.war,C:/dist/portlet2.war
 * -DuniqueNames=com.xyz.plt1,com.xyz.plt1</i> <br>
 * 
 * 
 * 3. Deploy multi portlets from Nexus <br>
 * 
 * 
 * <i>mvn clean websphere:deploy-multi-portlet -Dserver=was_box1
 * -DinstallableApps
 * =http://stype-nexus:8081/nexus/content/repositories/releases /com/xyz/ptl1
 * /9.34.87/ptl1-9.34.87.war,http://stype-nexus:8081/nexus/content
 * /repositories/releases/com/xyz/ptl2/9.34.87/ptl2-9.34.87.war</i> <br>
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
@Mojo(name = "deploy-multi-portlet", threadSafe = true)
public class DeployMultiPortletMojo extends
		AbstractMultiDeployMojo<DeployPortletMojo> {
	/**
	 * Portlets unique names separated by comma
	 * 
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueNames")
	private String uniqueNames;

	private boolean validInput = false;

	@Override
	protected void checkBeforeExecute(DeployPortletMojo mojo)
			throws MojoExecutionException, MojoFailureException {
		if (StringUtils.isNotBlank(uniqueNames)
				&& !validInput
				&& StringUtils.split(uniqueNames, COMMA).length != StringUtils
						.split(installableApps, COMMA).length) {
			throw new MojoExecutionException(
					"installableApps arguments is not pair with uniqueName argument, please verify!");

		} else {
			validInput = true;
		}

		String app = mojo.getInstallableApp();
		mojo.setUniqueName(StringUtils.split(uniqueNames, COMMA)[ArrayUtils
				.indexOf(StringUtils.split(installableApps, COMMA), app)]);
	}

	@Override
	protected DeployPortletMojo getDeployMojo() {
		DeployPortletMojo mojo = getSiblingPluginMojo("deploy-portlet");
		return mojo;
	}
}
