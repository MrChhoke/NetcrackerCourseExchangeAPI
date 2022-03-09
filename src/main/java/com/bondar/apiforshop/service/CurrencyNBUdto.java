package com.bondar.apiforshop.service;

import com.bondar.apiforshop.module.nbu.CurrencyNBUlist;
import com.bondar.apiforshop.module.nbu.NBUExchangeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class CurrencyNBUdto {

    @Autowired
    private NBUExchangeClient client;


    public CurrencyNBUlist getCurrencyNow() {
        Date date = new Date();
        CurrencyNBUlist currencyNBUlist = new CurrencyNBUlist();
        currencyNBUlist.setList(client.getCurrencyByDate(date));
        currencyNBUlist.setDate(date);
        return currencyNBUlist;
    }

    public CurrencyNBUlist getCurrencyByDate(Date date) {
        CurrencyNBUlist currencyNBUlist = new CurrencyNBUlist();
        currencyNBUlist.setList(client.getCurrencyByDate(date));
        currencyNBUlist.setDate(date);
        return currencyNBUlist;
    }
}
