package idv.kuan.flashcard5.database;

import java.io.Serializable;

public class Metadata implements Serializable {
    private Integer version;
    private String data;

    public Metadata(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "version=" + version +
                ", data='" + data + '\'' +
                '}';
    }

    public void setData(String data) {
        this.data = data;
    }
}
