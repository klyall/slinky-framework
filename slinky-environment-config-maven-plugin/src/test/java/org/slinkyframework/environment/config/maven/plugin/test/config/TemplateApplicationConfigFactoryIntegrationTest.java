package org.slinkyframework.environment.config.maven.plugin.test.config;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.templates.TemplateApplicationConfigFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileDoesNotExistMatcher.fileDoesNotExist;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileHasPropertyMatcher.hasProperty;


public class TemplateApplicationConfigFactoryIntegrationTest {

    public static final String TARGET_DIR = "target/generated-config/";
    public static final String SOURCE_DIR = "src/test/resources";

    private LinkedHashSet<String> delimiters = new LinkedHashSet<>();

    @Before
    public void setUp() throws IOException {
        delimiters.add("{{*}}");

        File file = new File(".");

        FileUtils.deleteQuietly(new File(TARGET_DIR));

        ConfigFileFactory testee = new TemplateApplicationConfigFactory(new File(SOURCE_DIR), new File(TARGET_DIR), delimiters);
        testee.generateFiles();
    }

    @Test
    public void shouldGenerateGlobalFilesToAllApplicationsAndEnvironments() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/global-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/global-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/global-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2/global-template1.conf"), fileExists());
    }

    @Test
    public void shouldGenerateGlobalFilesInSubDirectoryToAllApplicationsAndEnvironments() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/global-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/global-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/global-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/global-template2.conf"), fileExists());
    }

    @Test
    public void shouldGenerateApplicationFilesToAllEnvironmentsForSpecificApplication() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/application-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/application-template1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/application-template1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldGenerateApplicationFilesFromSubDirectoryToAllEnvironmentsForSpecificApplication() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/application-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/application-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/application-template2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/application-template2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldGenerateEnvironmentFilesToAllApplicationsForSpecificEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/environment-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/environment-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/environment-template1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/environment-template1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldGenerateEnvironmentFilesInSubDirectoryToAllApplicationsAForSpecificEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/environment-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/environment-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/environment-template2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/environment-template2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldGenerateApplicationEnvironmentFilesToAllSpecificApplicationEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-environment-template1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/application-environment-template1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app1/application-environment-template1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/application-environment-template1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldGenerateApplicationEnvironmentFilesInSubDirectoryToSpecificApplicationEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/application-environment-template2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/application-environment-template2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/application-environment-template2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/application-environment-template2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldOverrideGlobalFileWithApplicationFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-template-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env2/app1/application-template-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env1/app2/application-template-override.conf"), hasProperty("source", equalTo("global")));
        assertThat(new File(TARGET_DIR + "/env2/app2/application-template-override.conf"), hasProperty("source", equalTo("global")));
    }

    @Test
    public void shouldOverrideApplicationFileWithEnvironmentFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/environment-template-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env1/app2/environment-template-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env2/app1/environment-template-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env2/app2/environment-template-override.conf"), hasProperty("source", equalTo("global")));
    }

    @Test
    public void shouldOverrideEnvironmentFileWithApplicationEnvironmentFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-environment-template-override.conf"), hasProperty("source", equalTo("app-env")));
        assertThat(new File(TARGET_DIR + "/env1/app2/application-environment-template-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env2/app1/application-environment-template-override.conf"), hasProperty("source", equalTo("global")));
        assertThat(new File(TARGET_DIR + "/env2/app2/application-environment-template-override.conf"), hasProperty("source", equalTo("global")));
    }
}
