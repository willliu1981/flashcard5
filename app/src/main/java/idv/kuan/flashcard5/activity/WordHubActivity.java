package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import idv.kuan.flashcard5.R;


public class WordHubActivity extends AppCompatActivity {
    private Button btnAddWord, btnWordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_hub);

        init();
    }


    private void init() {
        initComponents();
    }

    private void initComponents() {
        (btnWordEdit = findViewById(R.id.word_hub_edit_word)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordHubActivity.this, WordEditActivity.class);
                startActivity(intent);
            }
        });

        (btnAddWord = findViewById(R.id.word_hub_add_word)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordHubActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });

    }
}