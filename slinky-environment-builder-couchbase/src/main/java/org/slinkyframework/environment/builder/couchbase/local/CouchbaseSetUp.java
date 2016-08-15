package org.slinkyframework.environment.builder.couchbase.local;

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

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.getCluster;

public class CouchbaseSetUp {

    private static Logger LOG = LoggerFactory.getLogger(CouchbaseSetUp.class);
    private String[] hosts;

    public CouchbaseSetUp(String[] hosts) {
        this.hosts = hosts;
    }

    public void setUp(CouchbaseBuildDefinition buildDefinition) {

        Cluster cluster = getCluster(hosts);
        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        if (!clusterManager.hasBucket(buildDefinition.getBucketName())) {
            createBucket(clusterManager, buildDefinition);
        }

        Bucket bucket = cluster.openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword());

        createViews(bucket, buildDefinition);
        createPrimaryIndex(bucket, buildDefinition);

        bucket.close();
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

    private void createViews(Bucket bucket, CouchbaseBuildDefinition buildDefinition) {
        LOG.debug("Creating {} views in '{}'", buildDefinition.getViews().size(), buildDefinition.getBucketName());

        // Initialize design document
        DesignDocument designDoc = DesignDocument.create(
                buildDefinition.getDesignDocumentName(),
                buildDefinition.getViews());

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
            LOG.error("Failed to create primary index: {}", result.status());
            throw new EnvironmentBuilderException("Failed to create primary index for " + buildDefinition.getBucketName());
        }

        LOG.debug("Primary index created in bucket '{}'", buildDefinition.getBucketName());
    }
}
