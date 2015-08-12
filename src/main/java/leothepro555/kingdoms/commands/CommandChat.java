package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandChat {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		
		if(args.length == 2){
			if(args[1].equalsIgnoreCase("p") || args[1].equalsIgnoreCase("public")){
				plugin.setChatOption(p, "public");
				p.sendMessage(ChatColor.AQUA + "Chat set to public.");
			}else if(args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("ally")){
			if(plugin.hasKingdom(p)){	
				plugin.setChatOption(p, "ally");
				p.sendMessage(ChatColor.AQUA + "Chat set to allies.");
			}else{
				p.sendMessage(ChatColor.RED + "You need a kingdom to toggle private ally kingdoms chat!");
			}
			}else if(args[1].equalsIgnoreCase("f") || 
					args[1].equalsIgnoreCase("kingdom")||
					args[1].equalsIgnoreCase("k") ||
					args[1].equalsIgnoreCase("faction")){
			if(plugin.hasKingdom(p)){	
				p.sendMessage(ChatColor.AQUA + "Chat set to kingdom.");
				plugin.setChatOption(p, "kingdom");
			}else{
				p.sendMessage(ChatColor.RED + "You need a kingdom to toggle private kingdom chat!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "Only the options k, p and a are allowed.");
			}
		}else{
			p.sendMessage(ChatColor.AQUA + "Current Chat Group: " + ChatColor.GOLD + plugin.getChatOption(p));
		}
		
		
	}

}
