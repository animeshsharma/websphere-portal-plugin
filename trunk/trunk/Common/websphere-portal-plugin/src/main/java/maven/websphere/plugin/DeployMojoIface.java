package maven.websphere.plugin;

import org.apache.maven.plugin.Mojo;

/**
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public interface DeployMojoIface extends Mojo {
	public void setInstallableApp(String installableApp);

	public void setServer(String server);

}
