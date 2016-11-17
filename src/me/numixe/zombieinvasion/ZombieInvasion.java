package me.numixe.zombieinvasion;

import java.util.Arrays;

import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.entities.ScoreboardAPI;
import me.numixe.zombieinvasion.entities.Teleport;
import me.numixe.zombieinvasion.listeners.SetupListeners;
import me.numixe.zombieinvasion.timing.StartCoolDown;
import me.numixe.zombieinvasion.timing.StartTimer;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieInvasion extends JavaPlugin {
	
	//public static ZombieInvasion pl;
	private Game game;
	private ScoreboardAPI scoreboard;
	private SetupListeners setupEvents;
	private Teleport teleport;
	private Lobby lobby;
	//public String prefix = "\u00A76ZombieInvasion> ";
	
	public void onEnable() {
		
		//pl = this;
		/* Supervisor classes */
		
		game = new Game(this);
		setupEvents = new SetupListeners(this);
		setupEvents.register();
		teleport = new Teleport(this);
		teleport.loadHub();
		teleport.loadSpawns();
		
		/* Support classes */
		
		Disguiser.initAPI();
		lobby = new Lobby();
		lobby.loadData(this);
	    scoreboard = new ScoreboardAPI();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public void onDisable() {
		
		game.stop(Game.CAUSE_INTERRUPT);
		setupEvents.unregister();
		saveConfig();
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = null;
		
		if (sender instanceof Player)
			p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("zombieinvasion") || cmd.getName().equalsIgnoreCase("zi")) {
			
			if (args.length == 0) {
				
				p.sendMessage("\u00a7f\u00a7m--------------------------------------");
		    	p.sendMessage(" ");
		    	p.sendMessage("  \u00a7f\u00a7lName: \u00a7a\u00a7lZombieInvasion");
		    	p.sendMessage("  \u00a7f\u00a7lAuthor: \u00a76\u00a7lNumixe & Atlas97");
		    	p.sendMessage("  \u00a7f\u00a7lVersion: \u00a7b\u00a7l1.0");
		    	p.sendMessage(" ");
		    	p.sendMessage("\u00a7f\u00a7m--------------------------------------");
				
			} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("info")) {
				p.sendMessage("\u00A79\u00A7l\u00A7m------------------------------------------");
				p.sendMessage("                       \u00A76\u00A7lZombieInvasion \u00A7f\u00A7lInfo");			
				p.sendMessage(" ");
				p.sendMessage("\u00A76/zombieinvasion \u00A77ls - List of the lobby");
				p.sendMessage("\u00A76/zombieinvasion \u00A77setspawn [id] [name] - Set new or overwrite old spawn with the current location");
				p.sendMessage("\u00A76/zombieinvasion \u00A77rmspawn [id] [name] - Remove an existing spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77spawn - Teleport to hub spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77sethub - Set the hub spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77start - Start the game instantly");
				p.sendMessage("\u00A76/zombieinvasion \u00A77timerstart - Start the game after a 5 seconds timer");
				p.sendMessage("\u00A76/zombieinvasion \u00A77stop - Interrupt the game");
				p.sendMessage("\u00A76/zombieinvasion \u00A77villagerform - Set the form to villager");
				p.sendMessage("\u00A76/zombieinvasion \u00A77zombieform - Set the form to zombie");
				p.sendMessage("\u00A76/zombieinvasion \u00A77nullform - Set the normal form");
				p.sendMessage("\u00A76/zombieinvasion \u00A77refreshscore - Update the scoreboard data");
				p.sendMessage("\u00A76/zombieinvasion \u00A77add - Add to the game lobby");
				p.sendMessage(" ");
				p.sendMessage("\u00A79\u00A7l\u00A7m------------------------------------------");
				p.playSound(p.getLocation(), Sound.PIG_DEATH, 30.0F, 30.0F);
				return false;
				}
				
			if (args[0].equalsIgnoreCase("ls"))
				return listLobby(p);
			else if (args[0].equalsIgnoreCase("setspawn"))
				return setSpawn(p, subargs(args));
			
			else if (args[0].equalsIgnoreCase("rmspawn"))
				return removeSpawn(p, subargs(args));
				
			else if (args[0].equalsIgnoreCase("spawn"))
				teleport.toHub(p);
				
			else if (args[0].equalsIgnoreCase("sethub"))
				return setHub(p);
				
			else if (args[0].equalsIgnoreCase("start"))
				return start(p);
					
			else if (args[0].equalsIgnoreCase("stop"))
				game.stop(Game.CAUSE_INTERRUPT);
				
			else if (args[0].equalsIgnoreCase("timerstart") || args[0].equalsIgnoreCase("tstart") || args[0].equalsIgnoreCase("timer"))
				return timerStart(p);
			
			else if (args[0].equalsIgnoreCase("cooldownstart") || args[0].equalsIgnoreCase("cdstart"))
				return coolDownStart(p);
				
			else if (args[0].equalsIgnoreCase("villagerform") || args[0].equalsIgnoreCase("vform"))
				Disguiser.setVillager(p);
				
			else if (args[0].equalsIgnoreCase("zombieform") || args[0].equalsIgnoreCase("zform"))
				Disguiser.setZombie(p);
					
			else if (args[0].equalsIgnoreCase("nullform") || args[0].equalsIgnoreCase("nform"))
				Disguiser.setNull(p);
					
			else if (args[0].equalsIgnoreCase("refreshscore"))
				scoreboard.refresh(lobby.getCount());
					
			else if (args[0].equalsIgnoreCase("add"))
				add(p);
		  }
		}
		
		return true;
	}
	
	private static String[] subargs(String[] args) {	// tralascia il primo argomento dell'array
		
		return Arrays.copyOfRange(args, 1, args.length);
	}
	
	/**
	 *  Commands section
	 */
	
	private boolean listLobby(Player sender) {
		
		for (String name : lobby.getPlayersName())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7a" + name);
		
		return true;
	}
	
	private boolean setSpawn(Player sender, String[] args) {
		
		// syntax: /zombieinvasion setspawn <id> <spawn-name>
		
		if (args.length < 2)
			return false;
			
		PlayerID id = null;
			
		if (args[0].equalsIgnoreCase("zombie") || args[0].equalsIgnoreCase("z"))
			id = PlayerID.ZOMBIE;
		else if (args[0].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("v"))
			id = PlayerID.VILLAGER;
		else
			return false;
			
		teleport.setSpawn(id, args[1], sender.getLocation());
		sender.sendMessage("\u00A76ZombieInvasion> \u00A77You have just set a Spawn: \u00A7a" + args[1]);
		saveConfig();
		
		return true;
	}
	
	private boolean removeSpawn(Player sender, String[] args) {
		
		// syntax: /zombieinvasion rmspawn <id> <spawn-name>
		
		if (args.length < 2)
			return false;
			
		PlayerID id = null;
			
		if (args[0].equalsIgnoreCase("zombie") || args[0].equalsIgnoreCase("z"))
			id = PlayerID.ZOMBIE;
		else if (args[0].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("v"))
			id = PlayerID.VILLAGER;
		else
			return false;
			
		teleport.removeSpawn(id, args[1]);
		sender.sendMessage("\u00A76ZombieInvasion> \u00A77You have just removed a Spawn: \u00A7a" + args[1]);
		saveConfig();
		
		return true;
	}
	
	private boolean setHub(Player sender) {
		
		teleport.setHub(sender.getLocation());
		sender.sendMessage("\u00A76ZombieInvasion> \u00A77Successfully set hub");
		saveConfig();
		
		return true;
	}
	
	private boolean start(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A77The game is currently running");
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fCi vogliono almeno " + lobby.getMinPlayers() + " giocatori per iniziare il gioco");
		else if (!teleport.canSpawn())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fDeve esistere almeno uno spawn per ogni disguise type");
		else
			game.start();
		
		return true;
	}
	
	public boolean timerStart(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fGioco attualmente in esecuzione");
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fCi vogliono almeno " + lobby.getMinPlayers() + " giocatori per iniziare il gioco");
		else if (!teleport.canSpawn())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fDeve esistere almeno uno spawn per ogni disguise type");
		else
			new StartTimer(this);
		
		return true;
	}
	
	public boolean coolDownStart(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fGioco attualmente in esecuzione");
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fCi vogliono almeno " + lobby.getMinPlayers() + " giocatori per iniziare il gioco");
		else if (!teleport.canSpawn())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fDeve esistere almeno uno spawn per ogni disguise type");
		else
			new StartCoolDown(this);
		
		return true;
	}
	
	private boolean add(Player sender) {
		
		switch (lobby.addPlayer(sender)) {
		
		case Lobby.FAIL_FULL:
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fLa lobby e' piena");
			break;
		case Lobby.FAIL_NAME:
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fSei gia' entrato in game");
			break;
		default:
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fSei entrato in game");
			break;
		}
		
		return true;
	}
}

