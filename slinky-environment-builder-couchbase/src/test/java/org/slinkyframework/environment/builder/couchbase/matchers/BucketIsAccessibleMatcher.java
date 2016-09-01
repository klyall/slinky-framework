package org.slinkyframework.environment.builder.couchbase.matchers;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.Bucket;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.openBucket;

public class BucketIsAccessibleMatcher extends TypeSafeMatcher<String> {

    private CouchbaseBuildDefinition buildDefinition;

    public BucketIsAccessibleMatcher(CouchbaseBuildDefinition buildDefinition) {
        this.buildDefinition = buildDefinition;
    }

    @Override
    protected boolean matchesSafely(String host) {
        boolean success;

        try {
            Bucket bucket = openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword(), host);
            success = true;
        } catch (CouchbaseException e) {
            success = false;
        }

        return success;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("bucket is accessible");
    }

    @Override
    protected void describeMismatchSafely(String host, Description mismatchDescription) {
        mismatchDescription.appendText("bucket is not accessible");
    }
}
