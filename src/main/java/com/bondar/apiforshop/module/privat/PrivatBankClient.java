package com.bondar.apiforshop.module.privat;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import lombok.Data;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Data
@Scope("prototype")
public class PrivatBankClient {
    public CurrencyPrivatBankList getCurrencyByDateFromJSONjackson(Date date) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String url = String.format("https://api.privatbank.ua/p24api/exchange_rates?json&date=%s",
                simpleDateFormat.format(date));
        try {
            return objectMapper.readValue(new URL(url), CurrencyPrivatBankList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CurrencyPrivatBankList getCurrencyByDateFromXML(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String url = String.format("https://api.privatbank.ua/p24api/exchange_rates?xml&date=%s",
                simpleDateFormat.format(date));

        CurrencyPrivatBankList privatBankList = null;
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
            privatBankList = new CurrencyPrivatBankList();
            privatBankList.setBank(document.getChildNodes().item(0).
                    getAttributes().getNamedItem("bank").getNodeValue());
            privatBankList.setDate(document.getChildNodes().item(0).
                    getAttributes().getNamedItem("date").getNodeValue());
            privatBankList.setBaseCurrencyLit(document.getChildNodes().item(0).getAttributes().
                    getNamedItem("BaseCurrencyLit").getNodeValue());
            privatBankList.setBaseCurrency(Integer.parseInt(document.
                    getChildNodes().item(0).getAttributes().getNamedItem("BaseCurrency").getNodeValue()));
            List<CurrencyPrivatBankItem> list = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("exchangerate");
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i) instanceof Element) {
                    CurrencyPrivatBankItem item = new CurrencyPrivatBankItem();
                    item.setBaseCurrency(nodeList.item(i).getAttributes().getNamedItem("baseCurrency").getNodeValue());
                    item.setCurrency(nodeList.item(i).getAttributes().getNamedItem("currency") != null ?
                            nodeList.item(i).getAttributes().getNamedItem("currency").getNodeValue() : null);
                    item.setSaleRate(nodeList.item(i).getAttributes().getNamedItem("saleRate") != null ?
                        Double.parseDouble(nodeList.item(i).getAttributes().getNamedItem("saleRate").getNodeValue()) : 0);
                    item.setSaleRateNB(Double.parseDouble(nodeList.item(i).getAttributes().getNamedItem("saleRateNB").getNodeValue()));
                    item.setPurchaseRateNB(Double.parseDouble(nodeList.item(i).getAttributes().getNamedItem("purchaseRateNB").getNodeValue()));
                    item.setPurchaseRate(nodeList.item(i).getAttributes().getNamedItem("purchaseRate") != null ?
                            Double.parseDouble(nodeList.item(i).getAttributes().getNamedItem("purchaseRate").getNodeValue()) : 0);
                    list.add(item);
                }
            }
            privatBankList.setExchangeRate(list);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return privatBankList;
    }

    public CurrencyPrivatBankList getCurrencyByDateFromJSONorgJson(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String url = String.format("https://api.privatbank.ua/p24api/exchange_rates?json&date=%s",
                simpleDateFormat.format(date));
        JsonObject jsonObject;
        try {
            jsonObject = Json.parse(new BufferedReader(new InputStreamReader(new URL(url).openStream()))).asObject();
            CurrencyPrivatBankList privatBankList = new CurrencyPrivatBankList();
            privatBankList.setBaseCurrencyLit(jsonObject.get("baseCurrencyLit").asString());
            privatBankList.setDate(jsonObject.get("date").asString());
            privatBankList.setBaseCurrency(jsonObject.get("baseCurrency").asInt());
            privatBankList.setBank(jsonObject.get("bank").asString());
            JsonArray jsonArray = jsonObject.get("exchangeRate").asArray();
            List<CurrencyPrivatBankItem> list = new ArrayList<>(jsonArray.size() + 1);
            for(int i = 0; i < jsonArray.size(); i++){
                CurrencyPrivatBankItem item = new CurrencyPrivatBankItem();
                JsonObject itemJson = jsonArray.get(i).asObject();
                item.setBaseCurrency(itemJson.get("baseCurrency").asString());
                item.setCurrency(itemJson.get("currency") != null ?
                        itemJson.get("currency").asString() : null);
                item.setSaleRate(itemJson.get("saleRate") != null ?
                        itemJson.get("saleRate").asDouble() : 0);
                item.setSaleRateNB(itemJson.get("saleRateNB").asDouble());
                item.setPurchaseRateNB(itemJson.get("purchaseRateNB").asDouble());
                item.setPurchaseRate(itemJson.get("purchaseRate") != null ?
                        itemJson.get("purchaseRate").asDouble() : 0);
                list.add(item);
            }
            privatBankList.setExchangeRate(list);
            return privatBankList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
