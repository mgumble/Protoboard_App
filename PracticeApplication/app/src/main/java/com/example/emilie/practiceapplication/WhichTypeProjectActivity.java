package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WhichTypeProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Button EmptyBoard = (Button) findViewById(R.id.empty_board_button);
        Button LTButton = (Button) findViewById(R.id.LT_button);
        setContentView(R.layout.activity_which_type_project);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_which_type_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void emptyBoard(View view)
    {
        Intent intent = new Intent(this, EmptyBoardActivity.class);
        startActivity(intent);
    }

    public void LTBoard(View view)
    {
        Intent intent = new Intent(this, LTBoardActivity.class);
        startActivity(intent);
    }
}
