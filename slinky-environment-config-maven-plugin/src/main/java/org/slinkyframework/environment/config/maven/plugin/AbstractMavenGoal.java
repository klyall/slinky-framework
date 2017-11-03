package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

public abstract class AbstractMavenGoal {

    private File projectDir;
    private String version;
    private File targetDir;
    private String groupId;
    private final Invoker invoker;

    public AbstractMavenGoal(File projectDir, String groupId, String version, File targetDir) {
        this.projectDir = projectDir;
        this.groupId = groupId;
        this.version = version;
        this.targetDir = targetDir;

        invoker = new DefaultInvoker();
    }

    public abstract Properties getAdditionalProperties(File zipFile);

    public abstract String getGoal();

    public void processEnvironments() {
        File[] environmentDirs = targetDir.listFiles(File::isDirectory);

        if (environmentDirs != null) {
            for (File environmentDir: environmentDirs) {
                processEnvironment(environmentDir);
            }
        }
    }

    private void processEnvironment(File environmentDir) {
        File[] zipFiles = environmentDir.listFiles((dir, name) -> { return name.toLowerCase().endsWith(".zip"); });
        String environmentName = environmentDir.getName();

        for (File zipFile: zipFiles) {
            executeMavenGoal(zipFile, environmentName, getGoal(), getAdditionalProperties(zipFile));
        }
    }

    public void executeMavenGoal(File zipFile, String environmentName, String goal, Properties additionalProperties) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setShellEnvironmentInherited(true);
        request.setBaseDirectory(projectDir);
        request.setPomFile(new File(projectDir, "pom.xml"));
        request.setInteractive(false);
        request.setGoals(Collections.singletonList(goal));

        String environmentGroupId = groupId + "." + environmentName;
        String artifactId = zipFile.getName().replace("-" + version + ".zip", "");

        Properties props = new Properties(additionalProperties);
        props.setProperty("groupId", environmentGroupId);
        props.setProperty("artifactId", artifactId);
        props.setProperty("version", version);
        props.setProperty("generatePom", "true");
        props.setProperty("packaging", "zip");
        props.setProperty("file", zipFile.getAbsolutePath());

        additionalProperties.forEach((key, value) -> props.setProperty(key.toString(), value.toString()));

        request.setProperties(props);

        InvocationResult result;
        try {
            result = invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new EnvironmentConfigException(String.format("Error installing file %s", zipFile), e);
        }

        if (result.getExitCode() != 0) {
            if ( result.getExecutionException() != null ) {
                throw new EnvironmentConfigException("Failed to execute Maven goal.", result.getExecutionException());
            } else {
                throw new EnvironmentConfigException("Failed to execute Maven goal. Exit code: " + result.getExitCode());
            }
        }
    }
}
