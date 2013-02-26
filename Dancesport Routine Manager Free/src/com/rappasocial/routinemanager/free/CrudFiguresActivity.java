package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.rappasocial.routinemanager.free.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class CrudFiguresActivity extends Activity implements OnClickListener,
		OnEditorActionListener {

	ListView lvMain;
	Button btSubmitSelectedFigures, btButton_clear;
	SparseBooleanArray sbArray;
	ArrayList<Figure> figures;
	FiguresSelectionAdapter boxAdapter;
	ExtendedApplication extApp;
	TextView tvCurDance;
	EditText etInputSearchFigure;
	LinearLayout llRoutinesListActionPanel;
	Button btAddNewFigure, btRLback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crud_figures_list);

		lvMain = (ListView) findViewById(R.id.lvSelectFigures);

		// lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		figures = new ArrayList<Figure>();
		// sbArray = new SparseBooleanArray();
		extApp = (ExtendedApplication) getApplicationContext();

		fillData();
		boxAdapter = new FiguresSelectionAdapter(this, figures, sbArray);
		lvMain.setAdapter(boxAdapter);
		// btSubmitSelectedFigures = (Button)
		// findViewById(R.id.btSubmitSelectedFigures);
		// btSubmitSelectedFigures.setOnClickListener(this);
		btButton_clear = (Button) findViewById(R.id.btButton_clear);
		btButton_clear.setOnClickListener(this);

		etInputSearchFigure = (EditText) findViewById(R.id.etInputSearchFigure);
		etInputSearchFigure.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				CrudFiguresActivity.this.boxAdapter.getFilter().filter(cs);

			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		
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
		
		etInputSearchFigure.setOnEditorActionListener(this);
		
		btAddNewFigure = (Button) findViewById(R.id.btAddNewFigure);

		btAddNewFigure.setOnClickListener(this);
		
		btRLback = (Button) findViewById(R.id.btRLback);

		btRLback.setOnClickListener(this);

		Dance curDance = extApp.getcurrentDance();

		llRoutinesListActionPanel = (LinearLayout) findViewById(R.id.llRoutinesListActionPanel);

		TextView tvCurDanceChar = (TextView) findViewById(R.id.tvCurDanceChar);

		if ((curDance.name).compareToIgnoreCase(DBHelper.Samba) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_orange));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_orange));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_orange));
			tvCurDanceChar.setText("S");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.ChaCha) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_blue));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_blue));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_blue));
			tvCurDanceChar.setText("Ch");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Rumba) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_purple));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_purple));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_purple));
			tvCurDanceChar.setText("R");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.PasoDoble) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_red));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_red));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_red));
			tvCurDanceChar.setText("P");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Jive) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_yellow));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_yellow));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_yellow));
			tvCurDanceChar.setText("J");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Waltz) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_orange));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_orange));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_orange));
			tvCurDanceChar.setText("W");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Tango) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_red));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_red));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_red));
			tvCurDanceChar.setText("T");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.VienneseWaltz) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_purple));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_purple));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_purple));
			tvCurDanceChar.setText("V");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Foxtrot) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_blue));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_blue));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_blue));
			tvCurDanceChar.setText("F");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.Quickstep) == 0) {

			llRoutinesListActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_yellow));
			btAddNewFigure.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_yellow));
			btRLback.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.custom_button_yellow));
			tvCurDanceChar.setText("Q");

		}
		
		TextView tvWindowTitle = (TextView) findViewById(R.id.tvWindowTitle);
		tvWindowTitle.setText(this.getString(R.string.win_title_figures));
		// chbFigureSelect
		// // получаем массив из файла ресурсов
		// names = getResources().getStringArray(R.array.names);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		figures.clear();
		fillData();
		boxAdapter.notifyDataSetChanged();

	}

	void fillData() {

		// переменные для query
		// String[] columns = null;
		String selection = null;
		// String[] selectionArgs = null;
		// String groupBy = null;
		// String having = null;
		String orderBy = DBHelper.COLUMN_FIGURES_NAME;

		// курсор
		Cursor c = null;
		Dance curDance = extApp.getcurrentDance();
		if (curDance != null) {

			selection = DBHelper.COLUMN_FIGURES_DANCE_ID + " = "
					+ curDance.id;

		} else {

			selection = null;
		}

		c = extApp.db.query(DBHelper.DB_TABLE_FIGURES, null, selection,
				null, null, null, orderBy);

		if (c != null) {
			if (c.moveToFirst()) {

				do {

					figures.add(new Figure(
							c.getInt(c
									.getColumnIndex(DBHelper.COLUMN_FIGURES_ID)),
							c.getString(c
									.getColumnIndex(DBHelper.COLUMN_FIGURES_NAME)),
							curDance.id,
							c.getString(c
									.getColumnIndex(DBHelper.COLUMN_FIGURES_DESCRIPTION))));

				} while (c.moveToNext());
			}
			c.close();
		}
		;

		// dbHelper.close();

	}
	
	boolean isFigureUsed(int figure_id) {

		// переменные для query
		// String[] columns = null;
		String selection = null;
		// String[] selectionArgs = null;
		// String groupBy = null;
		// String having = null;
		//String orderBy = extApp.dbHelper.COLUMN_FIGURES_NAME;

		// курсор
		Cursor c = null;
		Dance curDance = extApp.getcurrentDance();
		if (curDance != null) {

			selection = DBHelper.COLUMN_ROUTINE_RAWS_FIGURE_ID + " = "
					+ figure_id;

		} else {

			selection = null;
		}

		c = extApp.db.query(DBHelper.DB_TABLE_ROUTINE_RAWS, null, selection,
				null, null, null, null);

		if (c != null) {
			
			if (!c.moveToFirst()){
				
				return false;
				
			} else {
				
				return true;
			}
			
		} else return false;

		// dbHelper.close();

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btAddNewFigure:

			Intent intent = new Intent(CrudFiguresActivity.this,
					AddFigureActivity.class);
			intent.putExtra("editmode", false);
			startActivity(intent);
			break;
		case R.id.btButton_clear:

			etInputSearchFigure.setText("");

			break;
			
		case R.id.btRLback:

			onBackPressed();
			break;

		}

	}

	public class FiguresSelectionAdapter extends BaseAdapter implements
			OnClickListener, Filterable {
		Context ctx;
		LayoutInflater lInflater;
		ArrayList<Figure> objects;
		ArrayList<Figure> Originalobjects;
		ExtendedApplication extApp;
		CheckBox chbFigureSelect;
		Button btSubmitSelectedFigures, btButton_clear;
		// SparseBooleanArray sbArray;
		LinearLayout llSelectFiguresClickable;

		FiguresSelectionAdapter(Context context, ArrayList<Figure> figures,
				SparseBooleanArray _sbArray) {
			ctx = context;
			objects = figures;
			lInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// sbArray = _sbArray;
			extApp = (ExtendedApplication) ctx.getApplicationContext();

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

		public Filter getFilter() {

			Filter filter = new Filter() {

				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {

					objects = (ArrayList<Figure>) results.values;
					figures = (ArrayList<Figure>) results.values;
					// CrudFiguresActivity.this.sbArray.clear();
					CrudFiguresActivity.this.boxAdapter.notifyDataSetChanged();
				}

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {

					FilterResults results = new FilterResults();
					ArrayList<Figure> FilteredArrayfigures = new ArrayList<Figure>();

					if (Originalobjects == null) {
						Originalobjects = new ArrayList<Figure>(objects);
					}
					if (constraint == null || constraint.length() == 0) {
						results.count = Originalobjects.size();
						results.values = Originalobjects;
					} else {
						constraint = constraint.toString().toLowerCase();
						for (int i = 0; i < Originalobjects.size(); i++) {
							Figure dataNames = Originalobjects.get(i);
							if (dataNames.name.toLowerCase().contains(
									constraint.toString())) {
								FilteredArrayfigures.add(dataNames);
							}
						}

						results.count = FilteredArrayfigures.size();

						results.values = FilteredArrayfigures;

					}

					return results;
				}
			};

			return filter;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View view = convertView;
			if (view == null) {
				view = lInflater.inflate(R.layout.crud_figures_item, parent,
						false);
			}

			Figure p = getFigure(position);
			// chbFigureSelect = (CheckBox) view
			// .findViewById(R.id.chbFigureSelect);
			// chbFigureSelect.setTag(position);
			// chbFigureSelect.setOnClickListener(this);
			Button btEditRoutine = (Button) view
					.findViewById(R.id.btEditRoutine);
			btEditRoutine.setTag(position);
			btEditRoutine.setOnClickListener(this);

			ImageButton btDeleteRoutine = (ImageButton) view
					.findViewById(R.id.btDeleteRoutine);
			btDeleteRoutine.setTag(position);
			btDeleteRoutine.setOnClickListener(this);

			llSelectFiguresClickable = (LinearLayout) view
					.findViewById(R.id.llSelectFiguresClickable);
			llSelectFiguresClickable.setTag(position);
			llSelectFiguresClickable.setOnClickListener(this);

			

			((TextView) view.findViewById(R.id.tvFigureName)).setText(p.name);

			// chbFigureSelect.setChecked(sbArray.get(position));

			return view;
		}

		Figure getFigure(int position) {
			return ((Figure) getItem(position));
		}

		public void onClick(View arg0) {

			switch (arg0.getId()) {

			case R.id.btEditRoutine:
				Intent intent = new Intent(CrudFiguresActivity.this,
						AddFigureActivity.class);
				intent.putExtra("editmode", true);

				intent.putExtra("figure_name_buff",
						figures.get((Integer) arg0.getTag()).name);
				intent.putExtra("cur_figure_id",
						figures.get((Integer) arg0.getTag()).id);
				intent.putExtra("figure_descr_buff",
						figures.get((Integer) arg0.getTag()).description);

				ctx.startActivity(intent);

				break;
			case R.id.btDeleteRoutine:
				Animation animRotate = AnimationUtils.loadAnimation(ctx,
						R.anim.anim_scale);
				arg0.startAnimation(animRotate);
				
				this.extApp.currentRoutineid = figures.get((Integer) arg0.getTag()).id; // /Very
				this.extApp.currentRoutineRawId = (Integer) arg0.getTag(); // very
				// KRUTO
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CrudFiguresActivity.this);

				// set title
				alertDialogBuilder.setTitle(R.string.deleting);

				// set dialog message
				alertDialogBuilder
						.setMessage(R.string.deleting_question)
						.setCancelable(true)
						.setPositiveButton(R.string.Yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										if (isFigureUsed(extApp.currentRoutineid)) {
											Context context = getApplicationContext();
											CharSequence text = getString(R.string.thefigure_is_used);
											int duration = Toast.LENGTH_SHORT;
											Toast.makeText(context, text, duration)
													.show();
										}
										else {
										DeleteCurrentFigure();
										Context context = getApplicationContext();
										CharSequence text = getString(R.string.Deleted);
										int duration = Toast.LENGTH_SHORT;
										Toast.makeText(context, text, duration)
												.show();}

									}
								})
						.setNegativeButton(R.string.No,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
				break;
			case R.id.llSelectFiguresClickable:

				intent = new Intent(CrudFiguresActivity.this,
						AddFigureActivity.class);
				intent.putExtra("editmode", true);

				intent.putExtra("figure_name_buff",
						figures.get((Integer) arg0.getTag()).name);
				intent.putExtra("cur_figure_id",
						figures.get((Integer) arg0.getTag()).id);
				intent.putExtra("figure_descr_buff",
						figures.get((Integer) arg0.getTag()).description);

				ctx.startActivity(intent);
				this.notifyDataSetChanged();
				break;

			}

		}

	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
			// hide virtual keyboard
			InputMethodManager imm = (InputMethodManager) getBaseContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etInputSearchFigure.getWindowToken(), 0);
			return true;
		}
		return false;

	}
	
	void DeleteCurrentFigure() {
		
		

		extApp.db.delete(DBHelper.DB_TABLE_FIGURES,
				DBHelper.COLUMN_FIGURES_ID + "="
						+ extApp.currentRoutineid, null);
		
		figures.remove(extApp.currentRoutineRawId);
		boxAdapter.notifyDataSetChanged();

	}

}
