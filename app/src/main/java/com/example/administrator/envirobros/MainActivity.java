package com.example.administrator.envirobros;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_ROWS = 3;
    private TextView title;
    private String[] string = new String[]{"Temperature", "Ph", "Conductivity"};
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //title = findViewById(R.id.textView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, string);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //i represents the position
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("type", i);
                MainActivity.this.startActivity(intent);
            }
        });
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