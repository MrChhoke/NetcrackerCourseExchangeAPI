package com.bondar.apiforshop.service;

import com.bondar.apiforshop.module.CurrencyList;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;

@Service
@Slf4j
public class ExchangeWordService {

    public static void getExchangeRateWord(CurrencyList currencyList){
        try (   FileInputStream fis = new FileInputStream(new File("template/template.docx").getAbsolutePath());
                XWPFDocument document = new XWPFDocument(fis)){
            int i = -1;
            boolean isDate = true;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if(!isDate) {
                        System.out.println(i);
                        text = text.replace("{name}", currencyList.getCurrency().get(i).getName());
                        text = text.replace("{buy}", String.valueOf(currencyList.getCurrency().get(i).getBuyPriceUAH()));
                        text = text.replace("{sold}", String.valueOf(currencyList.getCurrency().get(i).getSoldPriceUAH()));
                    }else{
                        text = text.replace("{date}", currencyList.getDate());
                    }
                    run.setText(text,0);
                }
                isDate = false;
                ++i;
            }
            try (OutputStream out = new FileOutputStream("template/output.docx")) {
                document.write(out);
            }
        } catch (IOException e) {
            log.error("Problems with ExchangeWordService: " + e);
        }
    }
}
