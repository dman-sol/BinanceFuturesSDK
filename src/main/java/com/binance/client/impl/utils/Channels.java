package com.binance.client.impl.utils;

import com.binance.client.model.enums.CandlestickInterval;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Channels {

    public static final String OP_SUB = "sub";
    public static final String OP_REQ = "req";

    public static String aggregateTradeChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();

        params.add(symbol + "@aggTrade");

        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String markPriceChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@markPrice");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String candlestickChannel(String symbol, CandlestickInterval interval) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@kline_" + interval);
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String miniTickerChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@miniTicker");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String miniTickerChannel() {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add("!miniTicker@arr");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String tickerChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@ticker");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String tickerChannel() {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add("!ticker@arr");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String bookTickerChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@bookTicker");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String bookTickerChannel() {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add("!bookTicker");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String liquidationOrderChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@forceOrder");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String liquidationOrderChannel() {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add("!forceOrder@arr");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String bookDepthChannel(String symbol, Integer limit) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@depth" + limit);
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String diffDepthChannel(String symbol) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(symbol + "@depth");
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
    public static String userDataChannel(String listenKey) {
        JsonObject json = new JsonObject();
        JsonArray params = new JsonArray();
        params.add(listenKey);
        json.add("params", params);
        json.addProperty("id", System.currentTimeMillis());
        json.addProperty("method", "SUBSCRIBE");
        return json.getAsString();
    }
  
}