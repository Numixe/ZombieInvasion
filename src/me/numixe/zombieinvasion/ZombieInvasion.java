package me.numixe.zombieinvasion;

import java.util.Arrays;

import me.numixe.zombieinvasion.entities.ActionBar;
import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.entities.ScoreboardAPI;
import me.numixe.zombieinvasion.entities.Teleport;
import me.numixe.zombieinvasion.listeners.SetupListeners;
import me.numixe.zombieinvasion.timing.StartTimer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieInvasion extends JavaPlugin {

	private Game game;
	private ActionBar actionbar;
	private ScoreboardAPI scoreboard;
	private SetupListeners setupEvents;
	private Teleport teleport;
	private Lobby lobby;
	
	public void onEnable() {
		
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
		actionbar = new ActionBar();
	    scoreboard = new ScoreboardAPI();
	    
		System.out.println("ZombieInvasion Attivo!");
		
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public void onDisable() {
		
		game.stop(Game.CAUSE_INTERRUPT);
		setupEvents.unregister();
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = null;
		
		if (sender instanceof Player)
			p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("zombieinvasion") || cmd.getName().equalsIgnoreCase("zi")) {
			
			if (args.length < 1) {
				
				p.sendMessage("\u00A76ZombieInvasion> " + "\u00A77Aggiungi un argomento!");
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
				
			else if (args[0].equalsIgnoreCase("villagerform") || args[0].equalsIgnoreCase("vform"))
				Disguiser.setVillager(this.actionbar, p);
				
			else if (args[0].equalsIgnoreCase("zombieform") || args[0].equalsIgnoreCase("zform"))
				Disguiser.setZombie(this.actionbar, p);
					
			else if (args[0].equalsIgnoreCase("nullform") || args[0].equalsIgnoreCase("nform"))
				Disguiser.setNull(p);
					
			else if (args[0].equalsIgnoreCase("refreshscore"))
				scoreboard.refresh(lobby.getCount());
					
			else if (args[0].equalsIgnoreCase("add"))
				add(p);
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
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7f" + name);
		
		return true;
	}
	
	private boolean setSpawn(Player sender, String[] args) {
		
		// syntax: /zombieinvasion setspawn <id> <spawn-name>
		
		if (args.length < 2)
			return false;
			
		PlayerID id = null;
			
		if (args[1].equalsIgnoreCase("zombie") || args[0].equalsIgnoreCase("z"))
			id = PlayerID.ZOMBIE;
		else if (args[1].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("v"))
			id = PlayerID.VILLAGER;
		else
			return false;
			
		teleport.setSpawn(id, args[1], sender.getLocation());
		
		return true;
	}
	
	private boolean removeSpawn(Player sender, String[] args) {
		
		// syntax: /zombieinvasion rmspawn <id> <spawn-name>
		
		if (args.length < 2)
			return false;
			
		PlayerID id = null;
			
		if (args[0].equalsIgnoreCase("zombie") || args[1].equalsIgnoreCase("z"))
			id = PlayerID.ZOMBIE;
		else if (args[0].equalsIgnoreCase("villager") || args[1].equalsIgnoreCase("v"))
			id = PlayerID.VILLAGER;
		else
			return false;
			
		teleport.removeSpawn(id, args[1]);
		
		return true;
	}
	
	private boolean setHub(Player sender) {
		
		teleport.setHub(sender.getLocation());
		sender.sendMessage("\u00A76ZombieInvasion> \u00A7f Hub impostato");
		
		return true;
	}
	
	private boolean start(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fGioco attualmente in esecuzione");
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fCi vogliono almeno " + lobby.getMinPlayers() + " giocatori per iniziare il gioco");
		else if (!teleport.canSpawn())
			sender.sendMessage("\u00A76ZombieInvasion> \u00A7fDeve esistere almeno uno spawn per ogni disguise type");
		else
			game.start();
		
		return true;
	}
	
	private boolean timerStart(Player sender) {
		
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

