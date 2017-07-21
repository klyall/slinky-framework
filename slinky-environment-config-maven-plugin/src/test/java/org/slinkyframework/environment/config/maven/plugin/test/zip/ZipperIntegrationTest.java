package org.slinkyframework.environment.config.maven.plugin.test.zip;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.zip.Zipper;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.ZipHasEntryMatcher.hasEntry;

public class ZipperIntegrationTest {

    private static final File SOURCE_DIR = new File("src/test/resources/zipper-test");
    private static final File TARGET_FILE = new File("target/zipper-test.zip");

    @Test
    public void shouldCreateAZipWithRootFiles() {

        Zipper.zipDirectory(SOURCE_DIR, TARGET_FILE);

        assertThat(TARGET_FILE, hasEntry("file1.conf"));
    }

    @Test
    public void shouldCreateAZipWithFileInSubFolder() {

        Zipper.zipDirectory(SOURCE_DIR, TARGET_FILE);

        assertThat(TARGET_FILE, hasEntry("sub/"));
        assertThat(TARGET_FILE, hasEntry("sub/dir/"));
        assertThat(TARGET_FILE, hasEntry("sub/dir/file2.conf"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shoudlThrowAnExceptionIfSourceDirDoesNotExist() {

        File sourceDir = new File("does/not/exist");

        Zipper.zipDirectory(sourceDir, TARGET_FILE);
    }
}
