package idv.kuan.flashcard5.database.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.libs.databases.daos.CommonDao;
import idv.kuan.libs.databases.models.MetadataEntity;
import idv.kuan.libs.databases.models.MetadataEntityUtil;
import idv.kuan.libs.databases.utils.QueryBuilder;

public class WordDao extends CommonDao<Word> {
    @Override
    protected Word createNewEntity() {
        return new Word();
    }

    /**
     * 寫入到table
     *
     * @param builder
     * @param entity
     */
    @Override
    protected void populateBuilderWithEntityProperties(QueryBuilder builder, Word entity) {
        builder.addColumnValue("term", entity.getTerm());
        builder.addColumnValue("translation", entity.getTranslation());
        builder.addColumnValue("at_created", entity.getAtCreated());
        builder.addColumnValue("translation", entity.getTranslation());
        //*
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

        // */

    }

    /**
     * 塞進Entity
     *
     * @param entity
     * @param resultSet
     * @throws SQLException
     */
    @Override
    protected void mapResultSetToEntity(Word entity, ResultSet resultSet) throws SQLException {


        entity.setId(resultSet.getInt("id"));
        entity.setTerm(resultSet.getString("term"));
        entity.setTranslation(resultSet.getString("translation"));
        entity.setAtCreated(resultSet.getString("at_created"));
        entity.setAtUpdated(resultSet.getString("at_updated"));

        //*
        byte[] retrievedData = resultSet.getBytes("metadata");
        MetadataEntity.Metadata metadata = MetadataEntityUtil.metadataBuilder().setData(retrievedData).build();
        entity.setMetadata(metadata);

        // */


    }

    @Override
    protected String getTableName() {
        return "word";
    }
}
