package me.leothepro555.kingdoms.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandHelp {
	
	public static void runCommand(Player p){
		
		p.sendMessage(ChatColor.LIGHT_PURPLE + "===Kingdoms Commands===");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k " + ChatColor.AQUA + "shows all commands");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k nexus " + ChatColor.AQUA + "changes a block into your kingdom's nexus block");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k info " + ChatColor.AQUA + "shows how Kingdoms work");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k join " + ChatColor.AQUA + "joins a Kingdom. Must be invited");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k leave " + ChatColor.AQUA + "leaves your Kingdom.");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k create [kingdom name] " + ChatColor.AQUA + "creates a kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k claim " + ChatColor.AQUA + "claims a patch of land for your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k chat " + ChatColor.AQUA + "Sets kingdom chat settins (k, p, a)");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k unclaim " + ChatColor.AQUA + "unclaims a patch of land from your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k invade " + ChatColor.AQUA + "challenges the champion of an enemy kingdom for a piece of their land.");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k show [kingdom] " + ChatColor.AQUA + "shows a kingdom's info");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k king [player] " + ChatColor.AQUA + "hands leadership of the kingdom to another player");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k mod [player] " + ChatColor.AQUA + "promotes another player to a mod of your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k demote [player] " + ChatColor.AQUA + "demotes a mod back to a normal member");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k kick [player] " + ChatColor.AQUA + "kicks a player from your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k invite [player] " + ChatColor.AQUA + "invites a player to your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k uninvite [player] " + ChatColor.AQUA + "uninvites a player to your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k sethome " + ChatColor.AQUA + "sets the home of your kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k home " + ChatColor.AQUA + "Goes to your kingdom home");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k ally [kingdom] " + ChatColor.AQUA + "Sends an ally request to another kingdom");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k enemy [kingdom] " + ChatColor.AQUA + "Marks another kingdom as an enemy");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k neutral [kingdom] " + ChatColor.AQUA + "Marks another kingdom as neutral");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k disband " + ChatColor.AQUA + "Disbands your kingdom.");
		p.sendMessage(ChatColor.LIGHT_PURPLE + "/k donate [kingdom] [amount] " + ChatColor.AQUA + "Donates an amount to another kingdom");
		
		if(p.hasPermission("kingdoms.map")){
			p.sendMessage(ChatColor.LIGHT_PURPLE + "/k map " + ChatColor.AQUA + "Shows a map, showing the land surrounding you.");
		}
		
		if(p.hasPermission("kingdoms.admin")){
			p.sendMessage(ChatColor.RED + "===Admin Commands===");
			p.sendMessage(ChatColor.DARK_PURPLE + "/k admin toggle " + ChatColor.AQUA + "allows you to harm anyone in any land, and allows you to break/place blocks in any land.");
			p.sendMessage(ChatColor.DARK_PURPLE + "/k admin disband [kingdom] " + ChatColor.AQUA + "Forcefully disband a kingdom");
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

}
