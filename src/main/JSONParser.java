import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JSONParser {

    public static Object parseJson(String jsonString) {
        if (jsonString == null || jsonString.trim().length()==0) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(jsonString);
        return parseJsonObject(jsonObject);
    }

    private static Object parseJsonObject(JSONObject jsonObject) {
        Map<String, Object> resultMap = new HashMap<String, Object>();


        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            resultMap.put(key, parseJsonValue(value));
        }
        return resultMap;
    }

    private static Object parseJsonValue(Object value) {
        if (value instanceof JSONObject) {
            return parseJsonObject((JSONObject) value);
        } else if (value instanceof org.json.JSONArray) {
            return parseJsonArray((org.json.JSONArray) value);
        } else if (value instanceof String) {
            return value;  // Return string as-is
        } else if (value instanceof Integer) {
            return BigInteger.valueOf((Integer) value);
        } else if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        } else if (value instanceof Double) {
            return new BigDecimal((Double) value);
        } else if (value instanceof Boolean) {
            return value;
        }
        return value;
    }

    private static Object parseJsonArray(org.json.JSONArray jsonArray) {
        List<Object> result = new ArrayList<Object>();

        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(parseJsonValue(jsonArray.get(i)));
        }
        return result;
    }

    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"height\":5.9,\"active\":true,\"children\":[{\"name\":\"Jane\",\"age\":10},{\"name\":\"Jake\",\"age\":8}]}";
        Object result = parseJson(jsonString);
        System.out.println(result);
    }
}