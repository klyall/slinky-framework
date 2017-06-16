package org.slinkyframework.environment.config.maven.plugin.templates;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.EnvironmentConfigException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class JtwigFileGenerator implements FileGenerator {

    private static Logger LOG = LoggerFactory.getLogger(JtwigFileGenerator.class);

    private final File targetDir;
    private final JtwigModel model;
    private final EnvironmentConfiguration environmentConfig;

    public JtwigFileGenerator(File targetDir, Map<String, Object> propertyMap) {
        this.targetDir = targetDir;
        this.model = JtwigModel.newModel(propertyMap);

        environmentConfig = EnvironmentConfigurationBuilder
                .configuration()
                .render()
                .withStrictMode(true)
                .and()
                .build();
    }

    @Override
    public void generateFile(File templateFile) {
        String subDir = StringUtils.substringAfter(templateFile.getParent(), TemplateApplicationConfigFactory.TEMPLATES_DIR);

        FileOutputStream fos = null;

        try {
            File targetSubDir = new File(targetDir, subDir);
            FileUtils.forceMkdir(targetSubDir);

            JtwigTemplate template = JtwigTemplate.fileTemplate(templateFile, environmentConfig);

            String targteFilename = StringUtils.removeEndIgnoreCase(templateFile.getName(), ".tmpl");
            File targetFile = new File(targetSubDir, targteFilename);

            fos = new FileOutputStream(targetFile);
            template.render(model, fos);

        } catch (IOException e) {
            throw new EnvironmentConfigException(String.format("Error processing template %s", templateFile), e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }
}
