package com.bondar.apiforshop.service;

import com.bondar.apiforshop.module.CurrencyList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Data
public class CurrencyService {

    @Autowired
    private CurrencyNBUdto currencyNBUdto;

    @Autowired
    private PrivatBankDTO privatBankDTO;

    public CurrencyList getCurrencyFromPrivat(){
        return CurrencyList.currencyListFromCurrencyListPrivat(privatBankDTO.getCurrentCurrencyFromXML());
    }

    public CurrencyList getCurrencyFromNBU(){
        return CurrencyList.currencyListFromCurrencyListNBU(currencyNBUdto.getCurrencyNow());
    }

    public CurrencyList getBestCurrencyLastWeekFromPrivat() {
        CurrencyList list = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -i - 7);
        for (int j = 0; j < 7; j++) {
            Date date2 = calendar.getTime();
            if (date.before(date2))
                break;
            CurrencyList listTemp = CurrencyList.
                    currencyListFromCurrencyListPrivat(privatBankDTO.getCurrencyByDateFromJSONjackson(date2));

            if (list == null || listTemp.getCurrency().stream().
                    filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH() <
                    list.getCurrency().stream().
                            filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH()) {
                list = listTemp;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }

    public CurrencyList getBestCurrencyLastWeekFromNBU() {
        CurrencyList list = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -i - 7);
        for (int j = 0; j < 7; j++) {
            Date date2 = calendar.getTime();
            CurrencyList listTemp = CurrencyList.
                    currencyListFromCurrencyListNBU(currencyNBUdto.getCurrencyByDate(date2));

            if (list == null || listTemp.getCurrency().stream().
                    filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH() <
                    list.getCurrency().stream().
                            filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH()) {
                list = listTemp;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }

    public CurrencyList getBestCurrencyLastMonthFromPrivat(){
        CurrencyList list = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        int i = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 0; j < i; j++) {
            Date date2 = calendar.getTime();
            if (date.before(date2))
                break;
            CurrencyList listTemp = CurrencyList.
                    currencyListFromCurrencyListPrivat(privatBankDTO.getCurrencyByDateFromJSONorgJson(date2));

            if (list == null || listTemp.getCurrency().stream().
                    filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH() <
                    list.getCurrency().stream().
                            filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH()) {
                list = listTemp;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }

    public CurrencyList getBestCurrencyLastMonthFromNBU(){
        CurrencyList list = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        int i = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 0; j < i; j++) {
            Date date2 = calendar.getTime();
            CurrencyList listTemp = CurrencyList.
                    currencyListFromCurrencyListNBU(currencyNBUdto.getCurrencyByDate(date2));

            if (list == null || listTemp.getCurrency().stream().
                    filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH() <
                    list.getCurrency().stream().
                            filter(currency -> currency.getName().equals("USD")).findAny().get().getSoldPriceUAH()) {
                list = listTemp;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }
}
