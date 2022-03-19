package Crafting;
import Item.ItemType;

import java.util.HashMap;

import Item.BlockType;
import Item.InventoryItem;
public enum CraftCategory {
	MATERIALS,TOOLS,BLOCKS,FOOD;
	

	public static Recipe WOODPICKAXERECIPE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(BlockType.WOODPLANK,3)}, new InventoryItem(ItemType.WOODPICKAXE,1));
	public static Recipe STONEPICKAXERECIPE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(BlockType.COBBLESTONE,3)}, new InventoryItem(ItemType.STONEPICKAXE,1));
	public static Recipe IRONPICKAXERECIPE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(ItemType.IRONBAR,3)}, new InventoryItem(ItemType.IRONPICKAXE,1));
	public static Recipe DIAMONDPICKAXERECIPE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(ItemType.DIAMOND,3)}, new InventoryItem(ItemType.DIAMONDPICKAXE,1));
	

	public static Recipe STICKRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.WOODLOG,1),null}, new InventoryItem(ItemType.STICK,4));
	//public static Recipe IRONBARWITHWOODRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.IRONORE,1), new InventoryItem(BlockType.WOODPLANK,1)}, new InventoryItem(ItemType.IRONBAR,1));

	
	
	public static Recipe WOODPLANKRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.WOODLOG,1), null}, new InventoryItem(BlockType.WOODPLANK,4));
	public static Recipe WOODPICKAXE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(BlockType.WOODPLANK,3)}, new InventoryItem(ItemType.WOODPICKAXE,1));	
	public static Recipe STONEPICKAXE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(BlockType.COBBLESTONE,3)}, new InventoryItem(ItemType.STONEPICKAXE,1));	
	public static Recipe IRONPICKAXE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(ItemType.IRONBAR,3)}, new InventoryItem(ItemType.IRONPICKAXE,1));	
	public static Recipe DIAMONDPICKAXE = new Recipe(new InventoryItem[] {new InventoryItem(ItemType.STICK,2),new InventoryItem(ItemType.DIAMOND,3)}, new InventoryItem(ItemType.DIAMONDPICKAXE,1));	
	
	public static Recipe IRONRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.IRONOREBLOCK,1), null}, new InventoryItem(ItemType.IRONBAR,1));
	public static Recipe DIAMONDRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.DIAMONDOREBLOCK,1), null}, new InventoryItem(ItemType.DIAMOND,1));
	
	//Block Recipes
	public static Recipe GRASSRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.DIRT,3), null}, new InventoryItem(BlockType.GRASS,1));
	public static Recipe DIRTRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.GRASS,3), null}, new InventoryItem(BlockType.DIRT,1));
	public static Recipe SNOWYGRASSRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.GRASS,3), null}, new InventoryItem(BlockType.SNOWGRASS,1));
	public static Recipe SNOWYDIRTRECIPE = new Recipe(new InventoryItem[] {new InventoryItem(BlockType.DIRT,3), null}, new InventoryItem(BlockType.SNOWDIRT,1));
	
	
	
	public static HashMap<String,Recipe> recipes;
	static {
		recipes = new HashMap<String,Recipe>();
		recipes.put("Stick", STICKRECIPE);
		recipes.put("WoodPlank", WOODPLANKRECIPE);
		recipes.put("WoodPickaxe", WOODPICKAXE);
		recipes.put("StonePickaxe", STONEPICKAXE);
		recipes.put("IronPickaxe",IRONPICKAXE);
		recipes.put("DiamondPickaxe", DIAMONDPICKAXE);
		recipes.put("IronBar", IRONRECIPE);
		recipes.put("Diamond", DIAMONDRECIPE);
		
		
		recipes.put("Grass", GRASSRECIPE);
		recipes.put("Dirt", DIRTRECIPE);
		recipes.put("SnowGrass", SNOWYGRASSRECIPE);
		recipes.put("SnowDirt", SNOWYDIRTRECIPE);
	}

	
	


	
	public String toString() {
		switch(this) {
		case MATERIALS: return "materials";
		case TOOLS: return "tools";
		case BLOCKS: return "blocks";
		case FOOD: return "food";
		default: return "n/a";
		}
	}
	
	public Recipe[] getAllRecipesFromCategory() {
		Recipe[] allRecipes;
		switch(this) {
		case MATERIALS:
			//allRecipes = new Recipe[] {STICKRECIPE,IRONBARWITHWOODRECIPE};
		case TOOLS:
			allRecipes = new Recipe[] {WOODPICKAXERECIPE,STONEPICKAXERECIPE,IRONPICKAXERECIPE,DIAMONDPICKAXERECIPE};
		case BLOCKS:
			allRecipes = new Recipe[] {WOODPLANKRECIPE};
		case FOOD:
			allRecipes = new Recipe[] {};
		default:
			allRecipes = new Recipe[] {};
		}
		return allRecipes;
	}
}
