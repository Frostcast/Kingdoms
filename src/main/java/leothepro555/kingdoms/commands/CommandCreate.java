package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandCreate {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(p.hasPermission("kingdoms.create")){
		if(!plugin.hasKingdom(p)){
		if(args.length == 2){
		if(plugin.kingdoms.get(args[1]) == null){
			if(!args[1].contains(" ")){
				if(!args[1].equalsIgnoreCase("SafeZone")&&
						!args[1].equalsIgnoreCase("WarZone")){
					plugin.newKingdom(p.getUniqueId(), args[1]);
			
			}
			}else{
			p.sendMessage(ChatColor.RED + "No spaces allowed in kingdom name");
		}
		}else{
			p.sendMessage(ChatColor.RED + args[1] + " already exists!");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k create [kingdomname]");
		}
	}else{
		p.sendMessage(ChatColor.RED + "You already have a kingdom! You must leave before creating a new Kingdom");
	}
	}
	
	}

}
