package com.binance.client.impl.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.binance.client.exception.BinanceApiException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class JsonWrapper {

    private final JsonObject json;

    public static JsonWrapper parseFromString(String text) {
        try {
            JsonObject jsonObject;
            if(JsonParser.parseString(text) instanceof JsonArray) {
                jsonObject = (JsonObject) JsonParser.parseString("{data:" + text + "}");
            } else {
                jsonObject = (JsonObject) JsonParser.parseString(text);
            }
            if (jsonObject != null) {
                return new JsonWrapper(jsonObject);
            } else {
                throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                        "[Json] Unknown error when parse: " + text);
            }
        } catch (JsonParseException e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR, "[Json] Fail to parse json: " + text);
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR, "[Json] " + e.getMessage());
        }
    }

    public JsonWrapper(JsonObject json) {
        this.json = json;
    }

    private void checkMandatoryField(String name) {
        if (!json.has(name)) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get json item field: " + name + " does not exist");
        }
    }

    public boolean containKey(String name) {
        return json.has(name);
    }

    public String getString(String name) {
        checkMandatoryField(name);
        try {
            return json.get(name).getAsString();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get string error: " + name + " " + e.getMessage());
        }
    }

    public String getStringOrDefault(String name, String def) {
        if (!containKey(name)) {
            return def;
        }
        return getString(name);
    }

    public Boolean getBooleanOrDefault(String name, Boolean defaultValue) {
        if (!containKey(name)) {
            return defaultValue;
        }
        return getBoolean(name);
    }

    public boolean getBoolean(String name) {
        checkMandatoryField(name);
        try {
            return json.get(name).getAsBoolean();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get boolean error: " + name + " " + e.getMessage());
        }
    }

    public int getInteger(String name) {
        checkMandatoryField(name);
        try {
            return json.get(name).getAsInt();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get integer error: " + name + " " + e.getMessage());
        }
    }

    public Integer getIntegerOrDefault(String name, Integer defValue) {
        try {
            if (!containKey(name)) {
                return defValue;
            }
            return json.get(name).getAsInt();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get integer error: " + name + " " + e.getMessage());
        }
    }

    public long getLong(String name) {
        checkMandatoryField(name);
        try {
            return json.get(name).getAsLong();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get long error: " + name + " " + e.getMessage());
        }
    }

    public long getLongOrDefault(String name, long defValue) {
        try {
            if (!containKey(name)) {
                return defValue;
            }
            return json.get(name).getAsLong();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get long error: " + name + " " + e.getMessage());
        }
    }
    public Double getDouble(String name) {
        checkMandatoryField(name);
        try {
            return json.get(name).getAsDouble();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get double error: " + name + " " + e.getMessage());
        }
    }

    public Double getDoubleOrDefault(String name, Double defValue) {
        try {
            if (!containKey(name)) {
                return defValue;
            }
            return json.get(name).getAsDouble();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get double error: " + name + " " + e.getMessage());
        }
    }

    public BigDecimal getBigDecimal(String name) {
        checkMandatoryField(name);
        try {
            return new BigDecimal(json.get(name).getAsBigDecimal().stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get decimal error: " + name + " " + e.getMessage());
        }
    }

    public BigDecimal getBigDecimalOrDefault(String name, BigDecimal defValue) {
        if (!containKey(name)) {
            return defValue;
        }
        try {
            return new BigDecimal(json.get(name).getAsBigDecimal().stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Get decimal error: " + name + " " + e.getMessage());
        }
    }

    public JsonWrapper getJsonObject(String name) {
        checkMandatoryField(name);
        return new JsonWrapper(json.getAsJsonObject(name));
    }

    public JsonObject convert2JsonObject() {
        return this.json;
    }

    public void getJsonObject(String name, Handler<JsonWrapper> todo) {
        checkMandatoryField(name);
        todo.handle(new JsonWrapper(json.getAsJsonObject(name)));
    }

    public JsonWrapperArray getJsonArray(String name) {
        checkMandatoryField(name);
        JsonArray array = null;
        try {
            array = json.getAsJsonArray(name);
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR, "[Json] Get array: " + name + " error");
        }
        if (array == null) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Json] Array: " + name + " does not exist");
        }
        return new JsonWrapperArray(array);
    }

    public JsonObject getJson() {
        return json;
    }

    public List<Map<String, String>> convert2DictList() {
        List<Map<String, String>> result = new LinkedList<>();
        Set<String> keys = this.json.keySet();
        keys.forEach((key) -> {
            Map<String, String> temp = new LinkedHashMap<>();
            temp.put(key, this.getString(key));
            result.add(temp);
        });
        return result;
    }

}
