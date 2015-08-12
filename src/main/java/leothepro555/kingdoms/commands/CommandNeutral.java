package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandNeutral {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(plugin.isKing(p) || plugin.isMod(plugin.plugin.getKingdom(p), p)){
		if(args.length == 2){
			
			String kingdomtoally = "";
			
			if(!plugin.kingdoms.getKeys(false).contains(args[1])){
				
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				
				kingdomtoally = plugin.getKingdom(Bukkit.getOfflinePlayer(args[1]));
				if(plugin.kingdoms.getKeys(false).contains(kingdomtoally)){
					if(!kingdomtoally.equals(plugin.getKingdom(p))){
				if(plugin.isAlly(kingdomtoally, p)){
					p.sendMessage(ChatColor.RED + "You have severed alliance ties with " + kingdomtoally + " without their knowledge.");
				    
				}
				
				if(plugin.isEnemy(kingdomtoally, p)){
					p.sendMessage(ChatColor.RED + "Be warned that " + kingdomtoally + " may still have your kingdom marked as an enemy, without your knowledge.");
				}
				
				
				plugin.neutralizeKingdom(plugin.getKingdom(p), kingdomtoally);
					p.sendMessage(ChatColor.GREEN + "You are now neutral with " + kingdomtoally);
				}else{
					p.sendMessage(ChatColor.RED + "You can't neutral yourself.");
				}
				}else{
					p.sendMessage(ChatColor.RED + args[1] + " doesn't exist! Be aware that kingdom names are case-sensitive");
				}
				
			}else{
				p.sendMessage(ChatColor.RED + "This player doesn't exist! Bear in mind that this is case sensitive.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You can't neutral yourself.");
		}
			
		}else{
			kingdomtoally = args[1];
			
			if(plugin.kingdoms.getKeys(false).contains(kingdomtoally)){
				if(!kingdomtoally.equals(plugin.getKingdom(p))){
					if(plugin.isAlly(kingdomtoally, p)){
						p.sendMessage(ChatColor.RED + "You have severed alliance ties with " + kingdomtoally + " without their knowledge.");
					}
					
					if(plugin.isEnemy(kingdomtoally, p)){
						p.sendMessage(ChatColor.RED + "Be warned that " + kingdomtoally + " may still have your kingdom marked as an enemy, without your knowledge.");
					}
					plugin. messageKingdomPlayers(plugin.getKingdom(p), ChatColor.GRAY + "You are now neutral with " + kingdomtoally);
			   
			    plugin.neutralizeKingdom(plugin.getKingdom(p), kingdomtoally);
				
			}else{
				p.sendMessage(ChatColor.RED + "You can't neutral yourself.");
			}
			
		}else{
			p.sendMessage(ChatColor.RED + args[1] + " doesn't exist! Be aware that kingdom names are case-sensitive");
		}
		}
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k neutral [kingdom name/ player name]");
		}
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom Kings and kingdom Mods neutral other kingdoms!");
		}
	
		
	
	}

}
