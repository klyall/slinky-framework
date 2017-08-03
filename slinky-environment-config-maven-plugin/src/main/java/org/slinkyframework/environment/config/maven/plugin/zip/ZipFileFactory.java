package org.slinkyframework.environment.config.maven.plugin.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.slinkyframework.environment.config.maven.plugin.zip.Zipper.zipDirectory;

public class ZipFileFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ZipFileFactory.class);

    private File targetDir;
    private String version;

    public ZipFileFactory(File targetDir, String version) {
        this.targetDir = targetDir;
        this.version = version;
    }

    public void createZipFiles() {
        File[] environmentDirs = targetDir.listFiles(File::isDirectory);

        if (environmentDirs != null) {
            for (File environmentDir: environmentDirs) {
                processEnvironment(environmentDir);
            }
        }
    }

    private void processEnvironment(File environmentDir) {
        File[] applictionDirs = environmentDir.listFiles(File::isDirectory);

        for (File applictionDir: applictionDirs) {
            File zipFile = new File(environmentDir, String.format("%s-config-%s.zip", applictionDir.getName(), version));

            zipDirectory(applictionDir, zipFile);
        }
    }
}
