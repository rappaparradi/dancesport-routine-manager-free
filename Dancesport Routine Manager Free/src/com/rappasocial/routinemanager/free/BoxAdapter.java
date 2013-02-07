package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import com.rappasocial.routinemanager.free.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoxAdapter extends BaseAdapter {
  Context ctx;
  LayoutInflater lInflater;
  ArrayList<Figure> objects;

  BoxAdapter(Context context, ArrayList<Figure> figures) {
    ctx = context;
    objects = figures;
    lInflater = (LayoutInflater) ctx
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

 
  public int getCount() {
    return objects.size();
  }

  
  public Object getItem(int position) {
    return objects.get(position);
  }

 
  public long getItemId(int position) {
    return position;
  }

  
  public View getView(int position, View convertView, ViewGroup parent) {

    View view = convertView;
    if (view == null) {
      view = lInflater.inflate(R.layout.figures_list_item, parent, false);
    }

    Figure p = getFigure(position);

  
    ((TextView) view.findViewById(R.id.tvFigureName)).setText(p.name);
  
    return view;
  }


  Figure getFigure(int position) {
    return ((Figure) getItem(position));
  }


}
