package me.numixe.zombieinvasion;

import java.util.Map;

import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.listeners.GameListeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Game {
	
	private boolean running;	// running game checking
	private ZombieInvasion plugin;	// pointer to the plugin
	private GameListeners events;	// events manager
	
	public static final int CAUSE_INTERRUPT = 0;
	public static final int CAUSE_VILLAGER_WIN = 1;
	public static final int CAUSE_ZOMBIE_WIN = 2;
	
	public static final int MIN_PLAYERS = 2;
	
	public Game(ZombieInvasion plugin) {
		
		running = false;
		this.plugin = plugin;
		events = new GameListeners(plugin);
	}
	
	public void start() {
		
		plugin.getLobby().randomAssignID();	
		
		for (Player p :  plugin.getLobby().getPlayers()) {	
			
			updatePlayerForm(p);
			plugin.getScoreboard().showBoard(p);
			plugin.getTeleportManager().toRandomSpawn(p);
		}
		
		plugin.getScoreboard().refresh();
		events.register();	// start handling events
		running = true;
	}
	
	public boolean isRunning() {
		
		return running;
	}
	
	public void stop(int cause) {
		
		if (!running)
			return;
		
		switch (cause) {
		
		case CAUSE_VILLAGER_WIN:
			Bukkit.getServer().broadcastMessage("�6ZombieInvasion> §fI villager hanno resistito con tenacia");
			plugin.getActionBar().message = "�a�I villager hanno resistito con tenacia";
			break;
		case CAUSE_ZOMBIE_WIN:
			Bukkit.getServer().broadcastMessage("�6ZombieInvasion> §2L'invasione zombie ha avuto la meglio");
			plugin.getActionBar().message = "�a�L'invasione zombie ha avuto la meglio";
			break;
		case CAUSE_INTERRUPT:
			Bukkit.getServer().broadcastMessage("�6ZombieInvasion> §fIl gioco e' stato interrotto, nessun vincitore");
			plugin.getActionBar().message = "�a�Il gioco e' stato interrotto, nessun vincitore";
			break;
		default:
			break;
		}
		
		for (Player p :  plugin.getLobby().getPlayers()) {	
			
			plugin.getActionBar().sendMessage(p);
			plugin.getLobby().setPlayerID(p, PlayerID.NONE);
			updatePlayerForm(p);
			plugin.getScoreboard().hideBoard(p);
			plugin.getTeleportManager().toHub(p);
		}
		
		plugin.getLobby().clear();
		plugin.getScoreboard().refresh();
		events.unregister();	// stop handling events
		running = false;
	}
	
	public void onDeathPlayer(Player player) {
		
		PlayerID id = plugin.getLobby().getPlayerID(player);
		
		if (id == null) {
			return;
		}
		
		switch (id) {
		
		case VILLAGER:
			plugin.getLobby().setPlayerID(player, PlayerID.ZOMBIE);
			Disguiser.setZombie(plugin.getActionBar(), player);
			break;
		case ZOMBIE:
			plugin.getLobby().setPlayerID(player, PlayerID.NONE);
			Disguiser.setNull(player);
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("�6ZombieInvasion> §4Sei morto");
			break;
		default:
			// do nothing
			break;
		}
	}
	
	public void winControl(Map<PlayerID, Integer> count) {
		
		if (count.get(PlayerID.VILLAGER) == 0)
			plugin.getGame().stop(Game.CAUSE_ZOMBIE_WIN);
		else if (count.get(PlayerID.ZOMBIE) == 0)
			plugin.getGame().stop(Game.CAUSE_VILLAGER_WIN);
	}
	
	public void updatePlayerForm(Player player) {
		
		PlayerID id = plugin.getLobby().getPlayerID(player);
		
		if (id == null)
			Disguiser.setNull(player);
		
		switch(id) {
			
		case VILLAGER:
			Disguiser.setVillager(plugin.getActionBar(), player);
			break;
		case ZOMBIE:
			Disguiser.setZombie(plugin.getActionBar(), player);
			break;
		case NONE:
			Disguiser.setNull(player);
		default:
			break;
		}
	}
}
