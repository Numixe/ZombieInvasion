package me.numixe.zombieinvasion;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class ZombieInvasion extends JavaPlugin {

	public static ZombieInvasion plugin;
	public static ActionBar actionbar;
	public static ScoreboardAPI scoreboard;
	
	private Random random;
	public Lobby lobby;
	
	public void onEnable() {		
		Disguiser.initAPI();	
		plugin = this;
		actionbar = new ActionBar();
	    scoreboard = new ScoreboardAPI();
	    random = new Random();
	    lobby = new Lobby();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		Bukkit.getServer().getPluginManager().registerEvents(new EventListeners(), this);
	}
	
	public int randomInt(int start, int range) {
    	
    	return random.nextInt(range) + start;
    }
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("villager")) {
			Disguiser.setZombie(p);
		}
		return true;
        
 }
}

