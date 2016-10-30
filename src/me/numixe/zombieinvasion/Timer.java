package me.numixe.zombieinvasion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class Timer implements Runnable {
	
	int seconds;
	String command, broadcast;
	public static String square = null;
	final int id;
	private volatile boolean sigint = false;
	
	public Timer(String command, String broadcast, int seconds) {
		
		this.seconds = seconds;
		this.command = command;
		this.broadcast = broadcast;
		id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 0);
	}
	
	public synchronized void interrupt() {
		
		sigint = true;
	}

	public void run() {
		
		for (int i = seconds; i > 0; i--) {
			
			for (Player ps : plugin.lobby.getPlayers()) {
				
				switch (i) {
				
				case 5:
					square = "§c█§7████ §f» §6";
					event.sendbar(ps, i);
					break;
				case 4:
					square = "§c██§7███ §f» §6";
					event.sendbar(ps, i);
					break;
				case 3:
					square = "§c███§7██ §f» §6";
					event.sendbar(ps, i);
					break;
				case 2:
					square = "§c████§7█ §f» §6";
					event.sendbar(ps, i);
					break;
				case 1:
					square = "§c█████ §f» §6";
					event.sendbar(ps, i);
					
					break;
				default:
					break;
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (sigint) {
				
				Bukkit.getServer().broadcastMessage("Timer interrotto");
				Bukkit.getServer().getScheduler().cancelTask(id);
				return;
			}
		}
		
		Game.start();
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
}
