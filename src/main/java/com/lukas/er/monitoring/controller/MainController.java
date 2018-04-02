package com.lukas.er.monitoring.controller;

import com.lukas.er.monitoring.dto.RateDataDto;
import com.lukas.er.monitoring.dto.TradingRateDataDto;
import com.lukas.er.monitoring.repository.AverangeRatesRepository;
import com.lukas.er.monitoring.repository.BuyAndSellRateRepository;
import com.lukas.er.monitoring.service.LinearRegressionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    public AverangeRatesRepository averangeRatesRepository;

    @Autowired
    public BuyAndSellRateRepository buyAndSellRateRepository;
    @Autowired
    public LinearRegressionServiceImpl linearRegressionService;

    @GetMapping("/average_rates")
    public List<RateDataDto> getDataById(@RequestParam(required=false) String code,
                                         @RequestParam(required=false) String startDate,
                                         @RequestParam(required=false) String stopDate) throws ParseException {
        List<RateDataDto> rateDataDtoList = averangeRatesRepository
                .getRateDataByCodeAndTableDateBetween(
                        code, Date.valueOf(startDate), Date.valueOf(stopDate));
        linearRegressionService.calculateAverageRatelinearRegression(rateDataDtoList,5);

        return rateDataDtoList;
    }

    @GetMapping("/average_rates/all")
    public List<RateDataDto> getDataAverageRatesAll()
    {
        String date = "2018-03-28";
        List<RateDataDto> rateDataDtoList = averangeRatesRepository
                .getAllRateByDate(Date.valueOf(date));

        return rateDataDtoList;
    }


    @GetMapping("/trading_rates")
    public List<TradingRateDataDto> getDataTraidingRates(@RequestParam(required=false) String code,
                                                         @RequestParam(required=false) String startDate,
                                                         @RequestParam(required=false) String stopDate)
    {

        List<TradingRateDataDto> tradingRateDataDtoList =buyAndSellRateRepository
                .getTradingRatesDataByCodeAndTableDateBetween(
                        code, Date.valueOf(startDate), Date.valueOf(stopDate));

        return tradingRateDataDtoList;
    }

    @GetMapping("/trading_rates/all")
    public List<TradingRateDataDto> getDataTraidingRatesAll()
    {
        String date = "2018-03-27";
        List<TradingRateDataDto> tradingRateDataDtoList =buyAndSellRateRepository
                .getAllTraidingRateByDate(Date.valueOf(date));

        return tradingRateDataDtoList;
    }


}
