package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.GameModes;
import android.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BigRoundActivity extends GameActivity {
	protected int game_init_score = 0;
	private GameModes gamemode = GameModes.BIG_ROUND;

	@Override
	protected void setConfirmTouchListner() {
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (current_attempt.getText().toString() != dummy_zero) {
					cur_score = Integer.parseInt(current_attempt.getText()
							.toString());
					if (legnum == 21) // bull
						if (cur_score == 25 || cur_score == 50
								|| cur_score == 0) {
							ShowLegViewScores();
							if (leg == 4) {
								sum = score_leg1 + score_leg2 + score_leg3;
								score_game += sum;
								writeLegResults();
								current_score_game.setText(String
										.valueOf(score_game));
								setDummyNumberListner(); // end of game, hold
															// screen
								showWinnerResults(legnum * 3,
										GameModes.BIG_ROUND);
							}
							current_attempt.setText(dummy_zero);
						}
					if (legnum <= 20)
						if ((cur_score % legnum == 0 && cur_score <= legnum * 3)
								|| cur_score == 0) {
							ShowLegViewScores();
							if (leg == 4)// 3 leg's attempts ready
								legOver();
							current_attempt.setText(dummy_zero);
						}
				}
				return false;
			}
		});
	}

	protected void legOver() {
		legnum++;
		tv_currentLeg.setText(getApplicationContext().getString(
				R.string.current_leg)
				+ String.valueOf(legnum));
		sum = score_leg1 + score_leg2 + score_leg3;
		score_game += sum;
		writeLegResults();
		current_score_game.setText(String.valueOf(score_game));
	}

	@Override
	protected void initNewGame() {
		tv_rem_score.setText(getApplicationContext().getString(
				R.string.tv_sumscores));
		score_data.clear();
		setNumberListner();
		score_game = 0;
		current_attempt.setText(dummy_zero);
		current_score_game.setText(String.valueOf(game_init_score));
		initLegViews();
		leg = 1;
		legnum = 1; // !1 testttt
		tv_currentLeg.setText(getApplicationContext().getString(
				R.string.current_leg)
				+ String.valueOf(legnum));
		context = getApplicationContext();
	}

	@Override
	@SuppressWarnings("unused")
	protected void showRule() {
		AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(
				this, R.style.AlertDialogCustom)).setTitle(R.string.biground)
				.setMessage(R.string.rule_bigRound).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		gameMode = gamemode;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.game_menu, menu);
		return true;
	}

	@Override
	protected void moreOnCreate() {
		// TODO Auto-generated method stub
		this.setTitle(this.getTitle()+" "+ login_name);
	}

}
