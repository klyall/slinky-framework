#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = false,
        monochrome = false,
        snippets = SnippetType.CAMELCASE,
        format = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"},
        features = {"src/test/resources/features"},
        glue = {"cucumber.api.spring", "${package}.stepdefs"},
        tags = {"~@ignore"}
)
public class RunCucumberIntegrationTest {
}