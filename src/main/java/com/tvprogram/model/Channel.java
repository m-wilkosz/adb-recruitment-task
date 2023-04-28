package com.tvprogram.model;

import java.util.List;

public class Channel {
    private String id;
    private String name;
    private String logoUrl;
    private List<Program> programs;

    public Channel(String id, String name, String logoUrl, List<Program> programs) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.programs = programs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}