package com.rappasocial.routinemanager.free;

import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StandardMenuActivity extends Activity implements OnClickListener {

	ExtendedApplication extApp;
	Button btMenuWaltz;
	Button btMenuTango;
	Button btMenuVienneseWaltz;
	Button btMenuFoxtrot;
	Button btMenuQuickstep;

	public void onClick(View v) {
		// TODO Auto-generated method stub

		String[] columns = null;
		String selection = null;
		String[] selectionArgs = null;
		// String groupBy = null;
		// String having = null;
		// String orderBy = null;
		//
		// // курсор
		Cursor c = null;

		switch (v.getId()) {
		case R.id.btMenuWaltz:

			selectionArgs = new String[] { "Waltz" };

			break;
		case R.id.btMenuTango:

			selectionArgs = new String[] { "Tango" };

			break;
		case R.id.btMenuVienneseWaltz:

			selectionArgs = new String[] { "Viennese Waltz" };

			break;
		case R.id.btMenuFoxtrot:

			selectionArgs = new String[] { "Foxtrot" };

			break;
		case R.id.btMenuQuickstep:

			selectionArgs = new String[] { "Quickstep" };

			break;

		}

		//

		selection = extApp.dbHelper.DB_TABLE_DANCES + "."
				+ extApp.dbHelper.COLUMN_DANCES_NAME + " = ?";

		c = extApp.db.query(extApp.dbHelper.DB_TABLE_DANCES, null, selection,
				selectionArgs, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {

				do {
					Dance locCurDance = new Dance(
							c.getInt(c
									.getColumnIndex(extApp.dbHelper.COLUMN_DANCES_ID)),
							c.getString(c
									.getColumnIndex(extApp.dbHelper.COLUMN_DANCES_NAME)));
					extApp.setcurrentDance(locCurDance);

				} while (c.moveToNext());
			}
			c.close();
		}
		;

		Intent intent = new Intent(StandardMenuActivity.this,
				RoutinesListActivity.class);
		startActivity(intent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_menu);

		extApp = (ExtendedApplication) getApplicationContext();

		btMenuWaltz = (Button) findViewById(R.id.btMenuWaltz);
		btMenuWaltz.setOnClickListener(this);
		btMenuTango = (Button) findViewById(R.id.btMenuTango);
		btMenuTango.setOnClickListener(this);
		btMenuVienneseWaltz = (Button) findViewById(R.id.btMenuVienneseWaltz);
		btMenuVienneseWaltz.setOnClickListener(this);
		btMenuFoxtrot = (Button) findViewById(R.id.btMenuFoxtrot);
		btMenuFoxtrot.setOnClickListener(this);
		btMenuQuickstep = (Button) findViewById(R.id.btMenuQuickstep);
		btMenuQuickstep.setOnClickListener(this);
		
		RefrashCountRoutines();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		RefrashCountRoutines();
		
	}
	
	public int GetCountRoutines(String DanceName) {

		String selection = null;
		// String[] selectionArgs = null;
		// String groupBy = null;
		// String having = null;
		// String orderBy = null;

		// курсор
		Cursor c = null;

		final String SQL_STATEMENT = "SELECT COUNT(*) FROM "
				+ extApp.dbHelper.DB_TABLE_ROUTINES + " as r LEFT JOIN "
				+ extApp.dbHelper.DB_TABLE_DANCES + " as d " + " ON r."
				+ extApp.dbHelper.COLUMN_ROUTINES_DANCE_ID + " = d."
				+ extApp.dbHelper.COLUMN_DANCES_ID + "  WHERE d."
				+ extApp.dbHelper.COLUMN_DANCES_NAME + "=?";
		c = extApp.db.rawQuery(SQL_STATEMENT, new String[] { DanceName });

		if (c != null) {
			if (c.moveToFirst()) {

				do {

					return c.getInt(0);

				} while (c.moveToNext());
			}
			c.close();
		}

		return 0;

	}

	public void RefrashCountRoutines() {
		
		btMenuWaltz.setText(getResources().getString(R.string.btMenuWaltz)
				+ " (" + GetCountRoutines(extApp.dbHelper.Waltz) + ")");

		btMenuTango.setText(getResources().getString(R.string.btMenuTango)
				+ " (" + GetCountRoutines(extApp.dbHelper.Tango) + ")");

		btMenuVienneseWaltz.setText(getResources().getString(R.string.btMenuVienneseWaltz)
				+ " (" + GetCountRoutines(extApp.dbHelper.VienneseWaltz) + ")");

		btMenuFoxtrot.setText(getResources().getString(
				R.string.btMenuFoxtrot)
				+ " (" + GetCountRoutines(extApp.dbHelper.Foxtrot) + ")");

		btMenuQuickstep.setText(getResources().getString(R.string.btMenuQuickstep) + " ("
				+ GetCountRoutines(extApp.dbHelper.Quickstep) + ")");

	}

}
