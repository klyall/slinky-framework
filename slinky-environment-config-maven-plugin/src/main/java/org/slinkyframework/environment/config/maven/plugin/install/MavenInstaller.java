package org.slinkyframework.environment.config.maven.plugin.install;

import org.slinkyframework.environment.config.maven.plugin.AbstractMavenGoal;

import java.io.File;
import java.util.Properties;

public class MavenInstaller extends AbstractMavenGoal {

    private static final String MAVEN_GOAL = "install:install-file";

    public MavenInstaller(File projectDir, String groupId, String version, File targetDir) {
        super(projectDir, groupId, version, targetDir);
    }

    public Properties getAdditionalProperties(File zipFile) {
        return new Properties();
    }

    public String getGoal() {
        return MAVEN_GOAL;
    }
}
