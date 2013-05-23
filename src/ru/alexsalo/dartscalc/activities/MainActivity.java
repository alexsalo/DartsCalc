package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button classic_train501_btn;
	private Button bigRoung_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		classic_train501_btn = (Button)findViewById(R.id.classic_train501);
		bigRoung_btn = (Button)findViewById(R.id.biground);
		
		classic_train501_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this, ClassicTrain501Activity.class);
				startActivity(i);
			}
		});
		
		bigRoung_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, BigRoundActivity.class);
				startActivity(i);
			}
		});
		return true;
	}

}
