package org.slinkyframework.environment.config.maven.plugin;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

    public Map<String, Object> merge() {

        return map(config.entrySet());
    }

    private Map<String, Object> map(Set<Map.Entry<String, ConfigValue>> entries) {
        Map<String, Object> props = new TreeMap<>();

        entries.stream()
                .forEach(s -> props.put(s.getKey(), s.getValue().unwrapped()));

        return props;
    }
}
