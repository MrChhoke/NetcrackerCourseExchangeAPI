package com.bondar.apiforshop.module;

import com.bondar.apiforshop.module.nbu.CurrencyNBUitem;
import com.bondar.apiforshop.module.nbu.CurrencyNBUlist;
import com.bondar.apiforshop.module.privat.CurrencyPrivatBankItem;
import com.bondar.apiforshop.module.privat.CurrencyPrivatBankList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

@Service
@Data
public class CurrencyList {
    @JacksonXmlElementWrapper(localName = "listCurrency")
    private List<Currency> currency;
    private String date;

    public static CurrencyList currencyListFromCurrencyListNBU(CurrencyNBUlist currencyNBUlist){
        CurrencyList currencyList = new CurrencyList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currencyList.setDate(simpleDateFormat.format(currencyNBUlist.getDate()));
        List<CurrencyNBUitem> list = currencyNBUlist.getList().
                stream().filter(currencyItemNBU -> currencyItemNBU.getR030() == 840 || currencyItemNBU.getR030() == 978).
                collect(Collectors.toList());
        List<Currency> list2 = new ArrayList<>();
        for(CurrencyNBUitem currencyNBUitem : list){
            list2.add(Currency.currencyFromDTO(currencyNBUitem));
        }
        currencyList.setCurrency(list2);
        return currencyList;
    }

    public static CurrencyList currencyListFromCurrencyListPrivat(CurrencyPrivatBankList currencyPrivatBankList){
        CurrencyList currencyList = new CurrencyList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currencyList.setDate(simpleDateFormat.format(currencyPrivatBankList.getDate()));
        List<CurrencyPrivatBankItem> list = currencyPrivatBankList.getExchangeRate().stream().
                filter(currencyPrivatBankItem -> currencyPrivatBankItem.getCurrency() != null).
                filter(currencyPrivatBankItem -> currencyPrivatBankItem.getCurrency().equals("USD") ||
                        currencyPrivatBankItem.getCurrency().equals("EUR")).collect(Collectors.toList());
        List<Currency> list2 = new ArrayList<>();
        for(CurrencyPrivatBankItem currencyPrivatBankItem : list){
            list2.add(Currency.currencyFromDTO(currencyPrivatBankItem));
        }
        currencyList.setCurrency(list2);
        return currencyList;
    }

}
