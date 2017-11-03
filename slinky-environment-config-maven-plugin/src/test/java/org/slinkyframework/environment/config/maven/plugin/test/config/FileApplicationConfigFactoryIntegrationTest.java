package org.slinkyframework.environment.config.maven.plugin.test.config;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.ConfigFileFactory;
import org.slinkyframework.environment.config.maven.plugin.config.files.FileApplicationConfigFactory;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileDoesNotExistMatcher.fileDoesNotExist;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileHasPropertyMatcher.hasProperty;


public class FileApplicationConfigFactoryIntegrationTest {

    public static final String TARGET_DIR = "target/generated-config/";
    public static final String SOURCE_DIR = "src/test/resources";

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteQuietly(new File(TARGET_DIR));

        ConfigFileFactory testee = new FileApplicationConfigFactory(new File(SOURCE_DIR), new File(TARGET_DIR));
        testee.generateFiles();
    }

    @Test
    public void shouldCopyGlobalFilesToAllApplicationsAndEnvironments() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/global-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/global-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/global-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2/global-file1.conf"), fileExists());
    }

    @Test
    public void shouldCopyGlobalFilesInSubDirectoryToAllApplicationsAndEnvironments() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/global-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/global-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/global-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/global-file2.conf"), fileExists());
    }

    @Test
    public void shouldCopyApplicationFilesToAllEnvironmentsForSpecificApplication() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/application-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/application-file1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/application-file1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldCopyApplicationFilesFromSubDirectoryToAllEnvironmentsForSpecificApplication() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/application-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/application-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/application-file2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/application-file2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldCopyEnvironmentFilesToAllApplicationsForSpecificEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/environment-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/environment-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/environment-file1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/environment-file1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldCopyEnvironmentFilesInSubDirectoryToAllApplicationsAForSpecificEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/environment-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/environment-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/environment-file2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/environment-file2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldCopyApplicationEnvironmentFilesToAllSpecificApplicationEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-environment-file1.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/application-environment-file1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app1/application-environment-file1.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/application-environment-file1.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldCopyApplicationEnvironmentFilesInSubDirectoryToSpecificApplicationEnvironment() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/sub/dir/application-environment-file2.conf"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2/sub/dir/application-environment-file2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app1/sub/dir/application-environment-file2.conf"), fileDoesNotExist());
        assertThat(new File(TARGET_DIR + "/env2/app2/sub/dir/application-environment-file2.conf"), fileDoesNotExist());
    }

    @Test
    public void shouldOverrideGlobalFileWithApplicationFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-file-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env2/app1/application-file-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env1/app2/application-file-override.conf"), hasProperty("source", equalTo("global")));
        assertThat(new File(TARGET_DIR + "/env2/app2/application-file-override.conf"), hasProperty("source", equalTo("global")));
    }

    @Test
    public void shouldOverrideApplicationFileWithEnvironmentFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/environment-file-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env1/app2/environment-file-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env2/app1/environment-file-override.conf"), hasProperty("source", equalTo("application")));
        assertThat(new File(TARGET_DIR + "/env2/app2/environment-file-override.conf"), hasProperty("source", equalTo("global")));
    }

    @Test
    public void shouldOverrideEnvironmentFileWithApplicationEnvironmentFile() throws IOException {
        assertThat(new File(TARGET_DIR + "/env1/app1/application-environment-file-override.conf"), hasProperty("source", equalTo("application-environment")));
        assertThat(new File(TARGET_DIR + "/env1/app2/application-environment-file-override.conf"), hasProperty("source", equalTo("environment")));
        assertThat(new File(TARGET_DIR + "/env2/app1/application-environment-file-override.conf"), hasProperty("source", equalTo("global")));
        assertThat(new File(TARGET_DIR + "/env2/app2/application-environment-file-override.conf"), hasProperty("source", equalTo("global")));
    }
}
