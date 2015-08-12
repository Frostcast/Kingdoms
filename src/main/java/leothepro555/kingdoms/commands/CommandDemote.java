package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandDemote {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p)){
		if(args.length == 2){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				plugin.unModPlayer(plugin.getKingdom(p), Bukkit.getOfflinePlayer(args[1]).getUniqueId());
				p.sendMessage(ChatColor.RED + "Demoted " + args[1] + ".");
			}else{
				p.sendMessage(ChatColor.RED + "This player is offline or doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k mod [player]");
		}
	}else{
		p.sendMessage(ChatColor.RED + "Only kingdom Kings can demote moderators!");
	}
	
	}

}
