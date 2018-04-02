package com.lukas.er;


import com.lukas.er.monitoring.dto.FileWatcherDataDto;
import com.lukas.er.monitoring.repository.AverangeRatesRepository;
import com.lukas.er.monitoring.repository.BuyAndSellRateRepository;
import com.lukas.er.monitoring.service.JsonObjectReaderService;
import com.lukas.er.monitoring.service.TableDataBaseService;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableDataBaseServiceTest {


    @Mock
    private AverangeRatesRepository averangeRatesRepositoryMock;
    @Mock
    private BuyAndSellRateRepository buyAndSellRateRepositoryMock;
    @Mock
    private JsonObjectReaderService jsonObjectReaderServiceMock;
    @Autowired
    private TableDataBaseService tableDataBaseService;



    @Before
    public void setUp()
    {
        //averangeRatesRepositoryMock = mock(AverangeRatesRepository.class);
        tableDataBaseService.setBuyAndSellRatesRepository(buyAndSellRateRepositoryMock);
        tableDataBaseService.setAverangeRatesRepository(averangeRatesRepositoryMock);
        tableDataBaseService.setJsonObjectReaderService(jsonObjectReaderServiceMock);
    }

    @Test
    public void tableDataBaseServiceCorrectTest() throws IOException, ParseException {
     /*   Rates createdRate = Rates.builder()
                .currency("afgani (Afganistan)").code("AFN").mid("0.048817").build();
        List<Rates> createdRatesList = new ArrayList<>();
        createdRatesList.add(createdRate);

        AverageRate crestedAverageRate = AverageRate.builder()
                .table("B").no("013/B/NBP/2018").effectiveDate(Date.valueOf("2018-03-28"))
                .rates(createdRatesList).build();

        TradingRates createdTradingRate = TradingRates.builder()
                .currency("dolar amerykański").code("USD").bid("3.3670")
                .ask("3.4350").build();
        List<TradingRates> createdTradingRateList = new ArrayList<>();
        createdTradingRateList.add(createdTradingRate);

        BuyAndSellRate createdBuyAndSellRate = BuyAndSellRate.builder()
                .table("C").no("062/C/NBP/2018").tradingDate(Date.valueOf("2018-03-27"))
                .effectiveDate(Date.valueOf("2018-03-28")).rates(createdTradingRateList).build();


        Rates persistedRate = Rates.builder()
                .id(1L).currency("afgani (Afganistan)").code("AFN").mid("0.048817").build();
        List<Rates> persistedRatesList = new ArrayList<>();
        createdRatesList.add(persistedRate);

        AverageRate persistedAverageRate = AverageRate.builder()
                .id(1L).table("B").no("013/B/NBP/2018").effectiveDate(Date.valueOf("2018-03-28"))
                .rates(persistedRatesList).build();

        TradingRates persistedTradingRate = TradingRates.builder()
                .id(1L).currency("dolar amerykański").code("USD")
                .bid("3.3670").ask("3.4350").build();
        List<TradingRates> persistedTradingRateList = new ArrayList<>();
        createdTradingRateList.add(persistedTradingRate);

        BuyAndSellRate persistedBuyAndSellRate = BuyAndSellRate.builder()
                .id(1L).table("C").no("062/C/NBP/2018").tradingDate(Date.valueOf("2018-03-27"))
                .effectiveDate(Date.valueOf("2018-03-28")).rates(persistedTradingRateList).build();

        when(averangeRatesRepositoryMock.save(any(AverageRate.class)))
                .thenReturn(persistedAverageRate);
        when(buyAndSellRateRepositoryMock.save(any(BuyAndSellRate.class)))
                .thenReturn(persistedBuyAndSellRate);*/


        List<FileWatcherDataDto> inputFileWatcherDataDtoList = new ArrayList<>();
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("A_2018-03-28.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("B_2018-03-28.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-28.json","ENTRY_CREATE"));
        List<FileWatcherDataDto> outputFileWatcherDataDtoList
                = tableDataBaseService.updateDataInDb(inputFileWatcherDataDtoList);

        assertEquals(3,outputFileWatcherDataDtoList.size());
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("A_2018-03-28.json","ENTRY_CREATED")));
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("B_2018-03-28.json","ENTRY_CREATED")));
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("C_2018-03-28.json","ENTRY_CREATED")));



        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-30.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-30.json","ENTRY_DELETE"));
        outputFileWatcherDataDtoList
                = tableDataBaseService.updateDataInDb(inputFileWatcherDataDtoList);

        assertEquals(5,outputFileWatcherDataDtoList.size());
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("C_2018-03-30.json","ENTRY_CREATED")));
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("C_2018-03-30.json","ENTRY_DELETED")));

    }

    @Test
    public void tableDataBaseServiceIncorrectTest() throws IOException, ParseException {

        List<FileWatcherDataDto> inputFileWatcherDataDtoList = new ArrayList<>();
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("_2018-03-28.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("B_2018-03-28.json","ENTRY_CREATES"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-28.json","ENTRY_C"));
        List<FileWatcherDataDto> outputFileWatcherDataDtoList
                = tableDataBaseService.updateDataInDb(inputFileWatcherDataDtoList);

        assertEquals(3,outputFileWatcherDataDtoList.size());
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("WRONG_FILENAME","FILE_NOT_ADDED")));
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("","WRONG_ENTRY")));
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("","WRONG_ENTRY")));



        inputFileWatcherDataDtoList=new ArrayList<>();
        outputFileWatcherDataDtoList=new ArrayList<>();
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-w0.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-.json","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-w0.jsox","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("x_2018-03-w0.jsox","ENTRY_CREATE"));
        inputFileWatcherDataDtoList.add(
                new FileWatcherDataDto("C_2018-03-w0.jsonC_2018-03-w0.json","ENTRY_CREATE"));
        outputFileWatcherDataDtoList
                = tableDataBaseService.updateDataInDb(inputFileWatcherDataDtoList);

        int numberDuplicatedObjects = Collections.frequency(outputFileWatcherDataDtoList,
                new FileWatcherDataDto("WRONG_FILENAME","FILE_NOT_ADDED"));

        assertEquals(inputFileWatcherDataDtoList.size(), outputFileWatcherDataDtoList.size());
        assertEquals(5, outputFileWatcherDataDtoList.size());
        assertEquals(5,numberDuplicatedObjects);
        assertTrue(outputFileWatcherDataDtoList.contains(
                new FileWatcherDataDto("WRONG_FILENAME","FILE_NOT_ADDED")));

    }
}


