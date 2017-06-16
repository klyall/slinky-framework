package org.slinkyframework.environment.config.maven.plugin.config;

import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ImmutableConfigMap implements Map {

    private ConfigObject config;

    public ImmutableConfigMap(ConfigObject config){
        this.config = config;
    }

    @Override
    public int size() {
        return config.size();
    }

    @Override
    public boolean isEmpty() {
        return config.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return config.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new EnvironmentConfigException("Not supported method");
    }

    @Override
    public Object get(Object key) {
        ConfigValue obj = config.get(key);

        if (obj == null) {
            return null;
        } else if (obj instanceof ConfigObject) {
            return new ImmutableConfigMap((ConfigObject) obj);
        } else {
            return obj.unwrapped();
        }
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
        return config.keySet();
    }

    @Override
    public Collection values() {
        return config.values();
    }

    @Override
    public Set<Entry> entrySet() {
        throw new EnvironmentConfigException("Not supported method");
    }
}
