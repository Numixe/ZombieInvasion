package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Lobby {

	public static final int NUMBEROF_ZOMBIES = 2;
	public static final int SIZEOF_LOBBY = 20;

	private Map<String, PlayerID> map;	// name, player identity
	
	public Lobby() {
		
		map = new HashMap<String, PlayerID>();
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
		
		return map.size() == SIZEOF_LOBBY;
	}
	
	public Set<String> getPlayersName() {
		
		return map.keySet();
	}
	
	public List<Player> getPlayers() {
		
		List<Player> list = new ArrayList<Player>();
		
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
	
	public void randomAssignID(Player p) {
		
		int tochoose = NUMBEROF_ZOMBIES;
		
		while(tochoose > 0) {	// finche non sono stati scelti tutti gli zombie
			
			for (String name : map.keySet()) {
				
				if (map.get(name) == PlayerID.ZOMBIE)	// skip zombies
					continue;
			
				int rand = plugin.randomInt(0, map.size());
				
				if (rand < tochoose) {
					map.put(name, PlayerID.ZOMBIE);
					tochoose--;
				} else
					map.put(name, PlayerID.VILLAGER);
			}
		}
	}
}
