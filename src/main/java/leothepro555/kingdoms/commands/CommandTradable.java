package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommandTradable {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.usewhitelist){
			p.sendMessage(ChatColor.GREEN + "Conversion ratio: 1 resource point per " + plugin.rpi + " items");
			p.sendMessage(ChatColor.GREEN + "===Enabled Trades===");
		for(Material mat:plugin.whitelistitems.keySet()){
			p.sendMessage(ChatColor.GREEN + mat.toString() + " | Worth " + plugin.whitelistitems.get(mat) + " item(s)");
		}
	}else{
		p.sendMessage(ChatColor.RED + "===Disabled Trades===");
		for(Material mat:plugin.blacklistitems){
			p.sendMessage(ChatColor.RED + mat.toString());
		}
		p.sendMessage("");
		p.sendMessage(ChatColor.GREEN + "===Special Trades===");
		for(Material mat:plugin.specialcaseitems.keySet()){
			p.sendMessage(ChatColor.GREEN + mat.toString() + " | Worth " + plugin.whitelistitems.get(mat) + " item(s)");
		}
	}
	
	}

}
