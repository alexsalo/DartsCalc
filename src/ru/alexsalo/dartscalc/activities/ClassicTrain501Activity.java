package ru.alexsalo.dartscalc.activities;

import java.util.ArrayList;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.GameModes;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class ClassicTrain501Activity extends GameActivity {
	private int m_game_init_score;
	private GameModes gamemode;
	private int dartnum = 1;
	private ArrayList<Integer> valid_scores = new ArrayList<Integer>();
	private ArrayList<Integer> valid_D_scores = new ArrayList<Integer>();

	@Override
	protected void setConfirmTouchListner() {
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (current_attempt.getText().toString() != dummy_zero) {
					cur_score = Integer.parseInt(current_attempt.getText()
							.toString());
					if (isValidScore(cur_score)
							&& !(score_game - cur_score == 0 && !isValidDScore(cur_score))) {
						ShowLegViewScores();
						if (score_game - cur_score < 2
								&& score_game - cur_score != 0) {// overdraft
							Toast.makeText(context, "Перебор, пробуй еще",
									Toast.LENGTH_SHORT).show();
							score_game += score_leg1 + score_leg2 + score_leg3;
							dartnum += 4 - leg; // fill the gap if overdraft in
												// 1st or second dart
							writeLegResults();
						}
						if (score_game - cur_score == 0) { // win
							score_game -= cur_score;
							current_score_game.setText(String
									.valueOf(score_game));
							writeLegResults();
							setDummyNumberListner();
							showWinnerResults(dartnum, gamemode);
						}
						if (score_game - cur_score > 0) {// play more
							score_game -= cur_score;
							current_score_game.setText(String
									.valueOf(score_game));
						}
						if (leg == 4) {
							writeLegResults();
						}

						current_attempt.setText(dummy_zero);
						tv_currentLeg.setText(context
								.getString(R.string.current_dart)
								+ String.valueOf(++dartnum));
					}
				}
				return false;
			}

		});
	}

	private boolean isValidScore(int n) {
		if (valid_scores.contains(n))
			return true;
		return false;
	}

	private boolean isValidDScore(int n) {
		if (valid_D_scores.contains(n))
			return true;
		return false;
	}

	@Override
	@SuppressWarnings("unused")
	protected void showRule() {
		AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(
				this, R.style.AlertDialogCustom))
				.setTitle(R.string.train_classic).setMessage(R.string.rule_501)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		gameMode = gamemode;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.game_menu, menu);
		return true;
	}

	@Override
	protected void initNewGame() {
		score_data.clear();
		setNumberListner();
		score_game = m_game_init_score;
		dartnum = 1;
		current_attempt.setText(dummy_zero);
		current_score_game.setText(String.valueOf(m_game_init_score));
		initLegViews();
		leg = 1;
		legnum = 1;
		tv_currentLeg.setText(getApplicationContext().getString(
				R.string.current_dart)
				+ String.valueOf(dartnum));
		context = getApplicationContext();
	}

	@Override
	protected void moreOnCreate() {
		Bundle b = getIntent().getExtras();
		m_game_init_score = b.getInt("init_score", 501);
		gamemode = (GameModes) getIntent().getSerializableExtra("gamemode");
		setGameScoreOutChartListner();
		fillValidScores();
		fillValidDScores();
	}

	protected void setGameScoreOutChartListner() {
		current_score_game.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				outChartDialog();
			}
		});
	}

	private void outChartDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		builder.setMessage(
				out_chart501.getOut(Integer.parseInt(current_score_game
						.getText().toString()))).show();
	}

	private void fillValidDScores() {
		for (int i = 2; i <= 40; i += 2)
			valid_D_scores.add(i);
		valid_D_scores.add(25);
		valid_D_scores.add(50);
	}

	private void fillValidScores() {
		for (int i = 0; i <= 22; i++)
			valid_scores.add(i);
		valid_scores.add(24);
		valid_scores.add(25);
		valid_scores.add(26);
		valid_scores.add(27);
		valid_scores.add(28);
		valid_scores.add(30);
		valid_scores.add(32);
		valid_scores.add(33);
		valid_scores.add(34);
		valid_scores.add(36);
		valid_scores.add(38);
		valid_scores.add(39);
		valid_scores.add(40);
		valid_scores.add(42);
		valid_scores.add(45);
		valid_scores.add(48);
		valid_scores.add(50);
		valid_scores.add(51);
		valid_scores.add(54);
		valid_scores.add(57);
		valid_scores.add(60);
	}
}
