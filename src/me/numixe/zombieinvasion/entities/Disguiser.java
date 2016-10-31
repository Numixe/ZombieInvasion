package me.numixe.zombieinvasion.entities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;


public class Disguiser implements Listener {
	
	public static DisguiseAPI api;
	
	@SuppressWarnings("deprecation")
	private static MobDisguise villager = new MobDisguise(DisguiseType.VILLAGER, true);
	@SuppressWarnings("deprecation")
	private static MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE, true);
	
	private final static long heart = 2;
	
	@SuppressWarnings("deprecation")
	public static void setVillager(ActionBar bar, Player p) {
		
		villager.setCustomName("�a" + p.getName());
        api.disguiseToAll(p, villager);
        bar.message = "�a�lSei un Villager!";
        bar.sendMessage(p);
        p.setMaxHealth(heart * 3);
        p.setHealth(heart * 3);
        p.setHealthScale(heart * 3);
	}
		
	
	@SuppressWarnings("deprecation")
	public static void setZombie(ActionBar bar, Player p) {
		
		zombie.setCustomName("�c" + p.getName());
        api.disguiseToAll(p, zombie);
        bar.message = "�a�lSei uno Zombie!";
        bar.sendMessage(p);
        p.setMaxHealth(heart * 13);
        p.setHealth(heart * 13);
        p.setHealthScale(heart * 13);
	}
	
	@SuppressWarnings("deprecation")
	public static void setNull(Player p) {
		
		if (p == null)
			return;
		
		api.undisguiseToAll(p);
		p.setMaxHealth(heart * 10);
		p.setHealth(heart * 10);
        p.setHealthScale(heart * 10);
	}
	
	public static void initAPI() {		
		api = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}
	
} 

