package com.googlecode.websphere.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Utility class to find and replace text in a file.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
public class FileHelper {

	public static void copyFile(File sourceFile, File destFile,
			Map<String, String> replacements) {
		try {
			String src = IOUtils.toString(new FileInputStream(sourceFile));

			if (null != replacements) {
				List<String> searchList = new ArrayList<String>();
				List<String> replacementList = new ArrayList<String>();

				for (Object key : replacements.keySet()) {
					searchList.add(key.toString());
					replacementList.add(replacements.get(key.toString()));
				}

				src = StringUtils.replaceEach(src, searchList
						.toArray(new String[searchList.size()]),
						replacementList.toArray(new String[replacementList
								.size()]));
			}
			FileUtils.write(destFile, src);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> loadReplacementFile(File replacementFile)
			throws IOException {
		Map<String, String> nameValPair = new HashMap<String, String>();
		if (null != replacementFile) {
			if (replacementFile.exists()) {

				List<String> lines = IOUtils.readLines(new FileInputStream(
						replacementFile));
				for (String ln : lines) {
					// System.out.println(ln);
					if (StringUtils.isNotBlank(ln)
							&& StringUtils.contains(ln, "=")
							&& StringUtils.length(ln) > 2) {
						int i = StringUtils.indexOf(ln, "=");
						String key = StringUtils.substring(ln, 0, i);
						String val = StringUtils.substring(ln, i + 1,
								StringUtils.length(ln));
						nameValPair.put(key, val);
					}
				}
			} else {
				System.out.print("Replacement file is not exists:"
						+ replacementFile.getPath());
			}
		}
		return nameValPair;
	}

	public static File findSingleFile(final File dir, final String fileName)
			throws FileNotFoundException {
		File file = null;
		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return StringUtils.equalsIgnoreCase(name, fileName);
			}
		});
		if (null == matches || matches.length == 0) {
			throw new FileNotFoundException();
		}
		file = matches[0];
		return file;
	}

	public static InputStream findSingleFileAsStream(final File dir,
			final String fileName) throws FileNotFoundException {
		File file = findSingleFile(dir, fileName);
		InputStream fis = new FileInputStream(file);
		return fis;
	}

	public static String guessFileName(String url) {
		String fileName = null;

		File f = new File(url);
		if (f.exists()) {
			fileName = f.getName();
		} else {
			// url might be a nexus url
			// e.g
			// http://repo1.maven.org/maven2/org/springframework/spring-webmvc/3.2.6.RELEASE/spring-webmvc-3.2.6.RELEASE.jar
			try {
				String[] parts = StringUtils.split(url, Constants.SLASH);
				int size = parts.length;
				fileName = StringUtils.remove(parts[size - 1], "-"
						+ parts[size - 2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileName;
	}

}
