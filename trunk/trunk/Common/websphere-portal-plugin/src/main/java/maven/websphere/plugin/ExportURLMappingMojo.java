package maven.websphere.plugin;

import java.util.HashMap;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * The export-urlmapping task enables you to export the WebSphere urlmapping
 * based on Uniquename or label. This task is a wrapper for the XMLAccess
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
 * 1. Export URL Mapping by uniqueName <br/>
 * 
 * 
 * <i>mvn clean websphere:urlmapping -Dserver=was_box1
 * -DuniqueName=com.xyz.urlmapping.friend1</i> <br/>
 * 
 * 
 * 2. Export URL Mapping by label <br/>
 * 
 * 
 * <i>mvn clean websphere:urlmapping -Dserver=was_box1 -Dlabel=friend1</i> <br/>
 * 
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "export-urlmapping", threadSafe = true)
public class ExportURLMappingMojo extends AbstractExportMojo {
	/**
	 * Unique name of URL mapping
	 * 
	 * @parameter property="${uniqueName}"
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName")
	private String uniqueName;
	/**
	 * url mapping lable
	 * 
	 * @parameter property="${label}"
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "label")
	private String label;

	private static final String FTL_UNIQUE_NAME = "unique_name";
	private static final String FTL_LABEL = "label";

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs() {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();

		if (StringUtils.isNotBlank(uniqueName)) {
			nameValuePairs.put(FTL_UNIQUE_NAME, uniqueName);
		}
		if (StringUtils.isNotBlank(label)) {
			nameValuePairs.put(FTL_LABEL, label);
		}
		return nameValuePairs;
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		return ScriptTemplatePair.EXPORT_URLMAPPING;
	}

	@Override
	protected void preAction() throws MojoExecutionException,
			MojoFailureException {
		if (StringUtils.isBlank(label + uniqueName)) {
			throw new MojoExecutionException(
					"Please specify at least one of uniqueName or label of the URL Mapping!");
		}
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

}
