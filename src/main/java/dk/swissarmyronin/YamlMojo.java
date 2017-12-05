package dk.swissarmyronin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Packaging Mojo that stores a ".yaml" file in a repository.
 */
@Mojo(name = "yaml", defaultPhase = LifecyclePhase.PACKAGE)
public class YamlMojo extends SwaggerMojo {
	/**
	 * Optional file name of service definition file located in the {@link #sourceDir}. 
	 * Default: ${project.artifactId}.yaml
	 */
	@Parameter(defaultValue = "${project.artifactId}.yaml", property = "swaggerFile", required = true)
	private String swaggerFile;

	@Override
	protected String getSwaggerFile() {
		return swaggerFile;
	}
}
