package com.googlecode.websphere;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * The migrate-page task enables you to migrate XMLAccess Page XML from source
 * WPS server to target WPS server. This task is a wrapper for the XMLAccess
 * command of the WebSphere Portal. Refer to the WebSphere Portal documentation
 * for more information. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Migrate a specified page from was_box1 to was_box2 <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-page -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueName=com.xyz.page.home1</i> <br/>
 * 
 * 
 * 2. Migrate a specified page from was_box1 to was_box2 without child pages <br/>
 * 
 * 
 * <i>mvn clean websphere:migrate-page -DsourceServer=was_box1
 * -DtargetServer=was_box2 -DuniqueName=com.xyz.page.home1 -Drecursion=false</i> <br/>
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 * 
 */
@Mojo(name = "migrate-page", threadSafe = true)
public class MigratePageMojo extends AbstractMigrateMojo<ExportPageMojo> {

	/**
	 * Unique name of the Page to be migrated
	 * 
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName", required = true)
	private String uniqueName;

	/**
	 * page recursion for child pages
	 * 
	 * @parameter property="${recursion}" default-value=true
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "recursion", defaultValue = "true")
	private String recursion;

	@Override
	protected ExportPageMojo getExportMojo() {
		ExportPageMojo exportMojo = getSiblingPluginMojo("export-page");
		exportMojo.setUniqueName(uniqueName);
		exportMojo.setRecursion(recursion);
		return exportMojo;
	}

}
