package dk.swissarmyronin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Packaging Mojo that stores a ".json" file in a repository.
 */
@Mojo(name = "json", defaultPhase = LifecyclePhase.PACKAGE)
public class JsonMojo extends SwaggerMojo {
	/**
	 * Optional file name of service definition file located in the {@link #sourceDir}. 
	 * Default: ${project.artifactId}.json
	 */
	@Parameter(defaultValue = "${project.artifactId}.json", property = "swaggerFile", required = true)
	private String swaggerFile;

	@Override
	protected String getSwaggerFile() {
		return swaggerFile;
	}
}
