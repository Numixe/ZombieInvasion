package me.numixe.zombieinvasion;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.robingrether.idisguise.api.DisguiseAPI;


public class ZombieInvasion extends JavaPlugin {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<Player> isZombie = new ArrayList();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<Player> isVillager = new ArrayList();
	
	public ZombieInvasion plugin;
	public Disguiser disguiser;
	public static DisguiseAPI api;
	
	public void onEnable() {
		plugin = this;
	    api = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
		System.out.println("ZombieInvasion Attivo!");
		disguiser = new Disguiser();
		Bukkit.getServer().getPluginManager().registerEvents(new Disguiser(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("villager")) {
			disguiser.beVillager(p);
		}
		return true;
        
 }
}

