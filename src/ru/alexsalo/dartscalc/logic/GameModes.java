package ru.alexsalo.dartscalc.logic;

public enum GameModes{
	BIG_ROUND("biground"), NUMBERS("numbers"), SECTOR20("sector20"),
		AMERICAN_CRICKET("american_cricket"), CLASSIC_501("classic_501");
	private String gameMode;
	private GameModes(String gameMode) {
	    this.gameMode = gameMode;
	}
	 
	public String getGameMode() {
	    return gameMode;
	}
}
