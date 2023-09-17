package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.sql.SQLException;

import idv.kuan.flashcard5.R;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.kuanandroidlibs.components.InitComponentActivity;
import idv.kuan.libs.databases.daos.Dao;

public class WordEditActivity extends AppCompatActivity implements InitComponentActivity {

    private AutoCompleteTextView actvTerm, actvTranslation;
    private Button btnConfirm;

    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_edit);

        init();

        Intent intent = this.getIntent();
        actvTerm.setText(intent.getStringExtra("term"));
        actvTranslation.setText(intent.getStringExtra("translation"));
        itemId = intent.getIntExtra("id", -1);

        if (itemId == -1) {
            System.out.println("dbg WEA:itemId=-1(null)");
        }
    }

    @Override
    public void init() {
        initComponents();
    }

    @Override
    public void initComponents() {
        actvTerm = findViewById(R.id.word_edit_term);
        actvTranslation = findViewById(R.id.word_edit_translation);

        (btnConfirm = findViewById(R.id.word_edit_btn_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dao dao = new WordDao();
                Word word = new Word();

                word.setId(itemId);
                word.setTerm(actvTerm.getText().toString());
                word.setTranslation(actvTranslation.getText().toString());

                try {
                    dao.update(word);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}