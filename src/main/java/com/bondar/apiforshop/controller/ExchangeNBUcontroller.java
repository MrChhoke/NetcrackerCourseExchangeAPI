package com.bondar.apiforshop.controller;


import com.bondar.apiforshop.module.CurrencyList;
import com.bondar.apiforshop.service.CurrencyNBUdto;
import com.bondar.apiforshop.service.CurrencyService;
import com.bondar.apiforshop.service.PrivatBankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/nbu")
public class ExchangeNBUcontroller {

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
        return new ResponseEntity(currencyService.getCurrencyFromNBU(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/best_last_week")
    public ResponseEntity<CurrencyList> bestCurrencyLastWeek(@RequestParam(name = "xml", required = false) String xml,
                                                             @RequestParam(name = "json", required = false) String json) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if(json == null)
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        else
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(currencyService.getBestCurrencyLastWeekFromNBU(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/best_last_month")
    public ResponseEntity<CurrencyList> bestCurrencyLastMonth(@RequestParam(name = "xml", required = false) String xml,
                                                              @RequestParam(name = "json", required = false) String json) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if(json == null)
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        else
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(currencyService.getBestCurrencyLastMonthFromNBU(), httpHeaders, HttpStatus.OK);
    }

}
