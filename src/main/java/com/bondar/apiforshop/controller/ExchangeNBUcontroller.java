package com.bondar.apiforshop.controller;


import com.bondar.apiforshop.module.CurrencyList;
import com.bondar.apiforshop.service.CurrencyService;
import com.bondar.apiforshop.service.ExchangeWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/nbu")
@Slf4j
public class ExchangeNBUcontroller {

    @Value("${wait.time.out.second}")
    private int timeOutSecond;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<CurrencyList> currencyNow(@RequestParam(name = "xml", required = false) String xml,
                                                    @RequestParam(name = "json", required = false) String json) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if(json == null)
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        else
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        CurrencyList currencyList;
        try {
            currencyList = currencyService.getCurrencyFromNBU().get(timeOutSecond, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            log.error("Problem with ExchangeNBUcontroller: " + e);
            return new ResponseEntity(null,httpHeaders,HttpStatus.REQUEST_TIMEOUT);
        }
        return new ResponseEntity(currencyList, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/best_last_week")
    public ResponseEntity<CurrencyList> bestCurrencyLastWeek(@RequestParam(name = "xml", required = false) String xml,
                                                             @RequestParam(name = "json", required = false) String json) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if(json == null)
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        else
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        CurrencyList currencyList;
        try {
            currencyList = currencyService.getBestCurrencyLastWeekFromNBU().get(timeOutSecond, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            log.error("Problem with ExchangeNBUcontroller: " + e);
            return new ResponseEntity(null,httpHeaders,HttpStatus.REQUEST_TIMEOUT);
        }
        return new ResponseEntity(currencyList, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/best_last_month")
    public ResponseEntity<CurrencyList> bestCurrencyLastMonth(@RequestParam(name = "xml", required = false) String xml,
                                                              @RequestParam(name = "json", required = false) String json) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if(json == null)
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        else
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        CurrencyList currencyList;
        try {
            currencyList = currencyService.getBestCurrencyLastMonthFromNBU().get(timeOutSecond, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            log.error("Problem with ExchangeNBUcontroller: " + e);
            return new ResponseEntity(null,httpHeaders,HttpStatus.REQUEST_TIMEOUT);
        }
        return new ResponseEntity(currencyList, httpHeaders, HttpStatus.OK);
    }


    @GetMapping("/download")
    public void downloadResource(HttpServletResponse response) throws IOException {
        File file = null;
        try {
            file = ExchangeWordService
                    .getExchangeRateWord(currencyService
                            .getCurrencyFromNBU()
                            .get(timeOutSecond, TimeUnit.SECONDS)
                    );
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Problem with download (ExchangeNBUcontroller): + e");
        }
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            file.delete();
        }
    }
}
