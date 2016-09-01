package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.search.UpsertSearchIndexRequest;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonNode;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.view.DesignDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.EnvironmentBuilderException;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.util.function.BiConsumer;

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.getCluster;
import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.openBucket;

public class CouchbaseSetUp {

    private static final int ONE_SECOND = 1000;
    private static final long THIRTY_SECONDS = 30000;
    private static final Logger LOG = LoggerFactory.getLogger(CouchbaseSetUp.class);

    private String[] hosts;

    public CouchbaseSetUp(String[] hosts) {
        this.hosts = hosts;
    }

    public void setUp(CouchbaseBuildDefinition buildDefinition) {

        Cluster cluster = getCluster(hosts);
        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        waitFor(this::couchbaseHealthy, hosts[0], buildDefinition);

        if (!clusterManager.hasBucket(buildDefinition.getBucketName())) {
            createBucket(clusterManager, buildDefinition);
        }

        waitFor(this::couchbaseHealthy, hosts[0], buildDefinition);

        Bucket bucket = openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword(), hosts);

        waitFor(this::couchbaseHealthy, hosts[0], buildDefinition);

        waitFor(this::createMapReduceViews, bucket, buildDefinition);
        waitFor(this::createSpatialViews, bucket, buildDefinition);
        waitFor(this::createPrimaryIndex, bucket, buildDefinition);
        createFullTextIndex(cluster, bucket, buildDefinition);

//        bucket.close();
    }

    private void createBucket(ClusterManager clusterManager, CouchbaseBuildDefinition buildDefinition) {
        LOG.info("Creating bucket '{}'", buildDefinition.getBucketName());

        BucketSettings bucketSettings = new DefaultBucketSettings.Builder()
                .type(BucketType.COUCHBASE)
                .name(buildDefinition.getBucketName())
                .password(buildDefinition.getBucketPassword())
                .quota(buildDefinition.getBucketSizeInMB())
                .build();

        clusterManager.insertBucket(bucketSettings);

        LOG.debug("Bucket '{}' created", buildDefinition.getBucketName());
    }

    private void createMapReduceViews(Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        LOG.debug("Creating {} views in '{}'", buildDefinition.getDesignDocuments().size(), buildDefinition.getBucketName());

        // Insert design document into the bucket
        BucketManager bucketManager = bucket.bucketManager();

        buildDefinition.getDesignDocuments().forEach(designDoc -> bucketManager.upsertDesignDocument(designDoc));

        LOG.debug("Views created in '{}'", buildDefinition.getBucketName());
    }

    private void createSpatialViews(Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        if (buildDefinition.getSpatialViews().size() == 0) {
            // No views to create
            return;
        }

        LOG.debug("Creating {} views in '{}'", buildDefinition.getSpatialViews().size(), buildDefinition.getBucketName());

        // Initialize design document
        DesignDocument designDoc = DesignDocument.create(
                buildDefinition.getSpatialDesignDocumentName(),
                buildDefinition.getSpatialViews());

        // Insert design document into the bucket
        BucketManager bucketManager = bucket.bucketManager();
        bucketManager.upsertDesignDocument(designDoc);

        LOG.debug("Views created in '{}'", buildDefinition.getBucketName());
    }

    private void createPrimaryIndex(Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        LOG.debug("Creating primary index in bucket '{}'", buildDefinition.getBucketName());

        N1qlQueryResult result = bucket.query(N1qlQuery.simple(
                Index.createPrimaryIndex().on(bucket.name())
        ));

        if (!result.finalSuccess()) {
            LOG.error("Failed to create primary index: {}", result.errors());
            throw new EnvironmentBuilderException("Failed to create primary index for " + buildDefinition.getBucketName());
        }

        LOG.debug("Primary index created in bucket '{}'", buildDefinition.getBucketName());
    }

    private void createFullTextIndex(Cluster cluster, Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        // This is a bit of a work around as there is not a well documented interface to create a full text index
        // using the couchbase-java-client. Replace this code when the API catches up.

        LOG.debug("Creating full text index in bucket '{}'", buildDefinition.getBucketName());

        StringBuilder sb = new StringBuilder();
        sb.append(buildDefinition.getFullTextIndexName());
        sb.append("?indexName=").append(buildDefinition.getFullTextIndexName());
        sb.append("&indexParams=%7B%22mapping%22:%7B%22default_mapping%22:%7B%22enabled%22:true,%22dynamic%22:true,%22display_order%22:%220%22%7D,%22type_field%22:%22type%22,%22default_type%22:%22_default%22,%22default_analyzer%22:%22standard%22,%22default_datetime_parser%22:%22dateTimeOptional%22,%22default_field%22:%22_all%22,%22byte_array_converter%22:%22json%22,%22store_dynamic%22:false,%22index_dynamic%22:true%7D,%22store%22:%7B%22kvStoreName%22:%22forestdb%22%7D%7D&indexType=fulltext-index&planParams=%7B%0A++%22maxPartitionsPerPIndex%22:+32,%0A++%22numReplicas%22:+0,%0A++%22hierarchyRules%22:+null,%0A++%22nodePlanParams%22:+null,%0A++%22pindexWeights%22:+null,%0A++%22planFrozen%22:+false%0A%7D&prevIndexUUID=");
        sb.append("&sourceName=").append(buildDefinition.getBucketName());
        sb.append("&sourceParams=%7B%22clusterManagerBackoffFactor%22:0,%22clusterManagerSleepInitMS%22:0,%22clusterManagerSleepMaxMS%22:2000,%22dataManagerBackoffFactor%22:0,%22dataManagerSleepInitMS%22:0,%22dataManagerSleepMaxMS%22:2000,%22feedBufferSizeBytes%22:0,%22feedBufferAckThreshold%22:0%7D&sourceType=couchbase");
        sb.append("&sourceUUID=");
        sb.append(bucket.bucketManager().info().raw().get("uuid").toString());

        String payload = "";
        UpsertSearchIndexRequest request = new UpsertSearchIndexRequest(sb.toString(), payload, buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        Observable<CouchbaseResponse> observable = cluster.core().send(request);

        observable.subscribe(new Subscriber<CouchbaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                LOG.error("Failed to create Full text index created in bucket '{}': {}", buildDefinition.getBucketName(), throwable);
            }

            @Override
            public void onNext(CouchbaseResponse response) {
                if (response.status().isSuccess()) {
                    LOG.debug("Full text index created in bucket '{}'", buildDefinition.getBucketName());
                } else {
                    throw new EnvironmentBuilderException(String.format("Failed to create Full Text Index in bucket '%s': %s", bucket.name(), response));
                }
            }
        });

        observable.toBlocking().subscribe();
    }

    private void waitFor(BiConsumer<Bucket, CouchbaseBuildDefinition> function, Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
        retryPolicy.setTimeout(THIRTY_SECONDS);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(ONE_SECOND);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setThrowLastExceptionOnExhausted(true);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        retryTemplate.execute(rc -> { function.accept(bucket, buildDefinition); return null; });
    }

    private void waitFor(BiConsumer<String, CouchbaseBuildDefinition> function, String host, CouchbaseBuildDefinition buildDefinition) {
        TimeoutRetryPolicy retryPolicy = new TimeoutRetryPolicy();
        retryPolicy.setTimeout(THIRTY_SECONDS);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(ONE_SECOND);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setThrowLastExceptionOnExhausted(true);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        retryTemplate.execute(rc -> { function.accept(host, buildDefinition); return null; });
    }

    private boolean couchbaseHealthy(String host, CouchbaseBuildDefinition buildDefinition) {
        LOG.debug("Checking if Couchbase is healthy");

        String url = String.format("http://%s:%s/pools/default/", host, 8091);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthorization(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword())
                .build();

        String body = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readValue(body, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode statusNode = node.at("/nodes/0/status");
        String status = statusNode.asText();

        if ("healthy".equals(status)) {
            LOG.debug("Couchbase is healthy.");
        } else {
            LOG.debug("Couchbase is not healthy. Currently '{}'", status);
            throw new EnvironmentBuilderException(String.format("Couchbase Node status was: %s", status));
        }
        return true;
    }
}
