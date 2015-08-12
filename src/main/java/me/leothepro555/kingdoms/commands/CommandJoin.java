package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandJoin {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		
		if(args.length == 2){	
			if(plugin.kingdoms.getKeys(false).contains(args[1])){
			if(p.hasMetadata("kinv " + args[1])){
				if(!plugin.hasKingdom(p)){
					plugin.joinKingdom(args[1], p);
				p.sendMessage(ChatColor.GREEN + "Joined " + args[1]);
			}else{
				p.sendMessage(ChatColor.RED + "");
			}
			}else{
				p.sendMessage(ChatColor.RED + "You must be invited to join this kingdom. Notify the kingdom's owner or one of the mods.");
			}
		}else{
			p.sendMessage(ChatColor.RED + args[1] + " is not an existant kingdom. Bear in mind that this is case sensitive");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k join [kingdom name]");
		}
		
	}

}
