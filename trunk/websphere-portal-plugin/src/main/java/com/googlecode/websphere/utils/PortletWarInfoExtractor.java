package com.googlecode.websphere.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Extracts Portlet Info from war file.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
public class PortletWarInfoExtractor implements Constants {

	public static Map<Object, Object> getInfo(File zipFile,
			Properties portletInfoProp) {
		Map<Object, Object> nameValuePairs = new HashMap<Object, Object>();

		for (Object key : portletInfoProp.keySet()) {
			nameValuePairs.put(key,
					queryValue(zipFile, (String) portletInfoProp.get(key)));
		}

		return nameValuePairs;
	}

	private static Object queryValue(File zipFile, String query) {
		String value = "";
		String resName = StringUtils.split(query,
				PROP_FILE_PORTLET_INFO_DIVIDER)[0];
		String q = StringUtils.split(query, PROP_FILE_PORTLET_INFO_DIVIDER)[1];

		// InputStream is = ResourceUtils.getResourceFromZip(zipFile, resName);
		String content = ResourceUtils.readResourceFromZip(zipFile, resName);
		if (StringUtils.isNotBlank(content)) {
			value = XQueryHelper.queryTextContent(content, q);
		}

		return value;
	}

}
