package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;


public class Disguiser implements Listener {
	
	public static DisguiseAPI api;
	
	@SuppressWarnings("deprecation")
	static MobDisguise villager = new MobDisguise(DisguiseType.VILLAGER, true);
	@SuppressWarnings("deprecation")
	static MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE, true);
	
	@SuppressWarnings("deprecation")
	public static void setVillager(Player p) {
            api.disguiseToAll(p, villager);
            actionbar.message = "§a§lSei un Villager!";
            actionbar.sendMessage(p);
			
		}
		
	
	@SuppressWarnings("deprecation")
	public static void setZombie(Player p) {
        api.disguiseToAll(p, zombie);
        actionbar.message = "§a§lSei uno Zombie!";
        actionbar.sendMessage(p);
		
	  }
	
	public static void initAPI() {		
		api = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}
	} 

