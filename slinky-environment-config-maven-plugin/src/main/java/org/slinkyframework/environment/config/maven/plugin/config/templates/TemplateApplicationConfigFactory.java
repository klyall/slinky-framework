package org.slinkyframework.environment.config.maven.plugin.config.templates;

import org.slinkyframework.environment.config.maven.plugin.config.AbstractApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigPropertyMerger;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Properties;

public class TemplateApplicationConfigFactory extends AbstractApplicationConfigFactory {

    public static final String TEMPLATES_DIR = "templates";
    private LinkedHashSet<String> delimiters;

    private boolean failOnMissingProperty = false;

    public TemplateApplicationConfigFactory(File sourceDir, File targetDir, LinkedHashSet<String> delimiters) {
        super(sourceDir, targetDir);
        this.delimiters = delimiters;
    }

    public void setFailOnMissingProperty(boolean failOnMissingProperty) {
        this.failOnMissingProperty = failOnMissingProperty;
    }

    @Override
    protected void processDirectory(String application, String environment, File sourceDir, File targetDir) {
        File templatesDir = new File(sourceDir, TEMPLATES_DIR);

        if (templatesDir.exists()) {
            ConfigPropertyMerger app1Env1factory = new ConfigPropertyMerger(getBaseDir(), application, environment);
            Properties properties = app1Env1factory.getProperties();

            FilterFileGenerator fileGenerator = new FilterFileGenerator(targetDir, properties, delimiters);
            fileGenerator.setFailOnMissingProperty(failOnMissingProperty);

            TemplateDirectoryWalker directoryWalker = new TemplateDirectoryWalker(fileGenerator);
            directoryWalker.generate(templatesDir);
        }
    }
}
