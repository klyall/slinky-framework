package org.slinkyframework.environment.config.maven.plugin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.lang.String.format;
import static org.apache.commons.io.IOUtils.closeQuietly;

public class ConfigPropertyMerger {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigPropertyMerger.class);

    Properties properties = new Properties();

    public ConfigPropertyMerger(File sourceDir, String application, String environment) {

        File globalFile         = new File(sourceDir, "/global/global.properties");
        File applicationFile    = new File(sourceDir, format("applications/%s/application.properties", application));
        File environmentFile    = new File(sourceDir, format("environments/%s/environment.properties", environment));
        File appEnvFile         = new File(sourceDir, format("environments/%s/applications/%s/application.properties", environment, application));

        if (LOG.isDebugEnabled()) {
            logLoadingOfConfigFiles(globalFile, applicationFile, environmentFile, appEnvFile);
        }

        loadProperties(properties, globalFile);
        loadProperties(properties, applicationFile);
        loadProperties(properties, environmentFile);
        loadProperties(properties, appEnvFile);
    }

    private void loadProperties(Properties properties, File file) {
        FileReader fr = null;
        try {
            if (file.exists()) {
                fr = new FileReader(file);
                properties.load(fr);
            } else {
                LOG.debug("WARNING: Properties file '{}' does not exist and will not be loaded", file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new EnvironmentConfigException(format("Unable to load properties file %s", file), e);
        } finally {
            closeQuietly(fr);
        }

    }

    public Properties getProperties() {
        return properties;
    }

    private void logLoadingOfConfigFiles(File globalFile, File applicationFile, File environmentFile, File appEnvFile) {
        logFile("global", globalFile);
        logFile("application", applicationFile);
        logFile("environment", environmentFile);
        logFile("application environment", appEnvFile);
    }

    private void logFile(String configType, File file) {
        LOG.debug("Loading {} config file '{}'", configType, file);
    }
}
