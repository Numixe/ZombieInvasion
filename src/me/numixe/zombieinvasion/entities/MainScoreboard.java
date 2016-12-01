package me.numixe.zombieinvasion.entities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import static me.numixe.zombieinvasion.ZombieInvasion.*;

public class MainScoreboard {
	
	public void setupScoreboard(Player p, int timer) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard onJoin = sm.getNewScoreboard();
		Objective o = onJoin.registerNewObjective("dash", "dummy");

		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(pl.getScoreboard().TITLE);

		Score spacer = null;

		Score nameTitle = null;
		Score name = null;

		Score spacer2 = null;

		Score server = null;
		Score servername = null;
		
		Score spacer3 = null;
		
		Score time = null;
		Score timen = null;

		try {

		spacer = o.getScore((ChatColor.BLUE + ""));
		spacer.setScore(9);

		nameTitle = o.getScore("\u00a7a\u00a7lServer");
		nameTitle.setScore(8);

		name = o.getScore("\u00a7fZI-1");
		name.setScore(7);

		spacer2 = o.getScore(ChatColor.RED + "");
		spacer2.setScore(6);
		
		server = o.getScore("\u00a7e\u00a7lPlayers");
		server.setScore(5);
		
		servername = o.getScore("\u00a7f" + pl.getLobby().size() + "/16");
		servername.setScore(4);
		
		spacer3 = o.getScore((ChatColor.YELLOW + ""));
		spacer3.setScore(3);
		
		time = o.getScore("\u00a7c\u00a7lGame Status");
		time.setScore(2);
		
		if (timer == 0) {
			timen = o.getScore("\u00a7fWaiting Players");
			timen.setScore(1);
		} else {	
		timen = o.getScore("\u00a7fStarting in " + timer);
		timen.setScore(1);
		}


		p.setScoreboard(onJoin);

		} catch (Exception ex)
		{
		System.out.println("ex");
		}
		}


}
