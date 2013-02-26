package com.rappasocial.routinemanager.free;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.mobeta.android.dslv.DragSortListView;
import com.rappasocial.routinemanager.free.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RoutineEditActivity extends ListActivity implements
		OnClickListener {

	ArrayList<RoutineRaw> routine_raws;
	BoxAdapterRoutineRaw boxAdapter;
	ExtendedApplication extApp;
	TextView tvCurDanceCharRE, tvRoutineTitle;
	Button btAddFigures, btRLback;
	DragSortListView lvRoutineRaws;
	LinearLayout llRoutineEditActionPanel;
	int gender;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		public void drop(int from, int to) {
			RoutineRaw item = boxAdapter.getItem(from);

			boxAdapter.remove(item);
			boxAdapter.insert(item, to);
			boxAdapter.notifyDataSetChanged();
			if (from != to) {

				extApp.isRoutineModified = true;

			}
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		public void remove(int which) {
			boxAdapter.remove(boxAdapter.getItem(which));
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.routine_edit);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.gender = extras.getInt("gender");

		}

		extApp = (ExtendedApplication) getApplicationContext();

		if (this.gender == 1) {
			routine_raws = this.extApp.mRoutine_rawsBufferArray;
		} else {

			routine_raws = this.extApp.wRoutine_rawsBufferArray;
		}

		fillData();
		boxAdapter = new BoxAdapterRoutineRaw(routine_raws,
				RoutineEditActivity.this);

		lvRoutineRaws = (DragSortListView) getListView();

		lvRoutineRaws.setDropListener(onDrop);
		lvRoutineRaws.setRemoveListener(onRemove);

		setListAdapter(boxAdapter);

		btAddFigures = (Button) findViewById(R.id.btAddFigures);

		btAddFigures.setOnClickListener(this);

		btRLback = (Button) findViewById(R.id.btRLback);

		btRLback.setOnClickListener(this);
		
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

		Dance curDance = extApp.getcurrentDance();

		llRoutineEditActionPanel = (LinearLayout) findViewById(R.id.llRoutineEditActionPanel);

		TextView tvCurDanceCharRE = (TextView) findViewById(R.id.tvCurDanceCharRE);

		if ((curDance.name).compareToIgnoreCase(DBHelper.Samba) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_orange));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_orange));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_orange));
			tvCurDanceCharRE.setText("S");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.ChaCha) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.bt_bar_blue));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_blue));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_blue));
			tvCurDanceCharRE.setText("Ch");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Rumba) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_purple));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_purple));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_purple));
			tvCurDanceCharRE.setText("R");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.PasoDoble) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_red));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_red));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_red));
			tvCurDanceCharRE.setText("P");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Jive) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_yellow));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_yellow));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_yellow));
			tvCurDanceCharRE.setText("J");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Waltz) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_orange));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_orange));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_orange));
			tvCurDanceCharRE.setText("W");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Tango) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_red));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_red));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_red));
			tvCurDanceCharRE.setText("T");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.VienneseWaltz) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_purple));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_purple));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_purple));
			tvCurDanceCharRE.setText("V");

		} else if ((curDance.name).compareToIgnoreCase(DBHelper.Foxtrot) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_blue));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_blue));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_blue));
			tvCurDanceCharRE.setText("F");

		} else if ((curDance.name)
				.compareToIgnoreCase(DBHelper.Quickstep) == 0) {

			llRoutineEditActionPanel.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.action_panel_bg_yellow));
			btAddFigures.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_yellow));
			btRLback.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.custom_button_yellow));
			tvCurDanceCharRE.setText("Q");

		}

		TextView tvRoutineTitle = (TextView) findViewById(R.id.tvRoutineTitle);
		tvRoutineTitle.setText(extApp
				.getRoutineNameByID(extApp.currentRoutineid));

		// Gesture detection
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// Toast.makeText(SelectFilterActivity.this, "Left Swipe",
					// Toast.LENGTH_SHORT).show();
					onBackPressed();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// Toast.makeText(SelectFilterActivity.this, "Right Swipe",
					// Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}
	}

	void copyPartnersPart() {

		if (this.gender == 1) {

			// routine_raws.addAll(new (ArrayList<RoutineRaw>)
			// extApp.wRoutine_rawsBufferArray.clone());

			for (int i = 0; i < extApp.wRoutine_rawsBufferArray.size(); i++) {

				routine_raws.add(new RoutineRaw(extApp.wRoutine_rawsBufferArray.get(i)));
				

			}

			for (int i = 0; i < routine_raws.size(); i++) {

				routine_raws.get(i).gender = this.gender;

			}
		} else {

//			routine_raws
//					.addAll((ArrayList<RoutineRaw>) extApp.mRoutine_rawsBufferArray
//							.clone());
//
//			for (int i = 0; i < routine_raws.size(); i++) {
//
//				routine_raws.get(i).gender = this.gender;
//
//			}
			
			for (int i = 0; i < extApp.mRoutine_rawsBufferArray.size(); i++) {

				routine_raws.add(new RoutineRaw(extApp.mRoutine_rawsBufferArray.get(i)));
				

			}

			for (int i = 0; i < routine_raws.size(); i++) {

				routine_raws.get(i).gender = this.gender;

			}
		}
		;
		extApp.isRoutineModified = true;
		boxAdapter.notifyDataSetChanged();

	}

	void fillData() {

		// переменные для query
		// String[] columns = null;
		String selection = null;
		// String[] selectionArgs = null;
		// String groupBy = null;
		// String having = null;
		// String orderBy = null;

		// курсор
		Cursor c = null;

		if (extApp.currentRoutineid != -1) {

			selection = DBHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID + " = "
					+ extApp.currentRoutineid;
			selection += " and " + DBHelper.COLUMN_ROUTINE_RAWS_GENDER
					+ " = " + this.gender;

		} else {

			selection = null;
		}

		c = extApp.db.query(DBHelper.DB_TABLE_ROUTINE_RAWS, null,
				selection, null, null, null,
				DBHelper.COLUMN_ROUTINE_RAWS_WEIGHT);

		if (c != null) {
			if (c.moveToFirst()) {

				do {

					routine_raws
							.add(new RoutineRaw(
									c.getInt(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_ID)),
									c.getInt(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID)),
									c.getInt(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_FIGURE_ID)),
									c.getString(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_TIMING)),
									c.getString(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_COMMENT)),
									c.getInt(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_WEIGHT)),
									c.getInt(c
											.getColumnIndex(DBHelper.COLUMN_ROUTINE_RAWS_GENDER))));

				} while (c.moveToNext());
			}
			c.close();
		}
		;

		// dbHelper.close();

	}

	private class ViewHolder {
		public TextView tvFigureName;
		public TextView tvTiming;
		public TextView tvRoutineRawComment;
		public TextView tvRawNumber;
		public int position;
	}

	public class BoxAdapterRoutineRaw extends ArrayAdapter<RoutineRaw>
			implements OnClickListener {
		Context ctx;
		LayoutInflater lInflater;
		ArrayList<RoutineRaw> objects;
		ExtendedApplication extApp;
		ImageButton btDeleteRoutineRaw;
		LinearLayout llRoutineEditBG, llRoutineEditClickAble;

		public BoxAdapterRoutineRaw(List<RoutineRaw> objects, Context ctx) {
			super(RoutineEditActivity.this, R.layout.routine_edit_raw,
					R.id.tvFigureName, objects);
			extApp = (ExtendedApplication) ctx.getApplicationContext();

			this.ctx = ctx;
		}

		// BoxAdapterRoutine(Context context, ArrayList<Routine> routines) {
		// ctx = context;
		// objects = routines;
		// lInflater = (LayoutInflater) ctx
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// extApp = (ExtendedApplication)ctx.getApplicationContext();
		// }

		// public int getCount() {
		// return objects.size();
		// }
		//
		//
		// public Routine getItem(int position) {
		// return objects.get(position);
		// }
		//
		//
		// public long getItemId(int position) {
		// return position;
		// }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// View view = convertView;
			// if (view == null) {
			// view = lInflater.inflate(R.layout.routine_list_item, parent,
			// false);
			// }
			//
			// Routine p = getRoutine(position);
			//
			//
			// ((TextView)
			// view.findViewById(R.id.tvRoutineName)).setText(p.name);
			// Button btEditRoutine = (Button)
			// view.findViewById(R.id.btEditRoutine);
			// btEditRoutine.setTag(position);
			// btEditRoutine.setOnClickListener(this);
			// // ((Button)
			// view.findViewById(R.id.btEditRoutine)).setOnClickListener(this);
			// // btEditRoutine.setOnClickListener(this);
			//
			// return view;

			View v = super.getView(position, convertView, parent);

			if (v != convertView && v != null) {
				ViewHolder holder = new ViewHolder();

				TextView tv1 = (TextView) v.findViewById(R.id.tvFigureName);
				TextView tv2 = (TextView) v.findViewById(R.id.tvTiming);
				TextView tv3 = (TextView) v
						.findViewById(R.id.tvRoutineRawComment);
				TextView tv4 = (TextView) v.findViewById(R.id.tvRawNumber);
				holder.tvFigureName = tv1;
				holder.tvTiming = tv2;
				holder.tvRoutineRawComment = tv3;
				holder.tvRawNumber = tv4;

				v.setTag(holder);
			}

			// Button btEditRaw = (Button) v.findViewById(R.id.btEditRaw);
			// btEditRaw.setTag(position);
			// btEditRaw.setOnClickListener(this);

			btDeleteRoutineRaw = (ImageButton) v
					.findViewById(R.id.btDeleteRoutineRaw);
			btDeleteRoutineRaw.setTag(position);
			btDeleteRoutineRaw.setOnClickListener(this);

			// ImageView drag_handle = (ImageView)
			// v.findViewById(R.id.drag_handle);
			// drag_handle.setOnClickListener(this);

			ViewHolder holder = (ViewHolder) v.getTag();
			String f_name = extApp.getFiguresNameByID(
					getItem(position).figure_id, extApp.getcurrentDance().id);
			String f_Timing = getItem(position).timing;
			String f_Comment = getItem(position).comment;

			holder.tvFigureName.setText(f_name);
			holder.tvTiming.setText(f_Timing);
			holder.tvRoutineRawComment.setText(f_Comment);
			holder.tvRawNumber.setText(String.valueOf(position + 1));

			llRoutineEditBG = (LinearLayout) v
					.findViewById(R.id.llRoutineEditBG);
			llRoutineEditClickAble = (LinearLayout) v
					.findViewById(R.id.llRoutineEditClickAble);
			llRoutineEditClickAble.setTag(position);
			llRoutineEditClickAble.setOnClickListener(this);
			Dance curDance = extApp.getcurrentDance();

			if ((curDance.name).compareToIgnoreCase(DBHelper.Samba) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_orange));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_orange));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.ChaCha) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_blue));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_blue));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Rumba) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_purple));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_purple));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.PasoDoble) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_red));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_red));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Jive) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_yellow));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_yellow));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Waltz) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_orange));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_orange));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Tango) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_red));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_red));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.VienneseWaltz) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_purple));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_purple));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Foxtrot) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_blue));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_blue));

			} else if ((curDance.name)
					.compareToIgnoreCase(DBHelper.Quickstep) == 0) {

				llRoutineEditBG.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_yellow));
				llRoutineEditClickAble.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.listrow_bg_clickable_yellow));

			}

			//
			holder.position = position;

			return v;

		}

		RoutineRaw getRoutineRaw(int position) {
			return (getItem(position));
		}

		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.llRoutineEditClickAble:

				this.extApp.currentRoutineRawId = (Integer) v.getTag();
				// this.extApp.routine_rawsBufferArray = routine_raws;
				this.extApp.editTimingBuffer = routine_raws.get((Integer) v
						.getTag()).timing;
				this.extApp.editCommentBuffer = routine_raws.get((Integer) v
						.getTag()).comment;
				Intent intent = new Intent(ctx, RoutineRawEditActivity.class);
				ctx.startActivity(intent);
				break;

			// case R.id.btEditRaw:
			// Animation animRotate = AnimationUtils.loadAnimation(ctx,
			// R.anim.anim_scale);
			// v.startAnimation(animRotate);
			// this.extApp.currentRoutineRawId = (Integer) v.getTag();
			// this.extApp.routine_rawsBufferArray = routine_raws;
			// this.extApp.editTimingBuffer = routine_raws.get((Integer)
			// v.getTag()).timing;
			// this.extApp.editCommentBuffer = routine_raws.get((Integer)
			// v.getTag()).comment;
			// intent = new Intent(ctx, RoutineRawEditActivity.class);
			// ctx.startActivity(intent);
			// break;

			case R.id.btDeleteRoutineRaw:
				Animation animRotate = AnimationUtils.loadAnimation(ctx,
						R.anim.anim_scale);
				v.startAnimation(animRotate);

				this.extApp.currentRoutineRawId = (Integer) v.getTag();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						RoutineEditActivity.this);

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
										routine_raws
												.remove(extApp.currentRoutineRawId);
										extApp.isRoutineModified = true;
										boxAdapter.notifyDataSetChanged();
										Context context = getApplicationContext();
										CharSequence text = getString(R.string.Deleted);
										int duration = Toast.LENGTH_SHORT;
										Toast.makeText(context, text, duration)
												.show();

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

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
		case R.id.btFigures:

			Intent intent = new Intent(RoutineEditActivity.this,
					CrudFiguresActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btShare:
			try { // catches IOException below

				String subject = "Dancesport Routine Manager";
				String body = "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><html><body><table align=\"center\" border=\"1\" bordercolor=\"#ccc\" cellpadding=\"5\" cellspacing=\"0\" style=\"border-collapse:collapse;margin:10px 0 10px 15px;\">"
						+ "<thead>"
						+ "<tr>"
						+ "<th scope=\"col\">№</th>"
						+ "<th scope=\"col\">Фигура</th>"
						+ "<th scope=\"col\">Комментарий</th>"
						+ "<th scope=\"col\">Счет</th>"
						+ "</tr>"
						+ "</thead>" + "<tbody>";
				for (int i = 0; i < routine_raws.size(); i++) {

					// cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID,
					// extApp.currentRoutine.id);
					String htmlfname = extApp.getFiguresNameByID(
							routine_raws.get(i).figure_id,
							extApp.getcurrentDance().id);
					String htmlrawid = "" + (i + 1);
					String htmltiming = routine_raws.get(i).timing;
					String htmlcomment = routine_raws.get(i).comment;
					// cv.put(extApp.dbHelper.COLUMN_ROUTINE_RAWS_GENDER,
					// routine_raws.get(i).gender);
					body = body + "<tr>" + "<td>" + htmlrawid + "</td>"
							+ "<td nowrap=\"wrap\" style=\"width:380px;\">"
							+ htmlfname + "</td>" + "<td>" + htmlcomment
							+ "</td>" + "<td>" + htmltiming + "</td>" + "</tr>";

				}
				;

				body = body + "</tbody>" + "</table></body></html>";

				String strFile = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				File myFile = new File(strFile + "/dsrm_exported.html");
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(body);
				myOutWriter.close();
				fOut.close();
				// FileOutputStream fOut = openFileOutput("dsrm_exported.htm",

				final Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("message/rfc822");
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						subject);
				emailIntent
						.putExtra(
								android.content.Intent.EXTRA_TEXT,
								"Ваш документ в формате html находится во вложении");
				emailIntent.putExtra(Intent.EXTRA_STREAM,
						Uri.parse("file://" + strFile + "/dsrm_exported.html"));
				startActivity(Intent.createChooser(emailIntent, "Email:"));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			break;

		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.routines_edit_activity, menu);
		return true;
	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btAddFigures:

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					RoutineEditActivity.this);
			if (routine_raws.size() == 0) {

				if ((extApp.currentGender == 1 && extApp.wRoutine_rawsBufferArray
						.size() == 0)
						|| (extApp.currentGender == 0 && extApp.mRoutine_rawsBufferArray
								.size() == 0)) {

					Intent intent = new Intent(this,
							SelectFiguresActivity.class);
					this.startActivity(intent);
					return;

				}
				;
			} else {

				Intent intent = new Intent(this, SelectFiguresActivity.class);
				this.startActivity(intent);
				return;
			}

			// set title
			// alertDialogBuilder.setTitle(R.string.deleting);
			int dialog_message = 0;
			if (extApp.currentGender == 1) {

				dialog_message = R.string.copy_ladyspart_quest;

			} else {

				dialog_message = R.string.copy_manspart_quest;

			}
			;

			// set dialog message
			alertDialogBuilder
					.setMessage(dialog_message)
					.setCancelable(true)
					.setPositiveButton(R.string.Yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									copyPartnersPart();

								}
							})
					.setNegativeButton(R.string.No,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent intent = new Intent(
											RoutineEditActivity.this,
											SelectFiguresActivity.class);
									startActivity(intent);
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			// Intent intent = new Intent(this, SelectFiguresActivity.class);
			// this.startActivity(intent);
			break;

		case R.id.btRLback:
			
			KeyEvent ke = new KeyEvent(0,0,KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK,0,0);
			onKeyDown(KeyEvent.KEYCODE_BACK, ke);
			break;

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		for (int i = 0; i < extApp.figuresSelectionBufferArray.size(); i++) {
			RoutineRaw NewRoutineRaw = new RoutineRaw(
					extApp.figuresSelectionBufferArray.get(i).id);
			NewRoutineRaw.gender = this.gender;
			routine_raws.add(NewRoutineRaw);
			extApp.isRoutineModified = true;

		}
		extApp.currentGender = this.gender;
		boxAdapter.notifyDataSetChanged();
		extApp.figuresSelectionBufferArray.clear();

	}

	private void exit() {
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			this.getParent().onKeyDown(keyCode, event);

			return true;
		} else {

			return super.onKeyDown(keyCode, event);
		}

	}

//	@Override
//	public void onBackPressed() {
//		this.getParent().onBackPressed();
//	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	public void SaveTheRoutineRaws() {

		extApp.db.delete(DBHelper.DB_TABLE_ROUTINE_RAWS,
				DBHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID + "="
						+ extApp.currentRoutineid, null);
		ContentValues cv = new ContentValues();
		for (int i = 0; i < routine_raws.size(); i++) {

			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_ROUTINE_ID,
					extApp.currentRoutineid);
			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_FIGURE_ID,
					routine_raws.get(i).figure_id);
			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_WEIGHT, i);
			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_TIMING,
					routine_raws.get(i).timing);
			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_COMMENT,
					routine_raws.get(i).comment);
			cv.put(DBHelper.COLUMN_ROUTINE_RAWS_GENDER,
					routine_raws.get(i).gender);
			extApp.db.insert(DBHelper.DB_TABLE_ROUTINE_RAWS, null, cv);

		}

	}

}
