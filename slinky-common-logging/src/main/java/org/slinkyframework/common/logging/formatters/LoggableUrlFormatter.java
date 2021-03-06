package org.slinkyframework.common.logging.formatters;

import java.net.URL;

class LoggableUrlFormatter extends LoggableTypeFormatter<URL> {

    String extractValue(URL obj) {
        return obj.toString();
    }
}
