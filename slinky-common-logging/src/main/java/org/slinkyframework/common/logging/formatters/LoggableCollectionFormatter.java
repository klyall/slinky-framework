package org.slinkyframework.common.logging.formatters;

import java.util.Collection;

class LoggableCollectionFormatter extends LoggableTypeFormatter<Collection<?>> {

    String extractValue(Collection<?> obj) {
        return "(size=" + String.valueOf(obj.size()) + ")";
    }
}
