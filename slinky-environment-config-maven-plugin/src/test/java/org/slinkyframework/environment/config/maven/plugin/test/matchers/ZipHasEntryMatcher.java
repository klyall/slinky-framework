package org.slinkyframework.environment.config.maven.plugin.test.matchers;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipFile;


public class ZipHasEntryMatcher extends TypeSafeMatcher<File> {

    private String entry;

    private ZipHasEntryMatcher(String entry) {
        this.entry = entry;
    }

    @Override
    protected boolean matchesSafely(File file) {
        if (file.exists()) {
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(file);
                return (zipFile.getEntry(entry) != null);
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("ZipFile should have entry "));
        description.appendValue(entry);
    }

    @Override
    protected void describeMismatchSafely(File file, Description description) {
        if (file.exists()) {
            String contents = readFile(file);

            description.appendText("does not exists");
        } else {
            description.appendText(String.format("ZipFile '%s' does not exist", file));
        }
    }

    private String readFile(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            return "";
        }
    }

    public static ZipHasEntryMatcher hasEntry(String valueMatcher) {
        return new ZipHasEntryMatcher(valueMatcher);
    }
}
