package com.example.emilie.practiceapplication;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.emilie.practiceapplication.Parser.Component;

import java.io.Serializable;
import java.util.ArrayList;

public class VectorBoardActivity extends AppCompatActivity {

    private android.widget.RelativeLayout.LayoutParams layoutParams;
    public ArrayList<ImageView> iv = new ArrayList<ImageView>();
    ImageView board;

    public ImageView resistor;
    public ImageView resPart;
    public ImageView capacitor;
    public ImageView capPart;
    public ImageView inductor;
    public ImageView indPart;

    public TableLayout tableLayout;
    public LinearLayout tray;

    public Button left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_board);
        Intent intent = getIntent();
        tray = (LinearLayout) findViewById(R.id.ll);
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.ll_top);
        tableLayout = (TableLayout) findViewById(R.id.tl);


        //TODO Get everything not just strings
        ArrayList<String> StringList = new ArrayList<>();
        Serializable serializedList = intent.getSerializableExtra("ComponentList");
        ArrayList<Component> componentList = (ArrayList<Component>) serializedList;
        for(int i=0;i<componentList.size();i++)
        {
            StringList.add(componentList.get(i).type);
        }


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
        for (int i = 0; i < StringList.size(); i++)
        {
            final ImageView im = new ImageView(this);
            tray.addView(im);
            im.setImageResource(R.drawable.resistorfinal); //Not sure why this is here

            switch (StringList.get(i)) {
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
        left = (Button) findViewById(R.id.btn_flip);
        left.setOnClickListener(new MyClickListener());
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
        resistor.setOnTouchListener(new MyTouchListener());
        resPart = new ImageView(this);
        resPart.setImageResource(R.drawable.east_res1_4);

        capacitor = new ImageView(this);
        capacitor.setImageResource(R.drawable.capacitorfinal);
        capPart = new ImageView(this);
        capPart.setImageResource(R.drawable.east_capacitor1_2);

        inductor = new ImageView(this);
        inductor.setImageResource(R.drawable.inductor);
        indPart = new ImageView(this);
        indPart.setImageResource(R.drawable.east_inductor1_4);
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toolbar);
                final String value = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId() )).getText().toString();
                switch(value)
                {
                    case "Move":
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        break;
                    case "Rotate":
                        rotate(v);
                        break;
                    case "Inspect":
                        break;
                    default:
                        ClipData d = ClipData.newPlainText("", "");
                        View.DragShadowBuilder s = new View.DragShadowBuilder(v);
                        v.startDrag(d, s, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        break;
                }

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

    private void rotate(View v) {
        ImageView imageView = (ImageView)v;
        String clicked = "";
        int i;

        clicked = findClickable(imageView);

        switch(clicked)
        {
            case "res/drawable/east_res1_4.png":
                clear(imageView, "east", 1, 4);
                flip(imageView, clicked);
                break;
            case "res/drawable/north_res1_4.png":
                clear(imageView, "north", 1, 4);
                flip(imageView, clicked);
                break;
            case "res/drawable/south_res1_4.png":
                clear(imageView, "south", 1, 4);
                flip(imageView, clicked);
                break;
            case "res/drawable/west_res1_4.png":
                clear(imageView, "west",1,4);
                flip(imageView, clicked);
                break;
            case "res/drawable/east_capacitor1_2.png":
                clear(imageView, "east",1,2);
                flip(imageView, clicked);
                break;
            case "res/drawable/west_capacitor1_2.png":
                clear(imageView, "west",1,2);
                flip(imageView, clicked);
                break;
            case "res/drawable/north_capacitor1_2.png":
                clear(imageView, "north",1,2);
                flip(imageView, clicked);
                break;
            case "res/drawable/south_capacitor1_2.png":
                clear(imageView, "south",1,2);
                flip(imageView, clicked);
                break;
            case "res/drawable/east_inductor1_4.png":
                clear(imageView, "east",1,4);
                flip(imageView, clicked);
                break;
            case "res/drawable/west_inductor1_4.png":
                clear(imageView, "west",1,4);
                flip(imageView, clicked);
                break;
            case "res/drawable/north_inductor1_4.png":
                clear(imageView, "north",1,4);
                flip(imageView, clicked);
                break;
            case "res/drawable/south_inductor1_4.png":
                clear(imageView, "south",1,4);
                flip(imageView, clicked);
                break;
        }
    }

    private void flip(ImageView imageView, String clicked)
    {
        ImageView res1 = new ImageView(this);
        ImageView res2 = new ImageView(this);
        ImageView res3 = new ImageView(this);
        ImageView res4 = new ImageView(this);

        ImageView cap1 = new ImageView(this);
        ImageView cap2 = new ImageView(this);

        ImageView ind1 = new ImageView(this);
        ImageView ind2 = new ImageView(this);
        ImageView ind3 = new ImageView(this);
        ImageView ind4 = new ImageView(this);

        TableRow temp;
        ImageView image;
        TableRow row = (TableRow) imageView.getParent();
        int indexcolumn = row.indexOfChild(imageView);
        int indexrow = tableLayout.indexOfChild(row);
        switch(clicked)
        {
            case "res/drawable/east_res1_4.png":
                //was facing east
                res1.setImageResource(R.drawable.south_res1_4);
                res2.setImageResource(R.drawable.south_res2_4);
                res3.setImageResource(R.drawable.south_res3_4);
                res4.setImageResource(R.drawable.south_res4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow + 2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow + 3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res4.getDrawable());
                break;

            case "res/drawable/south_res1_4.png":
                //was facing south
                res1.setImageResource(R.drawable.west_res1_4);
                res2.setImageResource(R.drawable.west_res2_4);
                res3.setImageResource(R.drawable.west_res3_4);
                res4.setImageResource(R.drawable.west_res4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                image.setImageDrawable(res2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-2);
                image.setImageDrawable(res3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-3);
                image.setImageDrawable(res4.getDrawable());
                break;
            case "res/drawable/west_res1_4.png":
                //was facing west
                res1.setImageResource(R.drawable.north_res1_4);
                res2.setImageResource(R.drawable.north_res2_4);
                res3.setImageResource(R.drawable.north_res3_4);
                res4.setImageResource(R.drawable.north_res4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow-2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow-3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res4.getDrawable());
                break;
            case "res/drawable/north_res1_4.png":
                //was facing north
                res1.setImageResource(R.drawable.east_res1_4);
                res2.setImageResource(R.drawable.east_res2_4);
                res3.setImageResource(R.drawable.east_res3_4);
                res4.setImageResource(R.drawable.east_res4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(res1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                image.setImageDrawable(res2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+2);
                image.setImageDrawable(res3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+3);
                image.setImageDrawable(res4.getDrawable());
                break;

            case "res/drawable/east_capacitor1_2.png":
                //was facing east
                cap1.setImageResource(R.drawable.south_capacitor1_2);
                cap2.setImageResource(R.drawable.south_capacitor2_2);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap2.getDrawable());
                break;

            case "res/drawable/west_capacitor1_2.png":
                //was facing west
                cap1.setImageResource(R.drawable.north_capacitor1_2);
                cap2.setImageResource(R.drawable.north_capacitor2_2);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap2.getDrawable());
                break;

            case "res/drawable/south_capacitor1_2.png":
                //was facing south
                cap1.setImageResource(R.drawable.west_capacitor1_2);
                cap2.setImageResource(R.drawable.west_capacitor2_2);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                image.setImageDrawable(cap2.getDrawable());
                break;

            case "res/drawable/north_capacitor1_2.png":
                //was facing north
                cap1.setImageResource(R.drawable.east_capacitor1_2);
                cap2.setImageResource(R.drawable.east_capacitor2_2);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(cap1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                image.setImageDrawable(cap2.getDrawable());
                break;

            case "res/drawable/east_inductor1_4.png":
                //was facing east
                ind1.setImageResource(R.drawable.south_inductor1_4);
                ind2.setImageResource(R.drawable.south_inductor2_4);
                ind3.setImageResource(R.drawable.south_inductor3_4);
                ind4.setImageResource(R.drawable.south_inductor4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow + 2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow + 3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind4.getDrawable());
                break;

            case "res/drawable/west_inductor1_4.png":
                //was facing west
                ind1.setImageResource(R.drawable.north_inductor1_4);
                ind2.setImageResource(R.drawable.north_inductor2_4);
                ind3.setImageResource(R.drawable.north_inductor3_4);
                ind4.setImageResource(R.drawable.north_inductor4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow-2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow-3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind4.getDrawable());
                break;

            case "res/drawable/north_inductor1_4.png":
                //was facing north
                ind1.setImageResource(R.drawable.east_inductor1_4);
                ind2.setImageResource(R.drawable.east_inductor2_4);
                ind3.setImageResource(R.drawable.east_inductor3_4);
                ind4.setImageResource(R.drawable.east_inductor4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                image.setImageDrawable(ind2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+2);
                image.setImageDrawable(ind3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+3);
                image.setImageDrawable(ind4.getDrawable());
                break;

            case "res/drawable/south_inductor1_4.png":
                //was facing south
                ind1.setImageResource(R.drawable.west_inductor1_4);
                ind2.setImageResource(R.drawable.west_inductor2_4);
                ind3.setImageResource(R.drawable.west_inductor3_4);
                ind4.setImageResource(R.drawable.west_inductor4_4);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                image.setImageDrawable(ind1.getDrawable());
                image.setOnTouchListener(new MyTouchListener());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                image.setImageDrawable(ind2.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-2);
                image.setImageDrawable(ind3.getDrawable());

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-3);
                image.setImageDrawable(ind4.getDrawable());
                break;
        }
    }
    private String findClickable(ImageView imageView)
    {
        Resources res = getResources();
        TypedArray clickables = res.obtainTypedArray(R.array.clickable_parts);
        for(int i=0;i<clickables.length();i++)
        {
            ImageView temp = new ImageView(this);
            temp.setImageDrawable(clickables.getDrawable(i));
            if(compareImageViewEqual(temp,imageView))
                return clickables.getString(i);
        }
        return null;
    }

    private boolean clear (ImageView dropped, String rotation,int x, int y)
    {
        TableRow row = (TableRow) dropped.getParent();
        int indexcolumn = row.indexOfChild(dropped);
        int indexrow = tableLayout.indexOfChild(row);
        ImageView image = (ImageView) row.getChildAt(indexcolumn);
        ImageView temp;

        image.setImageResource(R.drawable.hole25x25);
        image.setVisibility(View.VISIBLE);

        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                switch(rotation) {
                    case "east":
                        row = (TableRow)tableLayout.getChildAt(indexrow+i);
                        temp = (ImageView) row.getChildAt(indexcolumn+j);
                        temp.setImageResource(R.drawable.hole25x25);

                        break;
                    case "west":
                        row = (TableRow)tableLayout.getChildAt(indexrow-i); 
                        temp = (ImageView) row.getChildAt(indexcolumn-j);
                        temp.setImageResource(R.drawable.hole25x25);
                        break;
                    case "north":
                        row = (TableRow)tableLayout.getChildAt(indexrow-j);
                        temp = (ImageView) row.getChildAt(indexcolumn+i);
                        temp.setImageResource(R.drawable.hole25x25);
                        break;
                    case "south":
                        row = (TableRow)tableLayout.getChildAt(indexrow+j);
                        temp = (ImageView) row.getChildAt(indexcolumn-i);
                        temp.setImageResource(R.drawable.hole25x25);
                        break;
                }
            }
        }

        return true;
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
                    int indexcolumn = row.indexOfChild(dropTarget);
                    int indexrow = tableLayout.indexOfChild(row);
                    String image = findClickable(dropped);
                    boolean bool;

                    switch (image)
                    {
                        //resistors
                        case "res/drawable/resistorfinal.png":
                            bool = setImageRes(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/east_res1_4.png":
                            clear(dropped,"east", 1,4);
                            bool = setImageRes(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/west_res1_4.png":
                            clear(dropped,"west", 1,4);
                            bool = setImageRes(dropTarget, row, indexcolumn, "west");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/north_res1_4.png":
                            clear(dropped,"north", 1,4);
                            bool = setImageRes(dropTarget, row, indexcolumn, "north");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/south_res1_4.png":
                            clear(dropped,"south", 1,4);
                            bool = setImageRes(dropTarget, row, indexcolumn, "south");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        //capacitors
                        case "res/drawable/capacitorfinal.png":
                            bool = setImageCap(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/east_capacitor1_2.png":
                            clear(dropped,"east", 1,2);
                            bool = setImageCap(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(capacitor, 0);
                            break;
                        case "res/drawable/west_capacitor1_2.png":
                            clear(dropped,"west", 1,2);
                            bool = setImageCap(dropTarget, row, indexcolumn, "west");
                            if (!bool)
                                tray.addView(capacitor, 0);
                            break;
                        case "res/drawable/north_capacitor1_2.png":
                            clear(dropped,"north", 1,2);
                            bool = setImageCap(dropTarget, row, indexcolumn, "north");
                            if (!bool)
                                tray.addView(capacitor, 0);
                            break;
                        case "res/drawable/south_capacitor1_2.png":
                            clear(dropped,"south", 1,2);
                            bool = setImageCap(dropTarget, row, indexcolumn, "south");
                            if (!bool)
                                tray.addView(capacitor, 0);
                            break;
                        //inductors
                        case "res/drawable/inductor.png":
                            bool = setImageInd(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/east_inductor1_4.png":
                            clear(dropped,"east", 1,4);
                            bool = setImageInd(dropTarget, row, indexcolumn, "east");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/west_inductor1_4.png":
                            clear(dropped,"west", 1,4);
                            bool = setImageInd(dropTarget, row, indexcolumn, "west");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/north_inductor1_4.png":
                            clear(dropped,"north", 1,4);
                            bool = setImageInd(dropTarget, row, indexcolumn, "north");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        case "res/drawable/south_inductor1_4.png":
                            clear(dropped,"south", 1,4);
                            bool = setImageInd(dropTarget, row, indexcolumn, "south");
                            if (!bool)
                                tray.addView(resistor, 0);
                            break;
                        default:
                            view.setVisibility(View.VISIBLE);
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

        private boolean setImageRes (ImageView image, TableRow row, int i, String direction)
        {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            Resources res = getResources();
            TypedArray resistor = res.obtainTypedArray(R.array.forward);
            if((i >= 0 && i <13) && (i+1 >= 0 && i+1 <13) && (i+2 >= 0 && i+2 <=13) && (i+3 >= 0 && i+3 <13) && tableLayout.indexOfChild(row)>=0 && tableLayout.indexOfChild(row) <= 13)
            {
                switch(direction)
                {
                    case "east":
                        image.setImageResource(R.drawable.east_res1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i+j);
                            temp.setImageDrawable(resistor.getDrawable(j));
                        }
                        break;
                    case "west":
                        image.setImageResource(R.drawable.west_res1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i-j);
                            temp.setImageDrawable(resistor.getDrawable(j+4));
                        }
                        break;
                    case "north":
                        image.setImageResource(R.drawable.north_res1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column-j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(resistor.getDrawable(11-j));
                        }
                        break;
                    case "south":
                        image.setImageResource(R.drawable.south_res1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column+j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(resistor.getDrawable(15-j));
                        }
                        break;
                }

                return true;
            }
            else
                return false;
        }

        private boolean setImageInd (ImageView image, TableRow row, int i, String direction)
        {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            Resources res = getResources();
            TypedArray inductor = res.obtainTypedArray(R.array.forward);
            if((i >= 0 && i <13) && (i+1 >= 0 && i+1 <13) && (i+2 >= 0 && i+2 <=13) && (i+3 >= 0 && i+3 <13) && tableLayout.indexOfChild(row)>=0 && tableLayout.indexOfChild(row) <= 13)
            {
                switch(direction)
                {
                    case "east":
                        image.setImageResource(R.drawable.east_inductor1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i+j);
                            temp.setImageDrawable(inductor.getDrawable(j+16));
                        }
                        break;
                    case "west":
                        image.setImageResource(R.drawable.west_inductor1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i-j);
                            temp.setImageDrawable(inductor.getDrawable(j+20));
                        }
                        break;
                    case "north":
                        image.setImageResource(R.drawable.north_inductor1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column-j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(inductor.getDrawable(27-j));
                        }
                        break;
                    case "south":
                        image.setImageResource(R.drawable.south_inductor1_4);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column+j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(inductor.getDrawable(31-j));
                        }
                        break;
                }
                return true;
            }
            else
                return false;
        }

        private boolean setImageCap (ImageView image, TableRow row, int i, String direction)
        {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            Resources res = getResources();
            TypedArray capacitor = res.obtainTypedArray(R.array.forward);
            if((i >= 0 && i <13) && (i+1 >= 0 && i+1 <13) && (i+2 >= 0 && i+2 <=13) && (i+3 >= 0 && i+3 <13) && tableLayout.indexOfChild(row)>=0 && tableLayout.indexOfChild(row) <= 13)
            {
                switch(direction)
                {
                    case "east":
                        image.setImageResource(R.drawable.east_capacitor1_2);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<2;j++)
                        {
                            temp = (ImageView) row.getChildAt(i+j);
                            temp.setImageDrawable(capacitor.getDrawable(j+32));
                        }
                        break;
                    case "west":
                        image.setImageResource(R.drawable.west_capacitor1_2);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<2;j++)
                        {
                            temp = (ImageView) row.getChildAt(i-j);
                            temp.setImageDrawable(capacitor.getDrawable(j+34));
                        }
                        break;
                    case "north":
                        image.setImageResource(R.drawable.north_capacitor1_2);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<2;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column-j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(capacitor.getDrawable(37-j));
                        }
                        break;
                    case "south":
                        image.setImageResource(R.drawable.south_capacitor1_2);
                        image.setOnTouchListener(new MyTouchListener());
                        for(j=1;j<2;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column+j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            temp.setImageDrawable(capacitor.getDrawable(40-j));
                        }
                        break;
                }
                return true;
            }
            else
                return false;
        }

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

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn_flip:
                    for(int i=0;i<12;i++)
                    {
                        TableRow row = new TableRow(getApplicationContext());
                        TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                        for(int j=12;j>=0;j--)
                        {
                            ImageView imageView = (ImageView) tableRow.getChildAt(j);
                            //switch to under view and fix order
                            imageView = findReverseImage(imageView);
                            String clicked = findClickable(imageView);
                            if(clicked != null)
                                imageView.setOnTouchListener(new MyTouchListener());
                            tableRow.removeView(imageView);
                            row.addView(imageView);
                        }
                        tableLayout.removeView(tableRow);
                        tableLayout.addView(row,i);
                    }

                    break;
            }
        }

        private ImageView findReverseImage(ImageView imageView)
        {
            Resources res = getResources();
            TypedArray forward = res.obtainTypedArray(R.array.forward);
            TypedArray backwards = res.obtainTypedArray(R.array.backwards);
            for(int i=0;i<forward.length();i++)
            {
                ImageView temp = new ImageView(getApplicationContext());
                temp.setImageDrawable(forward.getDrawable(i));
                if(compareImageViewEqual(imageView,temp))
                {
                    temp.setImageDrawable(backwards.getDrawable(i));
                    return temp;
                }
            }
            for(int i=0;i<backwards.length();i++)
            {
                ImageView temp = new ImageView(getApplicationContext());
                temp.setImageDrawable(backwards.getDrawable(i));
                if(compareImageViewEqual(imageView,temp))
                {
                    temp.setImageDrawable(forward.getDrawable(i));
                    return temp;
                }
            }
            return imageView;
        }
    }
}