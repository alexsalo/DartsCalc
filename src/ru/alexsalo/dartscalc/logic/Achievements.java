package ru.alexsalo.dartscalc.logic;

import java.util.HashMap;

public class Achievements {
	private String[] badges = new String[] { "Ничего", "3 Юношеский",
			"2 Юношеский", "1 юношеский", "3 разряд", "2 разряд", "1 разряд",
			"КМС", "Мастер Спорта" };
	private HashMap<GameModes, int[]> table = new HashMap<GameModes, int[]>();

	public Achievements() {
		table.put(GameModes.BIG_ROUND, new int[] { -1, 180, 250, 250, 320, 320,
				390, 360, 420, 450, 550, 580, 660, 650, 750, 810, 900 });
	}

	public String getAchievement(boolean male, GameModes gameMode, int score) {
		if (table.containsKey(gameMode)) {
			int i = 0;
			int[] scoretable = table.get(gameMode);
			if (male)
				for (i = 0; i <= 16; i += 2)
					if (score < scoretable[i])
						break;
					else
						for (i = 1; i <= 16; i += 2)
							if (score < scoretable[i])
								break;
			return male ? badges[(i / 2) - 1] : badges[(i / 2)];
		}
		else return "Недоступно";
	}
}
