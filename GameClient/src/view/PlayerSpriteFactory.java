package view;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerSpriteFactory {

	private TextureAtlas atlas;
	
	/**
	 * Creates a new PlayerSpriteFactory that generates
	 * renderable players using a specified texture atlas
	 * @param atlas
	 */
	public PlayerSpriteFactory(FileHandle atlas) {
		this.atlas = new TextureAtlas(atlas);
	}
	
	/**
	 * Generates a player sprite with a PlayerSprite representation
	 * @param type
	 * @return new player sprite instance
	 */
	public PlayerSprite create(PlayerType type) {
		TextureRegion region = this.atlas.findRegion(type.regionName);
		
		PlayerSprite player = new PlayerSprite(region);
		return player;
	}
}
