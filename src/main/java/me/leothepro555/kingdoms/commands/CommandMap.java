package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandMap {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(args.length == 1){
		if(p.hasPermission("kingdoms.map")){
		plugin.displayMap(p);
	}else{
		p.sendMessage(ChatColor.RED + "Insufficient Permissions!");
	}
	}else{
		if(!plugin.mapmode.contains(p.getUniqueId())){
		p.sendMessage(ChatColor.GREEN + "Auto map on");
		plugin.displayMap(p);
		plugin.mapmode.add(p.getUniqueId());
	}else{
		p.sendMessage(ChatColor.RED + "Auto map off");
		plugin.mapmode.remove(p.getUniqueId());
	}
	}
		
	}

}
