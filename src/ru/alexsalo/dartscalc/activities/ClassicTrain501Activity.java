package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class ClassicTrain501Activity extends GameActivity {
	protected int game_init_score = 501;
	
	protected void setConfirmTouchListner() {
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				cur_score = Integer.parseInt(current_attempt.getText()
						.toString());
				if (cur_score <= 60) {
					ShowLegViewScores();
					if (leg == 4) { // 3 leg's attempts ready
						legnum++;
						tv_currentLeg.setText(getApplicationContext().getString(R.string.current_leg)
								+ String.valueOf(legnum));
						sum = score_leg1 + score_leg2 + score_leg3;
						if (score_game - sum < 0) { // overdraft
							Toast.makeText(getApplicationContext(),
									"Overdraft, try again", Toast.LENGTH_SHORT)
									.show();
							initLegViews();
							leg = 1;
						} else {
							if (!(score_game == 0)) { // no win yet
								score_game -= sum;
								writeLegResults();
								current_score_game.setText(String
										.valueOf(score_game));
							} else { // win
								Toast.makeText(
										getApplicationContext(),
										"Win using " + String.valueOf(legnum)
												+ " legs!", Toast.LENGTH_SHORT)
										.show();
								score_game -= sum;
								writeLegResults();
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
	
	@SuppressWarnings("unused")
	protected void showRule(){
		AlertDialog dialog = new AlertDialog.Builder(this)
		.setTitle(R.string.train_classic)
		.setMessage(R.string.rule_501)
		.show();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		gameMode = "501Training";
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.game_menu, menu);
		return true;
	}
	
	protected void initNewGame(){
		score_game = 501;
		current_attempt.setText(dummy_zero);
		current_score_game.setText(String.valueOf(game_init_score));
		initLegViews();
		leg = 1;
		legnum = 0;
		tv_currentLeg.setText(R.string.current_leg);
	}
}
