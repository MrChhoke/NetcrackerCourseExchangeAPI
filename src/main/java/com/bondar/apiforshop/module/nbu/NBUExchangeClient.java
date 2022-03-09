package com.bondar.apiforshop.module.nbu;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Data
@Scope("prototype")
@Slf4j
public class NBUExchangeClient {
    private RestTemplate restTemplate = new RestTemplate();

    public List<CurrencyNBUitem> getCurrencyByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String url = String.format("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=%s&amp;json",
                simpleDateFormat.format(date));
        try {
            CurrencyNBUitem[] mass = restTemplate.getForObject(new URI(url), CurrencyNBUitem[].class);
            List<CurrencyNBUitem> list = new ArrayList<>(Arrays.asList(mass));
            return list;
        } catch (URISyntaxException e) {
            log.error("Problem with NBUExchangeClient: " + e);
        }
        return null;
    }
}
