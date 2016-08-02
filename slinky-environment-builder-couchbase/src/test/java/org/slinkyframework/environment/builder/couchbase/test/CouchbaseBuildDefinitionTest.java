package org.slinkyframework.environment.builder.couchbase.test;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CouchbaseBuildDefinitionTest {

    private static final String TEST_HOST = "dev";
    private static final String TEST_BUCKET_NAME = "testBucket";
    private static final String TEST_DOCUMENT_PACKAGE = "org.example";
    private static final String TEST_DOCUMENT_CLASS_NAME = "ExampleDocument";

    private CouchbaseBuildDefinition testee;

    @Before
    public void setUp() {
        testee = new CouchbaseBuildDefinition("Definition1", TEST_HOST, TEST_BUCKET_NAME, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
    }


    @Test
    public void shouldDefineDesignDocumentName() {
        String expectedDesignDocumentName = "exampleDocument";

        assertThat("Design document name", testee.getDesignDocumentName(), is(equalTo(expectedDesignDocumentName)));
    }
}
