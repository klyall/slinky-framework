package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.cluster.ClusterManager;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.getCluster;

public class CouchbaseTearDown {

    private String[] hosts;

    public CouchbaseTearDown(String[] hosts) {
        this.hosts = hosts;
    }

    public void tearDown(CouchbaseBuildDefinition buildDefinition) {
        Cluster cluster = getCluster(hosts);
        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        clusterManager.removeBucket(buildDefinition.getBucketName());
    }
}
