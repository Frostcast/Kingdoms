package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandKick {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p)|| plugin.isMod(plugin.getKingdom(p), p)){
		if(args.length == 2){
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				if(plugin.getKingdom(Bukkit.getOfflinePlayer(args[1])).equals(plugin.getKingdom(p))){
					if(plugin.isMod(plugin.getKingdom(p), Bukkit.getOfflinePlayer(args[1])) &&
							!plugin.isKing(p)){
						
						p.sendMessage(ChatColor.RED + "Only the King can kick mods!");
						
					}else{
						
						plugin.quitKingdom(plugin.getKingdom(p), Bukkit.getOfflinePlayer(args[1]));
						p.sendMessage(ChatColor.RED + args[1] + " has been kicked from your kingdom");
						plugin.messageKingdomPlayers(plugin.getKingdom(p), ChatColor.GREEN + p.getName() + " kicked " + args[1] + " from your kingdom! :O");
						
					}
			}else{
				p.sendMessage(ChatColor.RED + "This player isn't in your kingdom!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "This player doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You can't kick yourself!");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k kick [player]");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom Kings and Mods can kick members!");
		}
	
	}

}
