package idv.kuan.flashcard5.database.modle;


import idv.kuan.libs.databases.models.MetadataEntity;

public class Word extends MetadataEntity {

    private String term;
    private String translation;


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
    public String toString() {
        return "Word{" +
                "term='" + term + '\'' +
                ", translation='" + translation + '\'' +
                ", id=" + id +
                ", metadata=" + metadata +
                '}';
    }

}
