package com.fastcampus.projectboard.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class AuditingFieldsReflectionHelper {

    private AuditingFieldsReflectionHelper() {

    }

    public static void setAuditingFields(AuditingFields entity, int offset, LocalDateTime localDateTime) {
        setAuditingFields(entity, offset, localDateTime, 3);
    }

    public static void setAuditingFields(AuditingFields entity, int offset, LocalDateTime localDateTime, int modifiedAddMinute) {
        ReflectionTestUtils.setField(entity, "createdAt", localDateTime.plusMinutes(offset));
        ReflectionTestUtils.setField(entity, "createdBy", "createdBy" + offset);
        ReflectionTestUtils.setField(entity, "modifiedAt", localDateTime.plusMinutes(modifiedAddMinute + offset));
        ReflectionTestUtils.setField(entity, "modifiedBy", "modifiedBy" + offset);
    }
}
