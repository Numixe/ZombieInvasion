package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.ScreenAPI;

import org.bukkit.entity.Player;

public class StartTimer extends Timer {
	
	public static final int SECONDS = 5;
	private ZombieInvasion plugin;

	public StartTimer(ZombieInvasion plugin) {
		super(plugin, SECONDS);
		this.plugin = plugin;
	}

	@Override
	public void handleSecond(int second) {
		
		for (Player p : plugin.getLobby().getPlayers()) {
			
			String square = null;
			
			switch (second) {
			
			case 5:
				square = "\u00A77The Game Starts in \u00A7c█\u00A77████ \u00A7f» \u00A76";
				break;
			case 4:
				square = "\u00A77The Game Starts in \u00A7c██\u00A77███ \u00A7f» \u00A76";
				break;
			case 3:
				square = "\u00A77The Game Starts in \u00A7c███\u00A77██ \u00A7f» \u00A76";
				break;
			case 2:
				square = "\u00A77The Game Starts in \u00A7c████\u00A77█ \u00A7f» \u00A76";
				break;
			case 1:
				square = "\u00A77The Game Starts in \u00A7c█████ \u00A7f» \u00A76";		
				break;
			default:
				continue;
			}
			
			ScreenAPI.sendTimerbar(p, second, square);
		}
	}
	
	@Override
	public void endAction() {
		
		plugin.getGame().start();
	}
}
