package ru.alexsalo.dartscalc.logic;

import java.util.HashMap;

public class Achievements {
	private String[] badges = new String[]{"������", "3 ���������", "2 ���������", "1 ���������",
			"3 ������", "2 ������", "1 ������", "���", "������ ������"};
	private HashMap<GameModes, int[]> table = new HashMap<GameModes, int[]>();
	
	public Achievements(){
		table.put(GameModes.BIG_ROUND, new int[]{0,250,320,390,420,550,660,750,900});
	}
	public String getAchievement(boolean male, GameModes gameMode, int score){
		int i;
		int [] scoretable = table.get(gameMode);
		for (i = 0; i<=15; i++){
			if (score < scoretable[i])
				break;
		}
		return badges[i];
	}
}
