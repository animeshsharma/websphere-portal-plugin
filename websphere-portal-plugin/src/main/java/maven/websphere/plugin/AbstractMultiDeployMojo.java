package maven.websphere.plugin;

import static maven.websphere.plugin.utils.Constants.COMMA;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
public abstract class AbstractMultiDeployMojo<DeployMojo extends DeployMojoIface>
		extends AbstractWebsphereMojo {

	/**
	 * Comma separated paths of installable application
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "installableApps", required = true)
	protected String installableApps;
	/**
	 * WebSphere portal server profile id, which is configured in Maven
	 * Settings.xml
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "server", required = true)
	private String server;

	protected void checkBeforeExecute(DeployMojo mojo)
			throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doExecute() throws MojoExecutionException,
			MojoFailureException {

		String[] apps = StringUtils.split(installableApps, COMMA);
		for (String app : apps) {
			DeployMojo mojo = getDeployMojo();
			mojo.setInstallableApp(app);
			mojo.setServer(server);
			checkBeforeExecute(mojo);
			mojo.execute();
		}
	}

	protected abstract DeployMojo getDeployMojo();

}
