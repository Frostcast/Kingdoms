package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandUnclaim {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.hasKingdom(p)){
             if(plugin.isMod(plugin.getKingdom(p), p) || plugin.isKing(p)){			
            	 if(!plugin.isNexusChunk(p.getLocation().getChunk())){
            		 plugin.unclaimCurrentPosition(p);
             }else{
            	 p.sendMessage(ChatColor.RED + "You can't unclaim your nexus land! You must move your nexus with /k nexus before unclaiming this patch of land!");
             }
			}else{
				p.sendMessage(ChatColor.RED + "Your rank is too low to uclaim land!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "You are not in a kingdom!");
			}
			
	}

}
