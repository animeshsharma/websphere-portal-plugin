package maven.websphere.plugin;

import java.io.File;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * Super class of all the export mojos, defines the output file to be executed.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 * 
 */
public abstract class AbstractExportMojo extends AbstractXMLAccessMojo {
	/**
	 * export output file
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "outputFile")
	private File outputFile;

	@Override
	public File getXMLAccessOutputFile() {
		if (null != outputFile) {
			return outputFile;
		} else {
			return super.getXMLAccessOutputFile();
		}
	}

}
