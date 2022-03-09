package com.bondar.apiforshop.module.privat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@Component
public class CurrencyPrivatBankList {
    private Date date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;
    private List<CurrencyPrivatBankItem> exchangeRate;

    public void setDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            this.date = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
