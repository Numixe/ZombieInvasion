package me.numixe.zombieinvasion.entities;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ScreenAPI {
	
	public static void sendMessage(Player p, String message) {
		
		IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"" + message + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}

	public static void sendTimerbar(Player p, int i, String square) {
		
		if (square == null)
			return;
			
		sendMessage(p, square + i);
	}
	
	@SuppressWarnings("deprecation")
	public static void sendTitle(Player p, String title, String subtitle) {
		
		p.sendTitle(title, subtitle);
	}
}
