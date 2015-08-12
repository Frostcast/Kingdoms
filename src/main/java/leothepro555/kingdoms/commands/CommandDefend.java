package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandDefend {
	
	public static void runCommand(Player p, Kingdoms plugin){

		
		
		if(plugin.invasiondef.containsKey(p.getUniqueId())){
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Defend our land!");
			p.teleport(plugin.invasiondef.get(p.getUniqueId()));
			plugin.invasiondef.remove(p.getUniqueId());
		}
		
		
		
	}

}
