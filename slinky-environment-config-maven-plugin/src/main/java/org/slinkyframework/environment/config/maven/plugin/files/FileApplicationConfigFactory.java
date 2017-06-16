package org.slinkyframework.environment.config.maven.plugin.files;

import org.slinkyframework.environment.config.maven.plugin.AbstractApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.EnvironmentConfigException;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyDirectory;

public class FileApplicationConfigFactory extends AbstractApplicationConfigFactory {

    public static final String FILES_DIR = "files";

    public FileApplicationConfigFactory(File sourceDir, File targetDir) {
        super(sourceDir, targetDir);
    }

    @Override
    protected void processDirectory(String application, String environment, File sourceDir, File targetDir) {
        File filesDir = new File(sourceDir, FILES_DIR);

        if (filesDir.exists()) {
            try {
                copyDirectory(filesDir, targetDir);
            } catch (IOException e) {
                throw new EnvironmentConfigException(String.format("Problem copying from %s to %s", filesDir, targetDir), e);
            }
        }
    }

}
