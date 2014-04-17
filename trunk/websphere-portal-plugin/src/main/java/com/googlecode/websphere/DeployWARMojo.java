package com.googlecode.websphere;

import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.googlecode.websphere.model.ScriptTemplatePair;

/**
 * 
 * The deploy-war task enables you to install a Web Application into a WebSphere
 * Server or Cell.This task is a wrapper for the AdminApp.install() command of
 * the wsadmin tool. Refer to the wsadmin documentation for information on the
 * valid options available during application installation. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Build and Deploy a WAR <br/>
 * 
 * 
 * <i>mvn clean package websphere:deploy-war -Dserver=was_box1
 * -DcontextRoot=/wpsp/themes/HomePageTheme
 * -DwasHome=C:/IBM/WebSphere/AppServer</i> <br/>
 * 
 * 
 * 2. Deploy an existing WAR <br/>
 * 
 * 
 * <i>mvn clean websphere:deploy-war -Dserver=was_box1
 * -DcontextRoot=/wpsp/themes/HomePageTheme -DwasHome=C:/IBM/WebSphere/AppServer
 * -DinstallableApp=C:/dist/theme1.war</i> <br/>
 * 
 * 
 * 3. Deploy a WAR from Nexus <br/>
 * 
 * 
 * <i>mvn clean websphere:deploy-war -Dserver=was_box1 -DinstallableApp=http
 * ://stype-nexus:8081/nexus/content/repositories/releases
 * /com/xyz/theme1/9.34.87/theme1-9.34.87.war</i> <br/>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "deploy-war", threadSafe = true)
public class DeployWARMojo extends DeployEARMojo {

	/**
	 * The contextroot option specifies the context root that you use when
	 * installing a stand-alone web application archive (WAR) file.
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "contextRoot", required = true)
	private String contextRoot;

	private static final String FTL_CONTEXTROOT = "contextRoot";

	public String getContextRoot() {
		return contextRoot;
	}

	@Override
	protected ScriptTemplatePair getScriptTemplatePair() {
		return ScriptTemplatePair.DEPLOY_WAR;
	}

	@Override
	protected Map<Object, Object> getWSAntScriptNameValuePairs()
			throws MojoExecutionException {
		Map<Object, Object> nameValuePairs = super
				.getWSAntScriptNameValuePairs();

		nameValuePairs.put(FTL_CONTEXTROOT, getContextRoot());

		return nameValuePairs;
	}

}
