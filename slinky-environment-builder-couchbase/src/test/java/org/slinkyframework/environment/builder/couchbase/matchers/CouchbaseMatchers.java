package org.slinkyframework.environment.builder.couchbase.matchers;

public class CouchbaseMatchers {

    public static BucketExistsMatcher bucketExists() {
        return new BucketExistsMatcher();
    }

    public static HasViewMatcher hasView(String viewName) {
        return new HasViewMatcher(viewName);
    }
}
