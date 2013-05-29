package ru.alexsalo.dartscalc.activities;

import java.io.File;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.GameModes;
import ru.alexsalo.dartscalc.logic.LegsStatsGetter;
import ru.alexsalo.dartscalc.logic.xmlDataBuilder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class ClassicTrain501Activity extends GameActivity {
	protected int game_init_score = 501;
	private GameModes gamemode = GameModes.CLASSIC_501;
	private int dartnum = 1;

	@Override
	protected void setConfirmTouchListner() {
		tv_confirm.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (current_attempt.getText().toString() != dummy_zero) {
					cur_score = Integer.parseInt(current_attempt.getText()
							.toString());
					if (cur_score <= 60) {
						ShowLegViewScores();
						if (score_game - cur_score < 0)
							Toast.makeText(context, "Overdraft, try again",
									Toast.LENGTH_SHORT).show();
						if (score_game - cur_score == 0) { // win
							score_game -= cur_score;
							current_score_game.setText(String
									.valueOf(score_game));
							writeLegResults();
							setDummyNumberListner();
							int tmp[] = math.mean(score_data);
							score_data.add(tmp);
							showWinnerResults();
						}
						if (score_game - cur_score > 0) {
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

	DialogInterface.OnClickListener winDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			xmlDataBuilder xmlsaver = new xmlDataBuilder(gameMode);
			Toast.makeText(getApplicationContext(), "Data has been saved",
					Toast.LENGTH_LONG).show();
			File file = xmlsaver.saveToXml(score_data);
			saveResultToDb(file, xmlsaver.getLocalResultPath());

			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				initNewGame();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				break;
			}
		}
	};

	protected void showWinnerResults(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LegsStatsGetter mLSG = new LegsStatsGetter();
		//String s = achievement.getAchievement(sex, gamemode, score_game);
		builder.setMessage(
				"Победа!\n" +
				//"Вы выполнили норматив на: "+ s +"\n"+
				"Затрачено "+dartnum+" бросков, статистика очков:\n"+
				mLSG.getStats(score_data)+
				"Что дальше?"
				)
				.setPositiveButton("Еще раз", winDialogClickListener)
				.setNegativeButton("Хватит", winDialogClickListener).show();
	}

	@Override
	@SuppressWarnings("unused")
	protected void showRule() {
		AlertDialog dialog = new AlertDialog.Builder(this)
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
		score_game = 501;
		dartnum = 1;
		current_attempt.setText(dummy_zero);
		current_score_game.setText(String.valueOf(game_init_score));
		initLegViews();
		leg = 1;
		legnum = 1;
		tv_currentLeg.setText(getApplicationContext().getString(
				R.string.current_dart)
				+ String.valueOf(dartnum));
		context = getApplicationContext();
	}
}
