package org.slinkyframework.environment.builder.couchbase.test.local;

import com.couchbase.client.java.view.DefaultView;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.docker.DockerCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;

import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.bucketExists;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.hasView;

public class LocalCouchbaseEnvironmentBuilderIntegrationTest {

    private static final String TEST_HOST = "dev";
    private static final String TEST_BUCKET_NAME1 = "testBucket1";
    private static final String TEST_BUCKET_NAME2 = "testBucket2";
    private static final String TEST_DOCUMENT_PACKAGE = "org.example";
    private static final String TEST_DOCUMENT_CLASS_NAME = "ExampleDocument";

    private EnvironmentBuilder testee;
    private Set<CouchbaseBuildDefinition> buildDefinitions;
    private CouchbaseBuildDefinition definition1;
    private CouchbaseBuildDefinition definition2;

    @BeforeClass
    public static void setUpOnce() throws InterruptedException {
        // Need to have an instance of Couchbase available
        DockerCouchbaseEnvironmentBuilder dockerCouchbaseEnvironmentBuilder
                = new DockerCouchbaseEnvironmentBuilder(new LocalCouchbaseEnvironmentBuilder(TEST_HOST));

        dockerCouchbaseEnvironmentBuilder.setUp(new TreeSet<CouchbaseBuildDefinition>());

        Thread.sleep(10000);
    }

    @AfterClass
    public static void tearDownOnce() {
        DockerCouchbaseEnvironmentBuilder dockerCouchbaseEnvironmentBuilder
                = new DockerCouchbaseEnvironmentBuilder(new LocalCouchbaseEnvironmentBuilder(TEST_HOST));

        dockerCouchbaseEnvironmentBuilder.tearDown(new TreeSet<CouchbaseBuildDefinition>());
    }

    @Before
    public void setUp() {
        testee = new LocalCouchbaseEnvironmentBuilder(TEST_HOST);
        buildDefinitions = new TreeSet<>();
        definition1 = new CouchbaseBuildDefinition("Definition1", TEST_BUCKET_NAME1, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
        definition2 = new CouchbaseBuildDefinition("Definition2", TEST_BUCKET_NAME2, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
        testee.tearDown(buildDefinitions);
    }

    @After
    public void tearDown() {
        TreeSet<CouchbaseBuildDefinition> allDefinitions = new TreeSet<>();
        allDefinitions.add(definition1);
        allDefinitions.add(definition2);

        testee.tearDown(allDefinitions);
    }


    @Test
    public void shouldSetUpAndTearDownACouchbaseBucketUsingDefaultSettings() {
        buildDefinitions.add(definition1);

        testee.setUp(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, bucketExists(definition1));
        assertThat("Default view exists", TEST_HOST, hasView(definition1, CouchbaseBuildDefinition.VIEW_ALL));

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(definition1)));
    }

    @Test
    public void shouldSetUpAndTearDownMultipleCouchbaseBucketUsingDefaultSettings() {
        buildDefinitions.add(definition1);
        buildDefinitions.add(definition2);

        testee.setUp(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, bucketExists(definition1));
        assertThat("Bucket exists", TEST_HOST, bucketExists(definition2));

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(definition1)));
        assertThat("Bucket exists", TEST_HOST, not(bucketExists(definition2)));
    }

    @Test
    public void shouldBeAbleToSetUpABucketWithAView() {
        String testViewName = "testView";
        String testViewDefininiton = "function (doc, meta) { if (doc._class == 'org.example.Document') { emit(meta.id, null); } }";

        definition1.addView(DefaultView.create(testViewName, testViewDefininiton));

        buildDefinitions.add(definition1);

        testee.setUp(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, hasView(definition1, testViewName));
    }

    @Test
    public void shouldBeAbleToTearDownABucketThatDoesNotExist() {
        assertThat("Bucket exists", TEST_HOST, not(bucketExists(definition1)));

        buildDefinitions.add(definition1);

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(definition1)));
    }

//    @Test
//    public void shouldUpdateAnExsitingCouchbaseBucketUsingDefaultSettings() {
//        buildDefinitions.add(definition1);
//
//        testee.setUp(buildDefinitions);
//
//        definition1.setBucketSizeInMB(200);
//
//        testee.setUp(buildDefinitions);
//
//        assertThat("Bucket exists", definition1, bucketExists());
//    }
}
