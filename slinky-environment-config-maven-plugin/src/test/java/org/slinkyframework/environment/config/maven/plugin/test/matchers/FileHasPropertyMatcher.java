package org.slinkyframework.environment.config.maven.plugin.test.matchers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;

public class FileHasPropertyMatcher extends TypeSafeMatcher<File> {

    private final String propertyName;
    private final Matcher<?> valueMatcher;

    private FileHasPropertyMatcher(String propertyName, Matcher<String> valueMatcher) {
        this.propertyName = propertyName;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(File file) {
        if (file.exists()) {
            Config config = ConfigFactory.parseFile(file);

            return valueMatcher.matches(config.getString(propertyName));
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("File should have property \"%s\" with value ", propertyName));
        description.appendDescriptionOf(valueMatcher);
    }

    @Override
    protected void describeMismatchSafely(File file, Description description) {
        if (file.exists()) {
            Config config = ConfigFactory.parseFile(file);


            description.appendText(String.format("File '%s' with property \"%s\" ", file, propertyName));
            valueMatcher.describeMismatch(config.getString(propertyName), description);
        } else {
            description.appendText(String.format("File '%s' does not exist", file));
        }
    }

    public static FileHasPropertyMatcher hasProperty(String propertyName, Matcher<String> valueMatcher) {
        return new FileHasPropertyMatcher(propertyName, valueMatcher);
    }
}
