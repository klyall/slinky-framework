package org.slinkyframework.environment.builder.couchbase.local;

import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import java.util.Set;

public class LocalCouchbaseEnvironmentBuilder implements EnvironmentBuilder<CouchbaseBuildDefinition> {

    private final CouchbaseSetUp couchbaseSetUp;
    private final CouchbaseTearDown couchbaseTearDown;
    private String[] hosts;

    public LocalCouchbaseEnvironmentBuilder(String... hosts) {
        this.hosts = hosts;
        couchbaseSetUp = new CouchbaseSetUp(hosts);
        couchbaseTearDown = new CouchbaseTearDown(hosts);
    }

    @Override
    public void setUp(Set<CouchbaseBuildDefinition> buildDefinitions) {
        buildDefinitions.forEach(definition -> couchbaseSetUp.setUp(definition));
    }

    @Override
    public void tearDown(Set<CouchbaseBuildDefinition> buildDefinitions) {
        buildDefinitions.forEach(buildDefinition -> couchbaseTearDown.tearDown(buildDefinition));
    }

    @Override
    public void cleanUp() {
        ConnectionManager.disconnect();
    }

    public String[] getHosts() {
        return hosts;
    }
}
