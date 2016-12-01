package me.numixe.zombieinvasion.timing;

import me.numixe.zombieinvasion.ZombieInvasion;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import static me.numixe.zombieinvasion.ZombieInvasion.l;

public class StartCoolDown extends Timer {
	
	public static final int SECONDS = 30;
	private ZombieInvasion plugin;
	
	// questo e' un timer inizializza StartTimer quando termina

	public StartCoolDown(ZombieInvasion plugin) {
		super(plugin, SECONDS);
		this.plugin = plugin;
	}

	@Override
	public void handleSecond(int second) {
		
		if (!(plugin.getLobby().size() >= l.getLobby().getInt("Lobby.min_players"))) {
			interrompi();
			for (Player o : Bukkit.getOnlinePlayers())
				plugin.getMainScoreboard().setupScoreboard(o, 0);
			return;
		}
			
		for (Player p : plugin.getLobby().getPlayers()) {
						
			//if (second % 10 == 5) {	// prende in cosiderazione solo {25, 15, 5
				
				//ScreenAPI.sendTitle(p, "\u00A79\u00A7l" + (second + 5) + " seconds", null); // stampa {30, 20, 10}
				plugin.getMainScoreboard().setupScoreboard(p, second);
				if (second <= 10 && second >= 4) 
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 100, 1);
				else if (second <= 3 && second != 0)
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 100, 2);
				
			//}
		}
	}
	
	@Override
	public void endAction() {
		for (Player p : plugin.getLobby().getPlayers())
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 100, 1);
		plugin.getGame().start();
	}
}
