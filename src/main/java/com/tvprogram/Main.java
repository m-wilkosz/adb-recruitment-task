package com.tvprogram;

import com.tvprogram.model.Channel;
import com.tvprogram.utils.JsonParser;
import com.tvprogram.utils.TimeConverter;
import com.tvprogram.view.ChannelListView;

import javax.swing.SwingUtilities;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Channel> channels = JsonParser.parseChannels("channels.json");
        JsonParser.parseEvents("events.json", channels);

        channels.forEach(channel -> TimeConverter.convertToCurrentDate(channel.getPrograms()));

        SwingUtilities.invokeLater(() -> {
            ChannelListView listView = new ChannelListView(channels);
            listView.setVisible(true);
        });
    }
}