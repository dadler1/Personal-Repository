package Item;

public enum ItemType implements EntityType {
	STICK, COAL, IRONBAR, DIAMOND, //MATERIALS
	WOODPICKAXE, STONEPICKAXE, IRONPICKAXE, DIAMONDPICKAXE; //PICKAXES
	
	public static final String IMAGEDIRECTORY = "items/";
	public static final String IMAGEEXTENSION = ".png";
	
	@Override
	public String toString() {
		switch(this) {
		case STICK: return "Stick";
		case COAL: return "coal";
		case IRONBAR: return "IronBar";
		case DIAMOND: return "Diamond";
		case WOODPICKAXE: return "WoodPickaxe";
		case STONEPICKAXE: return "StonePickaxe";
		case IRONPICKAXE: return "IronPickaxe";
		case DIAMONDPICKAXE: return "DiamondPickaxe";
		default: return "n/a";
		}
	}
	
	@Override
	public String getName() {
		switch(this) {
		case STICK: return "Stick";
		case COAL: return "Coal";
		case IRONBAR: return "IronBar";
		case DIAMOND: return "Diamond";
		case WOODPICKAXE: return "Woodpickaxe";
		case STONEPICKAXE: return "StonePickaxe";
		case IRONPICKAXE: return "IronPickaxe";
		case DIAMONDPICKAXE: return "DiamondPickaxe";
		default: return "n/a";
		}
	}
	
	@Override
	public String getFilePath() {
		return IMAGEDIRECTORY + this + IMAGEEXTENSION;
	}
	
	@Override
	public int getItemID() {
		switch(this) {
		case STICK: return 51;
		case COAL: return 52;
		case IRONBAR: return 53;
		case DIAMOND: return 54;
		case WOODPICKAXE: return 55;
		case STONEPICKAXE: return 56;
		case IRONPICKAXE: return 57;
		case DIAMONDPICKAXE: return 58;
		default: return 0;
		}
	}
	
	@Override
	public boolean hasUtility() {
		return false; //Nothing item currently has a utility
	}
	
	public double getPickaxeHardness() {
		switch(this) {
		case WOODPICKAXE: return 2.00;
		case STONEPICKAXE: return 2.50;
		case IRONPICKAXE: return 3.00;
		case DIAMONDPICKAXE: return 4.00;
		default: return 1.00;
		}
	}
	
	public static void main(String args[]) {
		
	}
	
	
	
}
