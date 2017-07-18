package org.slinkyframework.environment.config.maven.plugin.config.templates;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.error.RootAttributeNotFoundException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;
import org.slinkyframework.environment.config.maven.plugin.config.ImmutableConfigMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.stripEnd;
import static org.apache.commons.lang3.StringUtils.stripStart;

public class PebbleFileGenerator implements FileGenerator {

    private static Logger LOG = LoggerFactory.getLogger(PebbleFileGenerator.class);

    private final File targetDir;
    private Config config;
    private final PebbleEngine pebbleEngine;

    public PebbleFileGenerator(File targetDir, Config config) {
        this.targetDir = targetDir;
        this.config = config;

        pebbleEngine = new PebbleEngine.Builder()
                .strictVariables(true)
                .build();
    }

    @Override
    public void generateFile(File templateFile) {
        String subDir = StringUtils.substringAfter(templateFile.getParent(), TemplateApplicationConfigFactory.TEMPLATES_DIR);

        Writer writer = null;

        try {
            File targetSubDir = new File(targetDir, subDir);
            FileUtils.forceMkdir(targetSubDir);

            PebbleTemplate compiledTemplate = pebbleEngine.getTemplate(templateFile.getAbsolutePath());

            String targteFilename = StringUtils.removeEndIgnoreCase(templateFile.getName(), ".tmpl");
            File targetFile = new File(targetSubDir, targteFilename);

            LOG.debug("Generating config file '{}' using properties {}", targetFile, config);

            writer = new FileWriter(targetFile);
            compiledTemplate.evaluate(writer, new ImmutableConfigMap(config.root()));

        } catch (RootAttributeNotFoundException e) {
            try {
                List<String> lines = FileUtils.readLines(templateFile, Charset.defaultCharset());
                String lineInError = lines.get(e.getLineNumber() - 1);
                String missingVariable = stripStart(stripEnd(lineInError, "}}"), "{{").trim();

                throw new EnvironmentConfigException(format("The property '%s' is missing from the configuration files", missingVariable));
            } catch (IOException e1) {
                throw new EnvironmentConfigException("Error reading template line", e1);
            }
        } catch (IOException | PebbleException e) {
            throw new EnvironmentConfigException(format("Error processing template %s", templateFile), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
