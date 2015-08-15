package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandDonate {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){
		if(plugin.hasKingdom(p)){
	    if(!plugin.isKing(p)){
	    	p.sendMessage(ChatColor.RED + "Only kings can donate!");
	    	return;
	    }
		if(args.length == 3){
			String kingdom = "";
			if(plugin.kingdoms.getKeys(false).contains(args[1])){
				kingdom = args[1];
			}else if(Bukkit.getOfflinePlayer(args[1]) != null){
				if(plugin.hasKingdom(Bukkit.getOfflinePlayer(args[1]))){
				kingdom = plugin.getKingdom(Bukkit.getOfflinePlayer(args[1]));
			    }else{
			    	p.sendMessage(ChatColor.RED + args[1] + " doesn't have a kingdom");
			    	return;
			    }
			}else{
				p.sendMessage(ChatColor.RED + args[1] + " is not an existing kingdom or player!");
				return;
			}
			
			if(kingdom.equals(plugin.getKingdom(p))){
				p.sendMessage(ChatColor.RED + "You can't donate to yourself!");
				return;
			}
			
			int donationpoints = 0;
			
			try{
				donationpoints = Integer.parseInt(args[2]);
			}catch(NumberFormatException e){
				p.sendMessage(ChatColor.RED + "Usage: /k donate [kingdom] [amount to donate]");
				return;
			}
			
			if(plugin.rpm.getRp(plugin.getKingdom(p)) >= donationpoints){
				plugin.rpm.minusRP(plugin.getKingdom(p), donationpoints);
				plugin.rpm.addRP(kingdom, donationpoints);
				plugin.messageKingdomPlayers(kingdom, ChatColor.GREEN + "Received " + donationpoints + " resource points from " + plugin.getKingdom(p));
			    p.sendMessage(ChatColor.GREEN + "Donated " + donationpoints + " to " + kingdom);
			}else{
				p.sendMessage(ChatColor.RED + "Your kingdom does not have " + donationpoints + " to donate!");
				return;
			}
			
		}else{
			p.sendMessage(ChatColor.RED + "Usage: /k donate [kingdom] [amount to donate]");
			return;
		}
	}else{
		p.sendMessage(ChatColor.RED + "You need a kingdom to donate!");
		return;
	}
		
	}

}
