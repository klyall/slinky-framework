package org.slinkyframework.environment.builder.maven.plugin.templates;

import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.EnvironmentConfigException;
import org.slinkyframework.environment.config.maven.plugin.templates.PebbleFileGenerator;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.builder.maven.plugin.matchers.FileExistsMatcher.fileExists;
import static org.slinkyframework.environment.builder.maven.plugin.matchers.FileHasContentMatcher.hasContent;

public class PebbleFileGeneratorIntegrationTest {

    @Test
    public void shouldGenerateAFile() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file1.txt.tmpl");

        Map<String, Object> model = new TreeMap<>();
        model.put("message", "success");

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, model);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, "file1.txt");

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo("success")));
    }

    @Test
    public void shouldGenerateAFileWithPropertiesWithDots() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file2.txt.tmpl");

        String message = "still successful";

        Map<String, Object> model = new TreeMap<>();
        model.put("another.message", message);

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, model);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, "file2.txt");

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo(message)));
    }

    @Test(expected = EnvironmentConfigException.class)
    public void shouldThrowAnExceptionIfMissingAProperty() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file1.txt.tmpl");

        String message = "still successful";

        Map<String, Object> model = new TreeMap<>();
        model.put("missing", message);

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, model);

        generator.generateFile(templateFile);
    }
}
