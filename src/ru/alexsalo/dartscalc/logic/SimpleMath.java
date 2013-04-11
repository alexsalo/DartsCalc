package ru.alexsalo.dartscalc.logic;

import java.util.ArrayList;

public class SimpleMath {
	public int[] mean(ArrayList<int[]> m) {
		int h = m.size();
		int w = m.get(0).length;
		int[][] mas = new int[h][w];
		for (int i = 0; i < h; i++) {
			mas[i] = m.get(i);
		}
		int[] meanmas = new int[w];
		for (int j = 0; j < w; j++) {//columns
			int sum = 0;
			for (int i = 0; i < h; i++) {//rows
				sum += mas[i][j];
			}
			meanmas[j] = sum/h;
		}
		return meanmas;
	}
}
