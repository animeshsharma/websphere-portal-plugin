package maven.websphere.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;
import maven.websphere.plugin.utils.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * 
 * The export-portlet task enables you to export the WebSphere portlet(s) based
 * on UniqueName(s). This task is a wrapper for the XMLAccess command of the
 * WebSphere Portal. Refer to the WebSphere Portal documentation for more
 * information. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Export single portlet <br/>
 * 
 * 
 * <i>mvn clean websphere:export-portlet -Dserver=was_box1
 * -DuniqueName=com.xyz.ptlt1</i> <br/>
 * 
 * 
 * 2. Export multi portlets <br/>
 * 
 * 
 * <i>mvn clean websphere:export-portlet -Dserver=was_box1
 * -DuniqueName=com.xyz.ptlt1,com.xyz.ptlt2</i> <br/>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 * 
 */
@Mojo(name = "export-portlet", threadSafe = true)
public class ExportPortletMojo extends AbstractExportMojo {
	/**
	 * Unique name of the Portlet to be exported
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueName")
	private String uniqueName;

	/**
	 * Comma separated uniqueNames of Portlets to be exported
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "uniqueNames")
	private String uniqueNames;

	private static final String FTL_PORTLET_UNIQUE_NAMES = "unique_names";

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs() {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();
		List<String> unames = new ArrayList<String>();

		if (StringUtils.isNotBlank(uniqueNames)) {
			unames.addAll(Arrays.asList(StringUtils.split(uniqueNames,
					Constants.COMMA)));
		}
		if (StringUtils.isNotBlank(uniqueName) && !unames.contains(uniqueName)) {
			unames.add(uniqueName);
		}

		nameValuePairs.put(FTL_PORTLET_UNIQUE_NAMES, unames);
		return nameValuePairs;
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		return ScriptTemplatePair.EXPORT_PORTLET;
	}

	@Override
	protected void preAction() throws MojoExecutionException,
			MojoFailureException {
		if (StringUtils.isBlank(uniqueName + uniqueNames))
			throw new MojoExecutionException(
					"Please specify either uniqueName or uniqueNames!");
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public void setUniqueNames(String uniqueNames) {
		this.uniqueNames = uniqueNames;
	}

}
