package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TradeRepositoryIT {

    @Autowired
    private TradeRepository tradeRepository;
    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade("Trade Account", "Type", 12);
    }

    @Test
    public void tradeTest_Update() {
        //ARRANGE
        trade.setTradeId(1);
        trade.setAccount("Trade Account Update");
        //ACT
        trade = tradeRepository.save(trade);
        //ASSERT
        assertTrue(trade.getAccount().equals("Trade Account Update"));
    }

    @Test
    public void tradeTest_FindAll() {
        //ACT
        List<Trade> listResult = tradeRepository.findAll();
        //ASSERT
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void tradeTest_Delete() {
        //ARRANGE
        trade.setTradeId(1);
        //ACT
        tradeRepository.delete(trade);
        //ASSERT
        Optional<Trade> tradeList = tradeRepository.findById(1);
        assertFalse(tradeList.isPresent());
    }
}