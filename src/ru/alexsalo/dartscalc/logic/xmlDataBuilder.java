package ru.alexsalo.dartscalc.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

public class xmlDataBuilder {
	FileWriter writer;
	File DartsCalcData;
	File filecsv;
	GameModes game_mode;
	Time time = new Time();

	public xmlDataBuilder(GameModes gameMode) {
		game_mode = gameMode;
	}

	public String saveToXml(ArrayList<int[]> mas) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mas.size(); i++)
			sb.append(String.valueOf(mas.get(i)[0]) + ","
					+ String.valueOf(mas.get(i)[1]) + ","
					+ String.valueOf(mas.get(i)[2]) + ","
					+ String.valueOf(mas.get(i)[3]) + ","
					+ String.valueOf(mas.get(i)[4]) + ","
					+ String.valueOf(mas.get(i)[5]) + ","
					+ String.valueOf(mas.get(i)[6]) + "\r\n");
		time.setToNow();
		String filename = "training_" + time.format("%Y%m%d_%H%M%S") + ".csv";
		String baseDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			DartsCalcData = new File(baseDir + File.separator + "DartsCalcData"
					+ File.separator + game_mode.getGameMode());
			if (!DartsCalcData.exists())
				DartsCalcData.mkdirs();
			filecsv = new File(DartsCalcData.getAbsoluteFile(), filename);
			try {
				FileOutputStream os = new FileOutputStream(filecsv);
				OutputStreamWriter out = new OutputStreamWriter(os);
				out.write(sb.toString());
				out.close();
			} catch (IOException e) {
				Log.w("ExternalStorage", "Error writing " + filecsv, e);
			}

		}

		return sb.toString();
	}
}
