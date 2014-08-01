package com.googlecode.websphere;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * The migrate-theme task enables you to migrate XMLAccess theme XML from source
 * WPS server to target WPS server. This task is a wrapper for the XMLAccess
 * command of the WebSphere Portal. Refer to the WebSphere Portal documentation
 * for more information. <br>
 * 
 * 
 * <br>
 * <br>
 * 
 * 
 * <b>Usages:</b> <br>
 * 
 * 
 * 1. Migrate Theme XML from was_box1 to was_box2 <br>
 * 
 * 
 * <i>mvn clean websphere:migrate-theme -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueName=com.xyz.theme1</i> <br>
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 * 
 */
@Mojo(name = "migrate-theme", threadSafe = true)
public class MigrateThemeMojo extends AbstractMigrateMojo<ExportThemeMojo> {

	/**
	 * Page unique name or Portlet uid or url mapping lable
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName", required = true)
	private String uniqueName;

	@Override
	protected ExportThemeMojo getExportMojo() {
		ExportThemeMojo exportMojo = getSiblingPluginMojo("export-theme");
		exportMojo.setUniqueName(uniqueName);
		return exportMojo;
	}
}
