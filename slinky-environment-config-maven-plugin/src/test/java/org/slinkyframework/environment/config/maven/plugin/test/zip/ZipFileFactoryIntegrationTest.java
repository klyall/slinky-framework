package org.slinkyframework.environment.config.maven.plugin.test.zip;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.zip.ZipFileFactory;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;

public class ZipFileFactoryIntegrationTest {

    public static final File TARGET_DIR = new File("src/test/resources/package-test");
    private static final String VERSION = "1.0.0";

    private File file1;
    private File file2;
    private File file3;
    private File file4;

    @Before
    public void setUp() {
        file1 = new File(TARGET_DIR + "/env1/app1-config-1.0.0.zip");
        file2 = new File(TARGET_DIR + "/env1/app2-config-1.0.0.zip");
        file3 = new File(TARGET_DIR + "/env2/app1-config-1.0.0.zip");
        file4 = new File(TARGET_DIR + "/env2/app2-config-1.0.0.zip");

        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
    }

    @Test
    public void shouldCreateAZipFileForEachApplicationAndEnvironment() {
        ZipFileFactory testee = new ZipFileFactory(TARGET_DIR, VERSION);

        testee.createZipFiles();

        assertThat(file1, fileExists());
        assertThat(file2, fileExists());
        assertThat(file3, fileExists());
        assertThat(file4, fileExists());
    }

    @Test
    public void shouldNotThrowExceptionWhenNothingToZip() {
        File targetDir = new File("does/not/exist");

        ZipFileFactory testee = new ZipFileFactory(targetDir, VERSION);

        testee.createZipFiles();
    }
}
