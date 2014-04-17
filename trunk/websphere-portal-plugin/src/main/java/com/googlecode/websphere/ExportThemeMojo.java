package com.googlecode.websphere;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.googlecode.websphere.model.ScriptTemplatePair;

/**
 * 
 * 
 * The export-theme task enables you to export the WebSphere Theme based on
 * Theme. This task is a wrapper for the XMLAccess command of the WebSphere
 * Portal. Refer to the WebSphere Portal documentation for more information. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Export theme <br/>
 * 
 * 
 * <i>mvn clean websphere:export-theme -Dserver=was_box1
 * -DuniqueName=com.xyz.theme1</i> <br/>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "export-theme", threadSafe = true)
public class ExportThemeMojo extends AbstractExportMojo {
	/**
	 * Unique name of the Theme to be exported
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName", required = true)
	private String uniqueName;

	private static final String FTL_PORTLET_UNIQUE_NAME = "unique_name";

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs() {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();
		nameValuePairs.put(FTL_PORTLET_UNIQUE_NAME, uniqueName);
		return nameValuePairs;
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		return ScriptTemplatePair.EXPORT_THEME;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

}
