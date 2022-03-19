package Item;

public class InventoryItem {
	private int quantity;
	private EntityType type;
	
	public static final int MAX_STACK = 64;
	public InventoryItem(EntityType type, int quantity) {
		this.type = type;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return quantity + " " + this.type.toString();
	}
	
	/*public boolean addToStack(int quantity) {
		if(this.quantity + quantity > 64 || this.quantity + quantity <0) {
			return false;
		}
		if(this.quantity + quantity == 0)
			
	}*/
	
	public String getName() {
		return this.type.getName();
	}
	
	public EntityType getType() {
		return type;
	}
	
	public String getFilePath() {
		return this.type.getFilePath();
	}
	
	public int getItemID() {
		return this.type.getItemID();
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public String getSaveData() {
		String returnString = new String();
		returnString+= type.toString() + " " + getQuantity();
		return returnString;
	}
	
	public int addToStack(int amount) {
		int remainder = 0;
		if(amount + quantity >64) {
			remainder = amount + quantity - 64;
			quantity = 64;
			return remainder;
		}
		quantity+=amount;
		return 0;
	}
	
	public int removeFromStack(int amount) {
		//System.out.print("Initial " +quantity);
		int remainder = amount;
		if(quantity>=amount) {
			//Quantity will be a positive number representing how many items are still remaining that
			//were not subtracted from this particular stack
			
			quantity-=amount;
			//System.out.print("Initial " +quantity);
			return 0;
		}
		remainder -= quantity;
		quantity = 0;
		
		//System.out.print("Initial " +quantity);
		return Math.abs(remainder);
	}
	
	
	public boolean isBlock() {
		return this.type instanceof BlockType;
	}
	
	public boolean hasInHandUtility() {
		if(!isBlock()) {
			if(type.hasUtility()) {
				return true;
			}
		}
		return false;
	}
	public void performUtility() {
		return;
	}
	public WorldBlock convertInventoryItemToBlock(int xcoord, int ycoord) {
		if(!isBlock()) {
			return null;
		}
		return new WorldBlock((BlockType)type, xcoord, ycoord);
	}
	
	
	public static void main(String args[]) {
		InventoryItem block = new InventoryItem(BlockType.COALORE,1);
		System.out.println(block.convertInventoryItemToBlock(1,1).toString()); //testing convertInventoryItemToBlock
		System.out.println(block);
	}
	
}
