package com.rappasocial.routinemanager.free;

import com.rappasocial.routinemanager.free.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LatinMenuActivity extends Activity implements OnClickListener {

	ExtendedApplication extApp;
	Button btMenuSamba;
	Button btMenuChaCha;
	Button btMenuRumba;
	Button btMenuPasoDoble;
	Button btMenuJive;

	public void onClick(View v) {

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
		case R.id.btMenuSamba:

			selectionArgs = new String[] { "Samba" };

			break;
		case R.id.btMenuChaCha:

			selectionArgs = new String[] { "Cha Cha Cha" };

			break;
		case R.id.btMenuRumba:

			selectionArgs = new String[] { "Rumba" };

			break;
		case R.id.btMenuPasoDoble:

			selectionArgs = new String[] { "Paso Doble" };

			break;
		case R.id.btMenuJive:

			selectionArgs = new String[] { "Jive" };

			break;

		}

		//

		selection = DBHelper.DB_TABLE_DANCES + "."
				+ DBHelper.COLUMN_DANCES_NAME + " = ?";

		c = extApp.db.query(DBHelper.DB_TABLE_DANCES, null, selection,
				selectionArgs, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {

				do {
					Dance locCurDance = new Dance(
							c.getInt(c
									.getColumnIndex(DBHelper.COLUMN_DANCES_ID)),
							c.getString(c
									.getColumnIndex(DBHelper.COLUMN_DANCES_NAME)));
					extApp.setcurrentDance(locCurDance);

				} while (c.moveToNext());
			}
			c.close();
		}
		;

		Intent intent = new Intent(LatinMenuActivity.this,
				RoutinesListActivity.class);
		startActivity(intent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.latin_menu);

		extApp = (ExtendedApplication) getApplicationContext();

		btMenuSamba = (Button) findViewById(R.id.btMenuSamba);
		btMenuSamba.setOnClickListener(this);
		btMenuChaCha = (Button) findViewById(R.id.btMenuChaCha);
		btMenuChaCha.setOnClickListener(this);
		btMenuRumba = (Button) findViewById(R.id.btMenuRumba);
		btMenuRumba.setOnClickListener(this);
		btMenuPasoDoble = (Button) findViewById(R.id.btMenuPasoDoble);
		btMenuPasoDoble.setOnClickListener(this);
		btMenuJive = (Button) findViewById(R.id.btMenuJive);
		btMenuJive.setOnClickListener(this);


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
				+ DBHelper.DB_TABLE_ROUTINES + " as r LEFT JOIN "
				+ DBHelper.DB_TABLE_DANCES + " as d " + " ON r."
				+ DBHelper.COLUMN_ROUTINES_DANCE_ID + " = d."
				+ DBHelper.COLUMN_DANCES_ID + "  WHERE d."
				+ DBHelper.COLUMN_DANCES_NAME + "=?";
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
		
		btMenuSamba.setText(getResources().getString(R.string.btMenuSamba)
				+ " (" + GetCountRoutines(DBHelper.Samba) + ")");

		btMenuChaCha.setText(getResources().getString(R.string.btMenuChaCha)
				+ " (" + GetCountRoutines(DBHelper.ChaCha) + ")");

		btMenuRumba.setText(getResources().getString(R.string.btMenuRumba)
				+ " (" + GetCountRoutines(DBHelper.Rumba) + ")");

		btMenuPasoDoble.setText(getResources().getString(
				R.string.btMenuPasoDoble)
				+ " (" + GetCountRoutines(DBHelper.PasoDoble) + ")");

		btMenuJive.setText(getResources().getString(R.string.btMenuJive) + " ("
				+ GetCountRoutines(DBHelper.Jive) + ")");

	}

}
