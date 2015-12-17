package io.bloc.android.blocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 12/17/2015.
 */
public class BlocsMenuActivity extends Activity{

    TextView mTextViewGameTitle;
    Button mButtonPlayGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_blocs);
        initUI();

    }

    private void initUI() {
            //init activity views
        mTextViewGameTitle = (TextView) findViewById(R.id.tv_game_title);
        mButtonPlayGame = (Button) findViewById(R.id.btn_start_game);

        mButtonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BlocsGameActivity.class);
                startActivity(intent);
            }
        });
    }

}
