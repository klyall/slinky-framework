package org.slinkyframework.environment.builder.couchbase.utils;

import org.apache.commons.io.IOUtils;
import org.slinkyframework.environment.builder.EnvironmentBuilderException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ClasspathUtils {

    public static String readFile(String fileName) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            String contents = IOUtils.toString(is, Charset.defaultCharset());
            return contents;

        } catch (IOException e) {
            throw new EnvironmentBuilderException("Unable to read file: " + fileName, e);
        }
    }
}
