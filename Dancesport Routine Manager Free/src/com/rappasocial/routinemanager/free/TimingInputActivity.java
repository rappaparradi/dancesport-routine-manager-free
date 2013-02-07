package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import com.rappasocial.routinemanager.free.R;
import com.rappasocial.routinemanager.free.SelectFiguresActivity.FiguresSelectionAdapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TimingInputActivity extends Activity implements OnClickListener {
	
	
	
	private Button mBSpace, mBdone, mBack, mBChange, mNum;
	private Button mB[] = new Button[12];
	EditText etTimingDialog;
	ExtendedApplication extApp;
	

	
	 public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.input_timing_field);

		  
			extApp = (ExtendedApplication)getApplicationContext();
			setKeys();
		   
			etTimingDialog = (EditText) findViewById(R.id.etTimingDialog);
			etTimingDialog.append(extApp.editTimingBuffer);
//		    btSubmitSelectedFigures = (Button) findViewById(R.id.btSubmitSelectedFigures);
//		    btSubmitSelectedFigures.setOnClickListener(this);
		    
//          chbFigureSelect
//		    // получаем массив из файла ресурсов
//		    names = getResources().getStringArray(R.array.names);
		  }
	 
	 

	public void onClick(View v) {
		
		if (v == mBdone) {
			
			extApp.editTimingBuffer = etTimingDialog.getText().toString();
			finish();

			

		} else if (v != mBdone && v != mBack && v != mBChange && v != mNum) {
			addText(v);


		} else if (v == mBack) {
			isBack(v);
//		} else if (v == btSaveRoutineRaw) {
//		    
//			RoutineRaw rrBuf = extApp.routine_rawsBufferArray.get(extApp.currentRoutineRawId);
//			rrBuf.timing = etTiming.getText().toString();
//			rrBuf.comment = etComment.getText().toString();
//			extApp.routine_rawsBufferArray.set(extApp.currentRoutineRawId, rrBuf);
//			finish();
			
		} 
		  
		  
		
	}
	
	private void setKeys() {
		
		
		mB[0] = (Button) findViewById(R.id.xA);
		mB[1] = (Button) findViewById(R.id.xAnd);
		mB[2] = (Button) findViewById(R.id.xQ);
		mB[3] = (Button) findViewById(R.id.xS);
		mB[4] = (Button) findViewById(R.id.x1);
		mB[5] = (Button) findViewById(R.id.x2);
		mB[6] = (Button) findViewById(R.id.x3);
		mB[7] = (Button) findViewById(R.id.x4);
		mB[8] = (Button) findViewById(R.id.x5);
		mB[9] = (Button) findViewById(R.id.x6);
		mB[10] = (Button) findViewById(R.id.x7);
		mB[11] = (Button) findViewById(R.id.x8);
		mBSpace = (Button) findViewById(R.id.xSpace);
		mBdone = (Button) findViewById(R.id.xDone);
		mBack = (Button) findViewById(R.id.xBack);
		
		for (int i = 0; i < mB.length; i++)
			mB[i].setOnClickListener(this);
		mBSpace.setOnClickListener(this);
		mBdone.setOnClickListener(this);
		mBack.setOnClickListener(this);


	}
	
	private void addText(View v) {

			String b = "";
			b = (String) v.getTag();
			if (b != null) {
				// adding text in Edittext
				etTimingDialog.append(b);

			}
		


	}
	
	private void isBack(View v) {
		
			CharSequence cc = etTimingDialog.getText();
			if (cc != null && cc.length() > 0) {
				{
					etTimingDialog.setText("");
					etTimingDialog.append(cc.subSequence(0, cc.length() - 1));
				}

			}
		


	}
	
	

}
