package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;
import me.leothepro555.kingdoms.main.TechnicalMethods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandHome {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(plugin.hasKingdom(p)){
			if(plugin.hasHome(plugin.getKingdom(p))){
				if(plugin.getChunkKingdom(TechnicalMethods.stringToLocation(plugin.kingdoms.getString(plugin.getKingdom(p) + ".home")).getChunk()) != null){
				if(plugin.getChunkKingdom(TechnicalMethods.stringToLocation(plugin.kingdoms.getString(plugin.getKingdom(p) + ".home")).getChunk()).equals(plugin.getKingdom(p))){
					p.sendMessage(ChatColor.GREEN + "Teleporting...");
					p.teleport(TechnicalMethods.stringToLocation(plugin.kingdoms.getString(plugin.getKingdom(p) + ".home")));

			}else{
				p.sendMessage(ChatColor.RED + "Contact your king to set a new home! Your old home was claimed by enemies!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "Contact your king to set a new home! Your old home is no longer in your land!");
			}
			}else{
				p.sendMessage(ChatColor.RED + "Your home has not been set or the spot where your home was is claimed.");
			}
		}
	
	}

}
