package com.example.emilie.practiceapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.emilie.practiceapplication.Parser.Component;
import com.example.emilie.practiceapplication.Parser.Net;
import com.example.emilie.practiceapplication.Parser.Terminal;

import java.util.List;


public class popup_inspect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_inspect);
        Intent intent = getIntent();
        Component component = (Component) intent.getSerializableExtra("component");
        if (component == null)
            finish();

       //Popup Code
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.5f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.5));
        //Popup Code

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
        final List<Terminal> terminals = component.getTerminals();
        for(Terminal terminal: terminals)
        {
            TextView terminalText = new TextView(this);
            terminalText.setText(terminal.getName() + " is in net " + terminal.CurrentNet.IntName);
            linearLayout.addView(terminalText);
        }
        Resources res = getResources();
        TypedArray forward = res.obtainTypedArray(R.array.forward);

        TableLayout tableLayout = new TableLayout(this);
        TableRow tableRow = new TableRow(this);
        int i;
        TextView one;
        TextView two;
        switch(component.type)
        {
            case "res":
                one = new TextView(this);
                two = new TextView(this);
                one.setText(terminals.get(0).getName());
                two.setText(terminals.get(1).getName());
                tableRow.addView(one);
                for(i=0;i<4;i++)
                {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageDrawable(forward.getDrawable(i));
                    tableRow.addView(imageView);
                }
                tableRow.addView(two);
                tableLayout.addView(tableRow);
                break;
            case "cap":
                one = new TextView(this);
                two = new TextView(this);
                one.setText(terminals.get(0).getName());
                two.setText(terminals.get(1).getName());
                tableRow.addView(one);
                for(i=0;i<2;i++)
                {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageDrawable(forward.getDrawable(i+32));
                    tableRow.addView(imageView);
                }
                tableRow.addView(two);
                tableLayout.addView(tableRow);
                break;
            case "ind":
                one = new TextView(this);
                two = new TextView(this);
                one.setText(terminals.get(0).getName());
                two.setText(terminals.get(1).getName());
                tableRow.addView(one);
                for(i=0;i<4;i++)
                {
                    ImageView imageView = new ImageView(this);
                    imageView.getParent();
                    imageView.setImageDrawable(forward.getDrawable(i+16));
                    tableRow.addView(imageView);
                }
                tableRow.addView(two);
                tableLayout.addView(tableRow);
                break;
            default:
                break;
        }
        linearLayout.addView(tableLayout);
    }

}
