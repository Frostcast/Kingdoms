package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandLeave {
	
	public static void runCommand(Player p, Kingdoms plugin)
	{

		
		if(plugin.hasKingdom(p)){
		if(!plugin.isKing(p)){
			p.sendMessage(ChatColor.RED + "Left " + plugin.getKingdom(p));
			plugin.quitKingdom(plugin.getKingdom(p), p);
		}else{
			p.sendMessage(ChatColor.RED + "As a king, you must pass on your leadership to another member of the kingdom with /k king [playername], or disband your kingdom with /k disband");
		}
	}else{
		p.sendMessage(ChatColor.RED + "You don't have a kingdom");
	}

	
	}
}
