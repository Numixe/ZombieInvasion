package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;

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
			
			plugin.getActionBar().sendbar(p, second, square);
		}
	}
	
	@Override
	public void endAction() {
		
		plugin.getGame().start();
	}
}
