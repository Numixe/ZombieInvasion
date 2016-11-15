package me.numixe.zombieinvasion.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.numixe.zombieinvasion.math.Math;
import me.numixe.zombieinvasion.timing.StartCoolDown;

import static me.numixe.zombieinvasion.ZombieInvasion.pl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Lobby {

	private int numberof_zombies;	// load from config
	private int max_size;	// load from config
	private int min_players;

	public Map<String, PlayerID> map;	// name, player identity
	
	public Lobby() {
		
		map = new HashMap<String, PlayerID>();
		numberof_zombies = 1;
		max_size = 20;
		min_players = 3;
	}
	
	/**
	 *  loads from data config
	 */
	
	public void loadData(Plugin plugin) {	// loads from config
		
		if (!plugin.getConfig().contains("lobby")) {
			
			plugin.getConfig().createSection("lobby");
			plugin.getConfig().createSection("lobby.zombies");
			plugin.getConfig().createSection("lobby.size");
			plugin.getConfig().set("lobby.zombies", 1);
			plugin.getConfig().set("lobby.size", 20);
			plugin.getConfig().set("lobby.min-players", 3);
			return;
		}
		
		this.numberof_zombies = plugin.getConfig().getInt("lobby.zombies");
		this.max_size = plugin.getConfig().getInt("lobby.size");
	}
	
	public void setNumberofZombies(Plugin plugin, int value) {	// loads from config
		
		if (!plugin.getConfig().contains("lobby")) {
			
			plugin.getConfig().createSection("lobby");
			plugin.getConfig().createSection("lobby.zombies");
			plugin.getConfig().createSection("lobby.size");
			plugin.getConfig().createSection("lobby.min-players");
			plugin.getConfig().set("lobby.size", 20);
			plugin.getConfig().set("lobby.min-players", 3);
		}
			
		plugin.getConfig().set("lobby.zombies", value);
		this.numberof_zombies = value;
	}

	public void setMaxSize(Plugin plugin, int value) {	// loads from config
	
		if (!plugin.getConfig().contains("lobby")) {
			
			plugin.getConfig().createSection("lobby");
			plugin.getConfig().createSection("lobby.zombies");
			plugin.getConfig().createSection("lobby.size");
			plugin.getConfig().createSection("lobby.min-players");
			plugin.getConfig().set("lobby.zombies", 2);
			plugin.getConfig().set("lobby.min-players", 3);
		}
			
		plugin.getConfig().set("lobby.size", value);
		this.max_size = value;
	}
	
	public void setMinPlayers(Plugin plugin, int value) {	// loads from config
		
		if (!plugin.getConfig().contains("lobby")) {
			
			plugin.getConfig().createSection("lobby");
			plugin.getConfig().createSection("lobby.zombies");
			plugin.getConfig().createSection("lobby.size");
			plugin.getConfig().createSection("lobby.min-players");
			plugin.getConfig().set("lobby.zombies", 2);
			plugin.getConfig().set("lobby.size", 20);
		}
			
		plugin.getConfig().set("lobby.min-players", value);
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
		
		if (!map.containsKey(player.getName()))
			return;
		
		map.remove(player.getName());
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
		
		return map.size() == max_size;
	}
	
	public int size() {
		
		return map.size();
	}
	
	public int getMinPlayers() {
		
		return min_players;
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
	
	public void startGame() {
		if (map.size() >= 3) 
			new StartCoolDown(pl);
	}
}