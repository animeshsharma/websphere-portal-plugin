package maven.websphere.plugin.tester;

/**
 * Plugin Interface defines all the tasks that can be performed with maven-websphere-plugin. 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
public interface WebspherePluginTesterIface {
	public void deployEarFromSource() throws Exception;

	public void deployEarFromNexus() throws Exception;

	public void deployPortletFromSource() throws Exception;

	public void deployPortletFromNexus() throws Exception;

	public void deployWarFromSource() throws Exception;

	public void deployWarFromNexus() throws Exception;

	public void deployThemeFromSource() throws Exception;

	public void deployThemeFromSourceFromNexus() throws Exception;

	public void exportPage() throws Exception;

	public void exportPortlet() throws Exception;

	public void exportURLMapping() throws Exception;

	public void migratePage() throws Exception;

	public void migratePortlet() throws Exception;

	public void migrateMapping() throws Exception;

	public void importXML() throws Exception;
}
