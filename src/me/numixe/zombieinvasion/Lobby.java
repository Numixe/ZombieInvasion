package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * @return if the lobby is full or got filled by this player
	 */
	
	public boolean addPlayer(Player player) {	
		
		if (map.containsKey(player.getName()))
			return false;
		
		if (isFull())
			return true;
		
		map.put(player.getName(), PlayerID.NONE);
		
		if (isFull())	// verify another time to notify the full status
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @return The player identity, or null if the player is not in the lobby
	 */
	
	public PlayerID getPlayerID(Player player) {
		
		return map.get(player.getName());
	}
	
	public void clear() {
		
		map.clear();
	}
	
	public boolean isFull() {
		
		return map.size() == SIZEOF_LOBBY;
	}
	
	public Set<String> getPlayerNames() {
		
		return map.keySet();
	}
	
	public void randomAssignID() {
		
		int choosen = NUMBEROF_ZOMBIES;	// set choosen to the initial number of zombies
		
		for (String name : map.keySet()) {
			
			if (choosen > 0) {
				
				int rand = plugin.randomInt(0, map.size());	// generate a random int between 0 and the size of the lobby - 1
				
				if (rand < choosen) {	// verify if that number is minor than choosen
					map.put(name, PlayerID.ZOMBIE);		// set that player as zombie
					choosen--;
				}
				
			} else
				map.put(name, PlayerID.VILLAGER);	// otherwise set the other players to villager
			
		}
	}
}
