package org.slinkyframework.environment.builder.couchbase.matchers;

import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

public class CouchbaseMatchers {

    public static BucketExistsMatcher bucketExists(CouchbaseBuildDefinition buildDefinition) {
        return new BucketExistsMatcher(buildDefinition);
    }

    public static BucketIsAccessibleMatcher bucketIsAccessible(CouchbaseBuildDefinition buildDefinition) {
        return new BucketIsAccessibleMatcher(buildDefinition);
    }

    public static HasViewMatcher hasView(CouchbaseBuildDefinition buildDefinition, String designDocumentName, String viewName) {
        return new HasViewMatcher(buildDefinition, designDocumentName, viewName);
    }

    public static HasPortAvailableMatcher hasPortAvailable(int port) {
        return new HasPortAvailableMatcher(port);
    }
}
