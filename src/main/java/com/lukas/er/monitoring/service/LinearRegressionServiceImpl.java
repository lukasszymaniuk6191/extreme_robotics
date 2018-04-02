package com.lukas.er.monitoring.service;

import com.lukas.er.monitoring.dto.RateDataDto;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class LinearRegressionServiceImpl {

    public List<RateDataDto> calculateAverageRatelinearRegression(List<RateDataDto> rateDataDtoList, int numberOfDays) throws ParseException {
        SimpleRegression simpleRegression = new SimpleRegression();


        double[][] historicalData = new double[rateDataDtoList.size()][2];
        int indexOfArray=1;

        for(int i=0; i<rateDataDtoList.size(); i++)
        {
            for(int j=0; j<2; j++)
            {
                if(j==0)
                {
                    historicalData[i][j]= indexOfArray;
                }
                else {
                    historicalData[i][j] =Double.parseDouble(rateDataDtoList.get(i).getMid()); // ThreadLocalRandom.current().nextDouble(3.3, 3.7);
                }

            }
            indexOfArray++;

        }



        simpleRegression.addData(historicalData);

        // querying for model parameters
        System.out.println("slope = " + simpleRegression.getSlope());
        System.out.println("intercept = " + simpleRegression.getIntercept());

        // trying to run model for unknown data
        System.out.println("prediction for 1.5 = " + simpleRegression.predict(1.5));

        RateDataDto rateDataDto = rateDataDtoList.get(rateDataDtoList.size()-1);
        System.out.println(rateDataDto.toString());

        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //rateDataDtoList.clear();

        for(int a=2;a<10; a++)
        {
            Date date2 = Date.from(date.plusDays(a).atStartOfDay(ZoneId.systemDefault()).toInstant());
            rateDataDtoList.add(new RateDataDto(date2
                    ,rateDataDto.getCurrency(),
                    rateDataDto.getCode(),String.valueOf(simpleRegression.predict(a))));
        }






        return null;
    }


}
