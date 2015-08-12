package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAdminRp {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

  	  if(args.length == 4){
  		  if(plugin.kingdoms.getKeys(false).contains(args[2])){
  			  int amount = 0;
  			  try{
  				  amount = Integer.parseInt(args[3]);
  			  }catch(NumberFormatException e){
  				  p.sendMessage(ChatColor.RED + "Your the resoure point amount must be an Integer!");
  			  }
  			  
  			  if(amount < 0){
  				plugin.rpm.minusRP(args[2], amount * -1);
  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points deducted from " + args[2]);
  			  }else if(amount > 0){
  				plugin.rpm.addRP(args[2], amount);
  				  p.sendMessage(ChatColor.GREEN + "" + amount + " resource points added to " + args[2]);
  			  }else if(amount == 0){
  				plugin.rpm.addRP(args[2], amount);
  				  p.sendMessage(ChatColor.GREEN + "0 resource points added to " + args[2]);
  			  }
  			  
  			  
  		  }
  	  }
	      
	}

}
