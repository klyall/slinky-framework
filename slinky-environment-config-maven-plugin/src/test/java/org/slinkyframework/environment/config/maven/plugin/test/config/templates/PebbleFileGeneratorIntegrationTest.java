package org.slinkyframework.environment.config.maven.plugin.test.config.templates;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.junit.Test;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;
import org.slinkyframework.environment.config.maven.plugin.config.templates.PebbleFileGenerator;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileExistsMatcher.fileExists;
import static org.slinkyframework.environment.config.maven.plugin.test.matchers.FileHasContentMatcher.hasContent;

public class PebbleFileGeneratorIntegrationTest {

    @Test
    public void shouldGenerateAFile() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file1.txt.tmpl");

        Config config = ConfigFactory.load().withValue("message", ConfigValueFactory.fromAnyRef("success"));

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, config);

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

        Config config = ConfigFactory.load().withValue("another.message", ConfigValueFactory.fromAnyRef(message));

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, config);

        generator.generateFile(templateFile);

        File generatedFile = new File(targetDir, "file2.txt");

        assertThat(generatedFile, fileExists());
        assertThat(generatedFile, hasContent(equalTo(message)));
    }

    @Test(expected = EnvironmentConfigException.class)
    public void shouldThrowAnExceptionIfMissingAProperty() {
        File targetDir = new File("target");
        File templateFile = new File("src/test/resources/file1.txt.tmpl");

        Config config = ConfigFactory.load();

        PebbleFileGenerator generator = new PebbleFileGenerator(targetDir, config);

        generator.generateFile(templateFile);
    }
}
