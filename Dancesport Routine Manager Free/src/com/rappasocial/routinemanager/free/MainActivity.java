package com.rappasocial.routinemanager.free;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.rappasocial.routinemanager.free.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button btLatin, btStan;
	ExtendedApplication extApp;
	DBHelper dbHelper;
	SQLiteDatabase db;

	// public static final String TAG = DbExportImport.class.getName();

	/** Directory that files are to be read from and written to **/
	protected static final File DATABASE_DIRECTORY = new File(
			Environment.getExternalStorageDirectory(), "MyDirectory");

	/** File path of Db to be imported **/
	protected static final File IMPORT_FILE = new File(DATABASE_DIRECTORY,
			"MyDb.db");

	public static final String PACKAGE_NAME = "com.rappasocial.routinemanager";
	public static final String DATABASE_NAME = "routineManagerDB.db";
	public static final String DATABASE_TABLE = "entryTable";
	public static final String DB_TABLE_DANCES = "dances";
	public static final String DB_TABLE_FIGURES = "figures";
	public static final String DB_TABLE_ROUTINES = "routines";
	public static final String DB_TABLE_ROUTINE_RAWS = "routine_raws";

	/** Contains: /data/data/com.example.app/databases/example.db **/
	private static final File DATA_DIRECTORY_DATABASE = new File(
			Environment.getDataDirectory() + "/data/" + PACKAGE_NAME
					+ "/databases/" + DATABASE_NAME);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btLatin = (Button) findViewById(R.id.btMenuLatin);
		btLatin.setOnClickListener(this);
		btStan = (Button) findViewById(R.id.btMenuStandard);
		btStan.setOnClickListener(this);
		extApp = (ExtendedApplication) getApplicationContext();
		dbHelper = extApp.dbHelper;
		db = extApp.db;
		try {
			File directory = new File(Environment.getExternalStorageDirectory()
					+ "/dancesport-routine-manager-share/");
			// Create the folder if it doesn't exist:
			if (!directory.exists()) {
				directory.mkdirs();
			}
	
		} catch (Exception e) {
			

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// protected static boolean exportDb(){
	// //if( ! SdIsPresent() ) return false;
	//
	// File dbFile = DATA_DIRECTORY_DATABASE;
	// String filename = DATABASE_NAME;
	//
	// File exportDir = DATABASE_DIRECTORY;
	// File file = new File(exportDir, filename);
	//
	// if (!exportDir.exists()) {
	// exportDir.mkdirs();
	// }
	//
	// try {
	// file.createNewFile();
	// copyFile(dbFile, file);
	// return true;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.btFigures:
			// Intent intent = new Intent(MainActivity.this,
			// FiguresManagerActivity.class);
			// startActivity(intent);
			break;

		case R.id.btAddRoutine:
			Intent intent = new Intent(
					MainActivity.this,
					com.rappasocial.routinemanager.authenticator.free.AuthenticatorActivity.class);
			startActivity(intent);
			break;

		case R.id.settings_option_item:
			intent = new Intent(MainActivity.this,
					com.rappasocial.routinemanager.free.PrefActivity.class);
			startActivity(intent);
			break;
		case R.id.btExport:

			if (!hasStorage(true)) {

				Toast.makeText(MainActivity.this, this.getString(R.string.sd_card_missing),
						Toast.LENGTH_LONG).show();
				return false;
			}

			InputStream myInput;

			try {

				myInput = new FileInputStream(
						"/data/data/com.rappasocial.routinemanager/databases/routineManagerDB");// this
																								// is
				// the path for all apps
				// insert your package instead
				// packagename,ex:com.mybusiness.myapp

				// Set the output folder on the SDcard
				File directory = new File(
						Environment.getExternalStorageDirectory()
								+ "/dancesport-routine-manager-share/");
				// Create the folder if it doesn't exist:
				if (!directory.exists()) {
					directory.mkdirs();
				}
				// Set the output file stream up:

				OutputStream myOutput = new FileOutputStream(
						directory.getPath() + "/routineManagerDB_v"
								+ String.valueOf(DBHelper.DB_VERSION)
								+ ".backup");

				// Transfer bytes from the input file to the output file
				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}
				// Close and clear the streams

				myOutput.flush();

				myOutput.close();

				myInput.close();

			} catch (FileNotFoundException e) {
				Toast.makeText(MainActivity.this, "Backup Unsuccesfull!",
						Toast.LENGTH_LONG).show();

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				Toast.makeText(MainActivity.this, "Backup Unsuccesfull!",
						Toast.LENGTH_LONG).show();

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(MainActivity.this, this.getString(R.string.export_done_succesfully),
					Toast.LENGTH_LONG).show();
			break;
		case R.id.btImport:

			if (!hasStorage(true)) {

				Toast.makeText(MainActivity.this, this.getString(R.string.sd_card_missing),
						Toast.LENGTH_LONG).show();
				return false;
			}

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);

			// set title
			alertDialogBuilder.setTitle(R.string.importstr);

			// set dialog message
			alertDialogBuilder
					.setMessage(R.string.import_quest)
					.setCancelable(true)
					.setPositiveButton(R.string.Yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									try {

										// Set the folder on the SDcard
										File directory = new File(
												Environment
														.getExternalStorageDirectory()
														+ "/dancesport-routine-manager-share/");
										// Set the input file stream up:

										FilenameFilter textFilter = new FilenameFilter() {
											public boolean accept(File dir,
													String name) {

												if (name.startsWith("routineManagerDB_v"
														+ String.valueOf(DBHelper.DB_VERSION))) {
													return true;
												} else {
													return false;
												}
											}
										};

										File[] files = directory
												.listFiles(textFilter);
										boolean bingo = false;

										if (files != null) {
											if (files.length > 1) {

												AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
														MainActivity.this);

												// set title
												alertDialogBuilder
														.setTitle(R.string.importstr);

												// set dialog message
												alertDialogBuilder
														.setMessage(
																R.string.import_dbforimport_toomanyfiles)
														.setPositiveButton(
																R.string.Yes,
																new DialogInterface.OnClickListener() {
																	public void onClick(
																			DialogInterface dialog,
																			int id) {

																	}
																});

												// create alert dialog
												AlertDialog alertDialog = alertDialogBuilder
														.create();

												// show it
												alertDialog.show();

												// return false;

											}
											;
											for (File file : files) {
												if (file.isDirectory()) {

												} else {

													bingo = true;
												}

											}
										}
										if (bingo == false) {

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													MainActivity.this);

											// set title
											alertDialogBuilder
													.setTitle(R.string.importstr);

											// set dialog message
											alertDialogBuilder
													.setMessage(
															R.string.import_dbforimport_notfound)
													.setPositiveButton(
															R.string.Yes,
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int id) {

																}
															});

											// create alert dialog
											AlertDialog alertDialog = alertDialogBuilder
													.create();

											// show it
											alertDialog.show();

											// return false;

										}
										;

										OutputStream myOutput;
										myOutput = new FileOutputStream(
												"/data/data/com.rappasocial.routinemanager/databases/routineManagerDB");

										InputStream myInputs = new FileInputStream(
												directory.getPath()
														+ "/routineManagerDB_v"
														+ String.valueOf(DBHelper.DB_VERSION)
														+ ".backup");

										// Transfer bytes from the input file to
										// the output file
										byte[] buffer = new byte[1024];
										int length;
										while ((length = myInputs.read(buffer)) > 0) {
											myOutput.write(buffer, 0, length);
										}

										// Close and clear the streams
										myOutput.flush();

										myOutput.close();

										myInputs.close();

									} catch (FileNotFoundException e) {
										Toast.makeText(MainActivity.this,
												"Import Unsuccesfull!",
												Toast.LENGTH_LONG).show();

										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										Toast.makeText(MainActivity.this,
												"Import Unsuccesfull!",
												Toast.LENGTH_LONG).show();

										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Toast.makeText(MainActivity.this,
											MainActivity.this.getString(R.string.import_done_succesfully),
											Toast.LENGTH_LONG).show();

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
		}
		return super.onOptionsItemSelected(item);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btMenuLatin:
			// переменные для query
			String[] columns = null;
			String selection = null;
			String[] selectionArgs = null;
			// String groupBy = null;
			// String having = null;
			// String orderBy = null;
			//
			// // курсор
			Cursor c = null;
			//
			//
			// selection = dbHelper.DB_TABLE_DANCES + "."
			// +dbHelper.COLUMN_DANCES_NAME + " = ?";
			// selectionArgs = new String[] { "Samba" };
			//
			//
			// c = db.query(dbHelper.DB_TABLE_DANCES, null, selection,
			// selectionArgs, null, null,
			// null);
			//
			// if (c != null) {
			// if (c.moveToFirst()) {
			//
			// do {
			// Dance locCurDance = new
			// Dance(c.getInt(c.getColumnIndex(dbHelper.COLUMN_DANCES_ID)),
			// c.getString(c.getColumnIndex(dbHelper.COLUMN_DANCES_NAME)));
			// extApp.setcurrentDance(locCurDance);
			//
			//
			//
			//
			// } while (c.moveToNext());
			// }
			// c.close();
			// } else dbHelper.close();

			Intent intent = new Intent(MainActivity.this,
					LatinMenuActivity.class);
			startActivity(intent);
			break;
		case R.id.btMenuStandard:
			// переменные для query
			// String[] columns = null;

			// selection = dbHelper.DB_TABLE_DANCES + "."
			// +dbHelper.COLUMN_DANCES_NAME + " = ?";
			// selectionArgs = new String[] { "Samba" };
			//
			//
			// c = db.query(dbHelper.DB_TABLE_DANCES, null, selection,
			// selectionArgs, null, null,
			// null);
			//
			// if (c != null) {
			// if (c.moveToFirst()) {
			//
			// do {
			// Dance locCurDance = new
			// Dance(c.getInt(c.getColumnIndex(dbHelper.COLUMN_DANCES_ID)),
			// c.getString(c.getColumnIndex(dbHelper.COLUMN_DANCES_NAME)));
			// extApp.setcurrentDance(locCurDance);
			//
			//
			//
			//
			// } while (c.moveToNext());
			// }
			// c.close();
			// } else dbHelper.close();
			//

			intent = new Intent(MainActivity.this, StandardMenuActivity.class);
			startActivity(intent);
			break;

		}

	}

	public static boolean hasStorage(boolean requireWriteAccess) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else if (!requireWriteAccess
				&& Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
