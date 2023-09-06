package idv.kuan.flashcard5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import idv.kuan.kuanandroidlibs.activites.ProxyMainActivity;

public class MainActivity extends ProxyMainActivity {

    @Override
    public <A extends AppCompatActivity> Class<A> getTargetActivityClass() {
        return (Class<A>) TestActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


}