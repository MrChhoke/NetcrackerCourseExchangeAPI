package com.bondar.apiforshop.module;

import com.bondar.apiforshop.module.nbu.CurrencyNBUitem;
import com.bondar.apiforshop.module.privat.CurrencyPrivatBankItem;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Currency {
    private String name;
    private double buyPriceUAH;
    private double soldPriceUAH;


    public static Currency currencyFromDTO(CurrencyPrivatBankItem item){
        Currency currency = new Currency();
        currency.name = item.getCurrency();
        currency.buyPriceUAH = item.getSaleRate();
        currency.soldPriceUAH = item.getPurchaseRate();
        return currency;
    }

    public static Currency currencyFromDTO(CurrencyNBUitem item){
        Currency currency = new Currency();
        currency.name = item.getCc();
        currency.buyPriceUAH = item.getRate();
        currency.soldPriceUAH = item.getRate();
        return currency;
    }
}
