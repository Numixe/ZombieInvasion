package me.numixe.zombieinvasion.timing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import static me.numixe.zombieinvasion.ZombieInvasion.pl;

public class Timer implements Runnable {
	
	int seconds;
	final int id;
	private volatile boolean sigint = false;
	
	public static final int BUKKIT_SECOND = 20;
	
	public Timer(Plugin plugin, int seconds) {
		
		this.seconds = seconds;
		
		if(this.seconds < 0)
			this.seconds = -seconds;
			
		id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, BUKKIT_SECOND);
	}
	
	public synchronized void interrupt() {
		
		sigint = true;
	}
	
	public synchronized void forcedInterrupt() {
		
		//sigint = true;
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public void interrompi() {
		
		Bukkit.getServer().getScheduler().cancelTask(id);
		pl.getGame().timing = false;
	}

	public void run() {
		
	    pl.getGame().timing = true;
		
		handleSecond(seconds);
			
		if (sigint) {
				
			Bukkit.getServer().broadcastMessage("Timer interrotto");
			Bukkit.getServer().getScheduler().cancelTask(id);
			sigint = false;
			return;
		}
		
		seconds--;
		
		if (seconds == -1) {
			
			endAction();
			Bukkit.getServer().getScheduler().cancelTask(id);
			pl.getGame().timing = false;
		}
	}
	
	public void handleSecond(int second) {
		
		// override this method in subclasses
	}
	
	public void endAction() {
		
		// override this method in subclasses
	}
}
