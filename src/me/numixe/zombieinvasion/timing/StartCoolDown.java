package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;
import me.numixe.zombieinvasion.entities.ScreenAPI;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StartCoolDown extends Timer {
	
	public static final int SECONDS = 25;
	private ZombieInvasion plugin;
	
	// questo e' un timer inizializza StartTimer quando termina

	public StartCoolDown(ZombieInvasion plugin) {
		super(plugin, SECONDS);
		this.plugin = plugin;
	}

	@Override
	public void handleSecond(int second) {
			
		for (Player p : plugin.getLobby().getPlayers()) {
						
			if (second % 10 == 5) {	// prende in cosiderazione solo {25, 15, 5}
				
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				ScreenAPI.sendTitle(p, "\u00A79\u00A7l" + (second + 5) + " seconds", null); // stampa {30, 20, 10}
			}
		}
	}
	
	@Override
	public void endAction() {
		new StartTimer(plugin);
	}
}
