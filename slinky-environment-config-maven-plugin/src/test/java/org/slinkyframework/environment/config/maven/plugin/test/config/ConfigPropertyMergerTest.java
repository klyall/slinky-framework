package org.slinkyframework.environment.config.maven.plugin.test.config;

import com.typesafe.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigPropertyMerger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigPropertyMergerTest {

    private Config app1Env1Config;
    private Config app2Env1Config;

    @Before
    public void setUp() {
        String app1 = "app1";
        String app2 = "app2";
        String environment = "env1";

        ConfigPropertyMerger app1Env1factory = new ConfigPropertyMerger(app1, environment);
        ConfigPropertyMerger app2Env1factory = new ConfigPropertyMerger(app2, environment);

        app1Env1Config = app1Env1factory.merge();
        app2Env1Config = app2Env1factory.merge();
    }

    @Test
    public void shouldGetGlobalDefinedProperty() {
        assertThat("Property", app1Env1Config.getString("global.property"), is(equalTo("global")));
    }

    @Test
    public void shouldGetApplicationOnlyProperty() {
        assertThat("Property", app1Env1Config.getString("application.only"), is(equalTo("application")));
    }

    @Test
    public void shouldGetApplicationOverridenProperty() {
        assertThat("Property", app1Env1Config.getString("application.override"), is(equalTo("application")));
    }

    @Test
    public void shouldGetEnvironmentOnlyProperty() {
        assertThat("Property", app1Env1Config.getString("environment.only"), is(equalTo("environment")));
    }

    @Test
    public void shouldGetEnvironmentOverridenProperty() {
        assertThat("Property", app1Env1Config.getString("environment.override"), is(equalTo("environment")));
    }

    @Test
    public void shouldGetApplicationEnvironmentOnlyProperty() {
        assertThat("Property", app1Env1Config.getString("application.environment.only"), is(equalTo("app-env")));
    }

    @Test
    public void shouldGetApplicationEnvironmentOverridenProperty() {
        assertThat("Property", app1Env1Config.getString("application.environment.override"), is(equalTo("app-env")));
    }

    @Test
    public void shouldGetPropertyEvenWhenNotAllFilesExists() {
        assertThat("Property", app2Env1Config.getString("application.environment.override"), is(equalTo("global")));
    }
}


