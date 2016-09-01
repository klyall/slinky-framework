package org.slinkyframework.environment.builder.couchbase.test.local;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.SearchQueryResult;
import com.couchbase.client.java.search.query.MatchQuery;
import com.couchbase.client.java.search.query.SearchQuery;
import com.couchbase.client.java.view.DefaultView;
import com.couchbase.client.java.view.SpatialViewQuery;
import com.couchbase.client.java.view.SpatialViewResult;
import com.couchbase.client.java.view.Stale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.DocumentDefinition;
import org.slinkyframework.environment.builder.couchbase.docker.DockerCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.local.ConnectionManager;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;

import java.util.TreeSet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.openBucket;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.bucketExists;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.bucketIsAccessible;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.hasView;

public class LocalCouchbaseEnvironmentBuilderIntegrationTest {

    private static final String TEST_HOST = "dev";
    private static final String TEST_BUCKET_PASSWORD = "password1";
    private static final String TEST_DOCUMENT_PACKAGE = "org.example";
    private static final String TEST_DOCUMENT_CLASS_NAME = "ExampleDocument";

    private EnvironmentBuilder testee;
    private TreeSet<CouchbaseBuildDefinition> bucketsCreated = new TreeSet<>();

    @BeforeClass
    public static void setUpOnce() throws InterruptedException {
        // Need to have an instance of Couchbase available
        DockerCouchbaseEnvironmentBuilder dockerCouchbaseEnvironmentBuilder
                = new DockerCouchbaseEnvironmentBuilder(new LocalCouchbaseEnvironmentBuilder(TEST_HOST));

        dockerCouchbaseEnvironmentBuilder.setUp(new TreeSet<CouchbaseBuildDefinition>());
    }

    @AfterClass
    public static void tearDownOnce() {
        DockerCouchbaseEnvironmentBuilder dockerCouchbaseEnvironmentBuilder
                = new DockerCouchbaseEnvironmentBuilder(new LocalCouchbaseEnvironmentBuilder(TEST_HOST));

        dockerCouchbaseEnvironmentBuilder.tearDown(new TreeSet<CouchbaseBuildDefinition>());

        ConnectionManager.disconnect();
    }

    @Before
    public void setUp() {
        testee = new LocalCouchbaseEnvironmentBuilder(TEST_HOST);
    }

    @After
    public void tearDown() throws InterruptedException {
        testee.tearDown(bucketsCreated);
    }


