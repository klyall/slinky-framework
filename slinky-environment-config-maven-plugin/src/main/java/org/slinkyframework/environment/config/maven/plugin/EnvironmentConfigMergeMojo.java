package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slinkyframework.environment.config.maven.plugin.config.CompositeConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.files.FileApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.templates.TemplateApplicationConfigFactory;

import java.io.File;

/**
 * @goal merge
 * @phase generate-resources
 */
public class EnvironmentConfigMergeMojo extends AbstractMojo {

    /**
     * @parameter expression="${config.sourceDir}" default-value="src/main/resources"
     */
    private String sourceDir;

    /**
     * @parameter expression="${config.targetDir}" default-value="target/generated-config"
     */
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
