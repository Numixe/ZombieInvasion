package me.numixe.zombieinvasion;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class ZombieInvasion extends JavaPlugin {

	public static ZombieInvasion plugin;
	public Game game;
	public ActionBar actionbar;
	public ScoreboardAPI scoreboard;
	public EventListeners event;
	public Teleport teleport;
	
	private Random random;
	public Lobby lobby;
	
	public void onEnable() {
		
		Disguiser.initAPI();	
		plugin = this;
		actionbar = new ActionBar();
		teleport = new Teleport();
	    scoreboard = new ScoreboardAPI();
	    event = new EventListeners();
	    random = new Random();
	    lobby = new Lobby();
	    game = new Game();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		Bukkit.getServer().getPluginManager().registerEvents(new EventListeners(), this);
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public int randomInt(int start, int range) {
    	
    	return random.nextInt(range) + start;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = null;
		
		if (sender instanceof Player)
			p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("zombieinvasion") || cmd.getName().equalsIgnoreCase("zi")) {
			
			if (args.length < 1) {
				
				p.sendMessage("�6ZombieInvasion> " + "�7Aggiungi un argomento!");
				return false;
			}
			
			if (args[0].equalsIgnoreCase("setspawn")) {
					
				// syntax: /zombieinvasion setspawn <id> <spawn-name>
					
				if (args.length < 3)
					return false;
					
				PlayerID id = null;
					
				if (args[1].equalsIgnoreCase("zombie") || args[1].equalsIgnoreCase("z"))
					id = PlayerID.ZOMBIE;
				else if (args[1].equalsIgnoreCase("villager") || args[1].equalsIgnoreCase("v"))
					id = PlayerID.VILLAGER;
				else
					return false;
					
				teleport.addSpawn(id, args[2], p.getLocation());
				
			} else if (args[0].equalsIgnoreCase("rmspawn")) {
				
				// syntax: /zombieinvasion rmspawn <id> <spawn-name>
				
				if (args.length < 3)
					return false;
					
				PlayerID id = null;
					
				if (args[1].equalsIgnoreCase("zombie") || args[1].equalsIgnoreCase("z"))
					id = PlayerID.ZOMBIE;
				else if (args[1].equalsIgnoreCase("villager") || args[1].equalsIgnoreCase("v"))
					id = PlayerID.VILLAGER;
				else
					return false;
					
				teleport.removeSpawn(id, args[2]);
				
			} else if (args[0].equalsIgnoreCase("spawn")) {
				
				teleport.toHub(p);
				
			} else if (args[0].equalsIgnoreCase("start")){
				
				game.start();
					
			} else if (args[0].equalsIgnoreCase("stop")) {
				
				game.stop();
				
			} else if (args[0].equalsIgnoreCase("timerstart") || args[0].equalsIgnoreCase("tstart")) {
				
				new StartTimer();
				
			} else if (args[0].equalsIgnoreCase("villagerform") || args[0].equalsIgnoreCase("vform")) {
				
					Disguiser.setVillager(p);
					
			} else if (args[0].equalsIgnoreCase("zombieform") || args[0].equalsIgnoreCase("zform")) {
				
					Disguiser.setZombie(p);
					
			} else if (args[0].equalsIgnoreCase("nullform") || args[0].equalsIgnoreCase("nform")) {
				
					Disguiser.setNull(p);
					
			} else if (args[0].equalsIgnoreCase("refreshscore")) {
				
					scoreboard.refresh();
					
			} else if (args[0].equalsIgnoreCase("add")) {
				
					lobby.addPlayer(p);
			}
		}
		
		return true;
  }
}

