package com.farido.websockettestapp.util;

import com.farido.websockettestapp.db.entity.CompanyDBEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String normalizeMessage(String rawMessage) {
        String message = rawMessage.replace("Text(value=", "");
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            message = message.substring(matcher.end(), message.length() - 1);
        }
        return message;
    }

    public static JsonArray getResultFromMessage(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonObject jsonObject = jsonArray.get(1).getAsJsonObject();
        return jsonObject.getAsJsonArray("result");
    }

    public static Long getUnixTimestampFromDateTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        long timestamp = 0L;

        try {
            Date date = format.parse(dateTime);
            if (date != null)
                timestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static List<CompanyDBEntity> getPojoFromMessage(JsonElement rawJsonElement){
        JsonArray jsonArray = rawJsonElement.getAsJsonArray();
        JsonObject rawJsonObject = jsonArray.get(1).getAsJsonObject();
        JsonArray resultJsonArray = rawJsonObject.getAsJsonArray("result");

        List<CompanyDBEntity> companyDBEntityList = new ArrayList<>();
        for (JsonElement jsonElement : resultJsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String direction = jsonObject.get("0").getAsString();
            String name = jsonObject.get("1").getAsString();
            String value1 = jsonObject.get("2").getAsString();
            String value2 = jsonObject.get("3").getAsString();
            String value3 = jsonObject.get("4").getAsString();
            String value4 = jsonObject.get("5").getAsString();
            String parameter = jsonObject.get("6").getAsString();
            Long dateTime = Util.getUnixTimestampFromDateTime(jsonObject.get("7").getAsString());

            CompanyDBEntity companyDBEntity = new CompanyDBEntity(direction, name,
                    value1, value2, value3, value4,
                    parameter, dateTime);

            companyDBEntityList.add(companyDBEntity);
        }

        return companyDBEntityList;

    }

}
