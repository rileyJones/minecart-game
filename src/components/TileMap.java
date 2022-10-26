package components;

import ecs.Component;
import ecs.TRAIT;

public class TileMap extends Component{

	private int width, height;
	private float tileWidth, tileHeight;
	
	public enum TILE {
		EMPTY,			//0
		UI,				//1
		GROUND,			//2
		BLOCK,			//3
		TRACK_VERT,		//4
		TRACK_HOR,		//5
		TRACK_DL,		//6
		TRACK_DR,		//7
		TRACK_UL,		//8
		TRACK_UR,		//9
		TRACK_SWAP,		//A
		TRACK_STOP,		//B
		SPAWN_PLAYER,	//C
		SPAWN_ENEMY,	//D
		HOLE,			//E
		BUTTON,			//F
		BLOCK_SPECIAL_0,//G
		BLOCK_SPECIAL_1,//H
		BLOCK_SPECIAL_2,//I
		BLOCK_SPECIAL_3,//J
	}
	
	public enum DIRECTION {
		NONE,
		UP,
		DOWN,
		LEFT,
		RIGHT,
		UP_LEFT,
		UP_RIGHT,
		DOWN_LEFT,
		DOWN_RIGHT,
	}
	
	TILE[][] tiles;
	public DIRECTION[][] paths;
	public double[][] distance;
	
	public TileMap(int width, int height, float tileWidth, float tileHeight) {
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		tiles = new TILE[width][height];
		paths = new DIRECTION[width][height];
		distance = new double[width][height];
	}
	public TileMap(float tileWidth, float tileHeight, int width, int[] tiles) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.width = width;
		this.height = tiles.length/width;
		this.tiles = new TILE[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				this.tiles[x][y] = TILE.values()[tiles[x+width*y]];
			}
		}
		paths = new DIRECTION[width][height];
		distance = new double[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getTileWidth() {
		return tileWidth;
	}
	
	public float getTileHeight() {
		return tileHeight;
	}
	
	public boolean setTile(int x, int y, TILE t) {
		if(x >= width || y >= height) {
			return false;
		} else {
			tiles[x][y] = t;
			return true;
		}
	}
	
	public TILE getTile(int x, int y) {
		return tiles[x][y];
	}
	
	@Override
	protected Component combine(Component other) {
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		return this;
	}

	@Override
	public Component modify(Component other) {
		return this;
	}

	@Override
	public Component set(Component other) {
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.TILEMAP;
	}

	@Override
	public Component clone() {
		return null;
	}

}