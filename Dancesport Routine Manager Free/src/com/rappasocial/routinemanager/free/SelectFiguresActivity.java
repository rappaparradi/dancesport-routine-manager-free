package com.rappasocial.routinemanager.free;

import java.util.ArrayList;

import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SelectFiguresActivity extends Activity implements OnClickListener,OnEditorActionListener {

	ListView lvMain;
	Button btSubmitSelectedFigures, btButton_clear;
	SparseBooleanArray sbArray;
	ArrayList<Figure> figures;
	FiguresSelectionAdapter boxAdapter;
	ExtendedApplication extApp;
	TextView tvCurDance;
	EditText etInputSearchFigure;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_figures_list);

		lvMain = (ListView) findViewById(R.id.lvSelectFigures);

		lvMain.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

		figures = new ArrayList<Figure>();
		sbArray = new SparseBooleanArray();
		extApp = (ExtendedApplication) getApplicationContext();

		fillData();
		boxAdapter = new FiguresSelectionAdapter(this, figures, sbArray);
		lvMain.setAdapter(boxAdapter);
		btSubmitSelectedFigures = (Button) findViewById(R.id.btSubmitSelectedFigures);
		btSubmitSelectedFigures.setOnClickListener(this);
		btButton_clear = (Button) findViewById(R.id.btButton_clear);
		btButton_clear.setOnClickListener(this);
		
		etInputSearchFigure = (EditText) findViewById(R.id.etInputSearchFigure);
		etInputSearchFigure.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				SelectFiguresActivity.this.boxAdapter.getFilter().filter(cs);
				
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		etInputSearchFigure.setOnEditorActionListener(this);
		// chbFigureSelect
		// // получаем массив из файла ресурсов
		// names = getResources().getStringArray(R.array.names);
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
							curDance.id));

				} while (c.moveToNext());
			}
			c.close();
		}
		;

		// dbHelper.close();

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btSubmitSelectedFigures:
			extApp.figuresSelectionBufferArray.clear();
			for (int i = 0; i < sbArray.size(); i++) {
				int key = sbArray.keyAt(i);
				if (sbArray.get(key)) {
					extApp.figuresSelectionBufferArray.add(figures.get(key));
				}
			}
			this.finish();
			break;
		case R.id.btButton_clear:
			
			etInputSearchFigure.setText("");

			
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
		SparseBooleanArray sbArray;
		LinearLayout llSelectFiguresClickable;

		FiguresSelectionAdapter(Context context, ArrayList<Figure> figures,
				SparseBooleanArray _sbArray) {
			ctx = context;
			objects = figures;
			lInflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			sbArray = _sbArray;
			extApp = (ExtendedApplication)ctx.getApplicationContext();

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
					SelectFiguresActivity.this.sbArray.clear();
					SelectFiguresActivity.this.boxAdapter.notifyDataSetChanged();
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
				view = lInflater.inflate(R.layout.select_figures_item, parent,
						false);
			}

			Figure p = getFigure(position);
			chbFigureSelect = (CheckBox) view
					.findViewById(R.id.chbFigureSelect);
			chbFigureSelect.setTag(position);
			chbFigureSelect.setOnClickListener(this);
			
			llSelectFiguresClickable = (LinearLayout) view.findViewById(R.id.llSelectFiguresClickable);
			llSelectFiguresClickable.setTag(position);
			llSelectFiguresClickable.setOnClickListener(this);
			
			((TextView) view.findViewById(R.id.tvFigureName)).setText(p.name);

			chbFigureSelect.setChecked(sbArray.get(position));

			return view;
		}

		Figure getFigure(int position) {
			return ((Figure) getItem(position));
		}

		public void onClick(View arg0) {

			switch (arg0.getId()) {

			case R.id.chbFigureSelect:
				if (((CheckBox) arg0).isChecked())
					sbArray.put((Integer) arg0.getTag(), true);
				else
					sbArray.put((Integer) arg0.getTag(), false);
				this.notifyDataSetChanged();
				break;

			case R.id.llSelectFiguresClickable:

				if (sbArray.get((Integer) arg0.getTag()) == false)
					sbArray.put((Integer) arg0.getTag(), true);
				else
					sbArray.put((Integer) arg0.getTag(), false);
				this.notifyDataSetChanged();
				break;

			}

		}

	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
	             (event.getKeyCode()           == KeyEvent.KEYCODE_ENTER)   )
	        {               
	           // hide virtual keyboard
	           InputMethodManager imm = 
	              (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	           imm.hideSoftInputFromWindow(etInputSearchFigure.getWindowToken(), 0);
	           return true;
	        }
	        return false;
		
	}

}
