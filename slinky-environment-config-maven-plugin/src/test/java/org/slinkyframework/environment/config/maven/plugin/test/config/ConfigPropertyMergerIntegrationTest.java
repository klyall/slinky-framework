package org.slinkyframework.environment.config.maven.plugin.test.config;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigPropertyMerger;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigPropertyMergerIntegrationTest {

    private Properties app1Env1Properties;
    private Properties app2Env1Properties;

    @Before
    public void setUp() {
        String app1 = "app1";
        String app2 = "app2";
        String environment = "env1";

        File baseDir = new File("src/test/resources");
        ConfigPropertyMerger app1Env1factory = new ConfigPropertyMerger(baseDir, app1, environment);
        ConfigPropertyMerger app2Env1factory = new ConfigPropertyMerger(baseDir, app2, environment);

        app1Env1Properties = app1Env1factory.getProperties();
        app2Env1Properties = app2Env1factory.getProperties();
    }

    @Test
    public void shouldGetGlobalDefinedProperty() {
        assertThat("Property", app1Env1Properties.get("global.property"), is(equalTo("global")));
    }

    @Test
    public void shouldGetGlobalDefinedPropertyAtRootLevel() {
        assertThat("Property", app1Env1Properties.get("global"), is(equalTo("global")));
    }

    @Test
    public void shouldGetApplicationOnlyProperty() {
        assertThat("Property", app1Env1Properties.get("application.only"), is(equalTo("application")));
    }

    @Test
    public void shouldGetApplicationOverridenProperty() {
        assertThat("Property", app1Env1Properties.get("application.override"), is(equalTo("application")));
    }

    @Test
    public void shouldGetEnvironmentOnlyProperty() {
        assertThat("Property", app1Env1Properties.get("environment.only"), is(equalTo("environment")));
    }

    @Test
    public void shouldGetEnvironmentOverridenProperty() {
        assertThat("Property", app1Env1Properties.get("environment.override"), is(equalTo("environment")));
    }

    @Test
    public void shouldGetApplicationEnvironmentOnlyProperty() {
        assertThat("Property", app1Env1Properties.get("application.environment.only"), is(equalTo("app-env")));
    }

    @Test
    public void shouldGetApplicationEnvironmentOverridenProperty() {
        assertThat("Property", app1Env1Properties.get("application.environment.override"), is(equalTo("app-env")));
    }

    @Test
    public void shouldGetPropertyEvenWhenNotAllFilesExists() {
        assertThat("Property", app2Env1Properties.get("application.environment.override"), is(equalTo("global")));
    }
}


