package org.slinkyframework.environment.config.maven.plugin;

import com.typesafe.config.Config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ImmutableConfigMap implements Map {

    private Config config;

    public ImmutableConfigMap(Config config){
        this.config = config;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return config.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return config.hasPath(key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return config.getObject(key.toString());
    }

    @Override
    public Object put(Object key, Object value) {
        throw new EnvironmentConfigException("Not supported method");
    }

    @Override
    public Object remove(Object key) {
        throw new EnvironmentConfigException("Not supported method");
    }

    @Override
    public void putAll(Map m) {
        throw new EnvironmentConfigException("Not supported method");
    }

    @Override
    public void clear() {
        throw new EnvironmentConfigException("Not supported method");
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
