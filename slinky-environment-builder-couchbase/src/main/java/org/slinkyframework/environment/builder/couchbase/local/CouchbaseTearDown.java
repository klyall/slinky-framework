package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.cluster.ClusterManager;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

public class CouchbaseTearDown {

    public void tearDown(String targetHost, CouchbaseBuildDefinition buildDefinition) {
        Cluster cluster = CouchbaseCluster.create(targetHost);

        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        clusterManager.removeBucket(buildDefinition.getBucketName());

        cluster.disconnect();
    }
}
