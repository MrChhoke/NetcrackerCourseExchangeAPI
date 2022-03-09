package com.bondar.apiforshop.module.nbu;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
public class CurrencyNBUlist {
    List<CurrencyNBUitem> list;
    Date date;
}
