package com.lukas.er.monitoring.repository;

import com.lukas.er.monitoring.dto.RateDataDto;
import com.lukas.er.monitoring.entity.AverageRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface AverangeRatesRepository extends JpaRepository<AverageRate, Long> {

    @Transactional
    void deleteAverageRateByFileName(String fileName);

    @Query(value = "SELECT new com.lukas.er.monitoring.dto.RateDataDto(ar.tableDate,r.currency, r.code, r.mid)  FROM " +
            "AverageRate ar JOIN ar.rates r  WHERE r.code=:code " +
            "AND ar.tableDate BETWEEN :startDate AND :stopDate ORDER BY ar.tableDate" )
    List<RateDataDto> getRateDataByCodeAndTableDateBetween(@Param("code") String code,
                                         @Param("startDate")Date starDate,
                                         @Param("stopDate")Date stopDate);


    @Query(value = "SELECT new com.lukas.er.monitoring.dto.RateDataDto(ar.tableDate,r.currency, r.code, r.mid)  FROM " +
            "AverageRate ar JOIN ar.rates r  WHERE ar.tableDate=:date ")
    List<RateDataDto> getAllRateByDate(@Param("date") Date date);

  /*
    @Query(value = "SELECT new com.lukas.er.monitoring.dto.RateDataDto(" +
            "basr.tableDate, r.currency, r.code, r.bid, r.ask) FROM " +
            "BuyAndSellRate basr JOIN basr.rates r WHERE basr.tableDate=:date")
    List<TradingRateDataDto> getAllTraidingRateByDate(@Param("date") Date date);*/





}
