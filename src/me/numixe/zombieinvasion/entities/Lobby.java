package me.numixe.zombieinvasion.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.numixe.zombieinvasion.math.Math;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Lobby {

	private int numberof_zombies;
	private int max_players;
	private int min_players;

	private Map<String, PlayerID> map = new HashMap<String, PlayerID>();	// name, player identity
	
	public void loadData(Plugin plugin) {	// loads from config
		
		if (!l.getLobby().contains("Lobby")) {
			l.getLobby().createSection("Lobby");
			l.getLobby().createSection("Lobby.min_players");
			l.getLobby().createSection("Lobby.zombies");
			l.getLobby().createSection("Lobby.max_players");
			l.getLobby().set("Lobby.min_players", 2);
			l.getLobby().set("Lobby.zombies", 1);
			l.getLobby().set("Lobby.max_players", 16);
			l.saveLobbyConfig();
		}
		
		numberof_zombies = l.getLobby().getInt("Lobby.zombies");
		max_players = l.getLobby().getInt("Lobby.max_players");
		min_players = l.getLobby().getInt("Lobby.min_players");
	}
	
	public void setNumberofZombies(Plugin plugin, int value) {	// loads from config
		
		if (!l.getLobby().contains("lobby")) {
			
			l.getLobby().createSection("Lobby");
			l.getLobby().createSection("Lobby.zombies");
			l.getLobby().createSection("Lobby.max_players");
			l.getLobby().createSection("Lobby.min_players");
			l.getLobby().set("Lobby.max_players", 16);
			l.getLobby().set("Lobby.min-players", 2);
		}
			
		l.getLobby().set("Lobby.zombies", value);
		this.numberof_zombies = value;
	}

	public void setMaxSize(Plugin plugin, int value) {	// loads from config
	
		if (!l.getLobby().contains("Lobby")) {
			
			l.getLobby().createSection("Lobby");
			l.getLobby().createSection("Lobby.zombies");
			l.getLobby().createSection("Lobby.max_players");
			l.getLobby().createSection("Lobby.min_players");
			l.getLobby().set("Lobby.zombies", 1);
			l.getLobby().set("Lobby.min_players", 2);
		}
			
		l.getLobby().set("Lobby.max_players", value);
		this.max_players = value;
	}
	
	public void setMinPlayers(Plugin plugin, int value) {	// loads from config
		
		if (!plugin.getConfig().contains("Lobby")) {
			
			l.getLobby().createSection("Lobby");
			l.getLobby().createSection("Lobby.zombies");
			l.getLobby().createSection("Lobby.max_players");
			l.getLobby().createSection("Lobby.min-players");
			l.getLobby().set("Lobby.zombies", 2);
			l.getLobby().set("Lobby.max_players", 16);
		}
			
		l.getLobby().set("Lobby.min_players", value);
		this.min_players = value;
	}
	
	/**
	 * 
	 * @param player
	 * @return Returns true if the player cannot be added into the lobby
	 */
	
	public static final int FAIL_FULL = 1;
	public static final int FAIL_NAME = 2;
	
	public int addPlayer(Player player) {
		
		if (map.containsKey(player.getName()))
			return FAIL_NAME;
		else if (this.isFull())
			return FAIL_FULL;
		
		map.put(player.getName(), PlayerID.NONE);
		
		return 0;
	}
	
	public void removePlayer(Player player) {
		
		if (!map.containsKey(player.getName())) {
			player.sendMessage(pl.prefix + m.getMessage().getString("notinlobby").replace("&", "§"));
			return;
		}
		map.remove(player.getName());
		player.sendMessage(pl.prefix + m.getMessage().getString("left").replace("&", "§"));
	}
	
	public void setPlayerID(Player player, PlayerID id) {
		
		map.put(player.getName(), id);
	}
	
	public void setPlayerID(String player, PlayerID id) {
		
		map.put(player, id);
	}
	
	/**
	 * 
	 * @param player
	 * @return The player identity, or null if the player is not in the lobby
	 */
	
	public PlayerID getPlayerID(Player player) {
		
		return map.get(player.getName());
	}
	
	public PlayerID getPlayerID(String name) {
		
		return map.get(name);
	}
	
	public void clear() {
		
		map.clear();
	}
	
	public boolean isFull() {
		
		return map.size() == max_players;
	}
	
	public int size() {
		
		return map.size();
	}
	
	public int getMinPlayers() {
		
		return min_players;
	}
	
	public int getMaxSize() {
		
		return max_players;
	}
	
	public Set<String> getPlayersName() {
		
		return map.keySet();
	}
	
	public Collection<Player> getPlayers() {
		
		Collection<Player> list = new ArrayList<Player>();
		
		for (String name : map.keySet())
			list.add(Bukkit.getServer().getPlayer(name));
		
		return list;
	}
	
	public Map<PlayerID, Integer> getCount() {
		
		int zombie = 0, villager = 0;
		
		for (String name : map.keySet()) {
			
			switch (map.get(name)) {
			
			case ZOMBIE:
				zombie++;
				break;
			case VILLAGER:
				villager++;
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
		
		Map<PlayerID, Integer> count = new HashMap<PlayerID, Integer>();
		count.put(PlayerID.ZOMBIE, zombie);
		count.put(PlayerID.VILLAGER, villager);
		
		return count;
	}
	
	public void randomAssignID() {
		
		int tochoose = numberof_zombies;
		int choosen = 0;
		
		System.out.println("Generating " + this.numberof_zombies + " zombies");
		
		while (tochoose > 0) {	// finche non sono stati scelti tutti gli zombie
			
			for (String name : map.keySet()) {
				
				if (map.get(name) == PlayerID.ZOMBIE)	// skip zombies
					continue;
			
				int rand = Math.randomInt(0, map.size() - choosen);
				
				if (rand < tochoose) {
					map.put(name, PlayerID.ZOMBIE);
					tochoose--;
					choosen++;
				} else
					map.put(name, PlayerID.VILLAGER);
			}
		}
	}
	
	/*public void startGame() {
		if (map.size() >= 3) 
			new StartCoolDown(pl);
	}*/
}