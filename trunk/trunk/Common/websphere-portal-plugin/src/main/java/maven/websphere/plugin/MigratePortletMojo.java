package maven.websphere.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * The migrate-portlet task enables you to migrate XMLAccess Portlet XML(s) from
 * source WPS server to target WPS server. This task is a wrapper for the
 * XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal
 * documentation for more information. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Migrate single portlet from was_box1 to was_box2 <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-portlet -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueName=com.xyz.ptlt1</i> <br/>
 * 
 * 
 * 2. Migrate multi portlets from was_box1 to was_box2 <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-portlet -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueNames=com.xyz.ptlt1,com.xyz.ptlt2</i> <br/>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 * 
 */
@Mojo(name = "migrate-portlet", threadSafe = true)
public class MigratePortletMojo extends AbstractMigrateMojo<ExportPortletMojo> {

	/**
	 * Portlet web module unique name
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName")
	private String uniqueName;
	/**
	 * Portlet web module unique names, comma separated string
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueNames")
	private String uniqueNames;

	@Override
	protected ExportPortletMojo getExportMojo() {
		ExportPortletMojo exportMojo = getSiblingPluginMojo("export-portlet");
		exportMojo.setUniqueName(uniqueName);
		exportMojo.setUniqueNames(uniqueNames);
		return exportMojo;
	}

}
