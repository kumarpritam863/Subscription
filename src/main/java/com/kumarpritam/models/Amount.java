package com.kumarpritam.models;

import com.kumarpritam.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
    private Double amount;
    private Currency currency;
}
