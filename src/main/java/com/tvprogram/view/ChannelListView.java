package com.tvprogram.view;

import com.tvprogram.model.Channel;
import com.tvprogram.model.Program;
import com.tvprogram.utils.LogoProvider;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ChannelListView extends JFrame {

    private static final int LOGO_WIDTH = 100;
    private static final int LOGO_HEIGHT = 50;

    private final JList<Channel> channelList;
    private final JLabel programLabel;

    public ChannelListView(List<Channel> channels) {
        setTitle("Aktualny program TV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultListModel<Channel> listModel = new DefaultListModel<>();
        channels.forEach(listModel::addElement);

        channelList = new JList<>(listModel);
        channelList.setCellRenderer(new ChannelListRenderer());

        programLabel = new JLabel();
        programLabel.setVerticalAlignment(SwingConstants.TOP);

        channelList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Channel selectedChannel = channelList.getSelectedValue();
                if (selectedChannel != null) {
                    Program currentProgram = getCurrentProgram(selectedChannel.getPrograms());
                    if (currentProgram != null) {
                        programLabel.setText("<html><h2 style='margin-left: 10px;'>" + selectedChannel.getName()
                                + "</h2><br><h4 style='margin-left: 10px;'>" + currentProgram.getName() + "</h4></html>");
                    } else {
                        programLabel.setText("<html><h2 style='margin-left: 10px;'>" + selectedChannel.getName()
                                + "</h2><br><h4 style='margin-left: 10px;'>Brak aktualnego programu</h4></html>");
                    }
                    programLabel.repaint();
                }
            }
        });

        JScrollPane channelListScrollPane = new JScrollPane(channelList);
        JScrollPane programScrollPane = new JScrollPane(programLabel);

        add(channelListScrollPane, BorderLayout.WEST);
        add(programScrollPane, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);

        Timer timer = new Timer(60 * 1000, e -> updateProgram());
        timer.start();
    }

    private class ChannelListRenderer extends JLabel implements ListCellRenderer<Channel> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Channel> list, Channel channel, int index, boolean isSelected, boolean cellHasFocus) {
            String text = channel.getName();
            ImageIcon logo = LogoProvider.getLogo(channel.getLogoUrl(), LOGO_WIDTH, LOGO_HEIGHT);
            if (logo != null) {
                setIcon(logo);
            } else {
                setIcon(null);
            }
            setText(text);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);

            return this;
        }
    }

    private Program getCurrentProgram(List<Program> programs) {
        LocalDateTime now = LocalDateTime.now();
        return programs.stream()
                .filter(p -> p.getStartTime().isBefore(now) && p.getEndTime().isAfter(now))
                .findFirst()
                .orElse(null);
    }

    private void updateProgram() {
        Channel selectedChannel = channelList.getSelectedValue();
        if (selectedChannel != null) {
            Program currentProgram = getCurrentProgram(selectedChannel.getPrograms());
            if (currentProgram != null) {
                programLabel.setText("<html><h2 style='margin-left: 10px;'>" + selectedChannel.getName()
                        + "</h2><br><h4 style='margin-left: 10px;'>" + currentProgram.getName() + "</h4></html>");
            } else {
                programLabel.setText("<html><h2 style='margin-left: 10px;'>" + selectedChannel.getName()
                        + "</h2><br><h4 style='margin-left: 10px;'>Brak aktualnego programu</h4></html>");
            }
            programLabel.repaint();
        }
    }
}