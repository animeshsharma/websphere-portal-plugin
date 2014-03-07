package maven.websphere.plugin.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class to get the files without versions.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class Utils {
//	public static String getFileNameWithoutVersionFromInstallableApp(
//			String installableApp) {
//		String fileName;
//		File tester = new File(installableApp);
//		if (tester.exists()) {
//			fileName = tester.getName();
//		} else {
//			String[] parts = StringUtils.split(installableApp, SLASH);
//			String fullName = parts[parts.length - 1];
//			String version = parts[parts.length - 2];
//			fileName = StringUtils.remove(fullName, "-" + version);
//		}
//
//		return fileName;
//	}
//	public static String getAppNameWithoutVersionFromInstallableApp(
//			String installableApp) {
//		String fileName = getFileNameWithoutVersionFromInstallableApp(installableApp);
//		return StringUtils.substring(fileName, 0, fileName.lastIndexOf("."));
//	}

	public static Map<Object, Object> toMap(Properties properties) {
		Map<Object, Object> m = new HashMap<Object, Object>();
		if (null != properties) {
			for (Object key : properties.keySet()) {
				m.put(key, properties.get(key));
			}
		}
		return m;
	}

}
