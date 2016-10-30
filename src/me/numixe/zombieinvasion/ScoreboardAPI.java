package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardAPI {
	
	public Scoreboard board;
	public Objective obj;
	
	public static final String TITLE = "�d�lZombie Invasion";
	public static final String VillagerColor = "�a";
	public static final String ZombieColor = "�c";
	
	public ScoreboardAPI() { // funzione da caricare all'avvio del server
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("zombieinvasion", "dummy");
		obj.setDisplayName(TITLE);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public Scoreboard getKillBoard() {
		return board;
	}
	
	@SuppressWarnings("unused")
	public void refresh() { // 
		
		Map<PlayerID, Integer> count = plugin.lobby.getCount();
		
		// per usare il count
		// conto dei villager = count.get(PlayerID.VILLAGER)
		// conto degli zombie = count.get(PlayerID.ZOMBIE)
		
		// ora aggiorna la tua scoreboard
	}
}
