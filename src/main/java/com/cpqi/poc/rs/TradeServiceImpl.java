package com.cpqi.poc.rs;

/**
 * 
 * The class TradeService is used to implement the web service.
 * 
 */
public class TradeServiceImpl implements TradeService {

    @Override
    public String findById(String id) {
	return "id" + id;
    }

    @Override
    public String findAll() {
	return "findAll";
    }

    @Override
    public String save(String data) {
	return data;
    }

    @Override
    public String delete(String id) {
	return id;
    }

    @Override
    public String count() {
	return "count";
    }
}
