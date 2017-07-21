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
import java.util.LinkedHashSet;

@Mojo(name = "merge", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
@Execute(goal = "merge", phase = LifecyclePhase.GENERATE_RESOURCES)
public class EnvironmentConfigMergeMojo extends AbstractMojo {

    private static final String DEFAULT_DELIMITER = "${*}";

    @Parameter(property = "config.sourceDir", defaultValue = "src/main/resources", readonly = true)
    private String sourceDir;

    @Parameter(property = "config.targetDir", defaultValue = "target/generated-config", readonly = true)
    private String targetDir;

    /**
     * <p>
     * Set of delimiters for expressions to filter within the resources. These delimiters are specified in the form
     * {@code beginToken*endToken}. If no {@code *} is given, the delimiter is assumed to be the same for start and end.
     * </p>
     * <p>
     * So, the default filtering delimiters might be specified as:
     * </p>
     *
     * <pre>
     * &lt;delimiters&gt;
     *   &lt;delimiter&gt;${*}&lt;/delimiter&gt;
     *   &lt;delimiter&gt;@&lt;/delimiter&gt;
     * &lt;/delimiters&gt;
     * </pre>
     * <p>
     * Since the {@code @} delimiter is the same on both ends, we don't need to specify {@code @*@} (though we can).
     * </p>
     */
    @Parameter
    private LinkedHashSet<String> delimiters;

    @Parameter(property = "config.failOnMissingProperty", defaultValue = "false", readonly = true)
    private boolean failOnMissingProperty;

    public EnvironmentConfigMergeMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (delimiters == null || delimiters.isEmpty()) {
            delimiters = new LinkedHashSet<>();
            delimiters.add(DEFAULT_DELIMITER);
        }

        ConfigFileFactory fileConfigFileFactory = new FileApplicationConfigFactory(new File(sourceDir), new File(targetDir));
        TemplateApplicationConfigFactory templateConfigFileFactory = new TemplateApplicationConfigFactory(new File(sourceDir), new File(targetDir), delimiters);
        templateConfigFileFactory.setFailOnMissingProperty(failOnMissingProperty);
        ConfigFileFactory configFileFactory = new CompositeConfigFileFactory(fileConfigFileFactory, templateConfigFileFactory);

        configFileFactory.generateFiles();
    }
}
