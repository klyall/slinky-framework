package org.slinkyframework.environment.builder.couchbase.local;

import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import java.util.Set;

public class LocalCouchbaseEnvironmentBuilder implements EnvironmentBuilder<CouchbaseBuildDefinition> {

    private CouchbaseSetUp couchbaseSetUp;
    private CouchbaseTearDown couchbaseTearDown;
    private String targetHost;

    public LocalCouchbaseEnvironmentBuilder(String targetHost) {
        this.targetHost = targetHost;
        couchbaseSetUp = new CouchbaseSetUp();
        couchbaseTearDown = new CouchbaseTearDown();
    }

    @Override
    public void setUp(Set<CouchbaseBuildDefinition> buildDefinitions) {
        buildDefinitions.forEach(definition -> couchbaseSetUp.setUp(targetHost, definition));
    }

    @Override
    public void tearDown(Set<CouchbaseBuildDefinition> buildDefinitions) {
        buildDefinitions.forEach(buildDefinition -> couchbaseTearDown.tearDown(targetHost, buildDefinition));
    }
}
