package com.aakash.tradeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aakash.tradeapp.model.Trade;
import com.aakash.tradeapp.service.TradeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TradeController {
	
	@Autowired
	TradeService tradeService;
	
	@PostMapping("/save")
	public HttpStatus tradeSave(@RequestBody Trade trade) throws Exception {		
		
		//Checking if received trade is valid or not
		if(tradeService.validTrade(trade)) {
			tradeService.saveTrade(trade);
			return HttpStatus.OK;
		} else {
			throw new Exception(trade.getVersion()+" is invalid  or not found.");
			//return HttpStatus.BAD_REQUEST;
		}
	}
	
	@GetMapping("/get")
	public List<Trade> tradeGetData() {
		System.out.println("Fetching all available data in trade list");
		return tradeService.fetchAllTrade(); 
	}
}

//http://localhost:8080/trade/save