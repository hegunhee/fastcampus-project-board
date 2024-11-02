package com.fastcampus.projectboard.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

class AuditingFieldsReflectionHelper {

    private AuditingFieldsReflectionHelper() {

    }

    static void setAuditingFieldsWithOffset(AuditingFields entity, int offset, LocalDateTime localDateTime) {
        ReflectionTestUtils.setField(entity, "createdAt", localDateTime.plusMinutes(offset));
        ReflectionTestUtils.setField(entity, "createdBy", "createdBy" + offset);
        int modifiedAddMinute = 3;
        ReflectionTestUtils.setField(entity, "modifiedAt", localDateTime.plusMinutes(modifiedAddMinute + offset));
        ReflectionTestUtils.setField(entity, "modifiedBy", "modifiedBy" + offset);
    }
}
