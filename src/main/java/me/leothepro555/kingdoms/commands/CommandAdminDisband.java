package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAdminDisband {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		  if(args.length == 3){
			  if(plugin.disbandKingdom(args[2])){
				  p.sendMessage(ChatColor.GREEN + args[2] + " successfully disbanded");
				  return;
				 
			  }else{
				  p.sendMessage(ChatColor.RED + args[2] + " does not exist!");
			  }
				  
		  }else{
			  p.sendMessage(ChatColor.RED + "Usage: /k admin disband [kingdom]");
		  }

	}

}
