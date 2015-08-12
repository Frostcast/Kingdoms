package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandDisband {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.hasKingdom(p)){
			if(plugin.isKing(p)){
			if(args.length == 1){
				plugin.disbandKingdom(plugin.getKingdom(p));
				p.sendMessage(ChatColor.RED + "Kingdom Disbanded.");
			}else{
				p.sendMessage(ChatColor.RED + "Usage: /k disband " + ChatColor.BOLD + "to permanently remove your kingdom.");
			}
			}else{
				p.sendMessage(ChatColor.RED + "Only kings can disband kingdoms.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You don't have a kingdom.");
		}
		
		
	}

}
