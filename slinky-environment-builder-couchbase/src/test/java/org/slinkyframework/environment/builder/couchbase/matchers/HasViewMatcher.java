package org.slinkyframework.environment.builder.couchbase.matchers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.View;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

import java.util.List;

import static org.slinkyframework.environment.builder.couchbase.local.ConnectionManager.openBucket;

public class HasViewMatcher extends TypeSafeMatcher<String> {

    private CouchbaseBuildDefinition buildDefinition;
    private String expectedViewName;
    private String designDocumentName;

    public HasViewMatcher(CouchbaseBuildDefinition buildDefinition, String designDocumentName, String expectedViewName) {
        this.buildDefinition = buildDefinition;
        this.designDocumentName = designDocumentName;
        this.expectedViewName = expectedViewName;
    }

    @Override
    protected boolean matchesSafely(String host) {
        Bucket bucket = openBucket(buildDefinition.getBucketName(), buildDefinition.getBucketPassword(), host);
        BucketManager bucketManager = bucket.bucketManager();

        return hasView(bucketManager);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("default view exists");
    }

    @Override
    protected void describeMismatchSafely(String host, Description mismatchDescription) {
        mismatchDescription.appendText("default view does not exist");
    }

    private boolean hasView(BucketManager bucketManager) {
        List<DesignDocument> designDocuments = bucketManager.getDesignDocuments();
        for (DesignDocument designDocument: designDocuments) {
            for (View view: designDocument.views()) {
                if (designDocument.name().equals(designDocumentName) && view.name().equals(expectedViewName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
