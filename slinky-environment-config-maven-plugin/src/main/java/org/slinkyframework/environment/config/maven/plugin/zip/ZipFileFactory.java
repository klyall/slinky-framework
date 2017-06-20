package org.slinkyframework.environment.config.maven.plugin.zip;

import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;

public class ZipFileFactory {
    private File targetDir;
    private String version;

    public ZipFileFactory(File targetDir, String version) {
        this.targetDir = targetDir;
        this.version = version;
    }

    public void createZipFiles() {
        File[] environmentDirs = targetDir.listFiles(File::isDirectory);

        for (File environmentDir: environmentDirs) {
            processEnvironment(environmentDir);
        }
    }

    private void processEnvironment(File environmentDir) {
        File[] applictionDirs = environmentDir.listFiles(File::isDirectory);

        for (File applictionDir: applictionDirs) {
            File zipFile = new File(environmentDir, String.format("%s-%s.zip", applictionDir.getName(), version));
            createZipFile(applictionDir, zipFile);
        }
    }

    private void createZipFile(File sourceDir, File outputFile) {
        ZipOutputStream zos = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(outputFile);
            zos = new ZipOutputStream(fos);
            compressDirectoryToZipfile(sourceDir, sourceDir, zos);
        } catch (IOException e) {
            throw new EnvironmentConfigException(String.format("Error creating Zip file %s from %s", outputFile, sourceDir), e);
        } finally {
            closeQuietly(zos);
            closeQuietly(fos);
        }
    }

    private static void compressDirectoryToZipfile(File rootDir, File sourceDir, ZipOutputStream zos) throws IOException {
        for (File file : sourceDir.listFiles()) {
            if (file.isDirectory()) {
                compressDirectoryToZipfile(rootDir, new File(sourceDir, file.getName()), zos);
            } else {
                ZipEntry entry = new ZipEntry(sourceDir.getAbsolutePath().replace(rootDir.getAbsolutePath(), "") + file.getName());
                zos.putNextEntry(entry);

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    copy(fis, zos);
                } finally {
                    closeQuietly(fis);
                }
            }
        }
    }
}
