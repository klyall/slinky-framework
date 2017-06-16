package org.slinkyframework.environment.config.maven.plugin.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigPropertyMerger {

    private Config config;

    public ConfigPropertyMerger(String application, String environment) {
        Config globalConfig = ConfigFactory.load("global/global.properties");
        Config applicationConfig = ConfigFactory.load(String.format("applications/%s/application.properties", application));
        Config environmentConfig = ConfigFactory.load(String.format("environments/%s/environment.properties", environment));
        Config appEnvConfig = ConfigFactory.load(String.format("environments/%s/applications/%s/application.properties", environment, application));

        config = appEnvConfig
                .withFallback(environmentConfig)
                .withFallback(applicationConfig)
                .withFallback(globalConfig);
    }

    public Config merge() {
        return config;
    }
}
