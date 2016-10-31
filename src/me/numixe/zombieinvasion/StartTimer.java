package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.plugin;

import org.bukkit.entity.Player;

public class StartTimer extends Timer {
	
	public static final int SECONDS = 5;

	public StartTimer() {
		super(SECONDS);
	}

	@Override
	public void handleSecond(int second) {
		
		for (Player p : plugin.lobby.getPlayers()) {
			
			String square = null;
			
			switch (second) {
			
			case 5:
				square = "§c█§7████ §f» §6";
				break;
			case 4:
				square = "§c██§7███ §f» §6";
				break;
			case 3:
				square = "§c███§7██ §f» §6";
				break;
			case 2:
				square = "§c████§7█ §f» §6";
				break;
			case 1:
				square = "§c█████ §f» §6";		
				break;
			default:
				continue;
			}
			
			plugin.actionbar.sendbar(p, second, square);
		}
	}
	
	@Override
	public void endAction() {
		
		plugin.game.start();
	}
}
