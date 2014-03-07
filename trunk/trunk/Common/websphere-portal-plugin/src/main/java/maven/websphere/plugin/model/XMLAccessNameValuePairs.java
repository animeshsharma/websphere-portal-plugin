package maven.websphere.plugin.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import maven.websphere.plugin.AbstractXMLAccessMojo;
import maven.websphere.plugin.utils.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * Mapping the name/values for FTL params and settings.xml.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class XMLAccessNameValuePairs {
	private static final String FTL_VAL_JAVA_HOME = "java_home";
	private static final String FTL_VAL_XMLACCESS_SCRIPT = "xmlaccess_script";
	private static final String FTL_VAL_WPS_CONFIG_URL = "wps_config_url";
	private static final String FTL_VAL_WPS_CONFIG_USERNAME = "wps_config_username";
	private static final String FTL_VAL_WPS_CONFIG_PASSWORD = "wps_config_password";
	private static final String FTL_VAL_XMLACCESS_OUTPUT = "xmlaccess_output";

	public static Map<Object, Object> asMap(AbstractXMLAccessMojo mojo) {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();
		nameValuePairs.put(FTL_VAL_JAVA_HOME, findJavaHome(mojo));
		nameValuePairs
				.put(FTL_VAL_WPS_CONFIG_URL, mojo.getWps().getConfigURL());
		nameValuePairs.put(FTL_VAL_WPS_CONFIG_USERNAME, mojo.getWps()
				.getWpsUsername());
		nameValuePairs.put(FTL_VAL_WPS_CONFIG_PASSWORD, mojo.getWps()
				.getWpsPassword());
		nameValuePairs.put(FTL_VAL_XMLACCESS_SCRIPT, mojo.getXMLAccessScript());

		try {
			nameValuePairs.put(FTL_VAL_XMLACCESS_OUTPUT, mojo
					.getXMLAccessOutputFile().getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameValuePairs;
	}

	private static String findJavaHome(AbstractXMLAccessMojo mojo) {
		String javaHome = mojo.getProject().getProperties()
				.getProperty(Constants.PROP_JAVAHOME);
		if (StringUtils.isNotBlank(javaHome))
			return javaHome;
		javaHome = System.getenv(Constants.PROP_JAVAHOME);
		if (StringUtils.isNotBlank(javaHome))
			return javaHome;
		javaHome = System.getProperty(Constants.PROP_S_JAVAHOME);
		if (StringUtils.isNotBlank(javaHome))
			return javaHome;
		mojo.getLog()
				.warn("Please specify the IBM Websphere JDK home by setting 'JAVA_HOME' variable");
		return null;
	}
}
