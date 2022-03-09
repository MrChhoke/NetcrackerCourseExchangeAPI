package com.bondar.apiforshop.module.nbu;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
@Scope("prototype")
public class CurrencyNBUlist {
    List<CurrencyNBUitem> list;
    Date date;
}
