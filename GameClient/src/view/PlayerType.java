package view;

/**
 * Identifies the player's appearance by the region name in the character atlas.
 */
public enum PlayerType
{

	/**
	 * Male appearance, type 1
	 */
	male_a("male_a"),
	/**
	 * Male appearance, type 2
	 */
	male_b("male_b");

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
