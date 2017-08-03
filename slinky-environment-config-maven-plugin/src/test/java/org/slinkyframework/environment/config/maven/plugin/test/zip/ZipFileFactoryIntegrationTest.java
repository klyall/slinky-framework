package org.slinkyframework.environment.config.maven.plugin.test.zip;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.zip.ZipFileFactory;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;

public class ZipFileFactoryIntegrationTest {

    public static final File TARGET_DIR = new File("target/generated-config");
    private static final String VERSION = "1.0.0";

    @Test
    public void shouldCreateAZipFileForEachApplicationAndEnvironment() {

        ZipFileFactory zipFileFactory = new ZipFileFactory(TARGET_DIR, VERSION);
        zipFileFactory.createZipFiles();

        assertThat(new File(TARGET_DIR + "/env1/app1-config-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2-config-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1-config-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2-config-1.0.0.zip"), fileExists());
    }

    @Test
    public void shouldNotThrowExceptionWhenNothingToZip() {

        File targetDir = new File("does/not/exist");

        ZipFileFactory zipFileFactory = new ZipFileFactory(targetDir, VERSION);
        zipFileFactory.createZipFiles();

    }
}
