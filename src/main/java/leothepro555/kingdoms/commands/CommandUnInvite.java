package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandUnInvite {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(args.length == 2){
			if(Bukkit.getPlayer(args[1]) != null){
				plugin.deinvitePlayer(plugin.getKingdom(p), Bukkit.getPlayer(args[1]));
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.RED + "Your invitation to " + plugin.getKingdom(p) + " has been revoked.");
				p.sendMessage(ChatColor.RED + "Unnvited " + args[1] + " to your kingdom.");
			}else{
				p.sendMessage(ChatColor.RED + "This player is offline or doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k uninvite [player]");
		}
	
	}

}
