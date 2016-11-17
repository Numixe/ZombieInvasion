package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.ScreenAPI;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StartCoolDown extends Timer {
	
	public static final int SECONDS = 30;
	private ZombieInvasion plugin;

	public StartCoolDown(ZombieInvasion plugin) {
		super(plugin, SECONDS);
		this.plugin = plugin;
	}

	@Override
	public void handleSecond(int second) {
			
		for (Player p : plugin.getLobby().getPlayers()) {
			
			String message = null;
			
			switch (second) {
			
			case 30:			
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A79\u00A7l30 seconds";
				break;
			case 20:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A79\u00A7l20 seconds";
				break;
			case 10:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A79\u00A7l10 seconds";
				break;
			case 3:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A7c\u00A7l3 seconds";
				break;
			case 2:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A76\u00A7l2 seconds";	
				break;
			case 1:
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				message = "\u00A7a\u00A7l1 second";	
				break;
			default:
				continue;
			}
			
			ScreenAPI.sendTitle(p, message, null);
		}
	}
	
	@Override
	public void endAction() {
		plugin.getGame().start();
	}
}
