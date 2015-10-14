package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EmptyBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_board);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String value = extras.getString("FileName");
            TextView test = (TextView) findViewById(R.id.textView3);
            test.setText(value);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty_board, menu);
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

    public void vectorBoard(View view)
    {
        Intent intent = new Intent(this, VectorBoardActivity.class);
        startActivity(intent);
    }

    public void breadBoard(View view)
    {
        Intent intent = new Intent(this, BreadBoardActivity.class);
        startActivity(intent);
    }
}
