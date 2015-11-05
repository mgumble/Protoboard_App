package com.example.emilie.practiceapplication;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class VectorBoardActivity extends AppCompatActivity {

    private android.widget.RelativeLayout.LayoutParams layoutParams;
    public ArrayList<ImageView> iv = new ArrayList<ImageView>();
    ImageView board;

    ImageView resistor;
    ImageView resPart;
    ImageView capacitor;
    ImageView capPart;
    ImageView inductor;
    ImageView indPart;

    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_board);
        Intent intent = getIntent();
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.ll_top);
        tableLayout = (TableLayout) findViewById(R.id.tl);


        //TODO Get everything not just strings
        ArrayList<String> componentList = intent.getStringArrayListExtra("ComponentList");

        //TODO make this dynamic based off of what the user inputs
        for(int i=0;i<12;i++)
        {
            TableRow row = new TableRow(this);
            for(int j=0;j<13;j++)
            {
                ImageView image = new ImageView(this);

                image.setImageResource(R.drawable.hole25x25);
                image.setOnDragListener(new MyDragListener());
//                image.requestLayout();
//                image.getLayoutParams().height=50;
//                image.getLayoutParams().width=50;
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
//                image.setLayoutParams(layoutParams);
                row.addView(image,j);
            }

            tableLayout.addView(row,i);
        }
        /*Builds the List of components*/
        for (int i = 0; i < componentList.size(); i++)
        {
            final ImageView im = new ImageView(this);
            layout.addView(im);
            im.setImageResource(R.drawable.resistorfinal); //Not sure why this is here

            switch (componentList.get(i)) {
                case "res":
                    im.setImageResource(R.drawable.resistorfinal);
                    break;
                case "cap":
                    im.setImageResource(R.drawable.capacitorfinal);
                    break;
                case "ind":
                    im.setImageResource(R.drawable.inductor);
                    break;
                default:
                    //TODO needs to include the IC size
                    break;
            }
            iv.add(im);
        }

        initControls(); //Set each image to be draggable1
    }

    private void initControls() {
        for(int i=0;i<iv.size();i++) {
           // iv.get(i).setTag(Integer.parseInt("%d"),i);
            iv.get(i).setOnTouchListener(new MyTouchListener());
        }

        tableLayout = (TableLayout) findViewById(R.id.tl);
        tableLayout.setOnDragListener(new MyDragListener());

        resistor = new ImageView(this);
        resistor.setImageResource(R.drawable.resistorfinal);
        resPart = new ImageView(this);
        resPart.setImageResource(R.drawable.res2_4);
        capacitor = new ImageView(this);
        capacitor.setImageResource(R.drawable.capacitorfinal);
        capPart = new ImageView(this);
        capPart.setImageResource(R.drawable.capacitor2_2);
        inductor = new ImageView(this);
        inductor.setImageResource(R.drawable.inductor);
        indPart = new ImageView(this);
        indPart.setImageResource(R.drawable.inductor2_4);
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data, shadowBuilder, v, 0);

                v.setVisibility(View.INVISIBLE);
                return true;
            }
            if(event.getAction()== MotionEvent.ACTION_UP)
            {
                v.setVisibility(View.VISIBLE);
                return true;
            }
            else return false;

        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
//                    layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    // Do nothing
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    int x_cord = (int) event.getX();
                    int y_cord = (int) event.getY();
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    x_cord = (int) event.getX();
                    y_cord = (int) event.getY();
//                    layoutParams.leftMargin = x_cord;
//                    layoutParams.topMargin = y_cord;
//                    v.setLayoutParams(layoutParams);
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    if(v!=board) {
                        v.setVisibility(View.VISIBLE);
                    }
