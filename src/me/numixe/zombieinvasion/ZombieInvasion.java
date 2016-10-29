package me.numixe.zombieinvasion;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.robingrether.idisguise.api.DisguiseAPI;


public class ZombieInvasion extends JavaPlugin {

	/** Dipendenze **/
	public static DisguiseAPI api;
	
	/** Memorie **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<Player> isZombie = new ArrayList();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<Player> isVillager = new ArrayList();
	
	
	public static ZombieInvasion plugin;
	public Disguiser disguiser;
	public static ActionBar actionbar;
	public static ScoreboardAPI scoreboard;
	
	private Random random;
	public Lobby lobby;
	
	public void onEnable() {
		
		api = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
		
		plugin = this;
		disguiser = new Disguiser();
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
			disguiser.beZombie(p);
		}
		return true;
        
 }
}

