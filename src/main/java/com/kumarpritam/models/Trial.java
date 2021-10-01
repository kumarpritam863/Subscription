package com.kumarpritam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trial {
    private Boolean isAnyTrialAvailable;
    private Long trialPeriod;
}
