package idv.kuan.flashcard5.database.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import idv.kuan.flashcard5.database.Metadata;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.libs.databases.daos.CommonDao;
import idv.kuan.libs.databases.utils.QueryBuilder;

public class WordDao extends CommonDao<Word> {
    @Override
    protected Word createNewEntity() {
        return new Word();
    }

    @Override
    protected void populateBuilderWithEntityProperties(QueryBuilder builder, Word entity) {
        builder.addColumnValue("term", entity.getTerm());
        builder.addColumnValue("translation", entity.getTranslation());
        builder.addColumnValue("at_created", entity.getAtCreated());
        builder.addColumnValue("translation", entity.getTranslation());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(entity.getMetadata());
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] dataToSave = bos.toByteArray();

        builder.addColumnValue("metadata", dataToSave);
        //builder.addColumnValue("translation5", entity.getTranslation() + "_test");

    }

    @Override
    protected void mapResultSetToEntity(Word entity, ResultSet resultSet) throws SQLException {


        entity.setId(resultSet.getInt("id"));
        entity.setTerm(resultSet.getString("term"));
        entity.setTranslation(resultSet.getString("translation"));
        entity.setAtCreated(resultSet.getString("at_created"));
        entity.setAtUpdated(resultSet.getString("at_updated"));

        byte[] retrievedData = resultSet.getBytes("metadata");
        if (retrievedData != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(retrievedData);
            try {
                ObjectInputStream ois = new ObjectInputStream(bis);
                Metadata retrievedMetadata = (Metadata) ois.readObject();
                entity.setMetadata(retrievedMetadata);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected String getTableName() {
        return "word";
    }
}