    @Test
    public void shouldSetUpAndTearDownACouchbaseBucketUsingDefaultSettings() {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket1");
        CouchbaseBuildDefinition buildDefinition = buildDefinitions.first();

        testee.setUp(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, bucketExists(buildDefinition));
        assertThat("Bucket is accessible", TEST_HOST, bucketIsAccessible(buildDefinition));
        assertThat("Default view exists", TEST_HOST, hasView(buildDefinition, buildDefinition.getDesignDocuments().get(0).name(), DocumentDefinition.VIEW_ALL));

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(buildDefinition)));
    }


    @Test
    public void shouldSetUpAndTearDownMultipleCouchbaseBucketUsingDefaultSettings() {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket2", "testBucket3");
        CouchbaseBuildDefinition buildDefinition1 = buildDefinitions.first();
        CouchbaseBuildDefinition buildDefinition2 = buildDefinitions.last();

        testee.setUp(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, bucketExists(buildDefinition1));
        assertThat("Bucket is accessible", TEST_HOST, bucketIsAccessible(buildDefinition1));

        assertThat("Bucket exists", TEST_HOST, bucketExists(buildDefinition2));
        assertThat("Bucket is accessible", TEST_HOST, bucketIsAccessible(buildDefinition2));

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(buildDefinition1)));
        assertThat("Bucket exists", TEST_HOST, not(bucketExists(buildDefinition2)));
    }

    @Test
    public void shouldBeAbleToSetUpABucketWithAView() {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket4");
        CouchbaseBuildDefinition buildDefinition = buildDefinitions.first();

        String testViewName = "testView";
        String testViewDefininiton = "function (doc, meta) { if (doc._class == 'org.example.Document') { emit(meta.id, null); } }";

        DocumentDefinition documentDefinition = new DocumentDefinition(TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
        documentDefinition.addView(DefaultView.create(testViewName, testViewDefininiton));

        buildDefinition.addDocumentDefinition(documentDefinition);

        testee.setUp(buildDefinitions);

        assertThat("Bucket has view", TEST_HOST, hasView(buildDefinition, documentDefinition.getDesignDocumentName(), testViewName));

        bucketsCreated.add(buildDefinition);
    }

    @Test
    public void shouldBeAbleToTearDownABucketThatDoesNotExist() {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket5");
        CouchbaseBuildDefinition buildDefinition = buildDefinitions.first();

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(buildDefinition)));

        buildDefinitions.add(buildDefinition);

        testee.tearDown(buildDefinitions);

        assertThat("Bucket exists", TEST_HOST, not(bucketExists(buildDefinition)));
    }

    @Test
    public void shouldBeAbleToDoASpatialQuery() {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket6");
        CouchbaseBuildDefinition buildDefinition = buildDefinitions.first();

        String viewName = "byLocation";

        buildDefinition.addSpatialView(viewName, "test-views/spatial.js");

        testee.setUp(buildDefinitions);

        Bucket bucket = openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword(), TEST_HOST);

        createLocation(bucket, 1, "Big Ben", 51.5005008, -0.1247279);
        createLocation(bucket, 2, "House Of Commons", 51.5005008, -0.1247279);
        createLocation(bucket, 3, "London Zoo", 51.5352875, -0.155619);

        JsonArray EUROPE_SOUTH_WEST = JsonArray.from(-10.8, 36.59);
        JsonArray EUROPE_NORTH_EAST = JsonArray.from(31.6, 70.67);

        SpatialViewQuery q = SpatialViewQuery.from(buildDefinition.getSpatialDesignDocumentName(), viewName)
                .stale(Stale.FALSE)
                .range(EUROPE_SOUTH_WEST, EUROPE_NORTH_EAST);

        SpatialViewResult result = bucket.query(q);

        assertThat("Spatial query success", result.success(), is(equalTo(true)));
        assertThat("Number of results", result.allRows().size(), is(equalTo(3)));

        bucketsCreated.add(buildDefinition);
    }

    @Test
    public void shouldBeAbleToDoAFreeTextSearch() throws Exception {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = createBuildDefinitions("testBucket7");
        CouchbaseBuildDefinition buildDefinition = buildDefinitions.first();

        testee.setUp(buildDefinitions);

        // Give free text index a chance to be updated
        Thread.sleep(5000);

        Bucket bucket = openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword(), TEST_HOST);

        createLocation(bucket, 1, "Big Ben", 51.5005008, -0.1247279);
        createLocation(bucket, 2, "House Of Commons", 51.5005008, -0.1247279);
        createLocation(bucket, 3, "London Zoo", 51.5352875, -0.155619);

        SearchQuery ftq = MatchQuery.on(buildDefinition.getFullTextIndexName())
                .match("Zoo")
                .limit(3)
                .build();

        SearchQueryResult result = bucket.query(ftq);

        assertThat("Number of results", result.totalHits(), is(equalTo(1L)));

        bucketsCreated.add(buildDefinition);
    }

    private TreeSet<CouchbaseBuildDefinition> createBuildDefinitions(String... bucketNames) {
        TreeSet<CouchbaseBuildDefinition> buildDefinitions = new TreeSet<>();

        for (String bucketName: bucketNames) {
            CouchbaseBuildDefinition definition1 = new CouchbaseBuildDefinition("Definition1", bucketName, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
            definition1.setBucketPassword(TEST_BUCKET_PASSWORD);

            buildDefinitions.add(definition1);
        }

        return buildDefinitions;
    }

    private void createLocation(Bucket bucket, int id, String name, Double latitude, Double longitude) {
        JsonObject user = JsonObject.empty()
                .put("name", name)
                .put("type", "location")
                .put("lat", latitude)
                .put("long", longitude);

        JsonDocument doc = JsonDocument.create(String.valueOf(id), user);
        JsonDocument response = bucket.upsert(doc);
    }

}
