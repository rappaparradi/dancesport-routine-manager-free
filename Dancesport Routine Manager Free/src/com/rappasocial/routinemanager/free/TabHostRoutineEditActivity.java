package com.rappasocial.routinemanager.free;

import java.util.ArrayList;
import java.util.List;

import com.mobeta.android.dslv.DragSortListView;
import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHostRoutineEditActivity extends TabActivity {
	/** Called when the activity is first created. */
	ExtendedApplication extApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		extApp = (ExtendedApplication) getApplicationContext();
		Bundle extras = getIntent().getExtras(); 
		   if(extras !=null)
		   {
			   
			   extApp.currentRoutineid = extras.getInt("cur_routine_id");
		   }

		setContentView(R.layout.tabhost_routine_edit);
		
		   LayoutParams params = new LinearLayout.LayoutParams(
			    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.5f);
			 
			  
			  TabHost.TabSpec spec1;
			  TabHost.TabSpec spec2;
			  Intent intent;
			  LayoutInflater inflater;
			  ImageView mlabel, wlabel;

		TabHost tabHost = getTabHost();
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// Tab for Photos
//		TabSpec mansPartTab = tabHost.newTabSpec("Man's");
//		// setting Title and Icon for the Tab
//		mansPartTab.setIndicator("",
//				getResources().getDrawable(R.drawable.sex_male));
//		Intent mansPartIntent = new Intent(this, RoutineEditActivity.class);
//		mansPartIntent.putExtra("gender", 1);
//		mansPartTab.setContent(mansPartIntent);

		// Tab for Songs
		View mansPartTab = inflater.inflate(R.layout.tabs_bg, getTabWidget(), false);
		// Mainly used to set the weight on the tab so each is equally wide
		mansPartTab.setLayoutParams(params);
		// Add some text to the tab
		mlabel = (ImageView) mansPartTab.findViewById(R.id.tabsIcon);
		mlabel.setImageDrawable(getResources().getDrawable(R.drawable.sex_male));
		// Show a thick line under the selected tab (there are many ways to show
		// which tab is selected, I chose this)
//		divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
//		divider.setVisibility(View.VISIBLE);
		// Intent whose generated content will be added to the tab content area
//		intent = new Intent(TabTutorialActivity.this, TabContentActivity.class);
		
		// Just some data for the tab content activity to use (just for demonstrating changing content)
//		intent.putExtra("content", "Content for HOME");
		Intent mansPartIntent = new Intent(this, RoutineEditActivity.class);
		mansPartIntent.putExtra("gender", 1);
//		mansPartTab.setContent(mansPartIntent);
		// Finalize the tabs specification
		spec1 = tabHost.newTabSpec("Man's").setIndicator(mansPartTab).setContent(mansPartIntent);
		// Add the tab to the tab manager
		tabHost.addTab(spec1);
		
//		TabSpec womansPartTab = tabHost.newTabSpec("Lady's");
//		womansPartTab.setIndicator("",
//				getResources().getDrawable(R.drawable.sex_female));
//
//		Intent womansPartIntent = new Intent(this, RoutineEditActivity.class);
//		womansPartIntent.putExtra("gender", 0);
//		womansPartTab.setContent(womansPartIntent);
		
		View womansPartTab = inflater.inflate(R.layout.tabs_bg, getTabWidget(), false);
		// Mainly used to set the weight on the tab so each is equally wide
		womansPartTab.setLayoutParams(params);
		// Add some text to the tab
		wlabel = (ImageView) womansPartTab.findViewById(R.id.tabsIcon);
		wlabel.setImageDrawable(getResources().getDrawable(R.drawable.sex_female));
		// Show a thick line under the selected tab (there are many ways to show
		// which tab is selected, I chose this)
//		divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
//		divider.setVisibility(View.VISIBLE);
		// Intent whose generated content will be added to the tab content area
//		intent = new Intent(TabTutorialActivity.this, TabContentActivity.class);
		
		// Just some data for the tab content activity to use (just for demonstrating changing content)
//		intent.putExtra("content", "Content for HOME");
		Intent womansPartIntent = new Intent(this, RoutineEditActivity.class);
		womansPartIntent.putExtra("gender", 0);
//		mansPartTab.setContent(mansPartIntent);
		// Finalize the tabs specification
		spec2 = tabHost.newTabSpec("Lady's").setIndicator(womansPartTab).setContent(womansPartIntent);

		// Adding all TabSpec to TabHost
//		tabHost.addTab(mansPartTab); // Adding photos tab
		tabHost.addTab(spec2); // Adding songs tab
		
		////!!!!!!
		int heightValue = 30;
	     //loop through the TabWidget's child Views (the tabs) and set their height value.
	     for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
	          tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (heightValue * this.getResources().getDisplayMetrics().density);
	     }
		

