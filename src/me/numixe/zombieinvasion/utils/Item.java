package me.numixe.zombieinvasion.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	
	public final static ItemStack villSword = villSword();
	public final static ItemStack zombSword = zombSword();
	
	public static void villagerItem(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(0, villSword);
	}
	
	public static void zombieItem(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(0, zombSword);
	}
	
	private static ItemStack villSword() {
		ItemStack villSword = new ItemStack(Material.STICK);
		ItemMeta meta = villSword.getItemMeta();
		meta.setDisplayName("\u00a7a\u00a7lVillager's Sword");
		meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		villSword.setItemMeta(meta);
		return villSword;
		
	}
	
	
	private static ItemStack zombSword() {
		ItemStack zombSword = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = zombSword.getItemMeta();
		meta.setDisplayName("\u00a7c\u00a7lZombie's Sword");
		meta.addEnchant(Enchantment.LUCK, 1, true);
		villSword.setItemMeta(meta);
		return villSword;
		
	}
}
