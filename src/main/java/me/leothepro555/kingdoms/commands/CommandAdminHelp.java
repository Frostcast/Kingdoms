package me.leothepro555.kingdoms.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAdminHelp {
	
	public static void runCommand(Player p){

		p.sendMessage(ChatColor.RED + "===Admin Commands===");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin toggle " + ChatColor.AQUA + "allows you to harm anyone in any land, and allows you to break/place blocks in any land.");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin disband [kingdom] " + ChatColor.AQUA + "Forcefully disband a kingdom");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin selectsafezone " + ChatColor.AQUA + "Select safezone through worldedit selection. Claims the chunks of the selected blocks. (Region may be larger than expected.)");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin selectwarzone " + ChatColor.AQUA + "Select warzone through worldedit selection. Claims the chunks of the selected blocks. (Region may be larger than expected.)");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin safezone " + ChatColor.AQUA + "claims current piece of land as a safezone.");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin warzone " + ChatColor.AQUA + "claims current piece of land as a warzone.");
		p.sendMessage(ChatColor.DARK_PURPLE + "/k admin unclaim " + ChatColor.AQUA + "forcefully unclaims a claimed piece of land. Can also be used to unclaim warzones and safezones");
	 	p.sendMessage(ChatColor.DARK_PURPLE + "/k admin show [kingdom/player name] " + ChatColor.AQUA + "shows all info on the specified kingdom/player");
	 	p.sendMessage(ChatColor.DARK_PURPLE + "/k admin rp [kingdom] [amount] " + ChatColor.AQUA + "adds or subtracts resourcepoints from the specified kingdom. To subtract, put a minus '-' in front of the amount. A kingdom's "
	 			+ " rp cannot go below 0");
	 	p.sendMessage(ChatColor.DARK_PURPLE + "/k admin rpforplayer [player] [amount] " + ChatColor.AQUA + "adds or subtracts resourcepoints from the specified player's kingdom. To subtract, put a minus '-' in front of the amount. A kingdom's "
	 			+ " rp cannot go below 0");
  
	}

}
