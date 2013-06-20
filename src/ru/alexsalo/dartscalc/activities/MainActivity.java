package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.GameModes;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button classic_train501_btn;
	private Button bigRoung_btn;
	private Button fastRound_btn;
	private Button americanCricket_btn;
	private Button train301_btn;
	private Button train170_btn;

	private Button guide_btn;
	private Button settings_btn;

	String login_name;
	String login_cell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		classic_train501_btn = (Button) findViewById(R.id.classic_train501);
		bigRoung_btn = (Button) findViewById(R.id.biground);
		fastRound_btn = (Button) findViewById(R.id.fast_round);
		americanCricket_btn = (Button) findViewById(R.id.americancriket);
		train301_btn = (Button) findViewById(R.id.train_301);
		train170_btn = (Button) findViewById(R.id.train_170);
		settings_btn = (Button) findViewById(R.id.settings_buttuon);
		guide_btn = (Button) findViewById(R.id.user_guide);

		classic_train501_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this,
						ClassicTrain501Activity.class);
				i.putExtra("init_score", 501);
				i.putExtra("gamemode", GameModes.CLASSIC_501);
				startActivity(i);
			}
		});

		train301_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this,
						ClassicTrain501Activity.class);
				i.putExtra("init_score", 301);
				i.putExtra("gamemode", GameModes.CLASSIC_301);
				startActivity(i);
			}
		});

		train170_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this,
						ClassicTrain501Activity.class);
				i.putExtra("init_score", 170);
				i.putExtra("gamemode", GameModes.CLASSIC_170);
				startActivity(i);
			}
		});

		bigRoung_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, BigRoundActivity.class);
				startActivity(i);
			}
		});

		fastRound_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(MainActivity.this,
				//		FastRoundActivity.class);
				//startActivity(i);
			}
		});

		americanCricket_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						AmericanCricketActivity.class);
				startActivity(i);
			}
		});

		settings_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginDialog();
			}
		});

		guide_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showGuide();
			}
		});

		return true;
	}

	private void showGuide() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		builder.setMessage(R.string.guide).show();
	}

	private void loginDialog() {
		// Preparing views
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.login_dialog,
				(ViewGroup) findViewById(R.id.login_dialog_layout));
		// layout_root should be the name of the "top-level" layout node in the
		// dialog_layout.xml file.
		final EditText nameBox = (EditText) layout
				.findViewById(R.id.login_username);
		final EditText phoneBox = (EditText) layout
				.findViewById(R.id.login_cell);
		SharedPreferences login_pref = getSharedPreferences("login_sp", 0);
		login_name = login_pref.getString("login_name", "username");
		login_cell = login_pref.getString("login_cell", "12345");
		nameBox.setText(login_name);
		phoneBox.setText(login_cell);

		// Building dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		builder.setView(layout);
		builder.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						login_name = nameBox.getText().toString();
						login_cell = phoneBox.getText().toString();
						SharedPreferences login_pref = getSharedPreferences(
								"login_sp", 0);
						SharedPreferences.Editor editor = login_pref.edit();
						editor.putString("login_name", login_name);
						editor.putString("login_cell", login_cell);
						editor.commit();
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
