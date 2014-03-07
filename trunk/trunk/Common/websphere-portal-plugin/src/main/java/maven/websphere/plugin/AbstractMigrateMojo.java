package maven.websphere.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import maven.websphere.plugin.utils.XMLFixer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Super class of migrate mojo's, defines migration work flow for pages,portlet,
 * theme and urlmappings.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public abstract class AbstractMigrateMojo<ExportMojo extends AbstractExportMojo>
		extends AbstractWebsphereMojo {

	/**
	 * Websphere Portal server name to perform the xmlacess access, configured
	 * in Settings.xml
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "targetServer", required = true)
	private String targetServer;

	/**
	 * Websphere Portal server name to perform the xmlacess access, configured
	 * in Settings.xml
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "sourceServer", required = true)
	private String sourceServer;

	/**
	 * Properties file which list all the pairs of replace by string to replace
	 * with string.
	 * 
	 * @parameter property="${replacementFile}"
	 */
	@Parameter(property = "replacementFile")
	private File replacementFile;

	@Override
	protected void doExecute() throws MojoExecutionException,
			MojoFailureException {

		File tmpFile = null;
		try {
			ExportMojo exportMojo = getExportMojo();
			exportMojo.setServer(sourceServer);
			exportMojo.execute();

			tmpFile = exportMojo.getXMLAccessOutputFile();
			fixXmlNamespace(tmpFile);

			log.info("xmlaccess request:");
			log.info(IOUtils.toString(new FileInputStream(tmpFile)));

			ImportMojo importMojo = getSiblingPluginMojo("import");
			importMojo.setServer(targetServer);
			importMojo.setImportXML(tmpFile);
			importMojo.setReplacementFile(replacementFile);
			importMojo.execute();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != tmpFile) {
				log.info("Remove the temp file:" + tmpFile.getPath());
				FileUtils.deleteQuietly(tmpFile);
			}
		}

	}

	private void fixXmlNamespace(File tmpFile) {
		XMLFixer.fixNamespace(tmpFile);
	}

	protected abstract ExportMojo getExportMojo();
}
