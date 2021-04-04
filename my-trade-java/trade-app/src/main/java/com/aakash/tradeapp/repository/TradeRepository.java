package com.aakash.tradeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aakash.tradeapp.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String>  {

}
