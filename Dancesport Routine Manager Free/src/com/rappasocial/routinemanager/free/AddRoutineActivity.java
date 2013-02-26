package com.rappasocial.routinemanager.free;

import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class AddRoutineActivity extends Activity implements OnClickListener,
		OnEditorActionListener {

	ExtendedApplication extApp;
	EditText etRoutinesName;
	Button btAddnewRoutine;
	boolean editmode; // true - edit false - add
	String routines_name_buff;
	int cur_routine_id;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btAddnewRoutine:

			String locRoutineName = etRoutinesName.getText().toString().trim();
			if (locRoutineName.length() == 0) {

				Context context = getApplicationContext();
				CharSequence text = getString(R.string.enter_the_name);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				
				return;
			}

			if (!editmode) {
				ContentValues cv = new ContentValues();

				cv.put(DBHelper.COLUMN_ROUTINES_NAME, locRoutineName);

				cv.put(DBHelper.COLUMN_ROUTINES_DANCE_ID,
						extApp.getcurrentDance().id);

				cv.put(DBHelper.COLUMN_ROUTINES_CREATED_ON,
						System.currentTimeMillis());

				cv.put(DBHelper.COLUMN_ROUTINES_MODIFIED_ON,
						System.currentTimeMillis());

				extApp.db.insert(DBHelper.DB_TABLE_ROUTINES, null, cv);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.Routine) + " '"
						+ etRoutinesName.getText().toString() + "' "
						+ getString(R.string.iscreated);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				finish();
				break;
			} else {

				ContentValues cv = new ContentValues();

				cv.put(DBHelper.COLUMN_ROUTINES_NAME, locRoutineName);

				cv.put(DBHelper.COLUMN_ROUTINES_MODIFIED_ON,
						System.currentTimeMillis());

				extApp.db.update(DBHelper.DB_TABLE_ROUTINES, cv,
						DBHelper.COLUMN_ROUTINES_ID + "="
								+ this.cur_routine_id, null);
				Context context = getApplicationContext();
				CharSequence text = getString(R.string.Routine) + " '"
						+ etRoutinesName.getText().toString() + "' "
						+ getString(R.string.ismodified);
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				finish();
			}

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_routine);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.editmode = extras.getBoolean("editmode");
			this.routines_name_buff = extras.getString("routines_name_buff");
			this.cur_routine_id = extras.getInt("cur_routine_id");
		}

		extApp = (ExtendedApplication) getApplicationContext();

		etRoutinesName = (EditText) findViewById(R.id.etRoutinesName);

		etRoutinesName.setText(this.routines_name_buff);

		btAddnewRoutine = (Button) findViewById(R.id.btAddnewRoutine);
		btAddnewRoutine.setOnClickListener(this);

		etRoutinesName.setOnEditorActionListener(this);

	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
			// hide virtual keyboard
			InputMethodManager imm = (InputMethodManager) getBaseContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etRoutinesName.getWindowToken(), 0);
			return true;
		}
		return false;

	}

}
