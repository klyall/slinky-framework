package org.slinkyframework.environment.config.maven.plugin.deploy;

import org.slinkyframework.environment.config.maven.plugin.AbstractMavenGoal;

import java.io.File;
import java.util.Properties;

public class MavenDeployer extends AbstractMavenGoal {

    private static final String MAVEN_GOAL = "deploy:deploy-file";
    private static final String PROPERTY_URL = "url";

    private Properties props = new Properties();

    public MavenDeployer(File projectDir, String groupId, String version, File targetDir) {
        super(projectDir, groupId, version, targetDir);
    }

    public MavenDeployer(File projectDir, String groupId, String version, File targetDir, String url) {
        super(projectDir, groupId, version, targetDir);
        props.setProperty(PROPERTY_URL, url);
    }

    public Properties getAdditionalProperties(File zipFile) {
        return props;
    }

    public String getGoal() {
        return MAVEN_GOAL;
    }
}
