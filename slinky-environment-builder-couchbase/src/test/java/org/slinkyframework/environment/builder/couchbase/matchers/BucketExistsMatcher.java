package org.slinkyframework.environment.builder.couchbase.matchers;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.cluster.ClusterManager;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

public class BucketExistsMatcher extends TypeSafeMatcher<CouchbaseBuildDefinition> {

    @Override
    protected boolean matchesSafely(CouchbaseBuildDefinition buildDefinition) {
        Cluster cluster = CouchbaseCluster.create(buildDefinition.getHosts());

        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        return clusterManager.hasBucket(buildDefinition.getBucketName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("bucket exists");
    }

    @Override
    protected void describeMismatchSafely(CouchbaseBuildDefinition buildDefinition, Description mismatchDescription) {
        mismatchDescription.appendText("bucket does not exist");
    }
}
