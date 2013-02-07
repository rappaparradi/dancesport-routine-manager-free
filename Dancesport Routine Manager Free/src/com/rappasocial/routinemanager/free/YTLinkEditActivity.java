package com.rappasocial.routinemanager.free;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YTLinkEditActivity extends Activity implements  OnClickListener {
	
	ExtendedApplication extApp;
	EditText etYTlink;
	EditText etComment;
	Button btSaveYT, btPlay;
	TextView tvCurFigureName;
	
	int cur_routine_id;
	
	
	
	public void onClick(View v) {
		
		
	if (v == btSaveYT) {
		
		String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

		Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = compiledPattern.matcher(etYTlink
				.getText().toString());
		if (!matcher.find() && (etYTlink.getText().toString() != null && etYTlink.getText().toString().trim().length() != 0)) {
			CharSequence text = "Неверная ссылка";
			int duration = Toast.LENGTH_SHORT;
			Toast.makeText(this, text, duration)
					.show();
		} else {
		     
			ContentValues cv = new ContentValues();

			cv.put(extApp.dbHelper.COLUMN_ROUTINES_YT_ID, etYTlink
					.getText().toString());


			cv.put(extApp.dbHelper.COLUMN_ROUTINES_MODIFIED_ON,
					System.currentTimeMillis());

			extApp.db.update(extApp.dbHelper.DB_TABLE_ROUTINES, cv, extApp.dbHelper.COLUMN_ROUTINES_ID + "=" + this.cur_routine_id, null);
//			Context context = getApplicationContext();
//			CharSequence text = getString(R.string.Routine) + " '"
//					+ etRoutinesName.getText().toString() + "' "
//					+ getString(R.string.iscreated);
//			int duration = Toast.LENGTH_SHORT;
//			Toast.makeText(context, text, duration).show();
			finish();}
			
			
		} else if (v == btPlay) {
			
			
				
				startActivity(new Intent(Intent.ACTION_VIEW, 
	                    Uri.parse(etYTlink
	        					.getText().toString())));
				finish();
			
		}  
		

		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			setContentView(R.layout.edit_yt_link);
			Bundle extras = getIntent().getExtras(); 
			   if(extras !=null)
			   {
			
				   this.cur_routine_id = extras.getInt("cur_routine_id");
			   }
			
			
		
			extApp = (ExtendedApplication)getApplicationContext();
		
			etYTlink = (EditText) findViewById(R.id.etYTlink);
			
			
	
			
			etYTlink.setText(extApp.editTimingBuffer);
			
			btSaveYT = (Button) findViewById(R.id.btSaveYT);
			btSaveYT.setOnClickListener(this);
			
			
			
			
//			etTiming.setOnClickListener(this);
//			etTiming.setOnFocusChangeListener(this);
			
			//setKeys();
//			setFrow();
//			setSrow();
//			setTrow();
//			setForow();
		
			
		
	
		
	}
	
	
	
	
		
		@Override
	 	protected void onResume() {
	 		super.onResume();
	 		etYTlink.setText(extApp.editTimingBuffer);
	 		extApp.editTimingBuffer = "";
	 	
	 		
	 	}

}
