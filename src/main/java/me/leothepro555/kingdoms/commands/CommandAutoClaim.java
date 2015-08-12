package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAutoClaim {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.hasKingdom(p)){
			
             if(plugin.isMod(plugin.getKingdom(p), p) || plugin.isKing(p)){
            	 plugin.claimCurrentPosition(p);
				 if(!plugin.rapidclaiming.contains(p.getUniqueId())){
					 plugin.rapidclaiming.add(p.getUniqueId());
	            	 p.sendMessage(ChatColor.GREEN + "Enabled auto-claiming");
	            	 }else{
	            		 plugin.rapidclaiming.remove(p.getUniqueId());
	            		 p.sendMessage(ChatColor.RED + "Disabled auto-claiming");
	            	 }
			}else{
				p.sendMessage(ChatColor.RED + "Your rank is too low to claim land!");
			}
             
            
             
				
			}else{
				p.sendMessage(ChatColor.RED + "You are not in a kingdom!");
			}
			
	}

}
