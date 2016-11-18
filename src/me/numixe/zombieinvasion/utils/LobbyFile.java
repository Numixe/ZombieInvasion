package me.numixe.zombieinvasion.utils;

import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class LobbyFile {
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadLobbyConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(pl.getDataFolder(), "lobby.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = new InputStreamReader(pl.getResource("lobby.yml"));
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getLobby() {
	    if (customConfig == null) {
	        reloadLobbyConfig();
	    }
	    return customConfig;
	}
	
	public void saveLobbyConfig() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getLobby().save(customConfigFile);
	    } catch (IOException ex) {
	        pl.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	
	public void saveDefaultLobbyConfig() {
	    if (customConfigFile == null) {
	        customConfigFile = new File(pl.getDataFolder(), "lobby.yml");
	    }
	    if (!customConfigFile.exists()) {            
	         pl.saveResource("lobby.yml", false);
	     }
	}
}
