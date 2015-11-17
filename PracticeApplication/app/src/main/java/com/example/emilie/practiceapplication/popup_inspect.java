package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.emilie.practiceapplication.Parser.Component;
import com.example.emilie.practiceapplication.Parser.Net;
import com.example.emilie.practiceapplication.Parser.Terminal;


public class popup_inspect extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_inspect);
        Intent intent = getIntent();
        Component component = (Component) intent.getSerializableExtra("component");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.componentInfo);
        TextView textView = new TextView(this);
        if(component.getValue() != null)
            textView.setText("Name: " + component.Name + " Type: " + component.type + " Value: " + component.getValue());
        else
            textView.setText("Name: " + component.Name + " Type: " + component.type);
        linearLayout.addView(textView);

        TextView netText = new TextView(this);
        netText.setText("Nets");
        linearLayout.addView(netText);

        for(Terminal terminal: component.getTerminals())
        {
            TextView terminalText = new TextView(this);
            terminalText.setText(terminal.getName() + " is in net " + terminal.CurrentNet);
            linearLayout.addView(terminalText);
        }
        Resources res = getResources();
        TypedArray forward = res.obtainTypedArray(R.array.forward);

        TableLayout tableLayout = new TableLayout(this);
        TableRow tableRow = new TableRow(this);
        int i;
        ImageView imageView = new ImageView(this);;
        switch(component.type)
        {
            case "res":
                for(i=0;i<4;i++)
                {

                    imageView.setImageDrawable(forward.getDrawable(i));
                    tableRow.addView(imageView);
                }
                tableLayout.addView(tableRow);
                break;
            case "cap":
                for(i=0;i<2;i++)
                {
                    imageView.setImageDrawable(forward.getDrawable(i+32));
                    tableRow.addView(imageView);
                }
                tableLayout.addView(tableRow);
                break;
            case "ind":
                for(i=0;i<4;i++)
                {
                    imageView.setImageDrawable(forward.getDrawable(i+16));
                    tableRow.addView(imageView);
                }
                tableLayout.addView(tableRow);
                break;
            default:
                break;
        }

        TableRow row = new TableRow(this);
        TextView one = new TextView(this);
        TextView two = new TextView(this);
        one.setText("A");
        two.setText("B");
        row.addView(one,0);
        row.addView(two,3);
        tableLayout.addView(row);
        linearLayout.addView(tableLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popup_inspect, menu);
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
