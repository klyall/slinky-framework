package org.slinkyframework.environment.config.maven.plugin.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;

public class FileDoesNotExistMatcher extends TypeSafeMatcher<File> {

    private FileDoesNotExistMatcher() {
    }

    @Override
    protected boolean matchesSafely(File file) {
        return !file.exists();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("File should not exist");
    }

    @Override
    protected void describeMismatchSafely(File file, Description description) {
        description.appendText("File exists: ");
        description.appendValue(file);
    }

    public static FileDoesNotExistMatcher fileDoesNotExist() {
        return new FileDoesNotExistMatcher();
    }
}
