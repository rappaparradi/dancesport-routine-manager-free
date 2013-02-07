package com.rappasocial.routinemanager.free;
//package com.rappasocial.routinemanager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mobeta.android.demodslv.ArbItemSizeDSLV;
//import com.mobeta.android.demodslv.R;
//import com.mobeta.android.demodslv.ArbItemSizeDSLV.JazzArtist;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//public class BoxAdapterRoutine extends ArrayAdapter<Routine> implements OnClickListener {
//  Context ctx;
//  LayoutInflater lInflater;
//  ArrayList<Routine> objects;
//  ExtendedApplication extApp;
//  
//
//      
//      public BoxAdapterRoutine(List<Routine> artists) {
//        super(ctx, R.layout.routine_list_item,
//          R.id.tvRoutineName, artists);
//      }
//
//  BoxAdapterRoutine(Context context, ArrayList<Routine> routines) {
//    ctx = context;
//    objects = routines;
//    lInflater = (LayoutInflater) ctx
//        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    extApp = (ExtendedApplication)ctx.getApplicationContext();
//  }
//
// 
//  public int getCount() {
//    return objects.size();
//  }
//
//  
//  public Object getItem(int position) {
//    return objects.get(position);
//  }
//
// 
//  public long getItemId(int position) {
//    return position;
//  }
//
//  
//  public View getView(int position, View convertView, ViewGroup parent) {
//
//    View view = convertView;
//    if (view == null) {
//      view = lInflater.inflate(R.layout.routine_list_item, parent, false);
//    }
//
//    Routine p = getRoutine(position);
//
//  
//    ((TextView) view.findViewById(R.id.tvRoutineName)).setText(p.name);
//    Button btEditRoutine = (Button) view.findViewById(R.id.btEditRoutine);
//    btEditRoutine.setTag(position);
//    btEditRoutine.setOnClickListener(this);
////    ((Button) view.findViewById(R.id.btEditRoutine)).setOnClickListener(this);
////    btEditRoutine.setOnClickListener(this);
//  
//    return view;
//  }
//
//
//  Routine getRoutine(int position) {
//    return ((Routine) getItem(position));
//  }
//  
//  public void onClick(View v) {
//		
//		switch (v.getId()) {
//
//			
//		case R.id.btEditRoutine:
//			Animation animRotate = AnimationUtils.loadAnimation(ctx, R.anim.anim_scale); 
//			v.startAnimation(animRotate); 
//            extApp.currentRoutine = getRoutine((Integer)  v.getTag()); ///Very very KRUTO
//            Intent intent = new Intent(ctx, RoutineEditActivity.class);
//            ctx.startActivity(intent);
//			break;
//		
//		}
//		
//	}
//
//
//}