package idv.kuan.flashcard5.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    }

    @Override
    protected void mapResultSetToEntity(Word entity, ResultSet resultSet) throws SQLException {
        entity.setId(resultSet.getInt("id"));
        entity.setTerm(resultSet.getString("term"));
        entity.setTranslation(resultSet.getString("translation"));
        entity.setAtCreated(resultSet.getString("at_created"));
        entity.setAtUpdated(resultSet.getString("at_updated"));
    }

    @Override
    protected String getTableName() {
        return "word";
    }
}
