package com.bondar.apiforshop.service;

import com.bondar.apiforshop.module.privat.CurrencyPrivatBankList;
import com.bondar.apiforshop.module.privat.PrivatBankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PrivatBankDTO {

    @Autowired
    private PrivatBankClient privatBankClient;

    public CurrencyPrivatBankList getCurrentCurrencyFromJSONjackson() {
        return privatBankClient.getCurrencyByDateFromJSONjackson(new Date());
    }

    public CurrencyPrivatBankList getCurrentCurrencyFromJSONorgJson() {
        return privatBankClient.getCurrencyByDateFromJSONorgJson(new Date());
    }

    public CurrencyPrivatBankList getCurrentCurrencyFromXML() {
        return privatBankClient.getCurrencyByDateFromXML(new Date());
    }

    public CurrencyPrivatBankList getCurrencyByDateFromJSONjackson(Date date) {
        return privatBankClient.getCurrencyByDateFromJSONjackson(date);
    }

    public CurrencyPrivatBankList getCurrencyByDateFromJSONorgJson(Date date) {
        return privatBankClient.getCurrencyByDateFromJSONorgJson(date);
    }

    public CurrencyPrivatBankList getCurrencyByDateFromXML(Date date) {
        return privatBankClient.getCurrencyByDateFromXML(date);
    }
}