//		Display display = getWindowManager().getDefaultDisplay();
//		int width = display.getWidth();
//
//		tabHost.getTabWidget()
//				.getChildAt(0)
//				.setLayoutParams(
//						new LinearLayout.LayoutParams((width / 2) - 2, 50));
//		tabHost.getTabWidget()
//				.getChildAt(1)
//				.setLayoutParams(
//						new LinearLayout.LayoutParams((width / 2) - 2, 50));

		getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// ExtendedApplication extApp = (ExtendedApplication)
				// getApplicationContext();
				// extApp.currentGender = 1;
				getTabHost().setCurrentTab(0);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.mans_part);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				Animation animRotate = AnimationUtils.loadAnimation(TabHostRoutineEditActivity.this,
						R.anim.anim_rotate);
				((ViewGroup)v).getChildAt(0).startAnimation(animRotate);

			}
		});

		getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// ExtendedApplication extApp = (ExtendedApplication)
				// getApplicationContext();
				// extApp.currentGender = 0;
				getTabHost().setCurrentTab(1);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.womans_part);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				Animation animRotate = AnimationUtils.loadAnimation(TabHostRoutineEditActivity.this,
						R.anim.anim_rotate);
				((ViewGroup)v).getChildAt(0).startAnimation(animRotate);

			}
		});
		SharedPrefs prefvar = new SharedPrefs();
		if (Integer.parseInt(prefvar.getSex(this)) == 0) {
			
			
			getTabHost().setCurrentTab(1);
			
			
		}
		
	}

	private void exit() {
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
 
			if (extApp.isRoutineModified == true) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						TabHostRoutineEditActivity.this);

				// set title
				alertDialogBuilder.setTitle(R.string.saving);

				// set dialog message
				alertDialogBuilder
						.setMessage(R.string.saving_question)
						.setCancelable(true)
						.setPositiveButton(R.string.Yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										SaveTheRoutineRaws();
										ContentValues cv = new ContentValues();

										cv.put(extApp.dbHelper.COLUMN_ROUTINES_MODIFIED_ON,
												System.currentTimeMillis());

										extApp.db.update(extApp.dbHelper.DB_TABLE_ROUTINES, cv, extApp.dbHelper.COLUMN_ROUTINES_ID + "=" + extApp.currentRoutineid, null);
										Context context = getApplicationContext();
										CharSequence text = getString(R.string.Routine) + " '"
												+ extApp.getRoutineNameByID(extApp.currentRoutineid) + "' "
												+ getString(R.string.ismodified);
										int duration = Toast.LENGTH_SHORT;
										Toast.makeText(context, text, duration).show();
										extApp.isRoutineModified = false;
//										context = getApplicationContext();
//										text = getString(R.string.Saved);
//										duration = Toast.LENGTH_SHORT;
//										Toast.makeText(context, text, duration)
//												.show();
										exit();

									}
								})
						.setNegativeButton(R.string.No,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										Context context = getApplicationContext();
										CharSequence text = getString(R.string.DidNotSave);
										int duration = Toast.LENGTH_SHORT;
										Toast.makeText(context, text, duration)
												.show();
										extApp.mRoutine_rawsBufferArray.clear();
										extApp.wRoutine_rawsBufferArray.clear();
										exit();

									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			} else {
				extApp.mRoutine_rawsBufferArray.clear();
				extApp.wRoutine_rawsBufferArray.clear();
				exit();
//				return super.onKeyDown(keyCode, event);
			}

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void SaveTheRoutineRaws() {

		extApp.db.delete(extApp.dbHelper.DB_TABLE_ROUTINE_RAWS,
				extApp.dbHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID + "="
						+ extApp.currentRoutineid, null);
		ContentValues cv = new ContentValues();

		ArrayList<RoutineRaw> routine_raws = extApp.mRoutine_rawsBufferArray;

		for (int i = 0; i < routine_raws.size(); i++) {

			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID,
					extApp.currentRoutineid);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_FIGURE_ID,
					routine_raws.get(i).figure_id);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_WEIGHT, i);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_TIMING,
					routine_raws.get(i).timing);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_COMMENT,
					routine_raws.get(i).comment);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_GENDER,
					routine_raws.get(i).gender);
			extApp.db.insert(extApp.dbHelper.DB_TABLE_ROUTINE_RAWS, null, cv);

		}

		routine_raws = extApp.wRoutine_rawsBufferArray;

		for (int i = 0; i < routine_raws.size(); i++) {

			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID,
					extApp.currentRoutineid);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_FIGURE_ID,
					routine_raws.get(i).figure_id);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_WEIGHT, i);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_TIMING,
					routine_raws.get(i).timing);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_COMMENT,
					routine_raws.get(i).comment);
			cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_GENDER,
					routine_raws.get(i).gender);
			extApp.db.insert(extApp.dbHelper.DB_TABLE_ROUTINE_RAWS, null, cv);

		}

		extApp.mRoutine_rawsBufferArray.clear();
		extApp.wRoutine_rawsBufferArray.clear();

	}

}
