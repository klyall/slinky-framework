package org.slinkyframework.common.logging.domain;

import java.util.List;

import static org.slinkyframework.common.logging.util.ListUtils.join;

public class LoggableObject {

    private String name;
    private List<LoggableVariable> params;

    public LoggableObject(String name, List<LoggableVariable> params) {
        this.name = name;
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(join(params));
        sb.append(")");

        return sb.toString();
    }
}
