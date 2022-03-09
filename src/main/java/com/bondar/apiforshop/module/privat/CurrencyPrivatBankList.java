package com.bondar.apiforshop.module.privat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@Component
@Scope("prototype")
@Slf4j
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
            log.error("Problem with CurrencyPrivatBankList: " + e);
        }
    }
}
