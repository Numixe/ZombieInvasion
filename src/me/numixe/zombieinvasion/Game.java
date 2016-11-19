package me.numixe.zombieinvasion;

import java.util.Map;

import me.numixe.zombieinvasion.entities.Disguiser;
import me.numixe.zombieinvasion.entities.PlayerID;
import me.numixe.zombieinvasion.entities.ScreenAPI;
import me.numixe.zombieinvasion.listeners.GameListeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import static me.numixe.zombieinvasion.ZombieInvasion.*;
public class Game {
	
	private boolean running;	// running game checking
	private ZombieInvasion plugin;	// pointer to the plugin
	private GameListeners events;	// events manager
	
	public static final int CAUSE_INTERRUPT = 0;
	public static final int CAUSE_VILLAGER_WIN = 1;
	public static final int CAUSE_ZOMBIE_WIN = 2;
	
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
			plugin.getTeleportManager().toRandomSpawn(p, plugin.getLobby().getPlayerID(p));
		}
		
		plugin.getScoreboard().refresh(plugin.getLobby().getCount());
		events.register();	// start handling events
		running = true;
	}
	
	public boolean isRunning() {
		
		return running;
	}
	
	public void stop(int cause) {
		
		if (!running)
			return;
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			switch (cause) {
		
			case CAUSE_VILLAGER_WIN:
				ScreenAPI.sendTitle(p, "\u00A7a\u00A7lVillager", "\u00A77Win the Game!");
				break;
			case CAUSE_ZOMBIE_WIN:
				ScreenAPI.sendTitle(p, "\u00A7c\u00A7lZombie", "\u00A77Win the Game!");
				break;
			case CAUSE_INTERRUPT:
				Bukkit.getServer().broadcastMessage("\u00A76ZombieInvasion> \u00A77The game was interrupt. No winners!");
				break;
			default:
				break;
			}
			p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 10, 10);
			p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 20, 20);
			p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 30, 30);
			p.playSound(p.getLocation(), Sound.FIREWORK_TWINKLE, 40, 40);
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			plugin.getLobby().setPlayerID(p, PlayerID.NONE);
			updatePlayerForm(p);
			plugin.getScoreboard().hideBoard(p);
			plugin.getTeleportManager().toHub(p);

			
		}
		
		plugin.getLobby().clear();
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
			Disguiser.setZombie(player, plugin.getScoreboard());
			Bukkit.getServer().broadcastMessage(plugin.prefix + m.getMessage().getString("villager_death").replace("&", "§").replace("%player%", player.getName()));
			break;
		case ZOMBIE:
			plugin.getLobby().setPlayerID(player, PlayerID.NONE);
			Disguiser.setNull(player, plugin.getScoreboard());
			Bukkit.getServer().broadcastMessage(plugin.prefix + m.getMessage().getString("zombie_death").replace("&", "§").replace("%player%", player.getName()));
			player.setGameMode(GameMode.SPECTATOR);
			ScreenAPI.sendTitle(player, "\u00A7c\u00A7lYou died!", null);
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
		
		player.setHealth(20);
		player.setFoodLevel(20);
		
		PlayerID id = plugin.getLobby().getPlayerID(player);
		
		if (id == null)
			Disguiser.setNull(player, plugin.getScoreboard());
		
		switch(id) {
			
		case VILLAGER:
			Disguiser.setVillager(player, plugin.getScoreboard());
			break;
		case ZOMBIE:
			Disguiser.setZombie(player, plugin.getScoreboard());
			break;
		case NONE:
			Disguiser.setNull(player, plugin.getScoreboard());
		default:
			break;
		}
	}
}
