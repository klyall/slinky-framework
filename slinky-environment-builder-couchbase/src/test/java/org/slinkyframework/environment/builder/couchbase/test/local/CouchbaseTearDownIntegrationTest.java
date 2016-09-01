package org.slinkyframework.environment.builder.couchbase.test.local;

import org.junit.Test;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.local.CouchbaseTearDown;

public class CouchbaseTearDownIntegrationTest {

    private static final String[] TEST_HOST = { "dev" };

    @Test
    public void shouldBeAbleToTearDownSuccessfullyWhenNothingToDo() {

        CouchbaseTearDown testee = new CouchbaseTearDown(TEST_HOST);


        CouchbaseBuildDefinition buildDefinition = new CouchbaseBuildDefinition("Test TearDown", "nonExistentBucket");

        testee.tearDown(buildDefinition);

    }
}
