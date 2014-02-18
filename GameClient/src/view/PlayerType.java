package view;

/**
 * Identifies the player's appearance by the region name in the
 * character atlas.
 */
public enum PlayerType {

	MALEA("male_a"),
	MALEB("male_b");
	
	public final String regionName;
	
	private PlayerType(final String name) {
		this.regionName = name;
	}
	
}
