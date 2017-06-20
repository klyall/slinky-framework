package org.slinkyframework.environment.config.maven.plugin.test.zip;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.zip.ZipFileFactory;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;

public class ZipFileFactoryTest {

    public static final File TARGET_DIR = new File("target/generated-config");
    private static final String VERSION = "1.0.0";

    @Test
    public void shouldCreateAZipFileForEachApplicationAndEnvironment() {

        ZipFileFactory zipFileFactory = new ZipFileFactory(TARGET_DIR, VERSION);
        zipFileFactory.createZipFiles();

        assertThat(new File(TARGET_DIR + "/env1/app1-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env1/app2-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app1-1.0.0.zip"), fileExists());
        assertThat(new File(TARGET_DIR + "/env2/app2-1.0.0.zip"), fileExists());
    }
}
