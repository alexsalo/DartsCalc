package ru.alexsalo.dartscalc.logic;

import java.util.Scanner;

import android.util.SparseArray;

public class OutChart501 {
	private SparseArray<String> out_chart;
	
	public OutChart501(String s){
		out_chart = new SparseArray<String>();
		Scanner scanner = new Scanner(s);
		int i = 2;
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			out_chart.append(i, line);
			i++;
		}
	}
	
	public String getOut(int n){
		if (out_chart.get(n) != null)
			return out_chart.get(n);
		else return "Impossible";
	}
}
