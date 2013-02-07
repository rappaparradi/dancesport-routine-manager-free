package com.rappasocial.routinemanager.free;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class AddFigureActivity extends Activity implements OnClickListener {

	ExtendedApplication extApp;
	EditText etFifuresName;
	EditText etFifuresDescription;
	Button btAddnewFigure;
	boolean editmode;
	int cur_figure_id;
	String figure_name_buff;
	String figure_descr_buff;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btAddnewFigure:

			String locFiguresName = etFifuresName.getText().toString().trim();
			if (locFiguresName.length() == 0) {

				Context context = getApplicationContext();
				CharSequence text = getString(R.string.enter_the_name);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();

				return;
			}

			if (!editmode) {
				ContentValues cv = new ContentValues();

				cv.put(extApp.dbHelper.COLUMN_FIGURES_NAME, locFiguresName);
				cv.put(extApp.dbHelper.COLUMN_FIGURES_DESCRIPTION,
						etFifuresDescription.getText().toString());
				cv.put(extApp.dbHelper.COLUMN_FIGURES_DANCE_ID,
						extApp.getcurrentDance().id);
				extApp.db.insert(extApp.dbHelper.DB_TABLE_FIGURES, null, cv);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.figure_added);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				finish();
				break;
			} else {

				ContentValues cv = new ContentValues();

				cv.put(extApp.dbHelper.COLUMN_FIGURES_NAME, locFiguresName);
				cv.put(extApp.dbHelper.COLUMN_FIGURES_DESCRIPTION,
						etFifuresDescription.getText().toString());
				cv.put(extApp.dbHelper.COLUMN_FIGURES_DANCE_ID,
						extApp.getcurrentDance().id);
				extApp.db.update(extApp.dbHelper.DB_TABLE_FIGURES, cv,
						extApp.dbHelper.COLUMN_FIGURES_ID + "="
								+ this.cur_figure_id, null);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.Saved);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				finish();

			}

			break;

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_figure);

		extApp = (ExtendedApplication) getApplicationContext();

		// btAddnewFigure = (Button) findViewById(R.id.btAddnewFigure);
		// btAddnewFigure.setOnClickListener(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.editmode = extras.getBoolean("editmode");
			this.figure_name_buff = extras.getString("figure_name_buff");
			this.figure_descr_buff = extras.getString("figure_descr_buff");
			this.cur_figure_id = extras.getInt("cur_figure_id");
		}

		etFifuresName = (EditText) findViewById(R.id.etFifuresName);
		etFifuresName.setText(this.figure_name_buff);
		etFifuresDescription = (EditText) findViewById(R.id.etFifuresDescription);
		etFifuresDescription.setText(this.figure_descr_buff);

		// extApp = (ExtendedApplication) getApplicationContext();
		//
		// etRoutinesName = (EditText) findViewById(R.id.etRoutinesName);
		//
		// etRoutinesName.setText(this.routines_name_buff);
		//
		btAddnewFigure = (Button) findViewById(R.id.btAddnewFigure);
		btAddnewFigure.setOnClickListener(this);

		// request TEST ads to avoid being disabled for clicking your own ads
		AdRequest adRequest = new AdRequest();

		// test mode on DEVICE (this example code must be replaced with your
		// device uniquq ID)
		adRequest.addTestDevice(Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID));

		AdView adView = (AdView) findViewById(R.id.ad);

		// Initiate a request to load an ad in test mode.
		// You can keep this even when you release your app on the market,
		// because
		// only emulators and your test device will get test ads. The user will
		// receive real ads.
		adView.loadAd(adRequest);

		//
		// etRoutinesName.setOnEditorActionListener(this);

	}

}
