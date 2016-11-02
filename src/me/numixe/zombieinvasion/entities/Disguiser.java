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
	public static void setVillager(Player p) {	
		
        api.disguiseToAll(p, villager);
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lSei un Villager!");
        p.setMaxHealth(heart * 3);
        p.setHealth(heart * 3);
        p.setHealthScale(heart * 3);
	}
	
	@SuppressWarnings("deprecation")
	public static void setZombie(Player p) {	
		
        api.disguiseToAll(p, zombie);
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lSei uno Zombie!");
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
		
		api = Bukkit.getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}
	
} 

