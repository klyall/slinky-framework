package org.slinkyframework.environment.config.maven.plugin.config.templates;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.config.maven.plugin.config.EnvironmentConfigException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TemplateDirectoryWalker extends DirectoryWalker {

    private static Logger LOG = LoggerFactory.getLogger(TemplateDirectoryWalker.class);

    private FileGenerator fileGenerator;

    public TemplateDirectoryWalker(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    public List generate(File startDirectory) {
        List results = new ArrayList();
        try {
            walk(startDirectory, results);
        } catch (IOException e) {
            throw new EnvironmentConfigException(String.format("Problem walking directory %s", startDirectory), e);
        }
        return results;
    }

    @Override
    protected boolean handleDirectory(File directory, int depth, Collection results) {
        return true;
    }

    @Override
    protected void handleFile(File templateFile, int depth, Collection results) {
        fileGenerator.generateFile(templateFile);
        results.add(templateFile);
    }
}
