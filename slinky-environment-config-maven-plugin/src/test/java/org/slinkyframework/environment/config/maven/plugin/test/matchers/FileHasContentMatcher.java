package org.slinkyframework.environment.config.maven.plugin.test.matchers;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


public class FileHasContentMatcher extends TypeSafeMatcher<File> {

    private final Matcher<?> valueMatcher;

    private FileHasContentMatcher(Matcher<String> valueMatcher) {
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(File file) {
        if (file.exists()) {
            String contents = readFile(file);

            return valueMatcher.matches(contents);
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("File should have content "));
        description.appendDescriptionOf(valueMatcher);
    }

    @Override
    protected void describeMismatchSafely(File file, Description description) {
        if (file.exists()) {
            String contents = readFile(file);

            description.appendText(String.format("File %s content ", file));
            valueMatcher.describeMismatch(contents, description);
        } else {
            description.appendText(String.format("File '%s' does not exist", file));
        }
    }

    private String readFile(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            return "";
        }
    }

    public static FileHasContentMatcher hasContent(Matcher<String> valueMatcher) {
        return new FileHasContentMatcher(valueMatcher);
    }
}
