package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandInvite {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(args.length == 2){
			if(Bukkit.getPlayer(args[1]) != null){
				if(plugin.getKingdom(Bukkit.getPlayer(args[1])) == null ||
						!plugin.getKingdom(Bukkit.getPlayer(args[1])).equals(plugin.getKingdom(p))){
				plugin.invitePlayer(plugin.getKingdom(p), Bukkit.getPlayer(args[1]));
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.RED + "===========INVITATION==========");
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + "");
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + p.getName() + " has invited you to join " + plugin.getKingdom(p));
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + "Do /k join " + plugin.getKingdom(p) + " to accept the invite.");
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + "");
				Bukkit.getPlayer(args[1]).sendMessage(ChatColor.RED + "===============================");
			p.sendMessage(ChatColor.GREEN + "Invited " + args[1] + " to your kingdom.");
			}else if(plugin.getKingdom(Bukkit.getPlayer(args[1])).equals(plugin.getKingdom(p))){
				p.sendMessage(ChatColor.RED + "This player is already in your kingdom!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "This player is offline or doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k invite [player]");
		}
	
	}

}
