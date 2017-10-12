package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static final CouchbaseEnvironment environment =
            DefaultCouchbaseEnvironment.builder()
//                    .queryEnabled(true)
                    .build();

    private static Map<String, Cluster> clusters = new HashMap<>();
    private static Map<String, Bucket> buckets = new HashMap<>();

    public static Cluster getCluster(String... hosts) {
        String firstHost = hosts[0];

        if (!clusters.containsKey(firstHost)) {
            clusters.put(firstHost, CouchbaseCluster.create(environment, hosts));
        }
        return clusters.get(firstHost);
    }

    public static Bucket openBucket(String bucketName, String bucketPassword, String... hosts) {
        Cluster cluster = getCluster(hosts);

        if (!buckets.containsKey(bucketName)) {
            Bucket bucket = cluster.openBucket(bucketName, bucketPassword);
            buckets.put(bucketName, bucket);
        }

        return buckets.get(bucketName);
    }

    public static void closeBucket(String bucketName) {
        if (buckets.containsKey(bucketName)) {
            Bucket bucket = buckets.get(bucketName);
            bucket.close();
            buckets.remove(bucketName);
        }
    }

    public static void disconnect() {
        clusters.values().forEach(c -> c.disconnect());
    }
}
