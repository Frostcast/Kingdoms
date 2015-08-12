package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandNexus {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.hasKingdom(p)){
			if(plugin.isKing(p)){
				plugin.placingnexusblock.add(p.getUniqueId());
				p.sendMessage(ChatColor.RED + "=======================================");
				p.sendMessage(ChatColor.RED + "");
				p.sendMessage(ChatColor.BLUE + "Right click to replace a clicked block with your nexus. Left click to cancel. Be careful"
						+ " not to click on chests/important blocks.");
				p.sendMessage(ChatColor.RED + "");
				p.sendMessage(ChatColor.RED + "=======================================");
			}else{
				p.sendMessage(ChatColor.RED + "You must be your kingdom's king to set your nexus!");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You don't have a kingdom!");
		}
	
	}

}
