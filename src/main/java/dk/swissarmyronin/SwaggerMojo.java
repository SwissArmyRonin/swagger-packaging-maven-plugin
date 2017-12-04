package dk.swissarmyronin;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "swagger", defaultPhase = LifecyclePhase.PACKAGE)
public class SwaggerMojo extends AbstractMojo {
	@Component
	private MavenProject project;

	@Parameter(defaultValue = "${project.basedir}", property = "sourceDir", required = true)
	private File sourceDir;

	@Parameter(defaultValue = "${project.artifactId}.yaml", property = "swaggerFile", required = true)
	private String swaggerFile;

	public void execute() throws MojoExecutionException {
		try {
			File file = new File(sourceDir, swaggerFile);
			if (!file.exists()) {
				throw new FileNotFoundException("Swagger file not found at '" + file + "'");
			}
			project.getArtifact().setFile(file);
		} catch (Exception e) {
			throw new MojoExecutionException("Unhandled exception", e);
		}

	}
}
