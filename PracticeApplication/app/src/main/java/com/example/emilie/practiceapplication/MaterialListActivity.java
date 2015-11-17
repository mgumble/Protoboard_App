package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emilie.practiceapplication.Parser.Component;

import java.io.Serializable;
import java.util.ArrayList;

public class MaterialListActivity extends AppCompatActivity {

    public Serializable serializedList;
    public ArrayList<Component> componentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        Intent intent = getIntent();
        serializedList = intent.getSerializableExtra("ComponentList");
        componentList = (ArrayList<Component>) serializedList;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.BOM);

        for(Component component:componentList)
        {
            LinearLayout hor = new LinearLayout(this);
            hor.setOrientation(LinearLayout.HORIZONTAL);
            TextView textView = new TextView(this);
            textView.setText(component.Name);
            hor.addView(textView);
            ImageView imageView = new ImageView(this);
            switch (component.type)
            {
                case "res":
                    imageView.setImageResource(R.drawable.resistorfinal);
                    imageView.setMaxHeight(75);
                    imageView.setMaxWidth(100);
                    break;
                case "cap":
                    imageView.setImageResource(R.drawable.capacitorfinal);
                    imageView.setMaxHeight(75);
                    imageView.setMaxWidth(100);
                    break;
                case "ind":
                    imageView.setImageResource(R.drawable.inductor);
                    imageView.setMaxHeight(75);
                    imageView.setMaxWidth(100);
                    break;
                default:
                    break;
            }
            hor.addView(imageView);

            linearLayout.addView(hor);
        }
    }

    public void submit(View v)
    {
        Intent i = new Intent(MaterialListActivity.this,VectorBoardActivity.class);
        i.putExtra("ComponentList", componentList);
        EditText rows = (EditText) findViewById(R.id.rows);
        EditText cols = (EditText) findViewById(R.id.columns);
        if(!rows.getText().equals("") && !cols.getText().equals(""))
        {
            i.putExtra("row", Integer.parseInt(rows.getText().toString()));
            i.putExtra("col", Integer.parseInt(cols.getText().toString()));
            startActivity(i);
        }
        else
        {
            i.putExtra("row", 10);
            i.putExtra("col", 10);
            startActivity(i);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_material_list, menu);
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
}
