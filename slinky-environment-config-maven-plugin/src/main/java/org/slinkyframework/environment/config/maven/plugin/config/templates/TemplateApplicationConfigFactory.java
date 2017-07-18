package org.slinkyframework.environment.config.maven.plugin.config.templates;

import com.typesafe.config.Config;
import org.slinkyframework.environment.config.maven.plugin.config.AbstractApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigPropertyMerger;

import java.io.File;

public class TemplateApplicationConfigFactory extends AbstractApplicationConfigFactory {

    public static final String TEMPLATES_DIR = "templates";

    public TemplateApplicationConfigFactory(File sourceDir, File targetDir) {
        super(sourceDir, targetDir);
    }

    @Override
    protected void processDirectory(String application, String environment, File sourceDir, File targetDir) {
        File templatesDir = new File(sourceDir, TEMPLATES_DIR);

        if (templatesDir.exists()) {
            ConfigPropertyMerger app1Env1factory = new ConfigPropertyMerger(getBaseDir(), application, environment);
            Config config = app1Env1factory.merge();

            FileGenerator fileGenerator = new PebbleFileGenerator(targetDir, config);

            TemplateDirectoryWalker directoryWalker = new TemplateDirectoryWalker(fileGenerator);
            directoryWalker.generate(templatesDir);
        }
    }
}
