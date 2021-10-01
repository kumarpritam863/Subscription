package com.kumarpritam.entitites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanEntity {
    private Long planId;
    private String uniqueId;
    private String period;
    private String interval;
    private String product;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
