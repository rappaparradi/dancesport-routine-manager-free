package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RoutineRawEditActivity extends Activity implements OnTouchListener, OnClickListener,
OnFocusChangeListener {
	
	ExtendedApplication extApp;
	EditText etTiming;
	EditText etComment;
	Button btSaveRoutineRaw;
	TextView tvCurFigureName;
	private Button mBack;
	private boolean isEdit = true;


	
	public void onClick(View v) {
		
		
	    if (v == mBack) {
			isBack(v);
		} else if (v == btSaveRoutineRaw) {
		     
			ArrayList<RoutineRaw> Localroutine_rawsBufferArray;
			if (extApp.currentGender == 1) {
				Localroutine_rawsBufferArray = this.extApp.mRoutine_rawsBufferArray;
			} else {

				Localroutine_rawsBufferArray = this.extApp.wRoutine_rawsBufferArray;
			}
			RoutineRaw rrBuf = Localroutine_rawsBufferArray.get(extApp.currentRoutineRawId);
			rrBuf.timing = etTiming.getText().toString();
			rrBuf.comment = etComment.getText().toString();
			Localroutine_rawsBufferArray.set(extApp.currentRoutineRawId, rrBuf);
			extApp.isRoutineModified = true;
			finish();
			
		} 
		

		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			setContentView(R.layout.edit_routine_raw);
		
			extApp = (ExtendedApplication)getApplicationContext();
		
			etTiming = (EditText) findViewById(R.id.etTiming);
			etComment = (EditText) findViewById(R.id.etComment);
			tvCurFigureName = (TextView) findViewById(R.id.tvCurFigureName);
			btSaveRoutineRaw = (Button) findViewById(R.id.btSaveRoutineRaw);
			btSaveRoutineRaw.setOnClickListener(this);
		
			etTiming.setOnTouchListener(this);
			
			etTiming.setText(extApp.editTimingBuffer);
			etComment.setText(extApp.editCommentBuffer);
//			etTiming.setOnClickListener(this);
//			etTiming.setOnFocusChangeListener(this);
		
//			setFrow();
//			setSrow();
//			setTrow();
//			setForow();
		
			//request TEST ads to avoid being disabled for clicking your own ads
	        AdRequest adRequest = new AdRequest();
	 
	        
	        
	        //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
	        adRequest.addTestDevice(Secure.getString(this.getContentResolver(),
	                Secure.ANDROID_ID));
	 
	        AdView adView = (AdView)findViewById(R.id.ad);      
	 
	        // Initiate a request to load an ad in test mode.
	        // You can keep this even when you release your app on the market, because
	        // only emulators and your test device will get test ads. The user will receive real ads.
	        adView.loadAd(adRequest);
		
	
		
	}
	

	
	public boolean onTouch(View v, MotionEvent event) {
	
		if (event.getAction() == MotionEvent.ACTION_DOWN && v == etTiming) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            extApp.editTimingBuffer = etTiming.getText().toString();
            Intent intent = new Intent(this, TimingInputActivity.class);
            this.startActivity(intent);
            return true;

		}

		return true;
	}
	

		

		
		public void onFocusChange(View v, boolean hasFocus) {
			if (v == etTiming && hasFocus == true) {
				isEdit = true;


			} else {
				
				isEdit = false;
				
			}

		}
		
		private void isBack(View v) {
			if (isEdit == true) {
				CharSequence cc = etTiming.getText();
				if (cc != null && cc.length() > 0) {
					{
						etTiming.setText("");
						etTiming.append(cc.subSequence(0, cc.length() - 1));
					}

				}
			}

	
		}
		
		@Override
	 	protected void onResume() {
	 		super.onResume();
	 		etTiming.setText(extApp.editTimingBuffer);
	 		extApp.editTimingBuffer = "";
	 	
	 		
	 	}

}
