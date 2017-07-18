package org.slinkyframework.environment.config.maven.plugin.test.config;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.CompositeConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.files.FileApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.templates.TemplateApplicationConfigFactory;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileHasPropertyMatcher.hasProperty;

public class CompositeConfigFileFactoryTest {

    public static final File TARGET_DIR = new File("target/generated-config/");
    public static final File SOURCE_DIR = new File("src/test/resources");

    @Test
    public void shouldOverwriteCopiedFileWithAGeneratedTemplate() {

        ConfigFileFactory fileConfigFileFactory = new FileApplicationConfigFactory(SOURCE_DIR, TARGET_DIR);
        ConfigFileFactory templateConfigFileFactory = new TemplateApplicationConfigFactory(SOURCE_DIR, TARGET_DIR);
        ConfigFileFactory configFileFactory = new CompositeConfigFileFactory(fileConfigFileFactory, templateConfigFileFactory);

        configFileFactory.generateFiles();

        assertThat(new File(TARGET_DIR, "env1/app1/global-template-override.conf"), hasProperty("type", equalTo("generated")));
        assertThat(new File(TARGET_DIR, "env1/app2/global-template-override.conf"), hasProperty("type", equalTo("generated")));
        assertThat(new File(TARGET_DIR, "env2/app1/global-template-override.conf"), hasProperty("type", equalTo("generated")));
        assertThat(new File(TARGET_DIR, "env2/app2/global-template-override.conf"), hasProperty("type", equalTo("generated")));
    }
}
