package org.slinkyframework.environment.config.maven.plugin.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang3.StringUtils.removeStart;

public class Zipper {

    private static final Logger LOG = LoggerFactory.getLogger(Zipper.class);

    public static void zipDirectory(File sourceDir, File outputFile) {
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("Directory does not exist: " + sourceDir);
        }

        LOG.debug("Zipping directory {} into {}", sourceDir, outputFile);

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

            StringBuilder sb = new StringBuilder();
            sb.append(removeStart(sourceDir.getAbsolutePath().replace(rootDir.getAbsolutePath(), ""), "/"));
            sb.append("/");
            sb.append(file.getName());

            if (file.isDirectory()) {
                sb.append("/");
            }

            String name =  removeStart(sb.toString(), "/");

            ZipEntry entry = new ZipEntry(name);
            zos.putNextEntry(entry);

            if (file.isDirectory()) {
                compressDirectoryToZipfile(rootDir, new File(sourceDir, file.getName()), zos);
            } else {
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
