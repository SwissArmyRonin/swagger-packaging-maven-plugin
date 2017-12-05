package dk.swissarmyronin;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/** Abstract base class for the Swagger related Mojos. */
abstract class SwaggerMojo extends AbstractMojo {
	@Component
	private MavenProject project;

	/**
	 * Optional source dir parameter. This is where the YAML or JSON file being deployed is expected to be found. 
	 * It does not have to be inside the service project dir.
	 * Default: ${project.basedir}
	 */
	@Parameter(defaultValue = "${project.basedir}", property = "sourceDir", required = true)
	private File sourceDir;

	/**
	 * Mark the {@link #swaggerFile} as the final artifact produced by this Mojo.
	 */
	public void execute() throws MojoExecutionException {
		try {
			File file = new File(sourceDir, getSwaggerFile());
			if (!file.exists()) {
				throw new FileNotFoundException("Swagger file not found at '" + file + "'");
			}
			project.getArtifact().setFile(file);
		} catch (Exception e) {
			throw new MojoExecutionException("Unhandled exception", e);
		}

	}

	/**
	 * Get the file name of service definition file located in the
	 * {@link #sourceDir}.
	 * 
	 * @return a service file name
	 */
	abstract protected String getSwaggerFile();
}
