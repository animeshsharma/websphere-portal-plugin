package com.googlecode.websphere;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.googlecode.websphere.model.ScriptTemplatePair;
import com.googlecode.websphere.utils.Constants;
import com.googlecode.websphere.utils.FileHelper;

/**
 * 
 * The import task enables you to import a XMLAccess XML into WebSphere Portal.
 * This task is a wrapper for the XMLAccess command of the WebSphere Portal.
 * Refer to the WebSphere Portal documentation for more information. <br/>
 * 
 * 
 * <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 
 * 
 * 1. Import a XMLAccess XML <br/>
 * 
 * 
 * <i>mvn clean websphere:import -DimportXML=c:/xmls/pages_portal1.xml</i> <br/>
 * 
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "import", threadSafe = true)
public class ImportMojo extends AbstractXMLAccessMojo {

	/**
	 * XMLAccess XML File to be imported
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "importXML", required = true, readonly = true)
	private File importXML;

	/**
	 * Properties file which list all the pairs of replace by string to replace
	 * with string.
	 * 
	 * @parameter property="${replacementFile}"
	 */
	@Parameter(property = "replacementFile")
	private File replacementFile;

	private File fixAndFilterXMLAccessOutput(File source) {
		File dest = null;
		Writer destWriter = null;

		try {
			dest = File.createTempFile(Constants.MAVEN_WEBSPHERE_PLUGIN,
					Constants.TEMP_FILE_SUFFIX);

			Map<String, String> rs = FileHelper
					.loadReplacementFile(replacementFile);
			FileHelper.copyFile(source, dest, rs);
			// Do fixing IBM stupid bug =_=!!!
			final String missing_string = "</request>";

			if (-1 == StringUtils
					.lastIndexOf(IOUtils.toString(new FileInputStream(dest)),
							missing_string)) {
				destWriter = new FileWriter(dest, true);
				destWriter.write(missing_string);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(destWriter);
		}
		return dest;
	}

	@Override
	public String getXMLAccessScript() {
		return fixAndFilterXMLAccessOutput(importXML).getPath();
	}

	@Override
	protected Map<Object, Object> getXMLAccessScriptNameValuePairs()
			throws MojoExecutionException {
		// no need to swap the value name pair for existing xmlaccess script,
		// return empty map
		return new HashMap<Object, Object>();
	}

	@Override
	protected ScriptTemplatePair getXMLAccessScriptPair() {
		// do nothing since import xml access script is specified by user
		return null;
	}

	public void setImportXML(File importXML) {
		this.importXML = importXML;
	}

	public void setReplacementFile(File replacementFile) {
		this.replacementFile = replacementFile;
	}

}
