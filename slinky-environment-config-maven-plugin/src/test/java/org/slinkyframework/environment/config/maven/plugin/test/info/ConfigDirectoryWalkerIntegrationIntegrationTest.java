package org.slinkyframework.environment.config.maven.plugin.test.info;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.info.ConfigDirectoryWalker;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConfigDirectoryWalkerIntegrationIntegrationTest {

    @Test
    public void shouldFindPropertyFiles() {
        File sourceDir = new File("src/test/resources");

        ConfigDirectoryWalker directoryWalker = new ConfigDirectoryWalker();
        List results = directoryWalker.walk(sourceDir);

        assertThat(results.size(), is(equalTo(5)));
    }
}
