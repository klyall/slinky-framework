package org.slinkyframework.environment.builder.couchbase.matchers;

import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;

public class CouchbaseMatchers {

    public static BucketExistsMatcher bucketExists(CouchbaseBuildDefinition buildDefinition) {
        return new BucketExistsMatcher(buildDefinition);
    }

    public static HasViewMatcher hasView(CouchbaseBuildDefinition buildDefinition, String viewName) {
        return new HasViewMatcher(buildDefinition, viewName);
    }
}
