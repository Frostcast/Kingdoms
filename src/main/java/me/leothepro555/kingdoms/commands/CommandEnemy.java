package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandEnemy {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p) || plugin.isMod(plugin.getKingdom(p), p)){
		if(args.length == 2){
			
			String kingdomtoally = "";
			
			if(!plugin.kingdoms.getKeys(false).contains(args[1])){
				
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				
				kingdomtoally = plugin.getKingdom(Bukkit.getOfflinePlayer(args[1]));
				if(plugin.kingdoms.getKeys(false).contains(kingdomtoally)){
					if(!kingdomtoally.equals(plugin.getKingdom(p))){
				if(plugin.isAlly(kingdomtoally, p)){
					p.sendMessage(ChatColor.RED + "You have severed alliance ties with " + kingdomtoally);
				    
				}
				
				plugin.messageKingdomPlayers(plugin.getKingdom(p), ChatColor.RED + "You are now enemies with " + kingdomtoally);
				 plugin.messageKingdomPlayers(kingdomtoally, ChatColor.RED + "You are now enemies with " + plugin.getKingdom(p));
				 plugin.enemyKingdom(plugin.getKingdom(p), kingdomtoally);
					p.sendMessage(ChatColor.GREEN + "You are now enemies with " + kingdomtoally);
				}else{
					p.sendMessage(ChatColor.RED + "You can't enemy yourself.");
				}
				}else{
					p.sendMessage(ChatColor.RED + args[1] + " doesn't exist! Be aware that kingdom names are case-sensitive");
				}
				
			}else{
				p.sendMessage(ChatColor.RED + "This player doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You can't enemy yourself.");
		}
			
		}else{
			kingdomtoally = args[1];
			
			if(plugin.kingdoms.getKeys(false).contains(kingdomtoally)){
				if(!kingdomtoally.equals(plugin.getKingdom(p))){
					if(plugin.isAlly(kingdomtoally, p)){
						p.sendMessage(ChatColor.RED + "You have severed alliance ties with " + kingdomtoally);
					}
			
			
					plugin.enemyKingdom(plugin.getKingdom(p), kingdomtoally);
					plugin.messageKingdomPlayers(plugin.getKingdom(p), ChatColor.RED + "You are now enemies with " + kingdomtoally);
					plugin.messageKingdomPlayers(kingdomtoally, ChatColor.RED + "You are now enemies with " + plugin.getKingdom(p));
				p.sendMessage(ChatColor.RED + "You are now enemies with " + kingdomtoally);
			}else{
				p.sendMessage(ChatColor.RED + "You can't enemy yourself.");
			}
			
		}else{
			p.sendMessage(ChatColor.RED + args[1] + " doesn't exist! Be aware that kingdom names are case-sensitive");
		}
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k enemy [kingdom name/ player name]");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom Kings and kingdom Mods enemy other kingdoms!");
		}
	
	}

}
