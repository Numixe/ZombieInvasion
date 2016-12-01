package me.numixe.zombieinvasion.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Item {
	
	public final static ItemStack villSword = villSword();
	public final static ItemStack zombSword = zombSword();
	public final static ItemStack villSkull = villSkull();
	public final static ItemStack zombSkull = zombSkull();
	public final static ItemStack originalzombSkull = originalzombSkull();
	
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
	
	private static ItemStack villSkull() {
       ItemStack skull = new ItemStack(Material.SKULL_ITEM);
       skull.setDurability((short)3);
       SkullMeta sm = (SkullMeta) skull.getItemMeta();
       sm.setOwner("MHF_Villager");
       sm.setDisplayName("\u00a7a\u00a7lYou're a Villager!");
       skull.setItemMeta(sm);
       return skull;
 
    }
	
	private static ItemStack zombSkull() {
	       ItemStack skull = new ItemStack(Material.SKULL_ITEM);
	       skull.setDurability((short)3);
	       SkullMeta sm = (SkullMeta) skull.getItemMeta();
	       sm.setOwner("scraftbrothers11");
	       sm.setDisplayName("\u00a7c\u00a7lYou're a Zombie!");
	       skull.setItemMeta(sm);
	       return skull;
	 
	    }
	
	private static ItemStack originalzombSkull() {
	       ItemStack skull = new ItemStack(Material.SKULL_ITEM);
	       skull.setDurability((short)3);
	       SkullMeta sm = (SkullMeta) skull.getItemMeta();
	       sm.setOwner("MHF_Zombie");
	       sm.setDisplayName("\u00a7c\u00a7lYou're a Zombie!");
	       skull.setItemMeta(sm);
	       return skull;
	 
	    }
		
}
