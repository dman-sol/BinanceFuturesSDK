module BinanceFuturesSDK {
    requires org.apache.commons.lang3;
    requires com.google.gson;
    requires okhttp3;
    requires org.apache.commons.codec;

    exports com.binance.client;
    exports com.binance.client.constant;
    exports com.binance.client.exception;
    exports com.binance.client.impl;
    exports com.binance.client.model;
}
