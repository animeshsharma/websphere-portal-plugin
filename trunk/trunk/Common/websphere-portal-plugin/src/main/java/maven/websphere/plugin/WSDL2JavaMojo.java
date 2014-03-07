package maven.websphere.plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import maven.websphere.plugin.model.ScriptTemplatePair;
import maven.websphere.plugin.utils.FreemarkerHelper;
import maven.websphere.plugin.utils.MappedField;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * The wsdl-to-java task creates Java classes and deployment descriptor
 * templates from a Web Services Description Language (WSDL) file. This task is
 * a wrapper the com.ibm.websphere.ant.tasks.WSDL2Java, Refer to the IBM
 * documentation for information on the valid options available. <br/>
 * <br/>
 * 
 * <i>&lt;wsdl2javatask url=&quot;location of input WSDL document&quot; <br/>
 * output=&quot;root directory for emitted files&quot; <br/>
 * role=&quot;J2EE development role&quot; <br/>
 * container=&quot;j2ee container&quot; <br/>
 * genjava=&quot;generate java files&quot;/&gt;</i> <br/>
 * <br/>
 * 
 * 
 * <b>Usages:</b> <br/>
 * 1. Generate Java classes base by WSDL <br/>
 * 
 * 
 * <i>mvn clean websphere:wsdl-to-java package
 * -DwasHome=C:/IBM/WebSphere/AppServer
 * -Durl=http://memsvcuser:M3msvcus3r@dveweb1/
 * secure/mws/services/UserAccess.wsdl
 * -Doutput=${project.basedir}/src/main/generated</i> <br/>
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
@Mojo(name = "wsdl-to-java", threadSafe = true)
public class WSDL2JavaMojo extends AbstractWSAntMojo {
	@Parameter(property = "server")
	private String server;

	/**
	 * WSDL URL
	 * 
	 */
	@Parameter(property = "url", required = true)
	@MappedField
	private String url;
	/**
	 * Target folder for generating the JAVA classes
	 * 
	 * @since 1.0.3
	 */
	@Parameter(property = "output", defaultValue = "${project.build.directory}/generated-sources")
	@MappedField
	private File output;

	@Parameter(property = "inputMappingFile")
	@MappedField
	private String inputMappingFile;

	@Parameter(property = "role")
	@MappedField
	private String role;

	@Parameter(property = "container")
	@MappedField
	private String container;

	@Parameter(property = "debug")
	@MappedField
	private String debug;

	@Parameter(property = "testCase")
	@MappedField
	private String testCase;

	@Parameter(property = "deployScope")
	@MappedField
	private String deployScope;

	@Parameter(property = "genJava")
	@MappedField
	private String genJava;

	@Parameter(property = "genXML")
	@MappedField
	private String genXML;

	@Parameter(property = "all")
	@MappedField
	private String all;

	@MappedField
	@Parameter(property = "timeout")
	private String timeout;

	@MappedField
	@Parameter(property = "user")
	private String user;

	@Parameter(property = "password")
	@MappedField
	private String password;

	@MappedField
	@Parameter(property = "useResolver")
	private String useResolver;

	@Parameter(property = "generateResolver")
	@MappedField
	private Boolean generateResolver;

	@Parameter(property = "classpath")
	@MappedField
	private String classpath;

	@Parameter(property = "introspect")
	@MappedField
	private String introspect;

	@Parameter(property = "javaSearch")
	@MappedField
	private String javaSearch;

	@Parameter(property = "noDataBinding")
	@MappedField
	private Boolean noDataBinding;

	@Parameter(property = "noWrappedArrays")
	@MappedField
	private Boolean noWrappedArrays;

	@Parameter(property = "noWrappedOperations")
	@MappedField
	private Boolean noWrappedOperations;

	@Parameter(property = "fileNStoPkg")
	@MappedField
	private String fileNStoPkg;

	@Parameter(property = "genEquals")
	@MappedField
	private Boolean genEquals;

	@MappedField
	@Parameter(property = "genImplSer")
	private Boolean genImplSer;

	@Parameter(property = "scenario")
	@MappedField
	private String scenario;

	public String getAll() {
		return all;
	}

	public String getClasspath() {
		return classpath;
	}

	public String getContainer() {
		return container;
	}

	public String getDebug() {
		return debug;
	}

	public String getDeployScope() {
		return deployScope;
	}

	public String getFileNStoPkg() {
		return fileNStoPkg;
	}

	public String getGenJava() {
		return genJava;
	}

	public String getGenXML() {
		return genXML;
	}

	public String getInputMappingFile() {
		return inputMappingFile;
	}

	public String getIntrospect() {
		return introspect;
	}

	public String getJavaSearch() {
		return javaSearch;
	}

	public File getOutput() {
		return output;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public String getScenario() {
		return scenario;
	}

	@Override
	protected ScriptTemplatePair getScriptTemplatePair() {
		return ScriptTemplatePair.WSDL_2_JAVA;
	}

	public String getTestCase() {
		return testCase;
	}

	public String getTimeout() {
		return timeout;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getUseResolver() {
		return useResolver;
	}

	@Override
	protected Map<Object, Object> getWSAntScriptNameValuePairs()
			throws MojoExecutionException {
		return FreemarkerHelper.objectToMap(this);
	}

	@Override
	protected void init() throws MojoExecutionException {
		if (null != output && output.exists()) {
			try {
				FileUtils.deleteDirectory(output);
			} catch (IOException e) {
				throw new MojoExecutionException(
						"Fail to delete WSDL2Java output folder:"
								+ output.getPath());
			}
		}
	}

	public Boolean isGenEquals() {
		return genEquals;
	}

	public Boolean isGenerateResolver() {
		return generateResolver;
	}

	public Boolean isGenImplSer() {
		return genImplSer;
	}

	public Boolean isNoDataBinding() {
		return noDataBinding;
	}

	public Boolean isNoWrappedArrays() {
		return noWrappedArrays;
	}

	public Boolean isNoWrappedOperations() {
		return noWrappedOperations;
	}

	@Override
	protected void validate() throws MojoExecutionException {
		// do nothing
	}
}
