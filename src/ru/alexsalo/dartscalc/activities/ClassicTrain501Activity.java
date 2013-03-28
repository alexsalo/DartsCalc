package ru.alexsalo.dartscalc.activities;

import java.util.ArrayList;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.xmlDataBuilder;
import android.app.*;
import android.os.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class ClassicTrain501Activity extends Activity {
	private TextView current_attempt;
	private TextView current_score_game;
	private TextView current_leg1;
	private TextView current_leg2;
	private TextView current_leg3;
	private CharSequence dummy_zero = "00";
	private int game_init_score = 501;
	private byte leg;
	private int legnum;
	private int score_leg1;
	private int score_leg2;
	private int score_leg3;
	private int score_game;
	private ArrayList<int[]> score_data = new ArrayList<int[]>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classictrain501);

		current_attempt = (TextView) findViewById(R.id.current_attempt_tv);
		current_score_game = (TextView) findViewById(R.id.current_score_tv);
		current_leg1 = (TextView) findViewById(R.id.current_leg1_tv);
		current_leg2 = (TextView) findViewById(R.id.current_leg2_tv);
		current_leg3 = (TextView) findViewById(R.id.current_leg3_tv);

		initNewGame();

		View.OnTouchListener number_listner = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				CharSequence text = current_attempt.getText();
				int n = (Integer) v.getTag();
				if (text.equals(dummy_zero))
					current_attempt.setText("0" + String.valueOf(n));
				else if (Integer.parseInt(text.toString()) * 10 + n <= 60)
					current_attempt.setText(current_attempt.getText().charAt(1)
							+ String.valueOf(n));
				return false;
			}
		};

		TextView tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setTag(1);
		tv1.setOnTouchListener(number_listner);

		TextView tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setTag(2);
		tv2.setOnTouchListener(number_listner);

		TextView tv3 = (TextView) findViewById(R.id.tv3);
		tv3.setTag(3);
		tv3.setOnTouchListener(number_listner);

		TextView tv4 = (TextView) findViewById(R.id.tv4);
		tv4.setTag(4);
		tv4.setOnTouchListener(number_listner);

		TextView tv5 = (TextView) findViewById(R.id.tv5);
		tv5.setTag(5);
		tv5.setOnTouchListener(number_listner);

		TextView tv6 = (TextView) findViewById(R.id.tv6);
		tv6.setTag(6);
		tv6.setOnTouchListener(number_listner);

		TextView tv7 = (TextView) findViewById(R.id.tv7);
		tv7.setTag(7);
		tv7.setOnTouchListener(number_listner);

		TextView tv8 = (TextView) findViewById(R.id.tv8);
		tv8.setTag(8);
		tv8.setOnTouchListener(number_listner);

		TextView tv9 = (TextView) findViewById(R.id.tv9);
		tv9.setTag(9);
		tv9.setOnTouchListener(number_listner);

		TextView tv0 = (TextView) findViewById(R.id.tv0);
		tv0.setTag(0);
		tv0.setOnTouchListener(number_listner);

		TextView tv_erase = (TextView) findViewById(R.id.tv_erase);
		tv_erase.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				CharSequence text = current_attempt.getText();
				if (text.length() > 1)
					current_attempt.setText("0" + text.charAt(0));
				else
					current_attempt.setText(dummy_zero);
				xmlDataBuilder xmlsaver = new xmlDataBuilder("501Training");
				Toast.makeText(getApplicationContext(),
						xmlsaver.saveToXml(score_data), Toast.LENGTH_LONG)
						.show();
				return false;
			}
		});

		TextView tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int cur_score = Integer.parseInt(current_attempt.getText()
						.toString());
				if (cur_score <= 60) {
					switch (leg) {
					case 1:
						current_leg1.setText(String.valueOf(cur_score));
						score_leg1 = cur_score;
						break;
					case 2:
						current_leg2.setText(String.valueOf(cur_score));
						score_leg2 = cur_score;
						break;
					case 3:
						current_leg3.setText(String.valueOf(cur_score));
						score_leg3 = cur_score;
						break;
					}
					leg++;
					if (leg == 4) { // 3 leg's attempts ready
						legnum++;
						int sum = score_leg1 + score_leg2 + score_leg3;
						if (score_game - sum < 0) { //overdraft
							Toast.makeText(getApplicationContext(),
									"Overdraft, try again", Toast.LENGTH_SHORT)
									.show();
							initLegViews();
							leg = 1;
						} else {
							if (!(score_game == 0)) { //no win yet
								score_game -= sum;
								int[] mas = new int[5];
								mas[0] = score_leg1;
								mas[1] = score_leg2;
								mas[2] = score_leg3;
								mas[3] = sum;
								mas[4] = score_game;
								score_data.add(mas);
								leg = 1;
								current_score_game.setText(String
										.valueOf(score_game));
								initLegViews();
							} else { //win
								Toast.makeText(
										getApplicationContext(),
										"Win using " + String.valueOf(legnum)
												+ " legs!", Toast.LENGTH_SHORT)
										.show();
								score_game -= sum;
								int[] mas = new int[5];
								mas[0] = score_leg1;
								mas[1] = score_leg2;
								mas[2] = score_leg3;
								mas[3] = sum;
								mas[4] = score_game;
								//score_data populate stats 
								//use subList
								score_data.add(mas);
								initLegViews();
								initNewGame();
							}
						}
					}
					current_attempt.setText(dummy_zero);
				}
				return false;
			}
		});
	}

	private void initNewGame() {
		score_game = 501;
		current_attempt.setText(dummy_zero);
		current_score_game.setText(String.valueOf(game_init_score));
		initLegViews();
		leg = 1;
		legnum = 0;
	}

	private void initLegViews() {
		current_leg1.setText(dummy_zero);
		current_leg2.setText(dummy_zero);
		current_leg3.setText(dummy_zero);
	}
}
