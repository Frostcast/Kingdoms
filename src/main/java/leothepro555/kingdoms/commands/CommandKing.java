package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandKing {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p)){
		if(args.length == 2){
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				if(plugin.getKingdom(Bukkit.getOfflinePlayer(args[1])).equals(plugin.getKingdom(p))){
				plugin.kingPlayer(plugin.getKingdom(p), Bukkit.getOfflinePlayer(args[1]).getUniqueId());
				p.sendMessage(ChatColor.GREEN + "Passed leadership of your kingdom to " + args[1]);
			}
			}else{
				p.sendMessage(ChatColor.RED + "This player is offline or doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You already are the king!");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k king [player]");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom Kings can pass on their leadership!");
		}
	
	}

}
