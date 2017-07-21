package org.slinkyframework.environment.config.maven.plugin.config.templates;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.interpolation.PropertiesBasedValueSource;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.interpolation.multi.MultiDelimiterInterpolatorFilterReader;
import org.codehaus.plexus.interpolation.multi.MultiDelimiterStringSearchInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Properties;

import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang3.StringUtils.removeEndIgnoreCase;

public class FilterFileGenerator implements FileGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(FilterFileGenerator.class);

    private File targetDir;
    private Properties properties;
    private LinkedHashSet<String> delimiters;


    private boolean failOnMissingProperty = false;

    public FilterFileGenerator(File targetDir, Properties properties, LinkedHashSet<String> delimiters) {
        this.targetDir = targetDir;
        this.properties = properties;
        this.delimiters = delimiters;
    }

    public void setFailOnMissingProperty(boolean failOnMissingProperty) {
        this.failOnMissingProperty = failOnMissingProperty;
    }

    @Override
    public void generateFile(File templateFile) {
        Reader reader = null;
        Writer writer = null;

        try {
            String subDir = StringUtils.substringAfter(templateFile.getParent(), TemplateApplicationConfigFactory.TEMPLATES_DIR);
            File targetSubDir = new File(targetDir, subDir);
            forceMkdir(targetSubDir);

            String targetFilename = removeEndIgnoreCase(templateFile.getName(), ".tmpl");
            File targetFile = new File(targetSubDir, targetFilename);

            LOG.debug("Generating config file '{}' using properties {}", targetFile, properties);

            reader = new FileReader(templateFile);

            MultiDelimiterStringSearchInterpolator interpolator = new MultiDelimiterStringSearchInterpolator();
            interpolator.addValueSource(createValueSource());
            delimiters.forEach(d -> interpolator.addDelimiterSpec(d));

            MultiDelimiterInterpolatorFilterReader filteringReader = new MultiDelimiterInterpolatorFilterReader(
                    reader,
                    interpolator,
                    new SimpleRecursionInterceptor()
            );
            delimiters.forEach(d -> filteringReader.addDelimiterSpec(d));

            writer = new FileWriter(targetFile);

            copy(filteringReader, writer);

            closeQuietly(filteringReader);
        } catch (IOException e) {
            throw new EnvironmentConfigException("Unable to filter file", e);
        } finally {
            closeQuietly(reader);
            closeQuietly(writer);
        }
    }

    private ValueSource createValueSource() {
        if (failOnMissingProperty) {
            return new ManadatoryValueSource(new PropertiesBasedValueSource(properties));
        } else {
            return new LoggingValueSource(new PropertiesBasedValueSource(properties));
        }
    }
}
