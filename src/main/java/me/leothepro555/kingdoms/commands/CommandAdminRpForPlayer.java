package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdminRpForPlayer {
	
	public static void runCommand(CommandSender p, Kingdoms plugin, String[] args){

  	  if(args.length == 4){
  		  if(Bukkit.getOfflinePlayer(args[2]) != null){
  			  if(plugin.getKingdom(Bukkit.getOfflinePlayer(args[2])) != null){
  				  String kingdom = plugin.getKingdom(Bukkit.getOfflinePlayer(args[2]));
  			  int amount = 0;
  			  try{
  				  amount = Integer.parseInt(args[3]);
  			  }catch(NumberFormatException e){
  				  p.sendMessage(ChatColor.RED + "Your the resoure point amount must be an Integer!");
  			  }
  			  
  			  if(amount < 0){
  				plugin.rpm.minusRP(kingdom, amount * -1);
  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points deducted from " + kingdom);
  			  }else if(amount > 0){
  				plugin.rpm.addRP(kingdom, amount);
  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points added to " + kingdom);
  			  }else if(amount == 0){
  				plugin.rpm.addRP(kingdom, amount);
  				  p.sendMessage(ChatColor.GREEN + "0 resource points added to " + kingdom);
  			  }
  			Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has obtained " + amount + " for his Kingdom!");
  			  
  		  }else{
  			  p.sendMessage(ChatColor.RED + args[2] + " doesn't have a kingdom!");
  		  }
  		  }else{
  			  p.sendMessage(ChatColor.RED + args[2] + " doesn't exist!");
  		  }
  	  }
    
	}
	
	public static void runConsoleCommand(CommandSender p, Kingdoms plugin, String[] args){
       if(p.hasPermission("kingdoms.admin")){
	  	  if(args.length == 2){
	  		  if(Bukkit.getOfflinePlayer(args[0]) != null){
	  			  if(plugin.getKingdom(Bukkit.getOfflinePlayer(args[0])) != null){
	  				  String kingdom = plugin.getKingdom(Bukkit.getOfflinePlayer(args[0]));
	  			  int amount = 0;
	  			  try{
	  				  amount = Integer.parseInt(args[1]);
	  			  }catch(NumberFormatException e){
	  				  p.sendMessage(ChatColor.RED + "Your the resoure point amount must be an Integer!");
	  			  }
	  			  
	  			  if(amount < 0){
	  				plugin.rpm.minusRP(kingdom, amount * -1);
	  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points deducted from " + kingdom);
	  			  }else if(amount > 0){
	  				plugin.rpm.addRP(kingdom, amount);
	  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points added to " + kingdom);
	  			  }else if(amount == 0){
	  				plugin.rpm.addRP(kingdom, amount);
	  				  p.sendMessage(ChatColor.GREEN + "0 resource points added to " + kingdom);
	  			  }
	  			  
	  			  Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has obtained " + amount + " for his Kingdom!");
	  			  
	  		  }else{
	  			  p.sendMessage(ChatColor.RED + args[0] + " doesn't have a kingdom!");
	  		  }
	  		  }else{
	  			  p.sendMessage(ChatColor.RED + args[0] + " doesn't exist!");
	  		  }
	  	  }
	}
       
       
	
		}

}
