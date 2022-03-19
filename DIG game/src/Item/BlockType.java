package Item;
import java.util.ArrayList;
import java.util.Random;
public enum BlockType implements EntityType {
	CRAFTINGTABLE, FURNACE, DOOR, //UTILITY BLOCKS
	WOODLOG, WOODPLANK, DIRT, GRASS, STONE, COBBLESTONE, LEAVES, LAVA,SNOWDIRT, //WORLD BLOCKS
	COALORE,DIAMONDOREBLOCK,IRONOREBLOCK, //ORE BLOCKS
	AIR, /*COBBLESTONE, */CAVEBACKGROUND,SNOWGRASS; //ABSTRACT
	
	public static final String IMAGEEXTENSION = ".png";
	public static final String IMAGEDIRECTORY = "items/";
	
	public static final double ONEHUNDREDPERCENT = 100;
	
	@Override
	public String toString() {
		switch(this) {
		case CRAFTINGTABLE: return "craftingtable";
		case FURNACE: return "furnace";
		case DOOR: return "door";
		case WOODLOG: return "WoodLog";
		case WOODPLANK: return "woodplank";
		case DIRT: return "Dirt";
		case SNOWDIRT: return "SnowDirt";
		case GRASS: return "grass";
		case STONE: return "stone";
		case COBBLESTONE: return "CobbleStone";
//		case COBBLESTONE: return "CobbleStone";
		case LEAVES: return "Leaves";
		case COALORE: return "Coal";
		
		case LAVA: return "Lava";
		case CAVEBACKGROUND: return "CaveBackground";
		case SNOWGRASS: return "SnowGrass";
		case DIAMONDOREBLOCK: return "DiamondOreBlock";
		case IRONOREBLOCK: return "IronOreBlock";
		
		case AIR: return "air";
		default: return "NoImage";
		}
	}
	
	@Override
	public String getName() {
		switch(this) {
		case CRAFTINGTABLE: return "CraftingTable";
		case FURNACE: return "Furnace";
		case DOOR: return "Door";
		case WOODLOG: return "WoodLog";
		case WOODPLANK: return "WoodPlank";
		case DIRT: return "Dirt";
		case GRASS: return "Grass";
		case STONE: return "Stone";
		case COBBLESTONE: return "CobbleStone";
		case SNOWDIRT: return "SnowDirt";
//		case COBBLESTONE: return "CobbleStone";
		case LEAVES: return "Leaves";
		case COALORE: return "Coal";
		
		case LAVA: return "Lava";
		case CAVEBACKGROUND: return "CaveBackground";
		case SNOWGRASS : return "SnowGrass";
		case DIAMONDOREBLOCK: return "DiamondOreBlock";
		case IRONOREBLOCK: return "IronOreBlock";
		default: return "NoImage";
		}
	}
	
	@Override
	public int getItemID() {
		switch(this) {
		case CRAFTINGTABLE: return 1;
		case FURNACE: return 2;
		case DOOR: return 3;
		case WOODLOG: return 4;
		case WOODPLANK: return 5;
		case DIRT: return 6;
		case GRASS: return 7;
		case STONE: return 8;
		case LEAVES: return 9;
		case COALORE: return 10;
		
		case LAVA: return 13;
		case CAVEBACKGROUND: return 14;
		default: return 0;
		}
	}
	
	@Override
	public boolean hasUtility() {
		switch(this) {
		case CRAFTINGTABLE: return true;
		case FURNACE: return true;
		case DOOR: return true;
		default: return false;
		}
	}

	@Override
	public String getFilePath() {
		return IMAGEDIRECTORY + this + IMAGEEXTENSION;
	}
	
	
	public ArrayList<InventoryItem> getDrop() {
		ArrayList<InventoryItem> drops = new ArrayList<InventoryItem>();
		switch(this) {
		case CRAFTINGTABLE: 
			drops.add(new InventoryItem(CRAFTINGTABLE,1));
			break;
		case FURNACE:
			drops.add(new InventoryItem(FURNACE, 1));
			break;
		case DOOR: 
			drops.add(new InventoryItem(DOOR,1));
			break;
		case WOODLOG: 
			drops.add(new InventoryItem(WOODLOG,1));
			break;
		case WOODPLANK:
			drops.add(new InventoryItem(WOODLOG,1));
			break;
		case DIRT: 
			drops.add(new InventoryItem(DIRT,1));
			break;
		case GRASS:
			drops.add(new InventoryItem(DIRT,1));
			break;
		case STONE: 
			drops.add(new InventoryItem(STONE,1));
			break;
		case LEAVES:
			drops.add(new InventoryItem(LEAVES,1));
			break;
		case COALORE: 
			drops.add(new InventoryItem(ItemType.COAL,amountDropped()));
			break;
		case LAVA:
			drops.add(new InventoryItem(LAVA,1));
			break;
		default: break;
		}
		return drops;
	}
	
	//Represents amount of items dropped by an ore within the getDrop function
	private int amountDropped() {
		int dropCount = 0;
		int dropRate = 100;
		for(int i = 0; i < 3; i++) {
			if(isDropped(dropRate)) {
				dropCount++;
			}
			dropRate/=10;
		}
		return dropCount;
	}
	
	//Helper function used inside amountDropped
	private boolean isDropped(double percentChance) {
		Random rand = new Random();
		int rate = rand.nextInt((int)ONEHUNDREDPERCENT)+1;
		if((double)rate<=percentChance)
			return true;
		return false;
		
	}
	
	public double getHardness() {
		switch(this) {
		//to add constants
		case FURNACE: return 2.00;
		case STONE: return 2.00;
		case LEAVES: return 0.50;
		case COALORE: return 1.50;
		
		default: return 1.00;
		}
	}
	
	/*public static void main(String args[]) {
		
		//testing file path
		System.out.println(BlockType.COALORE.getFilePath());
	}
	*/
	
}
