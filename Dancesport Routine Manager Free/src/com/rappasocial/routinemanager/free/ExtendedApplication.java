package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExtendedApplication extends Application {

	public Dance currentDance;
	public int currentRoutineid;
	public int currentRoutineRawId;
	public int currentGender;
	public DBHelper dbHelper;
	public SQLiteDatabase db;
	public ArrayList<Figure> figuresSelectionBufferArray;
	public ArrayList<RoutineRaw> mRoutine_rawsBufferArray;
	public ArrayList<RoutineRaw> wRoutine_rawsBufferArray;
	public String editTimingBuffer;
	public String editCommentBuffer;
	public boolean isRoutineModified; 
	
	@Override
    public void onCreate() {
        // Here you could pull values from a config file in res/raw or somewhere else
        // but for simplicity's sake, we'll just hardcode values
        
        super.onCreate();
        dbHelper = new DBHelper(this); 
        db = dbHelper.getWritableDatabase();
        figuresSelectionBufferArray = new ArrayList<Figure>();
        mRoutine_rawsBufferArray = new ArrayList<RoutineRaw>();
        wRoutine_rawsBufferArray = new ArrayList<RoutineRaw>();
        editTimingBuffer = "";
        isRoutineModified = false;
//        SharedPrefs prefvar = new SharedPrefs();
//        currentGender =  prefvar.getSex(this);
        
    }
 
	public Dance getcurrentDance() {
        return currentDance;
    }
 
    public void setcurrentDance(Dance value) {
    	currentDance = value;
    }
    
    public String getFiguresNameByID(int f_id, int d_id) {
    	
    		String selection = null;
	        Cursor c = null;
		    
	        selection = dbHelper.COLUMN_FIGURES_DANCE_ID + " = " + d_id;
	        selection = selection + " AND " + dbHelper.COLUMN_FIGURES_ID + " = " + f_id;
		    	
		
		      
		      c = db.query(dbHelper.DB_TABLE_FIGURES, null, selection, null, null, null,
		          null);
		      
		      if (c != null) {
		          if (c.moveToFirst()) {
		            
		            do {
		              
		            	return c.getString(c.getColumnIndex(dbHelper.COLUMN_FIGURES_NAME));
		              
		              

		            } while (c.moveToNext());
		          }
		          c.close();
		        };
		        
		        return "";
		        
    }
    
    public String getRoutineNameByID(int r_id) {
    	
		String selection = null;
        Cursor c = null;
	    
        selection = dbHelper.COLUMN_ROUTINES_ID + " = " + r_id;
        
	    	
	
	      
	      c = db.query(dbHelper.DB_TABLE_ROUTINES, null, selection, null, null, null,
	          null);
	      
	      if (c != null) {
	          if (c.moveToFirst()) {
	            
	            do {
	              
	            	return c.getString(c.getColumnIndex(dbHelper.COLUMN_ROUTINES_NAME));
	              
	              

	            } while (c.moveToNext());
	          }
	          c.close();
	        };
	        
	        return "";
	        
}
	

}
