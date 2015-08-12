package me.leothepro555.kingdoms.commands;

import java.util.UUID;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandShow {
	
	public static void runCommand(Player p, Kingdoms plugin, String[] args){

		if(args.length == 1){
			if(plugin.hasKingdom(p)){
				
		String allies = "";
		String tallies = "";
		String enemies = "";
		String tenemies = "";
		
		for(String s:plugin.kingdoms.getStringList(plugin.getKingdom(p) + ".enemies")){
			tenemies = enemies;
			enemies = tenemies +" " + s;
		}
		for(String s:plugin.kingdoms.getStringList(plugin.getKingdom(p) + ".allies")){
			tallies = allies;
			allies = tallies +" " + s;
		}
		
		p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==[" +plugin.getKingdom(p)+ "]==--=-=-=-=-=");
		p.sendMessage(ChatColor.AQUA + "| King: " + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).getName());
		p.sendMessage(ChatColor.AQUA + "| Might: " + plugin.kingdoms.getInt(plugin.getKingdom(p) + ".might"));
		p.sendMessage(ChatColor.AQUA + "| Land: " + plugin.getAmtLand(plugin.getKingdom(p)));
		p.sendMessage(ChatColor.AQUA + "|" + " Allies:" + ChatColor.GREEN + allies);
		p.sendMessage(ChatColor.AQUA + "|" + " Enemies:" + ChatColor.RED + enemies);
		p.sendMessage(ChatColor.AQUA + "|");
		p.sendMessage(ChatColor.AQUA + "|     " + ChatColor.UNDERLINE +"Members" );
		p.sendMessage(ChatColor.AQUA + "|");
		p.sendMessage(ChatColor.AQUA + "|" + ChatColor.GREEN + " Online" + ChatColor.WHITE + " | " + ChatColor.RED + "Offline");
	    
		
		String kping = "";
		
       if(Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).isOnline()){
			
			kping = ChatColor.GREEN + "☀";
		}else{
			kping = ChatColor.RED + "☀";
		}
		
		p.sendMessage(ChatColor.AQUA + "| " + kping + "[King]" + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).getName() + "");
		
		
		for(String s:plugin.kingdoms.getStringList(plugin.getKingdom(p) + ".members")){

			String ign = plugin.players.getString(s + ".ign");
			String ping = "☀";
			if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
				ping = ChatColor.GREEN + "☀";
			}else{
				ping = ChatColor.RED + "☀";
			}
			
			
			
			if(plugin.isMod(plugin.getKingdom(p), Bukkit.getOfflinePlayer(UUID.fromString(s)))){
				
				p.sendMessage(ChatColor.AQUA + "| " + ping + "[Mod] " + ign);
				
			}else{
				p.sendMessage(ChatColor.AQUA + "| " + ping + ign);
			}
		}
		p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==-==-==--=-=-=-=-=");
		}else{
			p.sendMessage(ChatColor.RED + "You don't have a kingdom");
		}
	}else if(args.length ==2){
		if(plugin.kingdoms.getKeys(false).contains(args[1])){
			
			p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==[" +args[1]+ "]==--=-=-=-=-=");
			p.sendMessage(ChatColor.AQUA + "| King: " + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(args[1] + ".king")))).getName());
			p.sendMessage(ChatColor.AQUA + "| Might: " + plugin.kingdoms.getInt(args[1] + ".might"));
			p.sendMessage(ChatColor.AQUA + "|");
			p.sendMessage(ChatColor.AQUA + "|     " + ChatColor.UNDERLINE +"Members" );
			p.sendMessage(ChatColor.AQUA + "|");
			p.sendMessage(ChatColor.AQUA + "|" + ChatColor.GREEN + " Online" + ChatColor.WHITE + " | " + ChatColor.RED + "Offline");
		
			
			String kping = "";
			
           if(Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(args[1] + ".king")))).isOnline()){
				
				kping = ChatColor.GREEN + "☀";
			}else{
				kping = ChatColor.RED + "☀";
			}
			
			p.sendMessage(ChatColor.AQUA + "| " + kping + "[King]" + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(args[1] + ".king")))).getName() + "");
			
			
			for(String s:plugin.kingdoms.getStringList(args[1] + ".members")){

				String ign = plugin.players.getString(s + ".ign");
				String ping = "☀";
				if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
					ping = ChatColor.GREEN + "☀";
				}else{
					ping = ChatColor.RED + "☀";
				}
				
				
				
				if(plugin.isMod(args[1], Bukkit.getOfflinePlayer(UUID.fromString(s)))){
					
					p.sendMessage(ChatColor.AQUA + "| " + ping + "[Mod] " + ign);
					
				}else{
					p.sendMessage(ChatColor.AQUA + "| " + ping + ign);
				}
			}
			p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==-==-==--=-=-=-=-=");
			
		}else{
			if(!args[1].equalsIgnoreCase(p.getName())){
			if(Bukkit.getOfflinePlayer(args[1]) != null){
				
				String kingdom = plugin.getKingdom(Bukkit.getOfflinePlayer(args[1]));
				if(plugin.kingdoms.getKeys(false).contains(kingdom)){
					
						p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==[" +kingdom+ "]==--=-=-=-=-=");
						p.sendMessage(ChatColor.AQUA + "| King: " + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(kingdom + ".king")))).getName());
						p.sendMessage(ChatColor.AQUA + "| Might: " + plugin.kingdoms.getInt(kingdom + ".might"));
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
						
				
					
			}else{
				p.sendMessage(ChatColor.RED + "The player " + args[1] + " does not have a kingdom");
			}
			}else{
				p.sendMessage(ChatColor.RED + "The player or kingdom " + args[1] + " does not exist or is offline");
			return;
			}
					}else{
						
						if(plugin.hasKingdom(p)){
							p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==[" +plugin.getKingdom(p)+ "]==--=-=-=-=-=");
							p.sendMessage(ChatColor.AQUA + "| King: " + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).getName());
							p.sendMessage(ChatColor.AQUA + "| Might: " + plugin.kingdoms.getInt(plugin.getKingdom(p) + ".might"));
							p.sendMessage(ChatColor.AQUA + "|");
							p.sendMessage(ChatColor.AQUA + "|     " + ChatColor.UNDERLINE +"Members" );
							p.sendMessage(ChatColor.AQUA + "|");
							p.sendMessage(ChatColor.AQUA + "|" + ChatColor.GREEN + " Online" + ChatColor.WHITE + " | " + ChatColor.RED + "Offline");
						
							
							String kping = "";
							
				           if(Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).isOnline()){
								
								kping = ChatColor.GREEN + "☀";
							}else{
								kping = ChatColor.RED + "☀";
							}
							
							p.sendMessage(ChatColor.AQUA + "| " + kping + "[King]" + Bukkit.getOfflinePlayer((UUID.fromString(plugin.kingdoms.getString(plugin.getKingdom(p) + ".king")))).getName() + "");
							
							
							for(String s:plugin.kingdoms.getStringList(plugin.getKingdom(p) + ".members")){

								String ign = plugin.players.getString(s + ".ign");
								String ping = "☀";
								if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
									ping = ChatColor.GREEN + "☀";
								}else{
									ping = ChatColor.RED + "☀";
								}
								
								
								
								if(plugin.isMod(plugin.getKingdom(p), Bukkit.getOfflinePlayer(UUID.fromString(s)))){
									
									p.sendMessage(ChatColor.AQUA + "| " + ping + "[Mod] " + ign);
									
								}else{
									p.sendMessage(ChatColor.AQUA + "| " + ping + ign);
								}
							}
							p.sendMessage(ChatColor.AQUA + "=-=-=-=-=--==-==-==--=-=-=-=-=");
							}else{
								p.sendMessage(ChatColor.RED + "You don't have a kingdom");
							}
						
						
					}
		}
	}
	
	}

}
