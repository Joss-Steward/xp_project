package view;

/**
 * Identifies the player's appearance by the region name in the character atlas.
 */
public enum PlayerType
{

	/**
	 * Male appearance, type 1
	 */
	MALEA("male_a"),
	/**
	 * Male appearance, type 2
	 */
	MALEB("male_b");

	/**
	 * The name of the region in the loaded {@link:TextureAtlas} used by a
	 * {@link:PlayerSpriteFactory}
	 */
	public final String regionName;

	private PlayerType(final String name)
	{
		this.regionName = name;
	}

}
