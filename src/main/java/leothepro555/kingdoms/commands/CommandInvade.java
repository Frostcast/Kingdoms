package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CommandInvade {
	
	public static void runCommand(Player p, Kingdoms plugin){

		if(p.getGameMode() == GameMode.CREATIVE && !p.hasPermission("kingdoms.admin")){
			p.sendMessage(ChatColor.RED + "You can't invade while in creative!");
			return;
		}
		if(plugin.hasKingdom(p)){
	plugin.invadeCurrentPosition(p);
		
	}else{
		p.sendMessage(ChatColor.RED + "You need a kingdom to invade others!");
	}
	
	
	}

}
