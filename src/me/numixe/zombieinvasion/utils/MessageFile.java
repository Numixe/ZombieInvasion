package me.numixe.zombieinvasion.utils;

import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class MessageFile {
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadMessagesConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(pl.getDataFolder(), "messages.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = new InputStreamReader(pl.getResource("messages.yml"));
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getMessage() {
	    if (customConfig == null) {
	        reloadMessagesConfig();
	    }
	    return customConfig;
	}
	
	public void saveMessagesConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getMessage().save(customConfigFile);
	    } catch (IOException ex) {
	        pl.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public void saveDefaultMessagesConfig() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(pl.getDataFolder(), "messages.yml");
	    }
	    if (!customConfigFile.exists()) {            
	         pl.saveResource("messages.yml", false);
	     }
	}
}
