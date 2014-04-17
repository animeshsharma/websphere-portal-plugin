package com.googlecode.websphere.utils;

import static com.googlecode.websphere.utils.Constants.SLASH;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceCreationException;
import org.codehaus.plexus.resource.loader.ResourceNotFoundException;

/**
 * Extracts jar/zip/war/wsjar resources.
 * 
 * @author : <a href="mailto:Juanyong.zhang@gmail.com">Juanyong.Zhang</a><br/>
 */
public abstract class ResourceUtils {
	public static final String FILE_URL_PREFIX = "file:";
	public static final String URL_PROTOCOL_JAR = "jar";
	public static final String URL_PROTOCOL_ZIP = "zip";
	public static final String URL_PROTOCOL_WSJAR = "wsjar";
	public static final String URL_PROTOCOL_CODE_SOURCE = "code-source";
	public static final String JAR_URL_SEPARATOR = "!/";

	public static boolean isJarURL(URL url) {
		String protocol = url.getProtocol();
		return (URL_PROTOCOL_JAR.equals(protocol)
				|| URL_PROTOCOL_ZIP.equals(protocol)
				|| URL_PROTOCOL_WSJAR.equals(protocol) || (URL_PROTOCOL_CODE_SOURCE
				.equals(protocol) && url.getPath().contains(JAR_URL_SEPARATOR)));
	}

	public static URL extractJarFileURL(URL jarUrl)
			throws MalformedURLException {
		String urlFile = jarUrl.getFile();
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
		if (separatorIndex != -1) {
			String jarFile = urlFile.substring(0, separatorIndex);
			try {
				return new URL(jarFile);
			} catch (MalformedURLException ex) {
				if (!jarFile.startsWith(SLASH)) {
					jarFile = SLASH + jarFile;
				}
				return new URL(FILE_URL_PREFIX + jarFile);
			}
		} else {
			return jarUrl;
		}
	}

	public static void copyJarResourceToDirectory(
			ResourceManager resourceManager, String srcURL, String destDir)
			throws IOException, ResourceNotFoundException,
			FileResourceCreationException {
		URL url = new URL(srcURL);
		OutputStream jarOs = null;
		JarFile jar = null;
		if (isJarURL(url)) {
			URL jarURL = extractJarFileURL(url);
			File tempJar = File.createTempFile("tmp", ".jar");
			String keyPattern = StringUtils.substring(srcURL,
					srcURL.indexOf(JAR_URL_SEPARATOR) + 2);
			jarOs = new FileOutputStream(tempJar);
			jarOs.write(IOUtils.toByteArray((InputStream) jarURL.getContent()));

			jar = new JarFile(tempJar);
			Enumeration<JarEntry> jarEntries = jar.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();
				String entiryName = jarEntry.getName();

				if (entiryName.contains(keyPattern) && !jarEntry.isDirectory()) {
					String jarEntryURL = StringUtils.remove(srcURL, keyPattern)
							+ entiryName;
					File jarEntryFile = resourceManager
							.getResourceAsFile(jarEntryURL);
					String fileName = StringUtils
							.remove(entiryName, keyPattern);
					if (StringUtils.startsWith(fileName, SLASH)) {
						fileName = StringUtils.substring(fileName, 1);
					}
					File newJarEntryFile = new File(destDir + File.separator
							+ fileName);
					FileUtils.copyFile(jarEntryFile, newJarEntryFile);
					newJarEntryFile.setExecutable(true, true);
				}
			}

		}
	}

	public static String readResourceFromZip(File zip, String fileName) {
		String content = null;

		ZipFile zipFile = null;
		InputStream is = null;
		if (!zip.exists())
			return null;
		try {
			zipFile = new ZipFile(zip);
			ZipEntry entry = zipFile.getEntry(fileName);
			if (entry == null)
				return null;
			is = zipFile.getInputStream(entry);
			content = IOUtils.toString(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != zipFile) {
					zipFile.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return content;
	}

}