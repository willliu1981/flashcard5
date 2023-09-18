package idv.kuan.flashcard5.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import idv.kuan.flashcard5.R;
import idv.kuan.flashcard5.adapters.WordAdapter;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.kuanandroidlibs.components.InitComponentActivity;
import idv.kuan.libs.databases.daos.Dao;


public class WordHubActivity extends AppCompatActivity implements InitComponentActivity {
    public static int REQUEST_CODE_EDIT_WORD = 1;
    public static int REQUEST_CODE_ADD_WORD = 2;
    public static String RESULT_CODE_EDIT_WORD_UPDATE = "update";
    public static String RESULT_CODE_EDIT_WORD_DELETE = "delete";

    public static String RESULT_KEY_OPERATION = "operation";
    public static String RESULT_KEY_WORD = "word";

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

        refreshWordList();

    }

    @Override
    public void initComponents() {

        (btnAddWord = findViewById(R.id.word_hub_btn_add_word)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordHubActivity.this, AddWordActivity.class);
                ((Activity) view.getContext()).startActivityForResult(intent, WordHubActivity.REQUEST_CODE_ADD_WORD);
            }
        });

        (rvItemWordView = findViewById(R.id.word_hub_rv_word_list)).setLayoutManager(new LinearLayoutManager(this));
        rvItemWordView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    public void refreshWordList() {
        Dao dao = new WordDao();
        try {
            List listWord = dao.findAll();
            WordAdapter wordAdapter = new WordAdapter(listWord);
            rvItemWordView.setAdapter(wordAdapter);
            rvItemWordView.refreshDrawableState();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WordHubActivity.REQUEST_CODE_ADD_WORD && resultCode == RESULT_OK) {
            String word = data.getStringExtra(WordHubActivity.RESULT_KEY_WORD);
            String toastMsg = "";
            if (word == null) {
                toastMsg = "error";
            } else {
                toastMsg = "Added " + word;
                refreshWordList();
            }
            Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
        } else if (requestCode == WordHubActivity.REQUEST_CODE_EDIT_WORD && resultCode == RESULT_OK) {
            String word = data.getStringExtra(WordHubActivity.RESULT_KEY_WORD);
            String operation = data.getStringExtra(WordHubActivity.RESULT_KEY_OPERATION);
            String toastMsg = "";
            if (operation == null || word == null) {
                toastMsg = "error";
            } else {
                if (operation.equals(WordHubActivity.RESULT_CODE_EDIT_WORD_UPDATE)) {
                    toastMsg = "Updated " + word;
                    refreshWordList();
                } else if (operation.equals(WordHubActivity.RESULT_CODE_EDIT_WORD_DELETE)) {
                    toastMsg = word + " has been deleted";
                    refreshWordList();
                }
            }

            Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
        }


    }
}