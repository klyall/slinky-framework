package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.view.DesignDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

public class CouchbaseSetUp {

    private static Logger LOG = LoggerFactory.getLogger(CouchbaseSetUp.class);

    public void setUp(String targetHost, CouchbaseBuildDefinition buildDefinition) {
        LOG.info("Creating bucket '{}'", buildDefinition.getBucketName());


        Cluster cluster = CouchbaseCluster.create(targetHost);

        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        if (!clusterManager.hasBucket(buildDefinition.getBucketName())) {
            createBucket(clusterManager, buildDefinition);
        }

        Bucket bucket = cluster.openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword());
        BucketManager bucketManager = bucket.bucketManager();

        // Initialize design document
        DesignDocument designDoc = DesignDocument.create(
                buildDefinition.getDesignDocumentName(),
                buildDefinition.getViews());
//                Arrays.asList(
//                        DefaultView.create("all",
//                                "function (doc, meta) { if (doc._class == 'com.rbsg.branch.repository.domain.BranchDocument') { emit(meta.id, null); } }")
//                        , DefaultView.create("by_brand",
//                                "function (doc, meta) { if (doc._class == 'com.rbsg.branch.repository.domain.BranchDocument') { emit(doc.brands, null); } }")
//                        SpatialView.create("by_coordinates",
//                                "function (doc, meta) { if (doc.type == 'landmark') { emit([doc.geo.lon, doc.geo.lat], null); } }")
//                )
//        );

        // Insert design document into the bucket
        bucketManager.upsertDesignDocument(designDoc);

        bucket.query(N1qlQuery.simple(
                Index.createPrimaryIndex().on(bucket.name())
        ));

        cluster.disconnect();
    }

    private void createBucket(ClusterManager clusterManager, CouchbaseBuildDefinition buildDefinition) {
        BucketSettings bucketSettings = new DefaultBucketSettings.Builder()
                .type(BucketType.COUCHBASE)
                .name(buildDefinition.getBucketName())
                .password(buildDefinition.getBucketPassword())
                .quota(buildDefinition.getBucketSizeInMB())
                .replicas(1)
                .indexReplicas(true)
                .enableFlush(true)
                .build();

        clusterManager.insertBucket(bucketSettings);
    }
}
