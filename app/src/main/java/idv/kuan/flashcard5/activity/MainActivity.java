package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.kuanandroidlibs.activites.ProxyMainActivity;
import idv.kuan.kuanandroidlibs.databases.provider.AndroidDBFactory;
import idv.kuan.libs.databases.DBFactoryCreator;
import idv.kuan.libs.databases.utils.SQLiteSchemaModifiers;

public class MainActivity extends ProxyMainActivity {

    @Override
    public <A extends AppCompatActivity> Class<A> getTargetActivityClass() {
        return (Class<A>) WordHubActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化database
        DBFactoryCreator.getFactory(new AndroidDBFactory(this)).config("android1", "fc5.db", "fc5.db");


        //是否更新database結構
        PackageManager packageManager = getPackageManager();
        int versionCode = -4;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        changeTableStructure(versionCode);


        testCreate();
        testQuery();
    }

    private void changeTableStructure(int appVersion) {
        Connection connection = DBFactoryCreator.getFactory().getConnection();
        String sql1 = "CREATE TABLE \"word\" ( " +
                " \"id\" INTEGER NOT NULL UNIQUE, " +
                " \"term\" TEXT NOT NULL, " +
                " \"translation\" TEXT NOT NULL, " +
                " \"translation5\" , " +
                " \"at_created\" TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                " \"at_updated\" TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                " PRIMARY KEY(\"id\" AUTOINCREMENT) " +
                ")";

        String partMigrateSql = "id,term,translation,at_created,at_updated,translation5 : " +
                "id,term,translation,at_created,at_updated,'N/A5X1'";

        SQLiteSchemaModifiers.SchemaModifier schemaModifier = SQLiteSchemaModifiers.createSchemaModifier(connection, appVersion);
        schemaModifier.addSchemaModifierExecutor((c, v) -> {
            final String TABLE_NAME = "word";
            //method1:
            /*
            SQLiteSchemaModifiers.createOrUpdateTableWithDataMigration(c,
                    v, TABLE_NAME, sql1, partMigrateSql);
            //*/

            //method2:
            //*
            SQLiteSchemaModifiers.SchemaModifierSQL schemaModifierSQL = new SQLiteSchemaModifiers.SchemaModifierSQL(TABLE_NAME);
            schemaModifierSQL.createInsertIntoSQL("id,term,translation,at_created,at_updated,translation5",
                    "id,term,translation,at_created,at_updated,'N/A5X2'");
            SQLiteSchemaModifiers.createOrUpdateTableWithDataMigration(c, v, TABLE_NAME, sql1, schemaModifierSQL);
            //*/
            //最後組合出來的sql 可能會是:"INSERT INTO " + tableName + partMigrateSql + " FROM " + tableName + "__temp"
        });


        schemaModifier.createDatabase();

    }


    public void testCreate() {
        WordDao dao = new WordDao();
        Word word = new Word();
        word.setTerm("term1_" + ((int) (Math.random() * 100)));
        word.setTranslation("translation1");

        try {
            dao.create(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testQuery() {
        WordDao dao = new WordDao();
        try {
            List<Word> all = dao.findAll();
            System.out.println("xxx MA:for each=");
            all.forEach(x -> System.out.println("e=" + x));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}