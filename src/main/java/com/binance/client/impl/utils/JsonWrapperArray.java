package com.binance.client.impl.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import com.binance.client.exception.BinanceApiException;
import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;

public class JsonWrapperArray {

    private JsonArray array = null;

    public JsonWrapperArray(JsonArray array) {
        this.array = array;
    }

    public JsonWrapper getJsonObjectAt(int index) {
        if (array != null && array.size() > index) {
            JsonObject object = (JsonObject) array.get(index);
            if (object == null) {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                        "[Json] Cannot get object at index " + index + " in array");
            }
            return new JsonWrapper(object);
        } else {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    public void add(JsonElement val) {
        this.array.add(val);
    }

    public JsonWrapperArray getArrayAt(int index) {
        if (array != null && array.size() > index) {
            JsonArray newArray = (JsonArray) array.get(index);
            if (newArray == null) {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                        "[Json] Cannot get array at index " + index + " in array");
            }
            return new JsonWrapperArray(newArray);
        } else {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    private JsonElement getObjectAt(int index) {
        if (array != null && array.size() > index) {
            return array.get(index);
        } else {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Index is out of bound or array is null");
        }
    }

    public long getLongAt(int index) {
        try {
            return getObjectAt(index).getAsLong();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Cannot get long at index " + index + " in array: " + e.getMessage());
        }

    }

    public Integer getIntegerAt(int index) {
        try {
            return getObjectAt(index).getAsInt();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Cannot get integer at index " + index + " in array: " + e.getMessage());
        }

    }

    public BigDecimal getBigDecimalAt(int index) {

        try {
            return new BigDecimal(new BigDecimal(getStringAt(index)).stripTrailingZeros().toPlainString());
        } catch (RuntimeException e) {
            throw new BinanceApiException(null, e.getMessage());
        }

    }

    public String getStringAt(int index) {

        try {
            return getObjectAt(index).getAsString();
        } catch (RuntimeException e) {
            throw new BinanceApiException(null, e.getMessage());
        }

    }

    public void forEach(Handler<JsonWrapper> objectHandler) {
        array.forEach((object) -> {
            if (!(object instanceof JsonObject)) {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR, "[Json] Parse array error in forEach");
            }
            objectHandler.handle(new JsonWrapper((JsonObject) object));
        });
    }

    public void forEachAsArray(Handler<JsonWrapperArray> objectHandler) {
        array.forEach((object) -> {
            if (!(object instanceof JsonArray)) {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                        "[Json] Parse array error in forEachAsArray");
            }
            objectHandler.handle(new JsonWrapperArray((JsonArray) object));
        });
    }

    public void forEachAsString(Handler<String> objectHandler) {
        array.forEach((object) -> {
            if (object.getAsString() == null) {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                        "[Json] Parse array error in forEachAsString");
            }
            objectHandler.handle(object.getAsString());
        });
    }

    public List<String> convert2StringList() {
        List<String> result = new LinkedList<>();
        this.forEachAsString(result::add);
        return result;
    }

}
