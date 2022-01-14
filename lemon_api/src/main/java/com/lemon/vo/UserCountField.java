package com.lemon.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserCountField {
    FOLLOW_COUNT("follow_count"),
    FAN_COUNT("fan_count");

    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }
}
