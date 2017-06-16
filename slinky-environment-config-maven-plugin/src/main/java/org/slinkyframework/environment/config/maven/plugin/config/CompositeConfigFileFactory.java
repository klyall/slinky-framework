package org.slinkyframework.environment.config.maven.plugin.config;

public class CompositeConfigFileFactory implements ConfigFileFactory {

    private ConfigFileFactory[] configFileFactories;

    public CompositeConfigFileFactory(ConfigFileFactory... configFileFactories) {
        this.configFileFactories = configFileFactories;
    }

    @Override
    public void generateFiles() {
        for (ConfigFileFactory configFileFactory : configFileFactories) {
            configFileFactory.generateFiles();
        }
    }
}
