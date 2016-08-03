package org.slinkyframework.environment.builder.couchbase.matchers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.View;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import java.util.List;

public class HasViewMatcher extends TypeSafeMatcher<String> {

    private CouchbaseBuildDefinition buildDefinition;
    private String expectedViewName;

    public HasViewMatcher(CouchbaseBuildDefinition buildDefinition, String expectedViewName) {
        this.buildDefinition = buildDefinition;
        this.expectedViewName = expectedViewName;
    }

    @Override
    protected boolean matchesSafely(String host) {
        Cluster cluster = CouchbaseCluster.create(host);

        ClusterManager clusterManager = cluster.clusterManager(buildDefinition.getAdminUsername(), buildDefinition.getAdminPasssword());

        Bucket bucket = cluster.openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword());
        BucketManager bucketManager = bucket.bucketManager();

        List<DesignDocument> designDocuments = bucketManager.getDesignDocuments();
        for (DesignDocument document: designDocuments) {
            for (View view: document.views()) {
                if (document.name().equals(buildDefinition.getDesignDocumentName()) && view.name().equals(expectedViewName)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("default view exists");
    }

    @Override
    protected void describeMismatchSafely(String host, Description mismatchDescription) {
        mismatchDescription.appendText("default view does not exist");
    }
}
