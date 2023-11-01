package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import idv.kuan.flashcard5.database.Metadata;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.kuanandroidlibs.activites.ProxyMainActivity;
import idv.kuan.kuanandroidlibs.databases.provider.AndroidDBFactory;
import idv.kuan.libs.databases.utils.DBFactoryBuilder;
import idv.kuan.libs.databases.utils.schema.modifier.SchemaModifierHandler;
import idv.kuan.libs.databases.utils.schema.modifier.TableSchemaModifier;

public class MainActivity extends ProxyMainActivity {

    @Override
    public <A extends AppCompatActivity> Class<A> getTargetActivityClass() {
        return (Class<A>) WordHubActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化database
        DBFactoryBuilder.getFactory(new AndroidDBFactory(this)).config("android1", "fc5.db", "fc5.db");


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
        Connection connection = DBFactoryBuilder.getFactory().getConnection();

        SchemaModifierHandler handler = new SchemaModifierHandler(connection, appVersion);
        SchemaModifierHandler.SchemaModifierBuilder builder = handler.getSchemaModifierBuilder();
        builder.setConstructionSql("CREATE TABLE \"word\" ( " +
                " \"id\" INTEGER NOT NULL UNIQUE, " +
                " \"term\" TEXT NOT NULL, " +
                " \"translation\" TEXT NOT NULL, " +
                " \"at_created\" TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                " \"at_updated\" TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                " \"metadata\" BLOB, " +
                " PRIMARY KEY(\"id\" AUTOINCREMENT) " +
                ")");




        //$$$ 這部分需做反射處理
        TableSchemaModifier schemaModifier = (TableSchemaModifier) builder.createSchemaModifier(TableSchemaModifier.class);
        schemaModifier.setTableName("word");
        schemaModifier.setCurrentColumns("id,term,translation,at_created,at_updated");
        schemaModifier.setSelectedColumns("id,term,translation,at_created,at_updated");
        /* or set columns mapping
        schemaModifier.setColumnsMapping("id,term,translation,at_created,at_updated,translation5 :" +
                "id,term,translation,at_created,at_updated,'N/A5X2'");
        //*/

        handler.addSchemaModifier(schemaModifier);

        handler.execute();

    }


    public void testCreate() {
        WordDao dao = new WordDao();
        Word word = new Word();
        Metadata metadata=new Metadata(1);

        word.setTerm("term2_" + ((int) (Math.random() * 100)));
        word.setTranslation("translation1");
        metadata.setData("[text:234]");
        word.setMetadata(metadata);



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