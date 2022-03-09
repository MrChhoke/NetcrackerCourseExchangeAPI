package com.bondar.apiforshop.module.privat;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class CurrencyPrivatBankItem {
    private String baseCurrency;
    private String currency;
    private double saleRate;
    private double purchaseRate;
    private double saleRateNB;
    private double purchaseRateNB;
}
