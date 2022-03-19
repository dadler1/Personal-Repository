package Item;
import Worlds.Vector2;

public class WorldBlock {
	private BlockType type;
	private Vector2 coords;
	
	public WorldBlock(BlockType type, int xcoord, int ycoord) {
		this.type = type;
		this.coords = new Vector2(xcoord,ycoord);
	}
	
	public String toString() {
		return type.toString() + " at " + getCoordinates();
	}
	
	public String getFilePath() {
		return type.getFilePath();
	}
	
	public boolean canHarvestBlock(ItemType toolUsed) {
		if(toolUsed.getPickaxeHardness()>=type.getHardness()) {
			System.out.println("Block Harvested.");
			return true;
		}
		System.out.println("Block not Harvested.");
		return false;
		
	}
	
	public InventoryItem convertBlockToInventoryItem() {
		return new InventoryItem(type, 1);
	}
	public String getSaveData() {
		return getCoordinates() + "=" + type.toString();
	}
	
	public boolean performUtility() {
		return false;
	}
	public String getCoordinates() {
		return "(" + coords.getX() + "," + coords.getY() + ")";
	}
	
	/*public static void main(String args[]) {
		//WorldBlock block = new WorldBlock(BlockType.IRONORE, 1, 1);
		/*System.out.println(block); //testing toString()
		System.out.println(block.getSaveData()); //testing getSaveData()
		System.out.println(block.convertBlockToInventoryItem());
		System.out.println(block.canHarvestBlock(ItemType.WOODPICKAXE)); //testing canHarvestBlock. Should return false
		System.out.println(block.canHarvestBlock(ItemType.DIAMONDPICKAXE)); //testing canHarvestBlock. Should return false
		
	}
	*/
	
}
