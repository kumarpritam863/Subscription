package com.kumarpritam.models;

import com.kumarpritam.enums.Period;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @NonNull
    private String uniquePlanId;
    @NonNull
    private Period period;
    @NonNull
    private Integer interval;
    @NonNull
    private Product product;
}
