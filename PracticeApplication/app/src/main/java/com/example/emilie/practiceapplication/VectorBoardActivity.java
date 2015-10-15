package com.example.emilie.practiceapplication;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class VectorBoardActivity extends AppCompatActivity {

    private android.widget.RelativeLayout.LayoutParams layoutParams;
    public ArrayList<ImageView> iv = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_board);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.ll_top);
//        layout.setOnDragListener((View.OnDragListener) this);
        rLayout.setOnDragListener((View.OnDragListener) this);

        for (int i = 0; i < 50; i++) //replace the 50 with components.size
        {
            final ImageView im = new ImageView(this);
            layout.addView(im);
            im.setImageResource(R.drawable.res);
            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

//                    ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(im);

                    v.startDrag(null, myShadow, null, 0);
                    return true;
                }
            });

            im.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }

//                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            // Do nothing
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            int x_cord = (int) event.getX();
                            int y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();
                            layoutParams.leftMargin = x_cord;
                            layoutParams.topMargin = y_cord;
                            v.setLayoutParams(layoutParams);
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_ENDED:
                            // Do nothing
                            break;

                        case DragEvent.ACTION_DROP:
                            // Do nothing
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            im.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(im);

                        im.startDrag(data, shadowBuilder, im, 0);
                        im.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vector_board, menu);
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