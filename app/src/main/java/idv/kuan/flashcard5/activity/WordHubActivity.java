package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;
import java.util.List;

import idv.kuan.flashcard5.R;
import idv.kuan.flashcard5.adapters.WordAdapter;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.kuanandroidlibs.components.InitComponentActivity;
import idv.kuan.libs.databases.daos.Dao;


public class WordHubActivity extends AppCompatActivity implements InitComponentActivity {
    private Button btnAddWord, btnWordEdit;
    private RecyclerView rvItemWordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_hub);

        init();
    }

    @Override
    public void init() {
        initComponents();

        Dao dao = new WordDao();
        try {
            List listWord = dao.findAll();
            WordAdapter wordAdapter = new WordAdapter(listWord);
            rvItemWordView.setAdapter(wordAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initComponents() {
        (btnWordEdit = findViewById(R.id.word_hub_btn_edit_word)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordHubActivity.this, WordEditActivity.class);
                startActivity(intent);
            }
        });

        (btnAddWord = findViewById(R.id.word_hub_btn_add_word)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordHubActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });

        (rvItemWordView = findViewById(R.id.word_hub_rv_word_list)).setLayoutManager(new LinearLayoutManager(this));


    }
}