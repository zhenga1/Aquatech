package com.example.administrator.envirobros;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_ROWS = 3;
    private String[] title = new String[]{"Temperature", "Ph", "Conductivity"};
    private String[] descriptions = new String[]{"Here is the values collected for temperature", "Here is the values collected for pH", "Here is the values collected for conductivity"};
    private int[] images = {R.drawable.heat,R.drawable.ph, R.drawable.conductivity};
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //title = findViewById(R.id.textView);
        MyAdapter adapter = new MyAdapter(this, title,descriptions,images);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //i represents the position
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("type", i);
                startActivity(intent);
            }
        });
    }
    public void aboutus(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Aboutus.class);
        startActivity(intent);
    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rtitle[];
        String rdescription[];
        int rImgs[];
        MyAdapter(Context c, String title[], String descrip[], int img[])
        {
            super(c, R.layout.row, R.id.heading, title);
            this.context=c;
            this.rtitle=title;
            this.rdescription=descrip;
            this.rImgs=img;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView imageView = row.findViewById(R.id.image);
            TextView titleTextview = row.findViewById(R.id.heading);
            TextView subtitletextview = row.findViewById(R.id.subtitle);

            imageView.setImageResource(rImgs[position]);
            titleTextview.setText(rtitle[position]);
            subtitletextview.setText(rdescription[position]);

            return row;

        }
    }

}

/*
for(int i = 0; i<NUM_ROWS; i++){
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1.0f));
        Button button = new Button(this);
        button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        button.setText(string[i]);
        button.setTextColor(Color.BLACK);
        button.setTag(i);
        button.setPadding(0,0,0,0);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        button.setOnClickListener(new View.OnClickListener() {
    public void ButtonClick(int i){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("type", i);
        MainActivity.this.startActivity(intent);
    }
@Override
public void onClick(View view) {
        ButtonClick((int)view.getTag());
        }
        });
        tableRow.addView(button);
        listView.addView(tableRow);
        tableRow.invalidate();
        listView.invalidate();
        }*/