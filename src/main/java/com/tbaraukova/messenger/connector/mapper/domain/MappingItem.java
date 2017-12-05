package com.tbaraukova.messenger.connector.mapper.domain;

public class MappingItem {
    private String messenger;
    private String id;

    public MappingItem() {
    }

    public MappingItem(String messenger, String id) {
        this.messenger = messenger;
        this.id = id;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return messenger + "_" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappingItem that = (MappingItem) o;

        return (messenger != null ? messenger.equals(that.messenger) : that.messenger == null)
                && (id != null ? id.equals(that.id) : that.id == null);
    }

    @Override
    public int hashCode() {
        int result = messenger != null ? messenger.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
