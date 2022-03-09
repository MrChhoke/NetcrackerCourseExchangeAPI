package com.bondar.apiforshop.service;

import com.bondar.apiforshop.module.CurrencyList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
@Data
@Scope("prototype")
public class CurrencyService {

    @Autowired
    private CurrencyNBUdto currencyNBUdto;

    @Autowired
    private PrivatBankDTO privatBankDTO;

    @Async
    public CompletableFuture<CurrencyList> getCurrencyFromPrivat(){
        return CompletableFuture.completedFuture(CurrencyList.currencyListFromCurrencyListPrivat(privatBankDTO.getCurrentCurrencyFromXML()));
    }

    @Async
    public CompletableFuture<CurrencyList> getCurrencyFromNBU(){
        return CompletableFuture.completedFuture(CurrencyList.currencyListFromCurrencyListNBU(currencyNBUdto.getCurrencyNow()));
    }

    @Async
    public CompletableFuture<CurrencyList> getBestCurrencyLastWeekFromPrivat() {
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
        return CompletableFuture.completedFuture(list);
    }

    @Async
    public CompletableFuture<CurrencyList> getBestCurrencyLastWeekFromNBU() {
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
        return CompletableFuture.completedFuture(list);
    }

    @Async
    public CompletableFuture<CurrencyList> getBestCurrencyLastMonthFromPrivat(){
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
        return CompletableFuture.completedFuture(list);
    }

    @Async
    public CompletableFuture<CurrencyList> getBestCurrencyLastMonthFromNBU(){
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
        return CompletableFuture.completedFuture(list);
    }
}
