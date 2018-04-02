package com.lukas.er.monitoring.repository;

import com.lukas.er.monitoring.dto.TradingRateDataDto;
import com.lukas.er.monitoring.entity.BuyAndSellRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface BuyAndSellRateRepository extends JpaRepository<BuyAndSellRate, Long> {

    @Transactional
    void deleteBuyAndSellRateByFileName(String fileName);


    @Query(value = "SELECT new com.lukas.er.monitoring.dto.TradingRateDataDto(" +
            "basr.tableDate, r.currency, r.code, r.bid, r.ask)  FROM " +
            "BuyAndSellRate basr JOIN basr.rates r  WHERE r.code=:code " +
            "AND basr.tableDate BETWEEN :startDate AND :stopDate" )
    List<TradingRateDataDto> getTradingRatesDataByCodeAndTableDateBetween(@Param("code") String code,
                                                                  @Param("startDate")Date starDate,
                                                                  @Param("stopDate")Date stopDate);


    @Query(value = "SELECT new com.lukas.er.monitoring.dto.TradingRateDataDto(" +
            "basr.tableDate, r.currency, r.code, r.bid, r.ask) FROM " +
            "BuyAndSellRate basr JOIN basr.rates r WHERE basr.tableDate=:date")
    List<TradingRateDataDto> getAllTraidingRateByDate(@Param("date") Date date);
}
