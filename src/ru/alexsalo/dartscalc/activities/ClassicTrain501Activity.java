package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;

public class ClassicTrain501Activity extends GameActivity {
	protected int game_init_score = 501;
	
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
