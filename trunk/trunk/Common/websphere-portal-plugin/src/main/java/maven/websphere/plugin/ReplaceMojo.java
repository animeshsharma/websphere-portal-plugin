package maven.websphere.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import maven.websphere.plugin.utils.FileHelper;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Replace the key word in text file.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 * 
 */
@Mojo(name = "replace", threadSafe = true)
public class ReplaceMojo extends AbstractMojo {
	/**
	 * File to be replaced
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "importFile", required = true, readonly = true)
	private File importFile;

	/**
	 * File export
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "exportFile", required = true)
	private File exportFile;

	/**
	 * Properties file which list all the pairs of replace by string to replace
	 * with string.
	 * 
	 */
	@Parameter(property = "replacementFile", required = true, readonly = true)
	private File replacementFile;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		try {
			Map<String, String> nameValuePair = FileHelper
					.loadReplacementFile(replacementFile);
			FileHelper.copyFile(importFile, exportFile, nameValuePair);
			getLog().info(
					String.format("Exported file to [%s]", exportFile.getPath()));
			getLog().info(IOUtils.toString(new FileInputStream(exportFile)));
		} catch (IOException e) {
			throw new MojoExecutionException(
					"Unable to create XML Access executable", e);
		}
	}
}
