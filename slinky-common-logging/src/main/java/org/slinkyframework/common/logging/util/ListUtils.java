package org.slinkyframework.common.logging.util;

import java.util.List;

public class ListUtils {

    public  static String join(List<?> list) {
        StringBuilder sb = new StringBuilder();

        int endIndex = list.size();
        for (int i = 0; i < endIndex; i++) {
            String item = list.get(i).toString();

            if (i > 0) {
                sb.append(", ");
            }
            if (item != null) {
                sb.append(item);
            }
        }
        return sb.toString();
    }
}
