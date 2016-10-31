package me.numixe.zombieinvasion;

import java.util.Random;

import me.numixe.zombieinvasion.entities.ActionBar;
import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.entities.ScoreboardAPI;
import me.numixe.zombieinvasion.timing.StartTimer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class ZombieInvasion extends JavaPlugin {

	private Game game;
	private ActionBar actionbar;
	private ScoreboardAPI scoreboard;
	private EventListeners events;
	private Teleport teleport;
	private Random random;
	private Lobby lobby;
	
	public void onEnable() {
		
		/* Supervisor classes */
		
		game = new Game(this);
		events = new EventListeners(this);
		teleport = new Teleport(this);
		
		/* Support classes */
		
		Disguiser.initAPI();	
		actionbar = new ActionBar();
	    scoreboard = new ScoreboardAPI();
	    random = new Random();
	    lobby = new Lobby();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public void onDisable() {
		
		events.destroy();
	}
	
	public ActionBar getActionBar() {
		
		return this.actionbar;
	}
	
	public ScoreboardAPI getScoreboard() {
		
		return this.scoreboard;
	}
	
	public Teleport getTeleportManager() {
		
		return this.teleport;
	}
	
	public Lobby getLobby() {
		
		return this.lobby;
	}
	
	public Game getGame() {
		
		return this.game;
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
				
			} else if (args[0].equalsIgnoreCase("timerstart") || args[0].equalsIgnoreCase("tstart") || args[0].equalsIgnoreCase("timer")) {
				
				new StartTimer(this);
				
			} else if (args[0].equalsIgnoreCase("villagerform") || args[0].equalsIgnoreCase("vform")) {
				
				Disguiser.setVillager(this.actionbar, p);
				
			} else if (args[0].equalsIgnoreCase("zombieform") || args[0].equalsIgnoreCase("zform")) {
				
				Disguiser.setZombie(this.actionbar, p);
					
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

