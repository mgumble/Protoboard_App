package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
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
            textView.setText("Name: " + component.Name + ", Type: " + component.type + ", Image: ");
            hor.addView(textView);
            int i;
            Resources res = getResources();
            TypedArray forward = res.obtainTypedArray(R.array.forward);
            TableRow tableRow = new TableRow(this);
            switch (component.type)
            {
                case "res":
                    for(i=0;i<4;i++)
                    {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageDrawable(forward.getDrawable(i));
                        tableRow.addView(imageView);
                    }
                    break;
                case "cap":
                    for(i=0;i<2;i++)
                    {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageDrawable(forward.getDrawable(i+32));
                        tableRow.addView(imageView);
                    }
                    break;
                case "ind":
                    for(i=0;i<4;i++)
                    {
                        ImageView imageView = new ImageView(this);
                        imageView.getParent();
                        imageView.setImageDrawable(forward.getDrawable(i+16));
                        tableRow.addView(imageView);
                    }
                    break;
                default:
                    break;
            }
            hor.addView(tableRow);

            linearLayout.addView(hor);
        }
    }

    public void submit(View v)
    {
        Intent i = new Intent(MaterialListActivity.this,VectorBoardActivity.class);
        i.putExtra("ComponentList", componentList);
        EditText rows = (EditText) findViewById(R.id.rows);
        EditText cols = (EditText) findViewById(R.id.columns);
        int r = Integer.parseInt(rows.getText().toString());
        int c = Integer.parseInt(cols.getText().toString());
        if((!rows.getText().toString().equals("") && !cols.getText().toString().equals("")) && r<12 && r>0 && c<13 && c>0)
        {
            i.putExtra("row", r);
            i.putExtra("col", c);
            startActivity(i);
        }
        else
        {
            i.putExtra("row", 12);
            i.putExtra("col", 13);
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
