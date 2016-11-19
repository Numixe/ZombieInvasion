package me.numixe.zombieinvasion.utils;

import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class SpawnFile {
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadSpawnsConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(pl.getDataFolder(), "spawns.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = new InputStreamReader(pl.getResource("spawns.yml"));
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getSpawns() {
	    if (customConfig == null) {
	        reloadSpawnsConfig();
	    }
	    return customConfig;
	}
	
	public void saveSpawnsConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getSpawns().save(customConfigFile);
	    } catch (IOException ex) {
	        pl.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public void saveDefaultSpawnsConfig() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(pl.getDataFolder(), "spawns.yml");
	    }
	    if (!customConfigFile.exists()) {            
	         pl.saveResource("spawns.yml", false);
	     }
	}
}
