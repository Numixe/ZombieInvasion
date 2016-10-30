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
			
			Bukkit.getServer().broadcastMessage(broadcast.replaceAll("&sec", String.valueOf(i)));
			
			for (Player ps : Bukkit.getOnlinePlayers()) {
				if (i == 5) {
				square = "§c█§7████ §f» §6";
				event.sendbar(ps, i);
				} else if (i == 4) {
					square = "§c██§7███ §f» §6";
					event.sendbar(ps, i);
				} else if (i == 3) {
					square = "§c███§7██ §f» §6";
					event.sendbar(ps, i);
				} else if (i == 2) {
					square = "§c████§7█ §f» §6";
					event.sendbar(ps, i);
				} else if (i == 1) {
					square = "§c█████ §f» §6";
					event.sendbar(ps, i);
				}
			}
			
			if (sigint) {
				
				Bukkit.getServer().broadcastMessage("Timer interrotto");
				Bukkit.getServer().getScheduler().cancelTask(id);
				return;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (Player ps : Bukkit.getOnlinePlayers()) {
		event.getSpawn(ps);
		Game.start(ps);
		Bukkit.getServer().getScheduler().cancelTask(id);
		}
	}
}
