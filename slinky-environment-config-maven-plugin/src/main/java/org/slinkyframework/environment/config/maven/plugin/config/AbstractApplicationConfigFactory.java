package org.slinkyframework.environment.config.maven.plugin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class AbstractApplicationConfigFactory implements ConfigFileFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractApplicationConfigFactory.class);
    private static final File[] NO_FILES = {};

    public static final String APPLICATIONS_DIR = "applications";
    public static final String ENVIRONMENTS_DIR = "environments";
    public static final String GLOBAL_DIR = "global";

    protected File baseDir;
    protected File targetDir;

    public AbstractApplicationConfigFactory(File baseDir, File targetDir) {
        this.baseDir = baseDir;
        this.targetDir = targetDir;
    }

    public File getBaseDir() {
        return baseDir;
    }

    protected abstract void processDirectory(String application, String environment, File sourceDir, File targetDir);

    @Override
    public void generateFiles() {
        File[] applications = findApplications(baseDir);
        File[] environments = findEnvironments(baseDir);

        LOG.debug("Creating config for applications {} in environments {}", applications, environments);

        processGlobalFilesToAll(applications, environments);
        processApplicationFiles(applications, environments);
        processEnvironmentFiles(applications, environments);
        processApplicationEnvironmentFiles(applications, environments);
    }

    private void processApplicationFiles(File[] applications, File[] environments) {
        LOG.debug("Processing application config files");

        for (File application : applications) {
            for (File environment : environments) {
                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), application, targetApplicationDir);
            }
        }
    }

    private void processApplicationEnvironmentFiles(File[] applications, File[] environments) {
        LOG.debug("Processing application/environment config files");

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
        LOG.debug("Processing environment config files");

        for (File environment : environments) {
            for (File application : applications) {
                File targetEnvironmentDir = new File(targetDir, environment.getName());
                File targetApplicationDir = new File(targetEnvironmentDir, application.getName());

                processDirectory(application.getName(), environment.getName(), environment, targetApplicationDir);
            }
        }
    }

    private void processGlobalFilesToAll(File[] applications, File[] environments) {
        LOG.debug("Processing global config files");

        File globalDir = new File(baseDir, GLOBAL_DIR);

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
        File directory = new File(baseDir, dir);
        File[] directories = directory.listFiles(File::isDirectory);

        if (directories == null) {
            return NO_FILES;
        } else {
            return directories;
        }
    }
}
