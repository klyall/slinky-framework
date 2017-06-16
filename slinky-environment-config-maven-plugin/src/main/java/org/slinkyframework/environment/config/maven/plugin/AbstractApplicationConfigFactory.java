package org.slinkyframework.environment.config.maven.plugin;

import java.io.File;

public abstract class AbstractApplicationConfigFactory implements ConfigFileFactory {

    public static final String APPLICATIONS_DIR = "applications";
    public static final String ENVIRONMENTS_DIR = "environments";
    public static final String GLOBAL_DIR = "global";

    protected File sourceDir;
    protected File targetDir;

    public AbstractApplicationConfigFactory(File sourceDir, File targetDir) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
    }

    protected abstract void processDirectory(String application, String environment, File sourceDir, File targetDir);

    @Override
    public void generateFiles() {
        File[] applications = findApplications(sourceDir);
        File[] environments = findEnvironments(sourceDir);

        processGlobalFilesToAll(applications, environments);
        processApplicationFiles(applications, environments);
        processEnvironmentFiles(applications, environments);
        processApplicationEnvironmentFiles(applications, environments);
    }

    private void processApplicationFiles(File[] applications, File[] environments) {
        for (File application : applications) {
            for (File environment : environments) {
                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), application, targetApplicationDir);
            }
        }
    }

    private void processApplicationEnvironmentFiles(File[] applications, File[] environments) {
        for (File environment : environments) {
            for (File application : applications) {

                File applicationEnvironmentDir = new File(environment, APPLICATIONS_DIR + "/" + application.getName());

                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), applicationEnvironmentDir, targetApplicationDir);
            }
        }
    }

    private void processEnvironmentFiles(File[] applications, File[] environments) {
        for (File environment : environments) {
            for (File application : applications) {
                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), environment, targetApplicationDir);
            }
        }
    }

    private void processGlobalFilesToAll(File[] applications, File[] environments) {
        File globalDir = new File(sourceDir, GLOBAL_DIR);

        for (File application : applications) {
            for (File environment : environments) {
                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), globalDir, targetApplicationDir);
            }
        }
    }

    private File[] findApplications(File sourceDir) {
        return listDirectories(APPLICATIONS_DIR);
    }

    private File[] findEnvironments(File sourceDir) {
        return listDirectories(ENVIRONMENTS_DIR);
    }

    private File[] listDirectories(String dir) {
        File directory = new File(sourceDir, dir);
        return directory.listFiles(File::isDirectory);
    }
}
