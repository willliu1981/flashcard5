package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.sql.SQLException;

import idv.kuan.flashcard5.R;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.libs.databases.daos.Dao;


public class AddWordActivity extends AppCompatActivity {
    private AutoCompleteTextView actvTerm,actvTranslation;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        init();
    }


    private void init() {
        initComponents();
    }

    private void initComponents() {
        actvTerm=findViewById(R.id.add_word_actv_term);
        actvTranslation=findViewById(R.id.add_word_actv_translation);

        (btnConfirm=findViewById(R.id.add_word_btn_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dao dao=new WordDao();
                Word word=new Word();
                word.setTerm(actvTerm.getText().toString());
                word.setTranslation(actvTranslation.getText().toString());

                try {
                    dao.create(word);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}