package idv.kuan.flashcard5.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.sql.SQLException;

import idv.kuan.flashcard5.R;
import idv.kuan.flashcard5.database.dao.WordDao;
import idv.kuan.flashcard5.database.modle.Word;
import idv.kuan.kuanandroidlibs.components.InitComponentActivity;
import idv.kuan.libs.databases.daos.Dao;

public class WordEditActivity extends AppCompatActivity implements InitComponentActivity {
    public static int REQUEST_CODE = 1;
    private AutoCompleteTextView actvTerm, actvTranslation;
    private Button btnConfirm, btnOptions;

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
                    completeActivity();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        (btnOptions = findViewById(R.id.word_edit_btn_options)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(WordEditActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.edit_word_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.word_edit_menu_item_delete:

                                showDeleteConfirmationDialog();

                                return true;
                            case R.id.word_edit_menu_item_move:
                                return true;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }

            private void showDeleteConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(WordEditActivity.this);
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dao dao = new WordDao();
                        Word word = new Word();
                        word.setId(itemId);
                        try {
                            dao.delete(word);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Toast.makeText(WordEditActivity.this, "error", Toast.LENGTH_LONG).show();
                        }
                        dialogInterface.dismiss();
                        completeActivity();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });
    }

    private void completeActivity() {
        setResult(RESULT_OK);
        finish();
    }

}