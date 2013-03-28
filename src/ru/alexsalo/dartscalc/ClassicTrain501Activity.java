package ru.alexsalo.dartscalc;

import android.app.*;
import android.os.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;


public class ClassicTrain501Activity extends Activity {	
	private TextView current_attempt;
	private TextView current_score;
	private int score;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		score = 501;
		setContentView(R.layout.activity_classictrain501); 
		
		current_attempt = (TextView)findViewById(R.id.current_attempt_tv);
		current_score = (TextView)findViewById(R.id.current_score_tv);
		
		View.OnTouchListener number_listner = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				CharSequence text = current_attempt.getText();
				int n = (Integer) v.getTag();
			    if (text.equals("0"))
			    	current_attempt.setText(String.valueOf(n));
			    else if (Integer.parseInt(text.toString())*10+n<=150)
			    	current_attempt.setText(current_attempt.getText()+
			    			String.valueOf(n));
				return false;
			}
		};
		
		TextView tv1 = (TextView)findViewById(R.id.tv1);
		tv1.setTag(1);
		tv1.setOnTouchListener(number_listner);
		
		TextView tv2 = (TextView)findViewById(R.id.tv2);
		tv2.setTag(2);
		tv2.setOnTouchListener(number_listner);
		
		TextView tv3 = (TextView)findViewById(R.id.tv3);
		tv3.setTag(3);
		tv3.setOnTouchListener(number_listner);
		
		TextView tv4 = (TextView)findViewById(R.id.tv4);
		tv4.setTag(4);
		tv4.setOnTouchListener(number_listner);
		
		TextView tv5 = (TextView)findViewById(R.id.tv5);
		tv5.setTag(5);
		tv5.setOnTouchListener(number_listner);
		
		TextView tv6 = (TextView)findViewById(R.id.tv6);
		tv6.setTag(6);
		tv6.setOnTouchListener(number_listner);
		
		TextView tv7 = (TextView)findViewById(R.id.tv7);
		tv7.setTag(7);
		tv7.setOnTouchListener(number_listner);
		
		TextView tv8 = (TextView)findViewById(R.id.tv8);
		tv8.setTag(8);
		tv8.setOnTouchListener(number_listner);
		
		TextView tv9 = (TextView)findViewById(R.id.tv9);
		tv9.setTag(9);
		tv9.setOnTouchListener(number_listner);
		
		TextView tv0 = (TextView)findViewById(R.id.tv0);
		tv0.setTag(0);
		tv0.setOnTouchListener(number_listner);
		
		TextView tv_erase = (TextView)findViewById(R.id.tv_erase);
		tv_erase.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				CharSequence text = current_attempt.getText();
				if (text.length()>1)
				current_attempt.setText(text.subSequence(0, text.length()-1));
				else 
					current_attempt.setText("0");
				return false;
			}
		});
		
		TextView tv_confirm = (TextView)findViewById(R.id.tv_confirm);
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int cur_score = Integer.parseInt(current_attempt.getText().toString());
				if (cur_score <= 150)
					score -= cur_score; 
				current_score.setText(String.valueOf(score));
				current_attempt.setText("0");
				return false;
			}
		});
	}
}
