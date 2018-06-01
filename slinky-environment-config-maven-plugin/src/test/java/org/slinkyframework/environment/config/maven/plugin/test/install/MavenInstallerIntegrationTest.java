package org.slinkyframework.environment.config.maven.plugin.test.install;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.install.MavenInstaller;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;

public class MavenInstallerIntegrationTest {

    private static final String GROUP_ID = "org.slinkyframework.test.environments";
    private static final String VERSION = "1.0.0";
    private static final File TARGET_DIR = new File("src/test/resources/install-test");
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String MAVEN_HOME = "maven.home";

    @Before
    public void setUp() {
        FileUtils.deleteQuietly(new File(USER_DIR + "/.m2/repository/org/slinkyframework/test"));
    }

    @Test
    public void shouldInstallZipFilesIntoLocalRepo() {
        if (System.getProperty(MAVEN_HOME) == null) {
            System.setProperty(MAVEN_HOME, "/usr/local/Cellar/maven/3.5.3/libexec");
        }

        File projectDir = new File("./");

        MavenInstaller mavenInstaller = new MavenInstaller(projectDir, GROUP_ID, VERSION, TARGET_DIR);
        mavenInstaller.processEnvironments();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(new File(USER_DIR + "/.m2/repository/org/slinkyframework/test/environments/env1/app1-config/1.0.0/app1-config-1.0.0.zip"), fileExists());
        assertThat(new File(USER_DIR + "/.m2/repository/org/slinkyframework/test/environments/env1/app2-config/1.0.0/app2-config-1.0.0.zip"), fileExists());
        assertThat(new File(USER_DIR + "/.m2/repository/org/slinkyframework/test/environments/env2/app1-config/1.0.0/app1-config-1.0.0.zip"), fileExists());
        assertThat(new File(USER_DIR + "/.m2/repository/org/slinkyframework/test/environments/env2/app2-config/1.0.0/app2-config-1.0.0.zip"), fileExists());
    }
}
