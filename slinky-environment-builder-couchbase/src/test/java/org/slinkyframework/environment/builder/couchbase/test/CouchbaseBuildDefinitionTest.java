package org.slinkyframework.environment.builder.couchbase.test;

import com.couchbase.client.java.view.DesignDocument;
import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.DocumentDefinition;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CouchbaseBuildDefinitionTest {

    private static final String TEST_BUCKET_NAME = "testBucket";
    private static final String TEST_DOCUMENT_1_PACKAGE = "org.example";
    private static final String TEST_DOCUMENT_1_CLASS_NAME = "ExampleDocument";
    private static final String TEST_DOCUMENT_2_PACKAGE = "org.another.example";
    private static final String TEST_DOCUMENT_2_CLASS_NAME = "AnotherDocument";

    private CouchbaseBuildDefinition testee;

    @Before
    public void setUp() {
        testee = new CouchbaseBuildDefinition("Definition1", TEST_BUCKET_NAME, TEST_DOCUMENT_1_PACKAGE, TEST_DOCUMENT_1_CLASS_NAME);
    }

    @Test
    public void shouldBeAbleToStoreMultipleDocumentsInTheSameBucket() {
        testee.addDocumentDefinition(new DocumentDefinition(TEST_DOCUMENT_1_PACKAGE, TEST_DOCUMENT_1_CLASS_NAME));
        testee.addDocumentDefinition(new DocumentDefinition(TEST_DOCUMENT_2_PACKAGE, TEST_DOCUMENT_2_CLASS_NAME));

        assertThat("Document definitions", testee.getDocumentDefinitions().size(), is(equalTo(2)));
    }

    @Test
    public void shouldCreateDesignDocumentForEachDocumentDefinition() {
        testee.addDocumentDefinition(new DocumentDefinition(TEST_DOCUMENT_1_PACKAGE, TEST_DOCUMENT_1_CLASS_NAME));
        testee.addDocumentDefinition(new DocumentDefinition(TEST_DOCUMENT_2_PACKAGE, TEST_DOCUMENT_2_CLASS_NAME));

        assertThat("Design documents", testee.getDesignDocuments().size(), is(equalTo(2)));
    }

    @Test
    public void shouldDefineDesignDocumentName() {
        testee = new CouchbaseBuildDefinition("Definition1", TEST_BUCKET_NAME, TEST_DOCUMENT_1_PACKAGE, TEST_DOCUMENT_1_CLASS_NAME);

        String expectedDesignDocumentName = "exampleDocument";
        DesignDocument designDocument = testee.getDesignDocuments().get(0);

        assertThat("Design document name", designDocument.name(), is(equalTo(expectedDesignDocumentName)));
    }

    @Test
    public void shouldDefineSpatialDesignDocumentName() {
        assertThat("Design document name", testee.getSpatialDesignDocumentName(), is(equalTo("spatial")));
    }

    @Test
    public void shouldLoadSpatialViewFromClasspath() {
        String name         = "byLocation";
        String filename     = "test-views/dummy-view.js";
        String expectedMap  = "line 1\nline 2";

        testee.addSpatialView(name, filename);

        assertThat("Spatial views", testee.getSpatialViews().size(), is(equalTo(1)));
        assertThat("Spatial view definition", testee.getSpatialViews().get(0).map(), is(equalTo(expectedMap)));
    }
}
