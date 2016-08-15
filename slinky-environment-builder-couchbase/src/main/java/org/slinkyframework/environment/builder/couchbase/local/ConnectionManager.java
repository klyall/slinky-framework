package org.slinkyframework.environment.builder.couchbase.local;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static final CouchbaseEnvironment environment =
            DefaultCouchbaseEnvironment.builder()
                    .queryEnabled(true)
                    .build();

    private static Map<String, Cluster> clusters = new HashMap<>();

    public static Cluster getCluster(String... hosts) {
        String firstHost = hosts[0];

        if (!clusters.containsKey(firstHost)) {
            clusters.put(firstHost, CouchbaseCluster.create(environment, hosts));
        }
        return clusters.get(firstHost);
    }

    public static void disconnect() {
        clusters.values().forEach(c -> c.disconnect());
    }
}
