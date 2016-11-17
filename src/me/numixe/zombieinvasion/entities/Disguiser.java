package me.numixe.zombieinvasion.entities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.api.DisguiseAPI;
import me.numixe.zombieinvasion.utils.Item;


public class Disguiser implements Listener {
	
	public static DisguiseAPI api;
		
	//@SuppressWarnings("deprecation")
	//private static MobDisguise villager = new MobDisguise(DisguiseType.VILLAGER, true);
	//@SuppressWarnings("deprecation")
	//private static MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE, true);
	
	public static void setVillager(Player p) {
		
	    //pl.getScoreboard().vill.addPlayer(p);
        //api.disguiseToAll(p, villager);
		
		/*
		 *  La score board non si deve basare sul numero di villager e zombie in generale
		 *  Ma solo quelli presenti in gioco, dunque si aggiorna in automatico secondo il count fornito dalla lobby
		 */
		
	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"odisguise " + p.getName() + " villager");
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lSei un Villager!");
        p.getInventory().setItem(0, Item.villSword);
	}
	
	public static void setZombie(Player p) {	
    
		//pl.getScoreboard().zomb.addPlayer(p);
        //api.disguiseToAll(p, zombie);
        ScreenAPI.sendMessage(p, "\u00A7a\u00A7lSei uno Zombie!");      
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"odisguise " + p.getName() + " zombie infected");
        p.getInventory().setItem(0, Item.zombSword);
	}
	
	public static void setNull(Player p) {
		
		if (p == null)
			return;
		
		/*if (pl.getScoreboard().vill.getPlayers().contains(p))
			pl.getScoreboard().vill.removePlayer(p);
		if (pl.getScoreboard().zomb.getPlayers().contains(p))
			pl.getScoreboard().zomb.removePlayer(p);*/
		p.getInventory().clear();
		//api.undisguiseToAll(p);
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "undisguise " + p.getName());
	}
	
	public static void initAPI() {		
		
		api = Bukkit.getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}
} 

