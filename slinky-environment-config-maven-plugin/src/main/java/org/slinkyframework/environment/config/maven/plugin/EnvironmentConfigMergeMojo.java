package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slinkyframework.environment.config.maven.plugin.config.CompositeConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.files.FileApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.templates.TemplateApplicationConfigFactory;

import java.io.File;

@Mojo(name = "merge", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Execute(goal = "merge", phase = LifecyclePhase.GENERATE_RESOURCES)
public class EnvironmentConfigMergeMojo extends AbstractMojo {

    @Parameter(property = "config.sourceDir", defaultValue = "src/main/resources", readonly = true)
    private String sourceDir;

    @Parameter(property = "config.targetDir", defaultValue = "target/generated-config", readonly = true)
    private String targetDir;

    public EnvironmentConfigMergeMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        ConfigFileFactory fileConfigFileFactory = new FileApplicationConfigFactory(new File(sourceDir), new File(targetDir));
        ConfigFileFactory templateConfigFileFactory = new TemplateApplicationConfigFactory(new File(sourceDir), new File(targetDir));
        ConfigFileFactory configFileFactory = new CompositeConfigFileFactory(fileConfigFileFactory, templateConfigFileFactory);

        configFileFactory.generateFiles();
    }

}
