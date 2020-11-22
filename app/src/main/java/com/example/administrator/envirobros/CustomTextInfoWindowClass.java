package com.example.administrator.envirobros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class CustomTextInfoWindowClass implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private int number = 1;
    private Context mContext;


    public CustomTextInfoWindowClass(Context context, int i) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_popup_window, null);
        number = i;
    }
    private void renderWindowText(Marker marker, View view)
    {
        String title = marker.getTitle();
        TextView textTitle = (TextView) view.findViewById(R.id.popupTitle);
        if(!title.equals("")){
            textTitle.setText(title);
        }
        String subtitle = marker.getSnippet();
        TextView textSnippet = (TextView) view.findViewById(R.id.popupText);
        if(!subtitle.equals(""))
        {
            textSnippet.setText(subtitle);
        }
    }
    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
/*
    public CustomTextInfoWindowClass(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_popup_window, null);
    }*/