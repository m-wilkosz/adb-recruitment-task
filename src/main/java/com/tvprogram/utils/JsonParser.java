package com.tvprogram.utils;

import com.tvprogram.model.Channel;
import com.tvprogram.model.Program;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Channel> parseChannels(String fileName) {
        List<Channel> channels = new ArrayList<>();

        try {
            InputStream inputStream = JsonParser.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            byte[] jsonData = inputStream.readAllBytes();
            String content = new String(jsonData);
            JSONObject json = new JSONObject(content);
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject channelJson = data.getJSONObject(i);
                String id = channelJson.getString("id");
                String name = channelJson.getString("name");

                String logoUrl = null;
                if (channelJson.has("logos")) {
                    JSONArray logos = channelJson.getJSONArray("logos");
                    if (logos.length() > 0) {
                        logoUrl = logos.getJSONObject(0).getString("url");
                    }
                }

                channels.add(new Channel(id, name, logoUrl, new ArrayList<>()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return channels;
    }

    public static void parseEvents(String fileName, List<Channel> channels) {
        try {
            InputStream inputStream = JsonParser.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            byte[] jsonData = inputStream.readAllBytes();
            String content = new String(jsonData);
            JSONObject json = new JSONObject(content);
            JSONArray data = json.getJSONArray("data");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

            for (int i = 0; i < data.length(); i++) {
                JSONObject eventJson = data.getJSONObject(i);
                String channelId = eventJson.getString("channelId");
                String eventId = eventJson.getString("id");
                String eventName = eventJson.getString("name");
                LocalDateTime startTime = LocalDateTime.parse(eventJson.getString("startTime"), formatter);
                LocalDateTime endTime = LocalDateTime.parse(eventJson.getString("endTime"), formatter);

                Program program = new Program(eventId, eventName, startTime, endTime);

                for (Channel channel : channels) {
                    if (channel.getId().equals(channelId)) {
                        channel.getPrograms().add(program);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}