package me.numixe.zombieinvasion.timing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Timer implements Runnable {
	
	int seconds;
	final int id;
	private volatile boolean sigint = false;
	
	public Timer(Plugin plugin, int seconds) {
		
		this.seconds = seconds;
		id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 0);
	}
	
	public synchronized void interrupt() {
		
		sigint = true;
	}

	public void run() {
		
		for (int s = seconds; s > 0; s--) {
			
			handleSecond(s);
			
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
		
		endAction();
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public void handleSecond(int second) {
		
		// override this method in subclasses
	}
	
	public void endAction() {
		
		// override this method in subclasses
	}
}
