package me.leothepro555.kingdoms.commands;

import java.util.UUID;

import me.leothepro555.kingdoms.main.Kingdoms;
import me.leothepro555.kingdoms.main.TechnicalMethods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandAdminShow {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(args.length == 3){
			String kingdom = "";
			if(plugin.kingdoms.getKeys(false).contains(args[2])){
			kingdom = args[2];	

		}else if(Bukkit.getOfflinePlayer(args[2]) != null){
			if(plugin.hasKingdom(Bukkit.getOfflinePlayer(args[2]))){
				kingdom = plugin.getKingdom(Bukkit.getOfflinePlayer(args[2]));
			}
		}else{
			p.sendMessage(ChatColor.RED + "The kingdom or player " + args[2] + " doesn't exist.");
			return;
		}
			
			String allies = "";
			String tallies = "";
			String enemies = "";
			String tenemies = "";
			
			for(String s:plugin.kingdoms.getStringList(kingdom + ".enemies")){
				tenemies = enemies;
				enemies = tenemies +" " + s;
			}
			for(String s:plugin.kingdoms.getStringList(kingdom + ".allies")){
				tallies = allies;
				allies = tallies +" " + s;
			}
					
			p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==[" +kingdom+ "]==--=-=-=-=-=");
			p.sendMessage(ChatColor.AQUA + "| King: " + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(kingdom + ".king")))).getName());
			p.sendMessage(ChatColor.AQUA + "| Might: " + plugin.kingdoms.getInt(kingdom + ".might"));
			p.sendMessage(ChatColor.AQUA + "| Allies:" + ChatColor.GREEN + allies);
			p.sendMessage(ChatColor.AQUA + "| Enemies:" + ChatColor.RED + enemies);
			p.sendMessage(ChatColor.AQUA + "| Land: " + plugin.getAmtLand(kingdom));
			p.sendMessage(ChatColor.AQUA + "|" + " Resource Points: " + plugin.rpm.getRp(kingdom));
			p.sendMessage(ChatColor.AQUA + "|" + " Nexus Location: " + TechnicalMethods.locationToString(plugin.getNexusLocation(kingdom)));
			p.sendMessage(ChatColor.AQUA + "|" + " Home Location: " + plugin.kingdoms.getString(kingdom + ".home"));
			p.sendMessage(ChatColor.AQUA + "|");
			p.sendMessage(ChatColor.AQUA + "|     " + ChatColor.UNDERLINE +"Members" );
			p.sendMessage(ChatColor.AQUA + "|");
			p.sendMessage(ChatColor.AQUA + "|" + ChatColor.GREEN + " Online" + ChatColor.WHITE + " | " + ChatColor.RED + "Offline");
		    
			
			String kping = "";
			
         if(Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(kingdom + ".king")))).isOnline()){
				
				kping = ChatColor.GREEN + "☀";
			}else{
				kping = ChatColor.RED + "☀";
			}
			
			p.sendMessage(ChatColor.AQUA + "| " + kping + "[King]" + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(kingdom + ".king")))).getName() + "");
			
			
			for(String s:plugin.kingdoms.getStringList(kingdom + ".members")){

				String ign = plugin.players.getString(s + ".ign");
				String ping = "☀";
				if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
					ping = ChatColor.GREEN + "☀";
				}else{
					ping = ChatColor.RED + "☀";
				}
				
				
				
				if(plugin.isMod(kingdom, Bukkit.getOfflinePlayer(UUID.fromString(s)))){
					
					p.sendMessage(ChatColor.AQUA + "| " + ping + "[Mod] " + ign);
					
				}else{
					p.sendMessage(ChatColor.AQUA + "| " + ping + ign);
				}
			}
			p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==-==-==--=-=-=-=-=");
			
			
		}
  
	}

}
