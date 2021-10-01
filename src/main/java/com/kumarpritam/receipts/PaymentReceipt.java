package com.kumarpritam.receipts;

import com.kumarpritam.models.Amount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReceipt {
    private String transactionId;
    private Amount amount;
    private String instrumentName;
}
