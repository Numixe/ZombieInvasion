package me.numixe.zombieinvasion;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {
	
	public String message = null; 
	
	public void sendMessage(Player p) {
		IChatBaseComponent barmsg = ChatSerializer.a("{\"text\":\"" + message + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}

}
