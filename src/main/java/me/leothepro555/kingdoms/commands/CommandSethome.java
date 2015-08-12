package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;
import me.leothepro555.kingdoms.main.TechnicalMethods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandSethome {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.isKing(p)){
			if(plugin.getChunkKingdom(p.getLocation().getChunk()) != null &&
				plugin.getChunkKingdom(p.getLocation().getChunk()).equals(plugin.getKingdom(p))){
				plugin.kingdoms.set(plugin.getKingdom(p) + ".home", TechnicalMethods.locationToString(p.getLocation()));
				plugin.saveKingdoms();
			p.sendMessage(ChatColor.GREEN + "Kingdom home set to your location");
		}else{
			p.sendMessage(ChatColor.RED + "You can't set your kingdom home outside your land!");
		}
			
		}else{
			p.sendMessage(ChatColor.RED + "Only kingdom king can set the kingdom home!");
		}
		
	}

}
