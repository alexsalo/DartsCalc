package ru.alexsalo.dartscalc.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

import ru.alexsalo.dartscalc.R;
import ru.alexsalo.dartscalc.listners.EraseListener;
import ru.alexsalo.dartscalc.listners.NumberListner;
import ru.alexsalo.dartscalc.logic.Achievements;
import ru.alexsalo.dartscalc.logic.GameModes;
import ru.alexsalo.dartscalc.logic.LegsStatsGetter;
import ru.alexsalo.dartscalc.logic.OutChart501;
import ru.alexsalo.dartscalc.logic.SimpleMath;
import ru.alexsalo.dartscalc.logic.xmlDataBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class GameActivity extends Activity {
	protected boolean hand = true; // true - right, false - left hand
	protected boolean sex = true; // true - male, false - female
	protected TextView current_attempt;
	protected TextView current_score_game;
	protected TextView current_leg1;
	protected TextView current_leg2;
	protected TextView current_leg3;
	protected ImageView tv_confirm;
	protected TextView tv_currentLeg;
	protected TextView[] tv_numbers_mas;
	protected TextView tv_rem_score;
	protected CharSequence dummy_zero = "00";
	protected byte leg;
	protected int legnum;
	protected int score_leg1;
	protected int score_leg2;
	protected int score_leg3;
	protected int score_game;
	protected int cur_score;
	protected int sum;
	protected GameModes gameMode;
	protected ArrayList<int[]> score_data = new ArrayList<int[]>();
	// protected View.OnTouchListener number_listner;
	protected NumberListner number_listner;
	protected Context context;
	protected Achievements achievement;
	protected SimpleMath math;
	protected DbxAccountManager mDbxAcctMgr;
	protected String syncDir;
	protected OutChart501 out_chart501;
	
	protected String login_name; 
	protected String login_cell;

	final public String APP_KEY = "ackpz991o5f69y9";
	final public String APP_SECRET = "74mzwfyknxvwf3g";
	static final int REQUEST_LINK_TO_DBX = 0; // This value is up to you

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_rule:
			showRule();
			return true;
		case R.id.menu_newgame:
			initNewGame();
			return true;
		case R.id.menu_giveup:
			if (!score_data.isEmpty())
				saveGameResults("������");
			initNewGame();
			return true;
		case R.id.menu_left_hand:
			if (!item.isChecked())
				item.setChecked(true);
			hand = false;
			return true;
		case R.id.menu_right_hand:
			if (!item.isChecked())
				item.setChecked(true);
			hand = true;
			return true;
		case R.id.menu_sex_female:
			if (!item.isChecked())
				item.setChecked(true);
			sex = false;
			return true;
		case R.id.menu_sex_male:
			if (!item.isChecked())
				item.setChecked(true);
			sex = true;
			return true;
		/*case R.id.menu_save_to_disk:
			saveGameResults();
			return true;*/
		case R.id.dropbox_link:
			onClickLinkToDropBox();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classictrain501);
		achievement = new Achievements();
		math = new SimpleMath();

		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(),
				APP_KEY, APP_SECRET);

		current_attempt = (TextView) findViewById(R.id.current_attempt_tv);
		current_score_game = (TextView) findViewById(R.id.current_score_tv);
		current_leg1 = (TextView) findViewById(R.id.current_leg1_tv);
		current_leg2 = (TextView) findViewById(R.id.current_leg2_tv);
		current_leg3 = (TextView) findViewById(R.id.current_leg3_tv);
		tv_currentLeg = (TextView) findViewById(R.id.current_leg);
		tv_rem_score = (TextView) findViewById(R.id.tv_remaining);

		number_listner = new NumberListner(current_attempt, dummy_zero);

		tv_numbers_mas = new TextView[10];
		tv_numbers_mas[0] = (TextView) findViewById(R.id.tv0);
		tv_numbers_mas[1] = (TextView) findViewById(R.id.tv1);
		tv_numbers_mas[2] = (TextView) findViewById(R.id.tv2);
		tv_numbers_mas[3] = (TextView) findViewById(R.id.tv3);
		tv_numbers_mas[4] = (TextView) findViewById(R.id.tv4);
		tv_numbers_mas[5] = (TextView) findViewById(R.id.tv5);
		tv_numbers_mas[6] = (TextView) findViewById(R.id.tv6);
		tv_numbers_mas[7] = (TextView) findViewById(R.id.tv7);
		tv_numbers_mas[8] = (TextView) findViewById(R.id.tv8);
		tv_numbers_mas[9] = (TextView) findViewById(R.id.tv9);

		ImageView tv_erase = (ImageView) findViewById(R.id.tv_erase);
		tv_erase.setOnTouchListener(new EraseListener(current_attempt,
				dummy_zero));

		tv_confirm = (ImageView) findViewById(R.id.tv_confirm);
		setConfirmTouchListner();

		out_chart501 = new OutChart501(getApplicationContext().getString(
				R.string.out_chart_501));

		SharedPreferences login_pref = getSharedPreferences("login_sp", 0);
		login_name = login_pref.getString("login_name", "username");
		login_cell = login_pref.getString("login_cell", "12345");
		syncDir = login_name + "_" + login_cell + "_" + android.os.Build.MODEL
				+ "_" + android.os.Build.MANUFACTURER;

		moreOnCreate();
		initNewGame();
	}

	protected abstract void moreOnCreate();

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				mDbxAcctMgr.unlink();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// No button clicked
				break;
			}
		}
	};

	public void onClickLinkToDropBox() {
		if (!mDbxAcctMgr.hasLinkedAccount())
			mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"You already have linked account, do you want yo unlink?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(getApplicationContext(),
						"You was successfulyy connected with Dropbox account",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Link to Dropbox failed or was cancelled.",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	protected void saveResultToDb(File f) {
		if (mDbxAcctMgr.hasLinkedAccount()) {
			String syncPath =syncDir + File.separator + gameMode.getGameMode();
			DbxFile dfile;
			try {
				DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr
						.getLinkedAccount());
				dbxFs.createFolder(new DbxPath(syncPath));
				dfile = dbxFs.create(new DbxPath(syncPath + File.separator
						+ f.getName()));
				dfile.writeFromExistingFile(f, false);
				dfile.close();
			} catch (IOException e) {
			}
			;
		}
	}

	abstract void setConfirmTouchListner();

	abstract void initNewGame();

	protected void initLegViews() {
		current_leg1.setText(dummy_zero);
		current_leg2.setText(dummy_zero);
		current_leg3.setText(dummy_zero);
	}

	protected void ShowLegViewScores() {
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
	}

	protected void writeLegResults() {
		int[] mas = new int[8];
		mas[0] = score_leg1;
		mas[1] = score_leg2;
		mas[2] = score_leg3;
		mas[3] = sum;
		mas[4] = score_game;
		mas[5] = legnum;
		mas[6] = hand ? 1 : 0;
		mas[7] = sex ? 1 : 0;
		score_data.add(mas);
		leg = 1;
		erase_score_leg_results();
		initLegViews();
	}

	protected void erase_score_leg_results() {
		score_leg1 = 0;
		score_leg2 = 0;
		score_leg3 = 0;
	}

	abstract void showRule();

	protected void setNumberListner() {
		for (int i = 0; i <= 9; i++) {
			tv_numbers_mas[i].setTag(i);
			tv_numbers_mas[i].setOnTouchListener(number_listner);
		}
	}

	protected void setDummyNumberListner() {
		for (int i = 0; i <= 9; i++) {
			tv_numbers_mas[i].setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return false;
				}
			});
		}
	}

	DialogInterface.OnClickListener winDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			saveGameResults(null);

			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				initNewGame();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				break;
			}
		}
	};

	protected void showWinnerResults(int dartnum, GameModes gamemode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AlertDialogCustom));
		LegsStatsGetter mLSG = new LegsStatsGetter();
		String s = achievement.getAchievement(sex, gamemode, score_game);
		builder.setMessage(
				"������!\n" + "�� ��������� �������� ��: " + s + "\n"
						+ "��������� " + dartnum
						+ " �������\n ���������� �����:\n"
						+ mLSG.getStats(score_data) + "��� ������?")
				.setPositiveButton("��� ���", winDialogClickListener)
				.setNegativeButton("������", winDialogClickListener).show();
	}

	private void saveGameResults(String note) {
		int tmp[] = math.mean(score_data); // get mean
		score_data.add(tmp);

		xmlDataBuilder xmlsaver = new xmlDataBuilder(gameMode);
		Toast.makeText(getApplicationContext(), "������ ���������",
				Toast.LENGTH_LONG).show();
		File file = xmlsaver.saveToXml(score_data, note);
		saveResultToDb(file);
	}

}
