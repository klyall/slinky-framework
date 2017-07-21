package org.slinkyframework.environment.config.maven.plugin.test.config.templates;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;
import org.slinkyframework.environment.config.maven.plugin.config.templates.FilterFileGenerator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileHasContentMatcher.hasContent;

public class FilterFileGeneratorIntegrationTest {

    public static final String FILE1_TXT = "file1.txt";
    public static final String FILE2_TXT = "file2.txt";
    public static final String FILE3_TXT = "file3.txt";
    public static final String FILE4_TXT = "file4.txt";

    private LinkedHashSet<String> delimiters = new LinkedHashSet<>();

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(FILE1_TXT));
        FileUtils.deleteDirectory(new File(FILE2_TXT));
        FileUtils.deleteDirectory(new File(FILE3_TXT));
        FileUtils.deleteDirectory(new File(FILE4_TXT));

        delimiters.add("{{*}}");
    }

    @Test
    public void shouldGenerateAFile() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file1.txt.tmpl");

        Properties properties = new Properties();
        properties.put("message", "success");

        FilterFileGenerator generator = new FilterFileGenerator(targetDir, properties, delimiters);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, FILE1_TXT);

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo("success")));
    }

    @Test
    public void shouldGenerateAFileWithPropertiesWithDots() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file2.txt.tmpl");

        String message = "still successful";

        Properties properties = new Properties();
        properties.put("another.message", message);

        FilterFileGenerator generator = new FilterFileGenerator(targetDir, properties, delimiters);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, FILE2_TXT);

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo(message)));
    }

    @Test
    public void shouldGenerateAFileWithPropertiesWithDotsAndValuesAtEachLevel() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file3.txt.tmpl");

        String message = "level1=level1\nlevel2=level2\n";

        Properties properties = new Properties();
        properties.put("level1", "level1");
        properties.put("level1.level2", "level2");

        FilterFileGenerator generator = new FilterFileGenerator(targetDir, properties, delimiters);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, FILE3_TXT);

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo(message)));
    }

    @Test(expected = EnvironmentConfigException.class)
    public void shouldThrowAnExceptionIfMissingAProperty() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file4.txt.tmpl");

        Properties properties = new Properties();

        FilterFileGenerator generator = new FilterFileGenerator(targetDir, properties, delimiters);
        generator.setFailOnMissingProperty(true);

        generator.generateFile(templateFile);
    }
}
