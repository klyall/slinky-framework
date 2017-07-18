package org.slinkyframework.environment.config.maven.plugin.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.lang.String.format;

public class ConfigPropertyMerger {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigPropertyMerger.class);

    private Config config;

    public ConfigPropertyMerger(File sourceDir, String application, String environment) {

        File globalFile         = new File(sourceDir, "/global/global.properties");
        File applicationFile    = new File(sourceDir, format("applications/%s/application.properties", application));
        File environmentFile    = new File(sourceDir, format("environments/%s/environment.properties", environment));
        File appEnvFile         = new File(sourceDir, format("environments/%s/applications/%s/application.properties", environment, application));

        if (LOG.isDebugEnabled()) {
            logLoadingOfConfigFiles(globalFile, applicationFile, environmentFile, appEnvFile);
        }

        Config globalConfig         = ConfigFactory.parseFile(globalFile);
        Config applicationConfig    = ConfigFactory.parseFile(applicationFile);
        Config environmentConfig    = ConfigFactory.parseFile(environmentFile);
        Config appEnvConfig         = ConfigFactory.parseFile(appEnvFile);

        config = appEnvConfig
                .withFallback(environmentConfig)
                .withFallback(applicationConfig)
                .withFallback(globalConfig);
    }

    public Config merge() {
        return config;
    }

    private void logLoadingOfConfigFiles(File globalFile, File applicationFile, File environmentFile, File appEnvFile) {
        logFile("global", globalFile);
        logFile("application", applicationFile);
        logFile("environment", environmentFile);
        logFile("application environment", appEnvFile);
    }

    private void logFile(String configType, File file) {
        LOG.debug("Loading {} config file '{}'", configType, file);

        if (!file.exists()) {
            LOG.warn("Config file '{}' does not exist and will not be loaded", file.getAbsolutePath());
        }

    }
}
