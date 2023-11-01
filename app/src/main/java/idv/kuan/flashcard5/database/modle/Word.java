package idv.kuan.flashcard5.database.modle;

import java.sql.Timestamp;

import idv.kuan.flashcard5.database.Metadata;
import idv.kuan.libs.databases.models.CommonEntity;

public class Word implements CommonEntity {
    private Integer id;
    private String term;
    private String translation;
    private Timestamp atCreated;
    private Timestamp atUpdated;
    private Metadata metadata;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public Timestamp getAtCreated() {
        return atCreated;
    }

    @Override
    public void setAtCreated(Timestamp atCreated) {
        this.atCreated = atCreated;
    }

    @Override
    public Timestamp getAtUpdated() {
        return atUpdated;
    }

    @Override
    public void setAtUpdated(Timestamp atUpdated) {
        this.atUpdated = atUpdated;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", translation='" + translation + '\'' +
                ", atCreated=" + atCreated +
                ", atUpdated=" + atUpdated +
                ", metadata=" + metadata +
                '}';
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
