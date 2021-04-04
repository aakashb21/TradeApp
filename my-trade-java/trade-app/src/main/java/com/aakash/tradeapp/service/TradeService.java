package com.aakash.tradeapp.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.aakash.tradeapp.model.Trade;
import com.aakash.tradeapp.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	TradeRepository tradeStorageData;
	
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);
	
	public boolean validTrade(Trade trade) {
		boolean valdationFlag = false;
		
		//check if trade is present or not.
		Optional<Trade> existingTrade = tradeStorageData.findById(trade.getTradeId());
		
		if(existingTrade.isPresent()) {
			
			log.info("Existing trade found with ID = "+ existingTrade.get().getTradeId());
			
			//1. Lower version validation
			valdationFlag = validVersion(trade, existingTrade);
			
			//2. Less Maturity date
			valdationFlag = maturityDateValidation(trade, valdationFlag);
			
		} else {
			log.info("A new trade found, will further check for maturity.");
			valdationFlag = maturityDateValidation(trade, valdationFlag);
		}
		return valdationFlag;
	}
	
	
	private boolean validVersion(Trade trade, Optional<Trade> existingTradeDetails) {
		
		if(trade.getVersion() >= existingTradeDetails.get().getVersion()) {
			log.info("New trade ID is greater then existing trade, hence the value will be overwritten.");
			return true;
		} else {
			log.info("New trade ID is lower then existing trade value, trade rejected.");
			return false;
		}		
	}
	
	
	private boolean maturityDateValidation(Trade trade, boolean valdationFlag) {
		//LocalDate currentDate = new LocalDate();
		
		if(!valdationFlag) {
			if(trade.getMaturityDate().isBefore(LocalDate.now())) {
				log.info("Maturity has expired, cannot save the trade.");
				return false;
			} else {
				log.info("Valid trade, maturity is yet to expire.");
				return true;
			}			
		} else {
			log.info("Vaidation has already failed.");
			return false;
		}

		
	}
	
	public void saveTrade(Trade trade) {
		log.info("All vaidation passed, saving trade now.");
		trade.setCreatedDate(LocalDate.now());
		tradeStorageData.save(trade);
	}


	public List<Trade> fetchAllTrade() {		
		return tradeStorageData.findAll();
	}
	
}
