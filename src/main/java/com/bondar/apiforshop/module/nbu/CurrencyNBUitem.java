package com.bondar.apiforshop.module.nbu;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
@Data
@Scope("prototype")
public class CurrencyNBUitem {
    private int r030;
    private String txt;
    private double rate;
    private String cc;
    private Date exchangedate;

    public void setExchangedate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        exchangedate = simpleDateFormat.parse(date);
    }
}