//                    x_cord = (int) event.getX();
//                    y_cord = (int) event.getY();
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    // Do nothing
                     if (dropEventNotHandled(event))
                         v.setVisibility(View.VISIBLE);
                    break;

                case DragEvent.ACTION_DROP:
                    // Do nothing
                    View view = (View) event.getLocalState();
                    // view dragged item is being dropped on
                    ImageView dropTarget = (ImageView) v;
                    //view being dragged and dropped
                    ImageView dropped = (ImageView) view;
                    //Get Row of what is being dropped on
                    TableRow row = (TableRow) dropTarget.getParent();
                    for (int i=0;i <13; i ++ ) {
                        ImageView image = (ImageView) row.getChildAt(i);

                        if(image.equals(dropTarget)){

                            if(compareImageViewEqual(dropped, resistor)||compareImageViewEqual(dropped, resPart))
                            {
                                if(compareImageViewEqual(dropped, resPart))
                                {
                                    clear(dropped);
                                }
                                setImageRes(image, row, i);
                            } else if(compareImageViewEqual(dropped, capacitor)|| compareImageViewEqual(dropped, capPart))
                            {
                                if(compareImageViewEqual(dropped, capPart))
                                {
                                    clear(dropped);
                                }
                                setImageCap(image,row,i);
                            } else if(compareImageViewEqual(dropped, inductor)|| compareImageViewEqual(dropped, indPart))
                            {
                                if(compareImageViewEqual(dropped, indPart))
                                {
                                    clear(dropped);
                                }
                                setImageInd(image,row,i);
                            }
                            else
                            {
                                view.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
            return true;
        }

        private boolean dropEventNotHandled(DragEvent dragEvent) {
            return !dragEvent.getResult();
        }

        private boolean clear (ImageView dropped)
        {
            TableRow row = (TableRow) dropped.getParent();
            for(int j=0;j<13;j++)
            {
                ImageView image = (ImageView) row.getChildAt(j);

                if(image == dropped)
                {
                    image.setImageResource(R.drawable.hole25x25);
                    image.setVisibility(View.VISIBLE);
                    ImageView temp = (ImageView) row.getChildAt(j-1);
                    temp.setImageResource(R.drawable.hole25x25);
                    if(!compareImageViewEqual(dropped, capPart))
                    {
                        temp = (ImageView) row.getChildAt(j+1);
                        temp.setImageResource(R.drawable.hole25x25);
                        temp = (ImageView) row.getChildAt(j+ 2);
                        temp.setImageResource(R.drawable.hole25x25);
                    }
                }
            }
            return true;
        }

        private boolean setImageRes (ImageView image, TableRow row, int i)
        {
            image.setImageResource(R.drawable.res2_4);
            ImageView temp = (ImageView) row.getChildAt(i-1);
            temp.setImageResource(R.drawable.res1_4);
            temp = (ImageView) row.getChildAt(i+1);
            temp.setImageResource(R.drawable.res3_4);
            temp = (ImageView) row.getChildAt(i+2);
            temp.setImageResource(R.drawable.res4_4);
            image.setOnTouchListener(new MyTouchListener());
            return true;
        }

        private boolean setImageInd (ImageView image, TableRow row, int i)
        {
            image.setImageResource(R.drawable.inductor2_4);
            ImageView temp = (ImageView) row.getChildAt(i-1);
            temp.setImageResource(R.drawable.inductor1_4);
            temp = (ImageView) row.getChildAt(i+1);
            temp.setImageResource(R.drawable.inductor3_4);
            temp = (ImageView) row.getChildAt(i+2);
            temp.setImageResource(R.drawable.inductor4_4);
            image.setOnTouchListener(new MyTouchListener());
            return true;
        }

        private boolean setImageCap (ImageView image, TableRow row, int i)
        {
            image.setImageResource(R.drawable.capacitor2_2);
            ImageView temp = (ImageView) row.getChildAt(i-1);
            temp.setImageResource(R.drawable.capacitor1_2);
            image.setOnTouchListener(new MyTouchListener());
            return true;
        }
        private boolean compareImageViewEqual(ImageView view1, ImageView view2)
        {
            boolean result;
            if(view1.getDrawable().getConstantState().equals(view2.getDrawable().getConstantState()))
            {
                result = true;
            }
            else{
                result= false;
            }

            return result;
        }
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