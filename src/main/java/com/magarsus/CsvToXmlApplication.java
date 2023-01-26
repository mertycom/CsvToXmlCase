package com.magarsus;

import com.magarsus.service.CsvToXmlService;

public class CsvToXmlApplication {
    public static void main(String[] args) {

        CsvToXmlService.write(CsvToXmlService.read());
    }
}