package com.kumarpritam.responses;

import com.kumarpritam.enums.PlanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanResponse {
    public String id;
    public String uniquePlanId;
    public PlanStatus status;
    public Map<String, Object> message;
}
