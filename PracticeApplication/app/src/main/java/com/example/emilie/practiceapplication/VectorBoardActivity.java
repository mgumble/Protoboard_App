package com.example.emilie.practiceapplication;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.emilie.practiceapplication.Parser.Component;

import java.io.Serializable;
import java.util.ArrayList;


public class VectorBoardActivity extends AppCompatActivity {
    private static final String[] cardinals = {"north", "south", "east", "west"};
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    public ArrayList<ImageView> iv = new ArrayList<ImageView>();
    public ImageView board;

    public int rowMAX;
    public int columnMAX;

    public TableLayout tableLayout;
    public LinearLayout tray;

    public Button left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_board);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        tray = (LinearLayout) findViewById(R.id.ll);
        rowMAX = b.getInt("row");
        columnMAX = b.getInt("col");
        tableLayout = (TableLayout) findViewById(R.id.tl);
        Serializable serializedList = b.getSerializable("ComponentList");
        ArrayList<Component> componentList = (ArrayList<Component>) serializedList;

        for(int i=0;i<rowMAX;i++)
        {
            TableRow row = new TableRow(this);
            for(int j=0;j<columnMAX;j++)
            {
                ImageView image = new ImageView(this);
                image = editImageView(image, R.drawable.hole25x25, "hole", null);
                row.addView(image,j);
            }

            tableLayout.addView(row, i);
        }
        /*Builds the List of components to go in tray*/
        for (int i = 0; i < componentList.size(); i++)
        {
            ImageView image = new ImageView(this);
            tray.addView(image);

            switch (componentList.get(i).type) {
                case "res":
                    editImageView(image, R.drawable.resistorfinal, "resistorfinal", componentList.get(i));
                    break;
                case "cap":
                    editImageView(image, R.drawable.capacitorfinal, "capacitorfinal", componentList.get(i));
                    break;
                case "ind":
                    editImageView(image, R.drawable.inductor, "inductor", componentList.get(i));
                    break;
                default:
                    //TODO needs to include the 4x4 IC size

                    int numTerms = componentList.get(i).getTerminals().size();

                    if(numTerms>2 && numTerms <=4)
                    {
                        editImageView(image,R.drawable.chip2x2,"chip2x2",componentList.get(i));
                    }
                    else if(numTerms>4 && numTerms <=6)
                    {
                        editImageView(image,R.drawable.chip3x3,"chip3x3",componentList.get(i));
                    }
                    else
                    {
                        editImageView(image,R.drawable.chip4x4,"chip4x4",componentList.get(i));
                    }
                    break;
            }
        }
        left = (Button) findViewById(R.id.btn_flip);
        left.setOnClickListener(new MyClickListener());
        initControls(); //Set each image to be draggable1
    }
    private void initControls() {
        tableLayout = (TableLayout) findViewById(R.id.tl);
        tableLayout.setOnDragListener(new MyDragListener());
    }
    private void rotate(View v) {
        ImageView imageView = (ImageView)v;
        String clicked = "";
        int i;

        clicked = findClickable(imageView);

        switch(clicked)
        {
            case "east_res1_4":
                clear(imageView, "east", 1, 4);
                flip(imageView, clicked);
                break;
            case "north_res1_4":
                clear(imageView, "north", 1, 4);
                flip(imageView, clicked);
                break;
            case "south_res1_4":
                clear(imageView, "south", 1, 4);
                flip(imageView, clicked);
                break;
            case "west_res1_4":
                clear(imageView, "west",1,4);
                flip(imageView, clicked);
                break;
            case "east_capacitor1_2":
                clear(imageView, "east",1,2);
                flip(imageView, clicked);
                break;
            case "west_capacitor1_2":
                clear(imageView, "west",1,2);
                flip(imageView, clicked);
                break;
            case "north_capacitor1_2":
                clear(imageView, "north",1,2);
                flip(imageView, clicked);
                break;
            case "south_capacitor1_2":
                clear(imageView, "south",1,2);
                flip(imageView, clicked);
                break;
            case "east_inductor1_4":
                clear(imageView, "east",1,4);
                flip(imageView, clicked);
                break;
            case "west_inductor1_4":
                clear(imageView, "west",1,4);
                flip(imageView, clicked);
                break;
            case "north_inductor1_4":
                clear(imageView, "north",1,4);
                flip(imageView, clicked);
                break;
            case "south_inductor1_4":
                clear(imageView, "south", 1, 4);
                flip(imageView, clicked);
                break;
            case "east_2x2chip1_2_1_4":
                clear(imageView, "east", 4,2);
                flip(imageView,clicked);
                break;
            case "west_2x2chip1_2_1_4":
                clear(imageView, "west", 4,2);
                flip(imageView,clicked);
                break;
            case "north_2x2chip1_2_1_4":
                clear(imageView, "north", 4,2);
                flip(imageView,clicked);
                break;
            case "south_2x2chip1_2_1_4":
                clear(imageView, "south", 4,2);
                flip(imageView,clicked);
                break;
            case "east_3x3chip1_3_1_4":
                clear(imageView, "east", 4,3);
                flip(imageView,clicked);
                break;
            case "west_3x3chip1_3_1_4":
                clear(imageView, "west", 4,3);
                flip(imageView,clicked);
                break;
            case "north_3x3chip1_3_1_4":
                clear(imageView, "north", 4,3);
                flip(imageView,clicked);
                break;
            case "south_3x3chip1_3_1_4":
                clear(imageView, "south", 4,3);
                flip(imageView,clicked);
                break;
            case "east_4x4chip1_4_1_4":
                clear(imageView, "east", 4,4);
                flip(imageView,clicked);
                break;
            case "west_4x4chip1_4_1_4":
                clear(imageView, "west", 4,4);
                flip(imageView,clicked);
                break;
            case "north_4x4chip1_4_1_4":
                clear(imageView, "north", 4,4);
                flip(imageView,clicked);
                break;
            case "south_4x4chip1_4_1_4":
                clear(imageView, "south", 4,4);
                flip(imageView,clicked);
                break;
            case "east_west_wire":
                editImageView(imageView, R.drawable.north_south_wire, "north_south_wire", null);
                break;
            case "north_south_wire.":
                editImageView(imageView, R.drawable.east_west_wire, "east_west_wire", null);
                break;
            default:
                break;
        }
    }
    private void flip(ImageView imageView, String clicked)
    {

        TableRow temp;
        ImageView image;
        TableRow row = (TableRow) imageView.getParent();
        int j,i,counter;
        String tag;
        Resources res = getResources();;
        int indexcolumn = row.indexOfChild(imageView);
        int indexrow = tableLayout.indexOfChild(row);
        switch(clicked)
        {
            case "east_res1_4":
                //was facing east
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image,R.drawable.south_res1_4, "south_res1_4",null);

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image,R.drawable.south_res2_4, "south_res2_4",null);

                temp = (TableRow) tableLayout.getChildAt(indexrow + 2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image,R.drawable.south_res3_4, "south_res3_4",null);

                temp = (TableRow) tableLayout.getChildAt(indexrow + 3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image,R.drawable.south_res4_4, "south_res4_4",null);
                break;

            case "south_res1_4":
                //was facing south

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.west_res1_4, "west_res1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                editImageView(image, R.drawable.west_res2_4, "west_res2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-2);
                editImageView(image, R.drawable.west_res3_4, "west_res3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-3);
                editImageView(image, R.drawable.west_res4_4, "west_res4_4", null);
                break;
            case "west_res1_4":
                //was facing west

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_res1_4, "north_res1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_res2_4, "north_res2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_res3_4, "north_res3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_res4_4, "north_res4_4", null);
                break;
            case "north_res1_4":
                //was facing north
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.east_res1_4, "east_res1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                editImageView(image, R.drawable.east_res2_4, "east_res2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+2);
                editImageView(image, R.drawable.east_res3_4, "east_res3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+3);
                editImageView(image, R.drawable.east_res4_4, "east_res4_4", null);
                break;

            case "east_capacitor1_2":
                //was facing east
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_capacitor1_2, "south_capacitor1_2", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_capacitor2_2, "south_capacitor2_2", null);
                break;

            case "west_capacitor1_2":
                //was facing west
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_capacitor1_2, "north_capacitor1_2", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_capacitor2_2, "north_capacitor2_2", null);
                break;

            case "south_capacitor1_2":
                //was facing south
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.west_capacitor1_2, "west_capacitor1_2", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                editImageView(image, R.drawable.west_capacitor2_2, "west_capacitor2_2", null);
                break;

            case "north_capacitor1_2":
                //was facing north
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.east_capacitor1_2, "east_capacitor1_2", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                editImageView(image, R.drawable.east_capacitor2_2, "east_capacitor2_2", null);
                break;

            case "east_inductor1_4":
                //was facing east
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_inductor1_4, "south_inductor1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow+1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_inductor2_4, "south_inductor2_4", null);


                temp = (TableRow) tableLayout.getChildAt(indexrow + 2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_inductor3_4, "south_inductor3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow + 3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.south_inductor4_4, "south_inductor4_4", null);
                break;

            case "west_inductor1_4":
                //was facing west
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_inductor1_4, "north_inductor1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-1);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_inductor2_4, "north_inductor2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-2);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_inductor3_4, "north_inductor3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow-3);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.north_inductor4_4, "north_inductor4_4", null);
                break;

            case "north_inductor1_4":
                //was facing north
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.east_inductor1_4, "east_inductor1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+1);
                editImageView(image, R.drawable.east_inductor2_4, "east_inductor2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+2);
                editImageView(image, R.drawable.east_inductor3_4, "east_inductor3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn+3);
                editImageView(image, R.drawable.east_inductor4_4, "east_inductor4_4", null);
                break;

            case "south_inductor1_4":
                //was facing south
                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn);
                editImageView(image, R.drawable.west_inductor1_4, "west_inductor1_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-1);
                editImageView(image, R.drawable.west_inductor2_4, "west_inductor2_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-2);
                editImageView(image, R.drawable.west_inductor3_4, "west_inductor3_4", null);

                temp = (TableRow) tableLayout.getChildAt(indexrow);
                image = (ImageView) temp.getChildAt(indexcolumn-3);
                editImageView(image, R.drawable.west_inductor4_4, "west_inductor4_4", null);
                break;
            case "east_2x2chip1_2_1_4":
                //was facing east needs to face south
                TypedArray south = res.obtainTypedArray(R.array.twoxtwocomponent_south);
                for(i=0;i<2;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow + i);
                    counter = 0;
                    for(j=0;j<8;j=j+2)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - counter);
                        tag = findTag(south.getString(j+i));
                        editImageView(image,south.getDrawable(j+i), tag, null);
                        counter++;
                    }
                }
                break;

            case "south_2x2chip1_2_1_4":
                //was facing south needs to face west
                TypedArray west = res.obtainTypedArray(R.array.twoxtwocomponent_west);
                counter = 0;
                for(i=0;i<8;i=i+2)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow - counter);

                    for(j=0;j<2;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - j);
                        tag = findTag(west.getString(j+i));
                        editImageView(image, west.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }
                break;

            case "west_2x2chip1_2_1_4":
                //was facing west needs to face north
                TypedArray north = res.obtainTypedArray(R.array.twoxtwocomponent_north);
                counter=0;
                for(i=0;i<2;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow - i);
                    counter = 0;
                    for(j=0;j<8;j=j+2)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + counter);
                        tag = findTag(north.getString(j+i));
                        editImageView(image,north.getDrawable(j+i), tag, null);
                        counter++;
                    }
                }
                break;

            case "north_2x2chip1_2_1_4":
                //was facing north needs to face east
                TypedArray east = res.obtainTypedArray(R.array.twoxtwocomponent_east);
                counter = 0;
                for(i=0;i<8;i=i+2)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow + counter);

                    for(j=0;j<2;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + j -1);
                        tag = findTag(east.getString(j+i));
                        editImageView(image, east.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }

            //3x3
            case "east_3x3chip1_3_1_4":
                //was facing east needs to face south
                TypedArray south3 = res.obtainTypedArray(R.array.threexthreecomponent_south);
                for(i=0;i<3;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow + i);
                    counter = 0;
                    for(j=0;j<12;j=j+3)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - counter);
                        tag = findTag(south3.getString(j+i));
                        editImageView(image,south3.getDrawable(j+i), tag, null);
                        counter++;
                    }
                }
                break;

            case "south_3x3chip1_3_1_4":
                //was facing south needs to face west
                TypedArray west3 = res.obtainTypedArray(R.array.threexthreecomponent_west);
                counter = 0;
                for(i=0;i<12;i=i+3)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow - counter);

                    for(j=0;j<3;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - j);
                        tag = findTag(west3.getString(j+i));
                        editImageView(image, west3.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }
                break;

            case "west_3x3chip1_3_1_4":
                //was facing west needs to face north
                TypedArray north3 = res.obtainTypedArray(R.array.threexthreecomponent_north);
                counter=0;
                for(i=0;i<3;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow - i);
                    counter = 0;
                    for(j=0;j<12;j=j+3)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + counter);
                        tag = findTag(north3.getString(j+i));
                        editImageView(image,north3.getDrawable(j+i), tag, null);
                        counter++;
                    }
                }
                break;

            case "north_3x3chip1_3_1_4":
                //was facing north needs to face east
                TypedArray east3 = res.obtainTypedArray(R.array.threexthreecomponent_east);
                counter = 0;
                for(i=0;i<12;i=i+3)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow + counter);

                    for(j=0;j<3;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + j -1);
                        tag = findTag(east3.getString(j+i));
                        editImageView(image, east3.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }
                break;

            //4x4
            case "east_4x4chip1_4_1_4":
                //was facing east needs to face south
                TypedArray south4 = res.obtainTypedArray(R.array.fourxfourcomponent_south);
                for(i=0;i<4;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow + i);
                    counter = 0;
                    for(j=0;j<16;j=j+4)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - counter);
                        tag = findTag(south4.getString(j+i));
                        editImageView(image,south4.getDrawable(j + i), tag, null);
                        counter++;
                    }
                }
                break;

            case "south_4x4chip1_4_1_4":
                //was facing south needs to face west
                TypedArray west4 = res.obtainTypedArray(R.array.fourxfourcomponent_west);
                counter = 0;
                for(i=0;i<16;i=i+4)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow - counter);

                    for(j=0;j<4;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn - j);
                        tag = findTag(west4.getString(j+i));
                        editImageView(image, west4.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }
                break;

            case "west_4x4chip1_4_1_4":
                //was facing west needs to face north
                TypedArray north4 = res.obtainTypedArray(R.array.fourxfourcomponent_north);
                counter=0;
                for(i=0;i<4;i++)
                {
                    row = (TableRow) tableLayout.getChildAt(indexrow - i);
                    counter = 0;
                    for(j=0;j<16;j=j+4)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + counter);
                        tag = findTag(north4.getString(j+i));
                        editImageView(image,north4.getDrawable(j+i), tag, null);
                        counter++;
                    }
                }
                break;

            case "north_4x4chip1_4_1_4":
                //was facing north needs to face east
                TypedArray east4 = res.obtainTypedArray(R.array.fourxfourcomponent_east);
                counter = 0;
                for(i=0;i<16;i=i+4)
                {
                    row = (TableRow)tableLayout.getChildAt(indexrow + counter);

                    for(j=0;j<4;j++)
                    {
                        image = (ImageView) row.getChildAt(indexcolumn + j -1);
                        tag = findTag(east4.getString(j+i));
                        editImageView(image, east4.getDrawable(j + i), tag, null);
                    }
                    counter++;
                }
                break;
        }
    }
    private String findClickable(ImageView imageView)
    {
        Resources res = getResources();
        TypedArray clickables = res.obtainTypedArray(R.array.clickable_parts);
        for(int i=0;i<clickables.length();i++)
        {
            String fileName = clickables.getString(i);
            fileName = fileName.substring(13);  // removes the res/drawables
            fileName = fileName.substring(0,fileName.length()-4); //removes .png
            if(imageView.getTag(R.id.imageTag).equals(fileName))
                return fileName;
        }
        return "";
    }
    private boolean clear (ImageView dropped, String rotation,int x, int y)
    {
        TableRow row = (TableRow) dropped.getParent();
        int indexcolumn = row.indexOfChild(dropped);
        int indexrow = tableLayout.indexOfChild(row);
        ImageView image = (ImageView) row.getChildAt(indexcolumn);
        ImageView temp;

        editImageView(image, R.drawable.hole25x25, "hole", null);
        image.setVisibility(View.VISIBLE);

        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                switch(rotation) {
                    case "east":
                        row = (TableRow)tableLayout.getChildAt(indexrow+i);
                        temp = (ImageView) row.getChildAt(indexcolumn+j);
                        editImageView(temp,R.drawable.hole25x25,"hole",null);
                        break;
                    case "west":
                        row = (TableRow)tableLayout.getChildAt(indexrow-i); 
                        temp = (ImageView) row.getChildAt(indexcolumn-j);
                        editImageView(temp, R.drawable.hole25x25, "hole", null);
                        break;
                    case "north":
                        row = (TableRow)tableLayout.getChildAt(indexrow-j);
                        temp = (ImageView) row.getChildAt(indexcolumn+i);
                        editImageView(temp, R.drawable.hole25x25, "hole", null);
                        break;
                    case "south":
                        row = (TableRow)tableLayout.getChildAt(indexrow+j);
                        temp = (ImageView) row.getChildAt(indexcolumn-i);
                        editImageView(temp, R.drawable.hole25x25, "hole", null);
                        break;
                }
            }
        }

        return true;
    }
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ImageView image = (ImageView)v;

                String tag = (String) image.getTag(R.id.imageTag);
                boolean isHole = tag.equals("hole");
                boolean isclickable = !findClickable(image).equals("");
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toolbar);
                final String value = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId() )).getText().toString();
                ImageView temp = new ImageView(getApplicationContext());
                switch(value)
                {
                    case "Move":
                        if(!isHole && isclickable)
                        {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                            v.startDrag(data, shadowBuilder, v, 0);
                            v.setVisibility(View.INVISIBLE);
                            if (fromTray(v)) tray.removeViewAt(tray.indexOfChild(v));
                        }

                        break;
                    case "Rotate":
                        if(!isHole && isclickable)
                        {
                            rotate(v);
                        }
                        break;
                    case "Inspect":
                        if(!isHole && isclickable) {
                            Intent intent = new Intent(VectorBoardActivity.this, popup_inspect.class);
                            intent.putExtra("component", (Component) image.getTag(R.id.component));
                            startActivity(intent);
                        }
                        break;
                    case "Wire":
                        if(isHole)
                        {
                            wire(image);
                        }
                        break;
                    case "Clear":

                        if(!isHole && isclickable)
                        {
                            String type = "";
                            int width=1;
                            int length=1;
                            String imageFile = findClickable(image);

                            if(imageFile.contains("res"))
                            {
                                type = "res";
                                length = 4;
                            }
                            else if(imageFile.contains("cap"))
                            {
                                type = "cap";
                                length = 2;
                            }
                            else if(imageFile.contains("ind"))
                            {
                                type = "ind";
                                length = 4;
                            }
                            else if(imageFile.contains("2x2"))
                            {
                                type = "2x2";
                                length = 2;
                                width = 4;
                            }
                            else if(imageFile.contains("3x3"))
                            {
                                type = "3x3";
                                length = 3;
                                width = 4;
                            }
                            else if(imageFile.contains("4x4"))
                            {
                                type = "4x4";
                                length = 4;
                                width = 4;
                            }
                            imageFile = imageFile.substring(0,1);
                            switch (imageFile)
                            {
                                case "e":
                                    //need to fix for ICs
                                    clear(image,"east",width,length);
                                    break;
                                case "w":
                                    clear(image,"west",width,length);
                                    break;
                                case "n":
                                    clear(image,"north",width,length);
                                    break;
                                case "s":
                                    clear(image,"south",width,length);
                                    break;
                            }

                            switch (type)
                            {
                                case "res":
                                    editImageView(temp, R.drawable.resistorfinal, "resistorfinal", (Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                case "cap":
                                    editImageView(temp, R.drawable.capacitorfinal, "capacitorfinal",(Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                case "ind":
                                    editImageView(temp, R.drawable.inductor, "inductor", (Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                case "2x2":
                                    editImageView(temp, R.drawable.chip2x2, "chip2x2", (Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                case "3x3":
                                    editImageView(temp, R.drawable.chip3x3, "chip3x3", (Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                case "4x4":
                                    editImageView(temp, R.drawable.chip4x4, "chip4x4", (Component) image.getTag(R.id.component));
                                    tray.addView(temp);
                                    break;
                                default:
                                    break;
                            }
                        }
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

        private boolean fromTray(View v) {
            String string = findClickable((ImageView) v);
            assert string != null;
            if(string.equals("resistorfinal") ||
                    string.equals("inductor") ||
                    string.equals("capacitorfinal") ||
                    string.equals("chip2x2")||
                    string.equals("chip3x3") ||
                    string.equals("chip4x4")) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public void wire(ImageView image)
    {
        TableRow row = (TableRow)image.getParent();
        int columnindex = tableLayout.indexOfChild(row);
        int rowindex = row.indexOfChild(image);
        ImageView east = null;
        ImageView west = null;
        ImageView north = null;
        ImageView south = null;
        if(rowindex>0 && rowindex<rowMAX)
        {
            east = (ImageView)((TableRow)tableLayout.getChildAt(columnindex)).getChildAt(rowindex+1);
            west = (ImageView)((TableRow)tableLayout.getChildAt(columnindex)).getChildAt(rowindex-1);
        }
        if(columnindex>0 && columnindex<columnMAX)
        {
            north = (ImageView)((TableRow)tableLayout.getChildAt(columnindex-1)).getChildAt(rowindex);
            south = (ImageView)((TableRow)tableLayout.getChildAt(columnindex+1)).getChildAt(rowindex);
        }

        Drawable wire = findWireType(north, south, east, west);

        image.setImageDrawable(wire);
        image.setOnTouchListener(new MyTouchListener());
    }

    private Drawable findWireType(ImageView north, ImageView south, ImageView east, ImageView west) {
        boolean[] answers = new boolean[4];
        int i,j,counter,total;
        total = counter = 0;
        ImageView result= new ImageView(getApplicationContext());
        answers[0] = ifConnectable(north,"south");
        answers[1] = ifConnectable(south,"north");
        answers[2] = ifConnectable(east, "west");
        answers[3] = ifConnectable(west, "east");
        for(i=0;i<answers.length;i++)
        {
            if (answers[i]) total++;

        }
        Resources res = getResources();
        TypedArray wires = res.obtainTypedArray(R.array.wire_parts);
        for(i=0;i<wires.length();i++)
        {
            String fileName = wires.getString(i);
            counter = 0;
            for(j =0 ; j < 4 ; j++)
            {
                if(answers[j] && fileName.contains(cardinals[j]))
                {
                   counter++;
                }
            }
            if (counter == total)
            {
                return wires.getDrawable(i);
            }
        }
        return wires.getDrawable(0);
    }

    private boolean ifConnectable(ImageView imageView, String key) {
        Resources res = getResources();
        String fileName;
        TypedArray wires = res.obtainTypedArray(R.array.wire_parts);
        for(int i=0;i<wires.length();i++)
        {
            //ImageView temp = new ImageView(this);
            //temp.setImageDrawable(wires.getDrawable(i));
            fileName = wires.getString(i);
            fileName = fileName.substring(13);  // removes the res/drawables
            fileName = fileName.substring(0,fileName.length()-4); //removes .png

            if(imageView.getTag(R.id.imageTag).equals(fileName)) {
                if (fileName.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    private class MyDragListener implements View.OnDragListener {
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
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    if(v!=board) {
                        v.setVisibility(View.VISIBLE);
                    }
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
                    String image = (String)dropped.getTag(R.id.imageTag);

                    if (validMove(dropped,dropTarget)){
                        switch (image)
                        {
                            //resistors
                            case "resistorfinal":
                                setImageRes(dropTarget, row, indexcolumn, "east",(Component) dropped.getTag(R.id.component));
                                break;
                            case "east_res1_4":
                                clear(dropped,"east", 1,4);
                                setImageRes(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_res1_4":
                                clear(dropped,"west", 1,4);
                                setImageRes(dropTarget, row, indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_res1_4":
                                clear(dropped,"north", 1,4);
                                setImageRes(dropTarget, row, indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_res1_4":
                                clear(dropped,"south", 1,4);
                                setImageRes(dropTarget, row, indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;
                            //capacitors
                            case "capacitorfinal":
                                setImageCap(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "east_capacitor1_2":
                                clear(dropped,"east", 1,2);
                                setImageCap(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_capacitor1_2":
                                clear(dropped,"west", 1,2);
                                setImageCap(dropTarget, row, indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_capacitor1_2":
                                clear(dropped,"north", 1,2);
                                setImageCap(dropTarget, row, indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_capacitor1_2":
                                clear(dropped,"south", 1,2);
                                setImageCap(dropTarget, row, indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;
                            //inductors
                            case "inductor":
                                setImageInd(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "east_inductor1_4":
                                clear(dropped,"east", 1,4);
                                setImageInd(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_inductor1_4":
                                clear(dropped,"west", 1,4);
                                setImageInd(dropTarget, row, indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_inductor1_4":
                                clear(dropped,"north", 1,4);
                                setImageInd(dropTarget, row, indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_inductor1_4":
                                clear(dropped,"south", 1,4);
                                setImageInd(dropTarget, row, indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;
                            case "chip2x2":
                                setImageTwo(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "east_2x2chip1_2_1_4":
                                clear(dropped,"east",4,2);
                                setImageTwo(dropTarget,row,indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_2x2chip1_2_1_4":
                                clear(dropped,"west",4,2);
                                setImageTwo(dropTarget,row,indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_2x2chip1_2_1_4":
                                clear(dropped,"north",4,2);
                                setImageTwo(dropTarget,row,indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_2x2chip1_2_1_4":
                                clear(dropped,"south",4,2);
                                setImageTwo(dropTarget,row,indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;

                            case "chip3x3":
                            setImageThree(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                            break;
                            case "east_3x3chip1_3_1_4":
                                clear(dropped,"east",4,3);
                                setImageThree(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_3x3chip1_3_1_4":
                                clear(dropped,"west",4,3);
                                setImageThree(dropTarget, row, indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_3x3chip1_3_1_4":
                                clear(dropped,"north",4,3);
                                setImageThree(dropTarget, row, indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_3x3chip1_3_1_4":
                                clear(dropped,"south",4,3);
                                setImageThree(dropTarget, row, indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;

                            case "chip4x4":
                                setImageFour(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "east_4x4chip1_4_1_4":
                                clear(dropped,"east",4,4);
                                setImageFour(dropTarget, row, indexcolumn, "east", (Component) dropped.getTag(R.id.component));
                                break;
                            case "west_4x4chip1_4_1_4":
                                clear(dropped,"west",4,4);
                                setImageFour(dropTarget, row, indexcolumn, "west", (Component) dropped.getTag(R.id.component));
                                break;
                            case "north_4x4chip1_4_1_4":
                                clear(dropped,"north",4,4);
                                setImageFour(dropTarget, row, indexcolumn, "north", (Component) dropped.getTag(R.id.component));
                                break;
                            case "south_4x4chip1_4_1_4":
                                clear(dropped,"south",4,4);
                                setImageFour(dropTarget, row, indexcolumn, "south", (Component) dropped.getTag(R.id.component));
                                break;
                            default:
                                view.setVisibility(View.VISIBLE);
                        }
                }
                    view.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }

        private boolean validMove(ImageView DroppedImage, ImageView TargetImage) {
            TableRow row = (TableRow) TargetImage.getParent();
            int indexColumn = row.indexOfChild(TargetImage);
            int indexRow = tableLayout.indexOfChild(row);
            //ImageView hole = new ImageView(getApplicationContext());
            //hole.setImageResource(R.drawable.hole25x25);
            String direction = "";
            int xlength = 0;
            int ylength = 0;
            String imageFile = findClickable(DroppedImage);

            if(imageFile.endsWith("under")){
                return false;                             //checks to see if the image is under the board
            }
            if (imageFile.contains("_")) {//Not a final image
                if (imageFile.charAt(4) == '_') { //EAST WEST
                    direction = imageFile.substring(0, 4);
                    xlength = Integer.parseInt(imageFile.substring((imageFile.length()-1)));
                    if(imageFile.contains("chip")){
                        ylength = xlength;
                        xlength = Integer.parseInt(imageFile.substring(imageFile.length()-5,imageFile.length()-4));
                    }
                    else {
                        ylength = 1;  // single row component
                    }

                } else {  // NORTH SOUTH
                    // TODO: 11/17/2015 fix for ics
                    direction = imageFile.substring(0, 5);
                    xlength = Integer.parseInt(imageFile.substring((imageFile.length() - 1)));
                    if(imageFile.contains("chip")){
                        ylength = xlength;
                        xlength = Integer.parseInt(imageFile.substring(imageFile.length()-5,imageFile.length()-4));
                    }
                    else {
                        ylength = 1;  // single row component
                    }
                }
            }
            else{
                direction = "east";
                if(imageFile.equals("resistorfinal")) { //TODO CHANGE FINALS TO INCLUDE THE STRING
                    xlength = 4;
                    ylength = 1;
                }
                if(imageFile.equals("capacitorfinal")) {
                    xlength = 2;
                    ylength = 1;
                }
                if(imageFile.equals("inductor")) {
                    xlength = 4;
                    ylength = 1;
                }
                if(imageFile.equals("chip2x2")){
                    xlength = 2;
                    ylength = 4;
                }
                if(imageFile.equals("chip3x3")){
                    xlength = 3;
                    ylength = 4;
                }
                if(imageFile.equals("chip4x4")){
                    xlength = 4;
                    ylength = 4;
                }
            }
            switch (direction) {
                case "north":
                    //Am I within the bounds?
                    if( ylength + indexColumn > columnMAX ) return false;
                    if(-(xlength - 1) + indexRow < 0 ) return false;
                    //is the Path Clear
                    for (int x = 0 ; x < xlength ; x++) {
                        for (int y =0 ; y < ylength ;y++){
                            TableRow temp = (TableRow) tableLayout.getChildAt(indexRow - x);
                            if(!(row.getChildAt(indexColumn + y).getTag(R.id.imageTag).equals("hole")))
                                return false;
                        }
                    }

                    break;
                case "south":
                    //Am I within the bounds
                    if(-(ylength - 1) + indexColumn < 0 ) return false;
                    if(xlength + indexRow > rowMAX ) return false;
                    //is the Path Clear
                    for (int x = 0 ; x < xlength ; x++) {
                        for (int y =0 ; y < ylength ;y++){
                            TableRow temp = (TableRow) tableLayout.getChildAt(indexRow + x);
                            if(!(temp.getChildAt(indexColumn - y).getTag(R.id.imageTag).equals("hole")))
                                return false;
                        }
                    }
                    break;
                case "east":
                    //Am I within the bounds
                    if(xlength + indexColumn > columnMAX ) return false;
                    if(ylength + indexRow > rowMAX ) return false;
                    //is the Path Clear
                    for (int x = 0 ; x < xlength ; x++) {
                        for (int y =0 ; y < ylength ;y++){
                            TableRow temp = (TableRow) tableLayout.getChildAt(indexRow + y);
                            if(!((temp.getChildAt(indexColumn + x).getTag(R.id.imageTag).equals("hole"))))
                                return false;
                        }
                    }
                    break;
                case "west":
                    //Am I within Bounds
                    if(-(xlength - 1) + indexColumn < 0) return false;
                    if(-(ylength - 1) + indexRow < 0 ) return false;
                    //is the path clear
                    //is the Path Clear
                    for (int x = 0 ; x < xlength ; x++) {
                        for (int y =0 ; y < ylength ;y++){
                            TableRow temp = (TableRow) tableLayout.getChildAt(indexRow - y);
                            if(!(temp.getChildAt(indexColumn - x).getTag(R.id.imageTag).equals("hole")))
                                return false;
                        }
                    }
                    break;
            }
            return true;
        }

        private boolean dropEventNotHandled(DragEvent dragEvent) {
            return !dragEvent.getResult();
        }

        private boolean setImageRes(ImageView image, TableRow row, int i, String direction, Component component)
        {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            String tag;
            Resources res = getResources();
            TypedArray resistor = res.obtainTypedArray(R.array.forward);

                switch(direction)
                {
                    case "east":
                        editImageView(image,R.drawable.east_res1_4,"east_res1_4",component);
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i+j);
                            tag = findTag(resistor.getString(j));
                            editImageView(temp, resistor.getDrawable(j), tag, null);
                        }
                        break;
                    case "west":
                        editImageView(image, R.drawable.west_res1_4, "west_res1_4", component);
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i-j);
                            tag = findTag(resistor.getString(j + 4));
                            editImageView(temp, resistor.getDrawable(j + 4), tag, null);
                        }
                        break;
                    case "north":
                        editImageView(image, R.drawable.north_res1_4, "north_res1_4", component);
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column-j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            tag = findTag(resistor.getString(11 - j));
                            editImageView(temp, resistor.getDrawable(11 - j), tag, null);
                        }
                        break;
                    case "south":
                        editImageView(image, R.drawable.south_res1_4, "south_res1_4", component);
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column+j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            tag = findTag(resistor.getString(15 - j));
                            editImageView(temp, resistor.getDrawable(15 - j), tag, null);
                        }
                        break;
                }
                return true;
        }

        private boolean setImageInd(ImageView image, TableRow row, int i, String direction, Component component)
        {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            String tag;
            Resources res = getResources();
            TypedArray inductor = res.obtainTypedArray(R.array.forward);
            switch(direction)
                {
                    case "east":
                        editImageView(image, R.drawable.east_inductor1_4, "east_inductor1_4", component);
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i+j);
                            tag = findTag(inductor.getString(j + 16));
                            editImageView(temp, inductor.getDrawable(j + 16), tag, null);
                        }
                        break;
                    case "west":
                        editImageView(image, R.drawable.west_inductor1_4, "west_inductor1_4", component);
                        for(j=1;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(i - j);
                            tag = findTag(inductor.getString(j + 20));
                            editImageView(temp, inductor.getDrawable(j + 20),tag,null);
                        }
                        break;
                    case "north":
                        editImageView(image, R.drawable.north_inductor1_4, "north_inductor1_4", component);
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column-j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            tag = findTag(inductor.getString(27 - j));
                            editImageView(temp, inductor.getDrawable(27 - j), tag, null);
                        }
                        break;
                    case "south":
                        editImageView(image, R.drawable.south_inductor1_4, "south_inductor1_4", component);
                        for(j=1;j<4;j++)
                        {
                            tempRow = (TableRow) tableLayout.getChildAt(column+j);
                            temp = (ImageView) tempRow.getChildAt(i);
                            tag = findTag(inductor.getString(31 - j));
                            editImageView(temp, inductor.getDrawable(31 - j), tag, null);
                        }
                        break;
                }
                return true;
        }

        private boolean setImageCap(ImageView image, TableRow row, int i, String direction, Component component) {
            int column = tableLayout.indexOfChild(row);
            int j;
            ImageView temp;
            TableRow tempRow;
            String tag;
            Resources res = getResources();
            TypedArray capacitor = res.obtainTypedArray(R.array.forward);
            switch (direction) {
                case "east":
                    editImageView(image, R.drawable.east_capacitor1_2, "east_capacitor1_2", component);
                    for (j = 1; j < 2; j++) {
                        temp = (ImageView) row.getChildAt(i + j);
                        tag = findTag(capacitor.getString(j + 32));
                        editImageView(temp, capacitor.getDrawable(j + 32), tag, null);
                    }
                    break;
                case "west":
                    editImageView(image, R.drawable.west_capacitor1_2, "west_capacitor1_2", component);
                    for (j = 1; j < 2; j++) {
                        temp = (ImageView) row.getChildAt(i - j);
                        tag = findTag(capacitor.getString(j + 34));
                        editImageView(temp, capacitor.getDrawable(j + 34), tag, null);
                    }
                    break;
                case "north":
                    editImageView(image, R.drawable.north_capacitor1_2, "north_capacitor1_2", component);
                    for (j = 1; j < 2; j++) {
                        tempRow = (TableRow) tableLayout.getChildAt(column - j);
                        temp = (ImageView) tempRow.getChildAt(i);
                        tag = findTag(capacitor.getString(37 - j));
                        editImageView(temp, capacitor.getDrawable(37 - j), tag, null);
                    }
                    break;
                case "south":
                    editImageView(image, R.drawable.south_capacitor1_2, "south_capacitor1_2", component);
                    for (j = 1; j < 2; j++) {
                        tempRow = (TableRow) tableLayout.getChildAt(column + j);
                        temp = (ImageView) tempRow.getChildAt(i);
                        tag = findTag(capacitor.getString(40 - j));
                        editImageView(temp, capacitor.getDrawable(40 - j), tag, null);
                    }
                    break;
            }
            return true;
        }

        private boolean setImageTwo(ImageView image, TableRow row, int colIndex, String direction, Component component)
        {
            int rowIndex = tableLayout.indexOfChild(row);
            int j,i,counter;
            ImageView temp;
            String tag;
            Resources res = getResources();
            TypedArray east = res.obtainTypedArray(R.array.twoxtwocomponent_east);
            TypedArray west = res.obtainTypedArray(R.array.twoxtwocomponent_west);
            TypedArray north = res.obtainTypedArray(R.array.twoxtwocomponent_north);
            TypedArray south = res.obtainTypedArray(R.array.twoxtwocomponent_south);

            switch(direction)
            {
                case "east":
                    counter = 0;
                    for(i=0;i<8;i=i+2)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex + counter);

                        for(j=0;j<2;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + j);
                            tag = findTag(east.getString(j+i));
                            editImageView(temp, east.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "west":
                    counter = 0;
                    for(i=0;i<8;i=i+2)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex - counter);

                        for(j=0;j<2;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - j);
                            tag = findTag(west.getString(j+i));
                            editImageView(temp, west.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "north":
                    for(i=0;i<2;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex - i);
                        counter = 0;
                        for(j=0;j<8;j=j+2)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + counter);
                            tag = findTag(north.getString(j+i));
                            editImageView(temp,north.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                    break;

                case "south":
                {
                    for(i=0;i<2;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex + i);
                        counter=0;
                        for(j=0;j<8;j=j+2)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - counter);
                            tag = findTag(south.getString(j+i));
                            editImageView(temp,south.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                }
            }
            return true;
        }

        private boolean setImageThree(ImageView image, TableRow row, int colIndex, String direction, Component component)
        {
            int rowIndex = tableLayout.indexOfChild(row);
            int j,i,counter;
            ImageView temp;
            String tag;
            Resources res = getResources();
            TypedArray east = res.obtainTypedArray(R.array.threexthreecomponent_east);
            TypedArray west = res.obtainTypedArray(R.array.threexthreecomponent_west);
            TypedArray north = res.obtainTypedArray(R.array.threexthreecomponent_north);
            TypedArray south = res.obtainTypedArray(R.array.threexthreecomponent_south);

            switch(direction)
            {
                case "east":
                    counter = 0;
                    for(i=0;i<12;i=i+3)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex + counter);

                        for(j=0;j<3;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + j);
                            tag = findTag(east.getString(j+i));
                            editImageView(temp, east.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "west":
                    counter = 0;
                    for(i=0;i<12;i=i+3)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex - counter);

                        for(j=0;j<3;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - j);
                            tag = findTag(west.getString(j+i));
                            editImageView(temp, west.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "north":
                    for(i=0;i<3;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex - i);
                        counter = 0;
                        for(j=0;j<12;j=j+3)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + counter);
                            tag = findTag(north.getString(j+i));
                            editImageView(temp,north.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                    break;

                case "south":
                {
                    for(i=0;i<3;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex + i);
                        counter=0;
                        for(j=0;j<12;j=j+3)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - counter);
                            tag = findTag(south.getString(j+i));
                            editImageView(temp,south.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                }
            }
            return true;
        }

        private boolean setImageFour(ImageView image, TableRow row, int colIndex, String direction, Component component)
        {
            int rowIndex = tableLayout.indexOfChild(row);
            int j,i,counter;
            ImageView temp;
            String tag;
            Resources res = getResources();
            TypedArray east = res.obtainTypedArray(R.array.fourxfourcomponent_east);
            TypedArray west = res.obtainTypedArray(R.array.fourxfourcomponent_west);
            TypedArray north = res.obtainTypedArray(R.array.fourxfourcomponent_north);
            TypedArray south = res.obtainTypedArray(R.array.fourxfourcomponent_south);

            switch(direction)
            {
                case "east":
                    counter = 0;
                    for(i=0;i<16;i=i+4)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex + counter);

                        for(j=0;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + j);
                            tag = findTag(east.getString(j+i));
                            editImageView(temp, east.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "west":
                    counter = 0;
                    for(i=0;i<16;i=i+4)
                    {
                        row = (TableRow)tableLayout.getChildAt(rowIndex - counter);

                        for(j=0;j<4;j++)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - j);
                            tag = findTag(west.getString(j+i));
                            editImageView(temp, west.getDrawable(j + i), tag, component);
                        }
                        counter++;
                    }
                    break;

                case "north":
                    for(i=0;i<4;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex - i);
                        counter = 0;
                        for(j=0;j<16;j=j+4)
                        {
                            temp = (ImageView) row.getChildAt(colIndex + counter);
                            tag = findTag(north.getString(j+i));
                            editImageView(temp,north.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                    break;

                case "south":
                {
                    for(i=0;i<4;i++)
                    {
                        row = (TableRow) tableLayout.getChildAt(rowIndex + i);
                        counter=0;
                        for(j=0;j<16;j=j+4)
                        {
                            temp = (ImageView) row.getChildAt(colIndex - counter);
                            tag = findTag(south.getString(j+i));
                            editImageView(temp,south.getDrawable(j+i), tag, component);
                            counter++;
                        }
                    }
                }
            }
            return true;
        }
    }

    private String findTag(String string) {
        if(string != null && string.length() > 18) {
            string = string.substring(13);  // removes the res/drawables
            string = string.substring(0, string.length() - 4); //removes .png
            return string;
        }
        return string;
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn_flip:
                    for(int i=0;i<rowMAX;i++)
                    {
                        TableRow row = new TableRow(getApplicationContext());
                        TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                        for(int j=columnMAX-1;j>=0;j--)
                        {
                            ImageView imageView = (ImageView) tableRow.getChildAt(j);
                            //switch to under view and fix order
                            findReverseImage(imageView); // This edits imageView
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
                String fileName = forward.getString(i);
                fileName = fileName.substring(13);  // removes the res/drawables
                fileName = fileName.substring(0,fileName.length()-4); //removes .png
                if(fileName.equals(imageView.getTag(R.id.imageTag)))
                {
                    String tag = backwards.getString(i);
                    tag = findTag(tag);
                    editImageView(imageView,backwards.getDrawable(i),tag,null);
                    temp.setImageDrawable(backwards.getDrawable(i));
                    return temp;
                }
            }
            for(int i=0;i<backwards.length();i++)
            {
                ImageView temp = new ImageView(getApplicationContext());
                String fileName = backwards.getString(i);
                fileName = fileName.substring(13);  // removes the res/drawables
                fileName = fileName.substring(0,fileName.length()-4); //removes .png
                if(fileName.equals(imageView.getTag(R.id.imageTag)))
                {
                    String tag = forward.getString(i);
                    tag = findTag(tag);
                    editImageView(imageView, forward.getDrawable(i),tag, null);
                    temp.setImageDrawable(forward.getDrawable(i));
                    return temp;
                }
            }
            return imageView;
        }
    }
    private ImageView editImageView(ImageView current, int drawable, String tag, Component component)
    {
        current.setImageResource(drawable);
        if(tag !=null) {
            current.setTag(R.id.imageTag, tag);
        }
        if (component != null) {
            current.setTag(R.id.component, component);
        }

        String clicked = findClickable(current);
        if(clicked != null) {
            current.setOnTouchListener(new MyTouchListener());
        }
        if(tag.equals("hole")) {
            current.setOnDragListener(new MyDragListener());
        }
        return current;
    }

    private ImageView editImageView(ImageView current, Drawable drawable, String tag, Component component)
    {
        current.setImageDrawable(drawable);
        if(tag !=null) {
            current.setTag(R.id.imageTag, tag);
            if(tag.equals("hole")) {
                current.setOnDragListener(new MyDragListener());
            }
        }
        if (component != null) {
            current.setTag(R.id.component, component);
        }
        String clicked = findClickable(current);
        if(clicked != null) {
            current.setOnTouchListener(new MyTouchListener());
        }

        return current;
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
