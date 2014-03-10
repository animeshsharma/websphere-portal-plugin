package maven.websphere.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * The migrate-urlmapping task enables you to migrate XMLAccess Urlmapping XML
 * from source WPS server to target WPS server. This task is a wrapper for the
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
 * 1. Migrate urlmapping from was_box1 to was_box2 by uniqueName <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-urlmapping -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueName=com.xyz.urlmapping.friend1</i> <br/>
 * 
 * 
 * 2. Migrate URL Mapping by label <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-urlmapping -DsourceServer=was_box1
 * -DtargetServer=was_box2 -Dlabel=friend1</i> <br/>
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 * 
 */
@Mojo(name = "migrate-urlmapping", threadSafe = true)
public class MigrateURLMappingMojo extends
		AbstractMigrateMojo<ExportURLMappingMojo> {

	/**
	 * Page unique name or Portlet uid or url mapping lable
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName")
	private String uniqueName;
	/**
	 * Page unique name or Portlet uid or url mapping lable
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "label")
	private String label;

	@Override
	protected ExportURLMappingMojo getExportMojo() {
		ExportURLMappingMojo exportMojo = getSiblingPluginMojo("export-urlmapping");
		exportMojo.setUniqueName(uniqueName);
		exportMojo.setLabel(label);
		return exportMojo;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

}
