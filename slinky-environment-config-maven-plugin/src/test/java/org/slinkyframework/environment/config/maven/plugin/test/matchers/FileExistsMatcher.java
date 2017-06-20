package org.slinkyframework.environment.config.maven.plugin.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;

public class FileExistsMatcher extends TypeSafeMatcher<File> {

    private FileExistsMatcher() {
    }

    @Override
    protected boolean matchesSafely(File file) {
        return file.exists();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("File should exist");
    }

    @Override
    protected void describeMismatchSafely(File file, Description description) {
        description.appendText("File does not exist: ");
        description.appendValue(file);
    }

    public static FileExistsMatcher fileExists() {
        return new FileExistsMatcher();
    }
}
