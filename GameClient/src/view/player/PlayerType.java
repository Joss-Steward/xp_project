package view.player;

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
	male_b("male_b"),
	/**
	 * Female appearance, type 1
	 */
	female_a("female_a"),
	/**
	 * Female appearance, type 2
	 */
	female_b("female_b"),
	/**
	 * Knight who has a straw hat
	 */
	knight_with_straw_hat("knight_with_straw_hat"),
	/**
	 * Magic guy
	 */
	Magi("Magi"),
	/**
	 * Ninja
	 */
	ninja("ninja"),
	/**
	 * Merlin
	 */
	merlin("merlin"),
	/**
	 * Andy
	 */
	andy("andy"),
	/**
	 * Dave
	 */
	dave("dave"),
	/**
	 * Party Guy Frank
	 */
	partyguy("partyguy"),
	/**
	 * Matt
	 */
	matt_kujo("matt_kujo"),
	/**
	 * Ga
	 */
	ga("ga");
	

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
