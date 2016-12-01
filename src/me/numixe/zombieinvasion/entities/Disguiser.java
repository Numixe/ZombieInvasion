package me.numixe.zombieinvasion.entities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.api.DisguiseAPI;
import me.numixe.zombieinvasion.utils.Item;


public class Disguiser implements Listener {
	
	public static DisguiseAPI api;
	private final static int heart = 2;
		
	//@SuppressWarnings("deprecation")
	//private static MobDisguise villager = new MobDisguise(DisguiseType.VILLAGER, true);
	//@SuppressWarnings("deprecation")
	//private static MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE, true);
	
	@SuppressWarnings("deprecation")
	public static void setVillager(Player p, ScoreboardAPI teams) {
		
	    teams.vill.addPlayer(p);
        //api.disguiseToAll(p, villager);
	    p.setMaxHealth(heart * 10);
	    p.setHealthScale(heart * 10);
		p.setHealth(heart * 10);
		
	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"odisguise " + p.getName() + " villager");
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lYou're a Villager!");
        p.getInventory().setItem(0, Item.villSword);
        p.getInventory().setItem(8, Item.villSkull);
	}
	
	@SuppressWarnings("deprecation")
	public static void setZombie(Player p, ScoreboardAPI teams) {
    
		teams.zomb.addPlayer(p);
        //api.disguiseToAll(p, zombie);
		p.setMaxHealth(heart * 10);
		p.setHealthScale(heart * 10);
		p.setHealth(heart * 10);
			
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lYou're a Zombie!");      
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"odisguise " + p.getName() + " zombie infected");
        p.getInventory().setItem(0, Item.zombSword);
        p.getInventory().setItem(8, Item.zombSkull);
        
	}
	
	
	@SuppressWarnings("deprecation")
	public static void setOriginalZombie(Player p, ScoreboardAPI teams) {
    
		teams.zomb.addPlayer(p);
        //api.disguiseToAll(p, zombie);
		p.setMaxHealth(heart * 20);
		p.setHealthScale(heart * 20);
		p.setHealth(heart * 20);
		
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lYou're a ORIGINAL Zombie!");      
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"odisguise " + p.getName() + " zombie");
        p.getInventory().setItem(0, Item.zombSword);
        p.getInventory().setItem(8, Item.originalzombSkull);     
	}
	
	@SuppressWarnings("deprecation")
	public static void setNull(Player p, ScoreboardAPI teams) {
		
		if (p == null)
			return;
		
		if (teams.vill.getPlayers().contains(p))
			teams.vill.removePlayer(p);
		if (teams.zomb.getPlayers().contains(p))
			teams.zomb.removePlayer(p);
		p.getInventory().clear();
		//api.undisguiseToAll(p);
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "undisguise " + p.getName());
		p.setMaxHealth(heart * 10);
		p.setHealth(heart * 10);
		p.setHealthScale(heart * 10);
	}
	
	public static void initAPI() {		
		
		api = Bukkit.getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}
} 

