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
	public static EventListeners event;
	
	private Random random;
	public Lobby lobby;
	
	public void onEnable() {		
		Disguiser.initAPI();	
		plugin = this;
		actionbar = new ActionBar();
	    scoreboard = new ScoreboardAPI();
	    event = new EventListeners();
	    random = new Random();
	    lobby = new Lobby();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		Bukkit.getServer().getPluginManager().registerEvents(new EventListeners(), this);
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public int randomInt(int start, int range) {
    	
    	return random.nextInt(range) + start;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		
		if (cmd.getName().equalsIgnoreCase("zombieinvasion")) {
			if (args.length == 0) {
				p.sendMessage("§6ZombieInvasion> " + "§7Usa una variabile!");
			} else if (args.length >= 1) {
				
				if (args[0].equalsIgnoreCase("setspawn")) {
					event.setSpawn(p);
				}
				
				if (args[0].equalsIgnoreCase("spawn")) {
					event.getSpawn(p);
				}
				
				if (args[0].equalsIgnoreCase("timer")) {
					new Timer("startgame", "§7Il gioco iniziera' tra &sec secondi...", 5);
				}
				
				if (args[0].equalsIgnoreCase("villager")) {
					Disguiser.setVillager(p);
				}
				
				if (args[0].equalsIgnoreCase("zombie")) {
					Disguiser.setZombie(p);
				}
				
				if (args[0].equalsIgnoreCase("null")) {
					Disguiser.setNull(p);
				}
			}
		}
		return true;
  }
}

