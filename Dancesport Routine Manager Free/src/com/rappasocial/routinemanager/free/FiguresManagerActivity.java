package com.rappasocial.routinemanager.free;
//package com.rappasocial.routinemanager;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class FiguresManagerActivity extends Activity implements OnClickListener {
//	
//	
//
//	ArrayList<Figure> figures;
//	  BoxAdapter boxAdapter;
//	  DBHelper dbHelper;
//	  SQLiteDatabase db;
//	  ExtendedApplication extApp;
//	  TextView tvCurDance;
//	  Button btAddNewFigMngr;
//	  ListView lvMain;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//		  super.onCreate(savedInstanceState);
//		   setContentView(R.layout.figures_list);
//		   
//		   figures = new ArrayList<Figure>();
//		   extApp = (ExtendedApplication)getApplicationContext();
//		   
//		    dbHelper = new DBHelper(this); 
//			  
//		    // подключаемся к базе
//		    db = dbHelper.getWritableDatabase();
//		    // создаем адаптер
//		    fillData();
//		    boxAdapter = new BoxAdapter(this, figures);
//
//		    // настраиваем список
//		    lvMain = (ListView) findViewById(R.id.lvFigures);
//		    lvMain.setAdapter(boxAdapter);
//		    tvCurDance = (TextView) findViewById(R.id.tvCurDance);
//		    tvCurDance.setText(extApp.getcurrentDance().name);
//		    
//		    btAddNewFigMngr = (Button) findViewById(R.id.btAddNewFigMngr);
//		    btAddNewFigMngr.setOnClickListener(this);
//		    
//		
//		 
//		 
//		  
//	}
//    
//    @Override
//   	protected void onResume() {
//   		// TODO Auto-generated method stub
//   		super.onResume();
//   		figures.clear();
//   		fillData();
//   		boxAdapter.notifyDataSetChanged();
//   		
//   	}
//
//	/** Called when the activity is first created. */
//
//
//	  // генерируем данные для адаптера
//	  void fillData() {
//		  
//		  
//		  // переменные для query
//		  //  String[] columns = null;
//		    String selection = null;
//		  //  String[] selectionArgs = null;
//		    //String groupBy = null;
//		    //String having = null;
//		   // String orderBy = null;
//
//		    // курсор
//		    Cursor c = null;
//		    Dance curDance = extApp.getcurrentDance();
//		    if (curDance != null) {
//		    	
//		    	selection = dbHelper.COLUMN_FIGURES_DANCE_ID + " = " + curDance.id;
//		    	
//		    } else {
//		    	
//		    	selection = null;
//		    }
//		    
//		      
//		      c = db.query("figures", null, selection, null, null, null,
//		          null);
//		      
//		      if (c != null) {
//		          if (c.moveToFirst()) {
//		            
//		            do {
//		              
//		            	 figures.add(new Figure(c.getInt(c.getColumnIndex(dbHelper.COLUMN_FIGURES_ID)),
//		            			 c.getString(c.getColumnIndex(dbHelper.COLUMN_FIGURES_NAME))));
//		              
//		              
//
//		            } while (c.moveToNext());
//		          }
//		          c.close();
//		        } else
//		          
//
//		        dbHelper.close();
//		 
//	  }
//
//	public void onClick(View v) {
//		
//		switch (v.getId()) {
//		case R.id.btAddNewFigMngr:
//			 
//			Intent intent = new Intent(FiguresManagerActivity.this, AddFigureActivity.class);
//		    startActivity(intent);
//			break;
//		
//		}
//		
//	}
//
//
//}
