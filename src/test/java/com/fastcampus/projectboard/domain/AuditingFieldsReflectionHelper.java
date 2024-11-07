package com.fastcampus.projectboard.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class AuditingFieldsReflectionHelper {

    private AuditingFieldsReflectionHelper() {

    }

    public static void setAuditingFields(AuditingFields entity, LocalDateTime localDateTime, int modifiedAddMinute) {
        ReflectionTestUtils.setField(entity, "createdAt", localDateTime);
        ReflectionTestUtils.setField(entity, "createdBy", "createdBy" + localDateTime.getMinute());
        ReflectionTestUtils.setField(entity, "modifiedAt", localDateTime.plusMinutes(modifiedAddMinute));
        ReflectionTestUtils.setField(entity, "modifiedBy", "modifiedBy" + localDateTime.getMinute());
    }
}
