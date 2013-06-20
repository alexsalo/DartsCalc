package ru.alexsalo.dartscalc.activities;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.logic.GameModes;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AmericanCricketActivity extends Activity {
	private TextView ac_score_1;
	private TextView ac_score_2;
	private int ac_scor_1;
	private int ac_scor_2;
	private TextView pname1;
	private TextView pname2;
	private String name;
	LinearLayout[] ac_1_las;
	LinearLayout[] ac_2_las;
	int[] ac_1_strokes;
	int[] ac_2_strokes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.american_cricket_layout);

		ac_1_las = new LinearLayout[7];
		ac_1_las[0] = (LinearLayout) findViewById(R.id.ac_ll_15_1);
		ac_1_las[1] = (LinearLayout) findViewById(R.id.ac_ll_16_1);
		ac_1_las[2] = (LinearLayout) findViewById(R.id.ac_ll_17_1);
		ac_1_las[3] = (LinearLayout) findViewById(R.id.ac_ll_18_1);
		ac_1_las[4] = (LinearLayout) findViewById(R.id.ac_ll_19_1);
		ac_1_las[5] = (LinearLayout) findViewById(R.id.ac_ll_20_1);
		ac_1_las[6] = (LinearLayout) findViewById(R.id.ac_ll_B_1);

		ac_2_las = new LinearLayout[7];
		ac_2_las[0] = (LinearLayout) findViewById(R.id.ac_ll_15_2);
		ac_2_las[1] = (LinearLayout) findViewById(R.id.ac_ll_16_2);
		ac_2_las[2] = (LinearLayout) findViewById(R.id.ac_ll_17_2);
		ac_2_las[3] = (LinearLayout) findViewById(R.id.ac_ll_18_2);
		ac_2_las[4] = (LinearLayout) findViewById(R.id.ac_ll_19_2);
		ac_2_las[5] = (LinearLayout) findViewById(R.id.ac_ll_20_2);
		ac_2_las[6] = (LinearLayout) findViewById(R.id.ac_ll_B_2);

		ac_score_1 = (TextView) findViewById(R.id.ac_scores_1);
		ac_score_2 = (TextView) findViewById(R.id.ac_scores_2);

		pname1 = (TextView) findViewById(R.id.ac_name_1);
		pname2 = (TextView) findViewById(R.id.ac_name_2);
		pname1.setOnClickListener(nameClickListner);
		pname2.setOnClickListener(nameClickListner);

		initNewGame();
		setACListners();
	}

	OnClickListener nameClickListner = new OnClickListener() {
		public void onClick(View v) {
			inputPName(v);
		}
	};

	private void inputPName(final View v) {
		final EditText input = new EditText(getApplicationContext());
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		builder.setTitle("Введите имя")
				.setView(input)
				.setPositiveButton("Это я", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						name = input.getText().toString();
						((TextView)v).setText(name);
					}
				}).show();
	}

	private void initNewGame() {
		ac_scor_1 = 0;
		ac_scor_2 = 0;
		ac_score_1.setText("0");
		ac_score_2.setText("0");
		ac_1_strokes = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		ac_2_strokes = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		for (int i=0; i<7; i++){
			((ImageView) ac_1_las[i].getChildAt(1)).setImageResource(0);
			((ImageView) ac_2_las[i].getChildAt(1)).setImageResource(0);
		}
	}

	private void checkWinner() {
		boolean is1Win = true;
		boolean is2Win = true;
		for (int i = 0; i < 7; i++) {
			if (ac_1_strokes[i] != 3) // if not all closed
				is1Win = false;
			if (ac_2_strokes[i] != 3) // if not all closed
				is2Win = false;
		}
		if (is1Win && ac_scor_1 >= ac_scor_2)
			showWinnerResults(1, GameModes.AMERICAN_CRICKET);
		if (is2Win && ac_scor_2 >= ac_scor_1)
			showWinnerResults(2, GameModes.AMERICAN_CRICKET);
	}

	private void setACListners() {
		for (int i = 0; i < 7; i++) {
			ac_1_las[i].setTag(i);
			ac_2_las[i].setTag(i);
			ac_1_las[i].setOnClickListener(ac_listner_1);
			ac_2_las[i].setOnClickListener(ac_listner_2);
		}
	}

	OnClickListener ac_listner_1 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int t = (Integer) v.getTag();
			int id = 0;
			switch (ac_1_strokes[t]) {
			case 0:
				id = R.drawable.stroke1;
				break;
			case 1:
				id = R.drawable.stroke2;
				break;
			case 2:
				id = R.drawable.stroke3;
				break;
			case 3:
				if (ac_2_strokes[t] < 3) {// opponent has not closed the number
											// as well
					if (t == 6) // bull
						ac_scor_1 += 25;
					else
						// tag + 15
						ac_scor_1 += t + 15;
					ac_score_1.setText(String.valueOf(ac_scor_1));
				}
				break;
			}
			if (ac_1_strokes[t] < 3) {
				((ImageView) ac_1_las[t].getChildAt(1)).setImageResource(id);
				ac_1_strokes[t]++;
			}
			checkWinner();
		}
	};

	OnClickListener ac_listner_2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int t = (Integer) v.getTag();
			int id = 0;
			switch (ac_2_strokes[t]) {
			case 0:
				id = R.drawable.stroke1;
				break;
			case 1:
				id = R.drawable.stroke2;
				break;
			case 2:
				id = R.drawable.stroke3;
				break;
			case 3:
				if (ac_1_strokes[t] < 3) {
					if (t == 6) // bull
						ac_scor_2 += 25;
					else
						// tag + 15
						ac_scor_2 += t + 15;
					ac_score_2.setText(String.valueOf(ac_scor_2));
				}
				break;
			}
			if (ac_2_strokes[t] < 3) {
				((ImageView) ac_2_las[t].getChildAt(1)).setImageResource(id);
				ac_2_strokes[t]++;
			}
			checkWinner();
		}
	};

	protected void showWinnerResults(int winnerNum, GameModes gamemode) {
		String winnername;
		if (winnerNum == 1)
			winnername = pname1.getText().toString();
		else
			winnername = pname2.getText().toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		builder.setMessage(
				"Победил " + winnername + "\n")
				.setPositiveButton("Еще раз", winDialogClickListener)
				.setNegativeButton("Хватит", winDialogClickListener).show();
	}

	DialogInterface.OnClickListener winDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				initNewGame();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				break;
			}
		}
	};
}
