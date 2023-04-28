package com.tvprogram.view;

import com.tvprogram.model.Channel;
import com.tvprogram.model.Program;
import com.tvprogram.utils.LogoProvider;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChannelListView extends JFrame {

    private static final int LOGO_WIDTH = 100;
    private static final int LOGO_HEIGHT = 50;

    public ChannelListView(List<Channel> channels) {
        setTitle("Aktualne programy TV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            constraints.gridy = i;

            constraints.gridx = 0;
            add(new JLabel(channel.getName()), constraints);

            constraints.gridx = 1;
            ImageIcon logo = LogoProvider.getLogo(channel.getLogoUrl(), LOGO_WIDTH, LOGO_HEIGHT);
            if (logo != null) {
                add(new JLabel(logo), constraints);
            }

            constraints.gridx = 2;
            Program currentProgram = getCurrentProgram(channel.getPrograms());
            if (currentProgram != null) {
                add(new JLabel(currentProgram.getName()), constraints);
            }
        }

        pack();
        setLocationRelativeTo(null);
    }

    private Program getCurrentProgram(List<Program> programs) {
        LocalDateTime now = LocalDateTime.now();
        return programs.stream()
                .filter(p -> p.getStartTime().isBefore(now) && p.getEndTime().isAfter(now))
                .findFirst()
                .orElse(null);
    }
}