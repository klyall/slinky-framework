package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.cluster.ClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import java.net.ConnectException;

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.closeBucket;
import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.getCluster;

public class CouchbaseTearDown {

    private static final Logger LOG = LoggerFactory.getLogger(CouchbaseTearDown.class);

    private String[] hosts;

    public CouchbaseTearDown(String[] hosts) {
        this.hosts = hosts;
    }

    public void tearDown(CouchbaseBuildDefinition buildDefinition) {
        Cluster cluster = getCluster(hosts);
        closeBucket(buildDefinition.getBucketName());

        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        try {
            if (clusterManager.hasBucket(buildDefinition.getBucketName())) {
                clusterManager.removeBucket(buildDefinition.getBucketName());
            }
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                LOG.warn("Could not connect to Couchbase: {}", e.getCause().getMessage());
            } else {
                throw e;
            }
        }
    }
}
