package com.tbaraukova.messenger.connector.mapper.domain;

public class MappingPair {

    private String id;
    private MappingItem from;
    private MappingItem to;

    public MappingItem getFrom() {
        return from;
    }

    public void setFrom(MappingItem from) {
        this.from = from;
    }

    public MappingItem getTo() {
        return to;
    }

    public void setTo(MappingItem to) {
        this.to = to;
    }

    public String getId() {
        id = id == null ? toString() : id;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return from + "_" + to;
    }

}
