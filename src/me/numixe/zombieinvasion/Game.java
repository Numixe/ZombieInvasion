package me.numixe.zombieinvasion;

import org.bukkit.entity.Player;
import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Game {

	Lobby lobby;	// pointer to the lobby

	public Game(Lobby lobby) {
		
		this.lobby = lobby;
	}
	
	public static void start(Player p) {
		event.getSpawn(p);
		plugin.lobby.randomAssignID(p);
		p.setScoreboard(scoreboard.getKillBoard());
		scoreboard.refresh();
	}
	
	public static void stop() {
		
	}
}
