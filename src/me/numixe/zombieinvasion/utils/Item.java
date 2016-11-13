package me.numixe.zombieinvasion.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	
	public final static ItemStack villSword = villSword();
	public final static ItemStack zombSword = zombSword();
	
	private static ItemStack villSword() {
		ItemStack villSword = new ItemStack(Material.WOOD_SWORD);
		ItemMeta meta = villSword.getItemMeta();
		meta.setDisplayName("\u00a7a\u00a7lVillager's Sword");
		villSword.setItemMeta(meta);
		return villSword;
		
	}
	
	
	private static ItemStack zombSword() {
		ItemStack zombSword = new ItemStack(Material.WOOD_AXE);
		ItemMeta meta = zombSword.getItemMeta();
		meta.setDisplayName("\u00a7c\u00a7lZombie's Axe");
		zombSword.setItemMeta(meta);
		return zombSword;
		
	}
}
