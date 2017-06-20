package org.slinkyframework.environment.config.maven.plugin.config.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.config.AbstractApplicationConfigFactory;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyDirectory;

public class FileApplicationConfigFactory extends AbstractApplicationConfigFactory {

    public static final String FILES_DIR = "files";

    private static final Logger LOG = LoggerFactory.getLogger(FileApplicationConfigFactory.class);

    public FileApplicationConfigFactory(File sourceDir, File targetDir) {
        super(sourceDir, targetDir);
    }

    @Override
    protected void processDirectory(String application, String environment, File sourceDir, File targetDir) {
        File filesDir = new File(sourceDir, FILES_DIR);

        if (filesDir.exists()) {
            try {
                LOG.debug("Copying directory {} to {}", filesDir, targetDir);

                copyDirectory(filesDir, targetDir);
            } catch (IOException e) {
                throw new EnvironmentConfigException(String.format("Problem copying from %s to %s", filesDir, targetDir), e);
            }
        }
    }

}
