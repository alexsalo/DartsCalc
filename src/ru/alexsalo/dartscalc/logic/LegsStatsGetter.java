package ru.alexsalo.dartscalc.logic;

import java.util.ArrayList;

public class LegsStatsGetter {

	public String getStats(ArrayList<int[]> mas) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mas.size(); i++)
			sb.append(String.valueOf(mas.get(i)[0]) + ","
					+ String.valueOf(mas.get(i)[1]) + ","
					+ String.valueOf(mas.get(i)[2]) + ","
					+ String.valueOf(mas.get(i)[3]) + ","
					+ String.valueOf(mas.get(i)[4]) + "," + "\r\n");

		return sb.toString();
	}
}
