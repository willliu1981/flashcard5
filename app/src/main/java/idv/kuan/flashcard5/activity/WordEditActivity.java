package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import idv.kuan.flashcard5.R;
import idv.kuan.kuanandroidlibs.components.InitComponentActivity;

public class WordEditActivity extends AppCompatActivity implements InitComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_edit);
    }

    @Override
    public void init() {

    }

    @Override
    public void initComponents() {

    }
}