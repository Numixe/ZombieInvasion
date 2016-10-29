package me.numixe.zombieinvasion;

import static me.numixe.zombieinvasion.ZombieInvasion.*; // import all variable

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;


public class Disguiser implements Listener {
	
	@SuppressWarnings("deprecation")
	MobDisguise villager = new MobDisguise(DisguiseType.VILLAGER, true);
	@SuppressWarnings("deprecation")
	MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE, true);
	
	@SuppressWarnings("deprecation")
	public void beVillager(Player p) {
		//if (isZombie.contains(p.getName())) {
            api.disguiseToAll(p, villager);
            actionbar.message = "§a§lSei un Villager!";
            actionbar.sendMessage(p);
			
		//}
		
	}
	
	@SuppressWarnings("deprecation")
	public void beZombie(Player p) {
		//if (isZombie.contains(p.getName())) {
        api.disguiseToAll(p, zombie);
        actionbar.message = "§a§lSei uno Zombie!";
        actionbar.sendMessage(p);
		
	//}
	} 
}
