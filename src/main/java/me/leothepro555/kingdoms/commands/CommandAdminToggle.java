package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAdminToggle {
	
	public static void runCommand(Player p, Kingdoms plugin){

  	  if(plugin.adminmode.contains(p.getUniqueId())){
  		plugin.adminmode.remove(p.getUniqueId());
  		  p.sendMessage(ChatColor.RED + "[Kingdoms] Admin mode disabled");
  	  }else{
  		plugin.adminmode.add(p.getUniqueId());
  		  p.sendMessage(ChatColor.GREEN + "[Kingdoms] Admin mode enabled");
  	  }
    
	}

}
