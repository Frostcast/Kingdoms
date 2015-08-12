package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandMod {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p)){
		if(args.length == 2){
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				plugin.modPlayer(plugin.getKingdom(p), Bukkit.getOfflinePlayer(args[1]).getUniqueId());
				p.sendMessage(ChatColor.GREEN + "Promoted " + args[1] + " to moderator of your kingdom");
			}else{
				p.sendMessage(ChatColor.RED + "This player is offline or doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "The King can't be a moderator!");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k mod [player]");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom Kings can promote members!");
		}
	
	}

}
