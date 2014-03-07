package maven.websphere.plugin;

import java.util.HashMap;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * The export-page task enables you to export the WebSphere Portal Page based on
 * UniqueName. This task is a wrapper for the XMLAccess command of the WebSphere
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
 * 1. Export Page <br/>
 * 
 * 
 * <i>mvn clean websphere:export-page -Dserver=was_box1
 * -DuniqueName=com.xyz.page.home1</i> <br/>
 * 
 * 
 * 2. Export Page without Children <br/>
 * 
 * 
 * <i>mvn clean websphere:export-page -Dserver=was_box1
 * -DuniqueName=com.xyz.page.home1 -Drecursion=false</i> <br/>
 * 
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "export-page", threadSafe = true)
public class ExportPageMojo extends AbstractExportMojo {
	/**
	 * Unique name of the Page to be exported
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName", required = true)
	private String uniqueName;

	/**
	 * page recursion for child pages
	 * 
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "recursion", defaultValue = "true")
	private String recursion;

	private static final String FTL_PAGE_UNIQUE_NAME = "unique_name";

	private static final String FTL_RECURSION = "is_recursion";

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs() {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();
		nameValuePairs.put(FTL_PAGE_UNIQUE_NAME, uniqueName);
		nameValuePairs.put(FTL_RECURSION, recursion);
		return nameValuePairs;
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		return ScriptTemplatePair.EXPORT_PAGE;
	}

	public void setRecursion(String recursion) {
		this.recursion = recursion;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

}
