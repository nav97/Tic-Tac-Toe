package com.DevNav.Nav.tictactoe;

/**
 * Created by navi on 2014-12-03.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainMenuScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu);

        ((Button) findViewById(R.id.one_player)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainMenuScreen.this, MyActivity.class);
                intent.putExtra("gameType", true);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.two_player)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainMenuScreen.this, MyActivity.class);
                intent.putExtra("gameType", false);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.exit_game)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                MainMenuScreen.this.finish();
            }
        });
    }
}
