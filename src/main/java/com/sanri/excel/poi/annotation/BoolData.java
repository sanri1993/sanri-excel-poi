package com.sanri.excel.poi.annotation;

public @interface BoolData {
    String boolTrue() default "1";
    String boolFalse() default "0";
}
