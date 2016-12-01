package me.numixe.zombieinvasion;

import java.util.Arrays;

import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.Lobby;
import me.numixe.zombieinvasion.entities.MainScoreboard;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.entities.ScoreboardAPI;
import me.numixe.zombieinvasion.entities.Teleport;
import me.numixe.zombieinvasion.listeners.SetupListeners;
import me.numixe.zombieinvasion.timing.StartCoolDown;
import me.numixe.zombieinvasion.timing.StartTimer;
import me.numixe.zombieinvasion.utils.LobbyFile;
import me.numixe.zombieinvasion.utils.MessageFile;
import me.numixe.zombieinvasion.utils.SpawnFile;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieInvasion extends JavaPlugin {
	
	// yml file
	public static ZombieInvasion pl;
	public static LobbyFile l;
	public static SpawnFile s;
	public static MessageFile m;
	
	private Game game;
	private ScoreboardAPI scoreboard;
	private SetupListeners setupEvents;
	private Teleport teleport;
	private Lobby lobby;
	private MainScoreboard mains;
	public String prefix;
	
	public void onEnable() {
		
		/* Config File */ // Lasciami le variabili, mi servono!
		pl = this;
		l = new LobbyFile();
		s = new SpawnFile();
		m = new MessageFile();
		
		l.saveDefaultLobbyConfig();
		s.saveDefaultSpawnsConfig();
		m.saveDefaultMessagesConfig();
		prefix = m.getMessage().getString("prefix").replace("&", "§");
		
		/* Supervisor classes */		
		game = new Game(this);
		setupEvents = new SetupListeners(this);
		setupEvents.register();
		teleport = new Teleport();
		teleport.loadHub();
		teleport.loadSpectator();
		teleport.loadSpawns();
		
		/* Support classes */
		
		Disguiser.initAPI();
		lobby = new Lobby();
		lobby.loadData(this);
	    scoreboard = new ScoreboardAPI();
	    mains = new MainScoreboard();
	    
	}
	
	public void onDisable() {
		
		game.stop(Game.CAUSE_INTERRUPT);
		setupEvents.unregister();
		saveConfig();
	}
	
	public ScoreboardAPI getScoreboard() {
		
		return this.scoreboard;
	}
	
	public MainScoreboard getMainScoreboard() {
		
		return this.mains;
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
		    	p.sendMessage("  \u00a7f\u00a7lVersion: \u00a7b\u00a7l" + getDescription().getVersion());
		    	p.sendMessage(" ");
		    	p.sendMessage("\u00a7f\u00a7m--------------------------------------");
				
			} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")) {
				p.sendMessage("\u00A79\u00A7l\u00A7m------------------------------------------");
				p.sendMessage("                       \u00A76\u00A7lZombieInvasion \u00A7f\u00A7lHelp");			
				p.sendMessage(" ");
				p.sendMessage("\u00A76/zombieinvasion \u00A77ls - List of the lobby");
				p.sendMessage("\u00A76/zombieinvasion \u00A77setspawn [id] [name] - Set new or overwrite old spawn with the current location");
				p.sendMessage("\u00A76/zombieinvasion \u00A77rmspawn [id] [name] - Remove an existing spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77spawn - Teleport to hub spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77setspectator - Set the Spectator spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77sethub - Set the hub spawn");
				p.sendMessage("\u00A76/zombieinvasion \u00A77start - Start the game instantly");
				p.sendMessage("\u00A76/zombieinvasion \u00A77timerstart - Start the game after a 5 seconds timer");
				p.sendMessage("\u00A76/zombieinvasion \u00A77stop - Interrupt the game");
				p.sendMessage("\u00A76/zombieinvasion \u00A77refreshscore - Update the scoreboard data");
				p.sendMessage("\u00A76/zombieinvasion \u00A77add - Add to the game lobby");
				p.sendMessage("\u00A76/zombieinvasion \u00A77reload - Reload Config");
				p.sendMessage(" ");
				p.sendMessage("\u00A79\u00A7l\u00A7m------------------------------------------");
				p.playSound(p.getLocation(), Sound.PIG_DEATH, 30.0F, 30.0F);
				return false;
				}
				
			if (args[0].equalsIgnoreCase("ls"))
				if (p.hasPermission("ZombieInvasion.ls"))
				return listLobby(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
			
			
			else if (args[0].equalsIgnoreCase("setspawn"))
				if (p.hasPermission("ZombieInvasion.setspawn"))
				return setSpawn(p, subargs(args));
				else
					p.sendMessage(prefix + "§cNo Permission!");
			
			else if (args[0].equalsIgnoreCase("rmspawn"))
				if (p.hasPermission("ZombieInvasion.rmspawn"))
				return removeSpawn(p, subargs(args));
				else
					p.sendMessage(prefix + "§cNo Permission!");
				
			else if (args[0].equalsIgnoreCase("spawn"))
				if (p.hasPermission("ZombieInvasion.spawn"))
				teleport.toHub(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
				
			else if (args[0].equalsIgnoreCase("sethub"))
				if (p.hasPermission("ZombieInvasion.sethub"))
				return setHub(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
			
			else if (args[0].equalsIgnoreCase("setspectator"))
				if (p.hasPermission("ZombieInvasion.setspectator"))
					return setSpectator(p);
					else
						p.sendMessage(prefix + "§cNo Permission!");
				
			else if (args[0].equalsIgnoreCase("start"))
				if (p.hasPermission("ZombieInvasion.start"))
				return start(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
					
			else if (args[0].equalsIgnoreCase("stop"))
				game.stop(Game.CAUSE_INTERRUPT);
				
			else if (args[0].equalsIgnoreCase("timerstart") || args[0].equalsIgnoreCase("tstart") || args[0].equalsIgnoreCase("timer"))
				if (p.hasPermission("ZombieInvasion.start"))
					return timerStart(p);
					else
						p.sendMessage(prefix + "§cNo Permission!");
			
			else if (args[0].equalsIgnoreCase("cooldownstart") || args[0].equalsIgnoreCase("cdstart"))
				if (p.hasPermission("ZombieInvasion.start"))
					return coolDownStart(p);
					else
						p.sendMessage(prefix + "§cNo Permission!");
					
			else if (args[0].equalsIgnoreCase("refreshscore"))
				if (p.hasPermission("ZombieInvasion.refreshscore"))
				scoreboard.refresh(lobby.getCount());
				else
					p.sendMessage(prefix + "§cNo Permission!");
					
			else if (args[0].equalsIgnoreCase("add"))
				if (p.hasPermission("ZombieInvasion.add"))
				add(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
			else if (args[0].equalsIgnoreCase("reload"))
				if (p.hasPermission("ZombieInvasion.reload"))
				reload(p);
				else
					p.sendMessage(prefix + "§cNo Permission!");
			else
				p.sendMessage(prefix + "\u00a7cInvalid Argument!");
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
			sender.sendMessage(prefix + "\u00A7a" + name);
		
		return true;
	}
	
	private boolean reload(Player sender) {		
		l.reloadLobbyConfig();
		m.reloadMessagesConfig();
		s.reloadSpawnsConfig();
		sender.sendMessage(prefix + "\u00a77Reload Complete!");		
		prefix = m.getMessage().getString("prefix").replace("&", "§");
		return true;
	}
	
	private boolean setSpawn(Player sender, String[] args) {
		
		// syntax: /zombieinvasion setspawn <id> <spawn-name>
		
		if (args.length < 2)
			return false;
			
		PlayerID id = null;
			
		if (args[0].equalsIgnoreCase("zombie") || args[0].equalsIgnoreCase("z")) {
			id = PlayerID.ZOMBIE;
			sender.sendMessage(prefix + m.getMessage().getString("setZombieSpawn").replace("&", "§").replace("%spawname%", args[1]));
		} else if (args[0].equalsIgnoreCase("villager") || args[0].equalsIgnoreCase("v")) {
			id = PlayerID.VILLAGER;
			sender.sendMessage(prefix + m.getMessage().getString("setVillagerSpawn").replace("&", "§").replace("%spawname%", args[1]));
		}else
			return false;
			
		teleport.setSpawn(id, args[1], sender.getLocation());
		
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
		sender.sendMessage(prefix + m.getMessage().getString("removeSpawn").replace("&", "§").replace("%spawname%", args[1]));
		saveConfig();
		
		return true;
	}
	
	private boolean setHub(Player sender) {
		
		teleport.setHub(sender.getLocation());
		sender.sendMessage(prefix + m.getMessage().getString("setHub").replace("&", "§"));	
		return true;
	}
	
	private boolean setSpectator(Player sender) {
		
		teleport.setSpectator(sender.getLocation());
		sender.sendMessage(prefix + m.getMessage().getString("setHub").replace("&", "§"));	
		return true;
	}
	
	private boolean start(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage(prefix + m.getMessage().getString("isRunning").replace("&", "§")); 
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage(prefix + m.getMessage().getString("needPlayers").replace("&", "§").replace("%minplayer%", l.getLobby().getString("Lobby.min_players")));
		else if (!teleport.canSpawn())
			sender.sendMessage(prefix + m.getMessage().getString("needSpawns").replace("&", "§"));
		else
			game.start();
		
		return true;
	}
	
	public boolean timerStart(Player sender) {
		
		if (game.isRunning())
			sender.sendMessage(prefix + m.getMessage().getString("isRunning").replace("&", "§")); 
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage(prefix + m.getMessage().getString("needPlayers").replace("&", "§").replace("%minplayer%", l.getLobby().getString("Lobby.min_players")));
		else if (!teleport.canSpawn())
			sender.sendMessage(prefix + m.getMessage().getString("needSpawns").replace("&", "§"));
		else
			new StartTimer(this);
		
		return true;
	}
	
	public boolean coolDownStart(Player sender) {
		
		if (pl.getGame().isTiming()) {
			sender.sendMessage("\u00a7c\u00a7lHEY! \u00a77Timer already Started!");
			return true;
		}
		if (game.isRunning())
			sender.sendMessage(prefix + m.getMessage().getString("isRunning").replace("&", "§"));
		else if (lobby.size() < lobby.getMinPlayers())
			sender.sendMessage(prefix + m.getMessage().getString("needPlayers").replace("&", "§").replace("%minplayer%", l.getLobby().getString("Lobby.min_players")));
		else if (!teleport.canSpawn())
			sender.sendMessage(prefix + m.getMessage().getString("needSpawns").replace("&", "§"));
		else
			new StartCoolDown(this);
		
		return true;
	}
	
	private boolean add(Player sender) {
		
		switch (lobby.addPlayer(sender)) {
		
		case Lobby.FAIL_FULL:
			sender.sendMessage(prefix + m.getMessage().getString("full").replace("&", "§"));
			break;
		case Lobby.FAIL_NAME:
			sender.sendMessage(prefix + m.getMessage().getString("alreadyJoin").replace("&", "§"));
			break;
		default:
			sender.sendMessage(prefix + m.getMessage().getString("join").replace("&", "§"));
			break;
		}		
		return true;
	}
	
	public void joinLobby(Player p) {
		switch (getLobby().addPlayer(p)) {
		
  		case Lobby.FAIL_NAME:
  			p.sendMessage(pl.prefix + m.getMessage().getString("alreadyJoin").replace("&", "§"));
  			p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 10, 10);
  			break;
  		case Lobby.FAIL_FULL:
  			p.sendMessage(pl.prefix + m.getMessage().getString("full").replace("&", "§"));
			p.playSound(p.getLocation(), Sound.CREEPER_DEATH, 10, 10);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(prefix + "§7You are a Spectator!");
			getTeleportManager().toSpectator(p);
			break;
  		default:
  			p.sendMessage(pl.prefix + m.getMessage().getString("join").replace("&", "§"));
			p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 10, 10);
			p.playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 20, 20);
			break;
  	}
		if (getLobby().map.size() >= 6)
			new StartCoolDown(this);
	}
}

