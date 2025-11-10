package com.rcl.core.util;

import com.rcl.core.model.error.RclException;
import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static void requireNonBlank(Object value, String fieldName) {
        if (null == value || (value instanceof String str && StringUtils.isBlank(str))) {
            throw new RclException(fieldName + " cannot be null or blank");
        }
    }

}
