package maven.websphere.plugin.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.googlecode.jxquery.utils.ReflectionHelper;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Utility class to process freemarker templates.
 * 
 * @author : <a href="mailto:Juanyong.zhang@gmail.com">Juanyong.Zhang</a><br/>
 */
public class FreemarkerHelper {
	public static void process(InputStream template,
			Map<Object, Object> nameValuePairs, Writer out) throws IOException,
			TemplateException {
		Configuration cfg = new Configuration();
		BeansWrapper wrapper = freemarker.ext.beans.BeansWrapper
				.getDefaultInstance();
		wrapper.setExposeFields(true);
		wrapper.setExposureLevel(BeansWrapper.EXPOSE_SAFE);
		cfg.setObjectWrapper(wrapper);
		Template temp = new Template(null, new InputStreamReader(template), cfg);
		filterNull(nameValuePairs);
		temp.process(nameValuePairs, out);
		out.flush();
		IOUtils.closeQuietly(out);
	}

	public static String process(InputStream template,
			Map<Object, Object> nameValuePairs) throws IOException,
			TemplateException {
		StringWriter out = new StringWriter();
		process(template, nameValuePairs, out);
		return out.toString();
	}

	public static String process(String templatePath,
			Map<Object, Object> nameValuePairs) throws IOException,
			TemplateException {

		return process(new FileInputStream(templatePath), nameValuePairs);
	}

	private static void filterNull(Map<Object, Object> nameValuePairs) {
		for (Object key : nameValuePairs.keySet())
			if (null == nameValuePairs.get(key)) {
				nameValuePairs.put(key, "");
			}
	}

	public static void process(String templatePath,
			Map<Object, Object> nameValuePairs, Writer out) throws IOException,
			TemplateException {
		process(new FileInputStream(templatePath), nameValuePairs, out);
	}

	public static Map<Object, Object> objectToMap(Object obj) {
		Map<Object, Object> objMap = new HashMap<Object, Object>();

		@SuppressWarnings("unchecked")
		Field[] fs = ReflectionHelper.getAllFields(obj.getClass(),
				MappedField.class);
		if (null != fs) {
			for (Field f : fs) {
				String key = StringUtils.isNotBlank(f.getAnnotation(
						MappedField.class).alias()) ? f.getAnnotation(
						MappedField.class).alias() : f.getName();
				Object val = null;

				try {
					val = PropertyUtils.getProperty(obj, f.getName());
				} catch (Exception e) {
					// DO NOTHING
				}

				if (null != val) {
					objMap.put(key, val);
				}
			}
		}

		return objMap;
	}
}
