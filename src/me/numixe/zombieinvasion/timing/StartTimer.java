package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.ScreenAPI;

import org.bukkit.Sound;
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
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				square = "\u00A77The Game Starts in \u00A7c\u2588\u00A77\u2588\u2588\u2588\u2588 \u00A7f» \u00A76";
				break;
			case 4:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				square = "\u00A77The Game Starts in \u00A7c\u2588\u2588\u00A77\u2588\u2588\u2588 \u00A7f» \u00A76";
				break;
			case 3:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				square = "\u00A77The Game Starts in \u00A7c\u2588\u2588\u2588\u00A77\u2588\u2588 \u00A7f» \u00A76";
				break;
			case 2:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				square = "\u00A77The Game Starts in \u00A7c\u2588\u2588\u2588\u2588\u00A77\u2588 \u00A7f» \u00A76";
				break;
			case 1:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				square = "\u00A77The Game Starts in \u00A7c\u2588\u2588\u2588\u2588\u2588 \u00A7f» \u00A76";		
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
