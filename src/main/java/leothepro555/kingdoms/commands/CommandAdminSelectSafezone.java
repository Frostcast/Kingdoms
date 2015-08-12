package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CommandAdminSelectSafezone {
	
	public static void runCommand(Player p, Kingdoms plugin){

		 if(plugin.hasWorldEdit()){
			 WorldEditPlugin worldedit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
			 if(worldedit.getSelection(p) != null){
				 for(Chunk c: plugin.wet.getRegionChunks(p)){
					 plugin.claimSafezoneChunk(c);
				 }
				 p.sendMessage(ChatColor.GREEN + "Safezone claimed.");
			 }else{
				 p.sendMessage(ChatColor.RED + "You don't have a worldedit selection.");
			 }
		 }
   	  
 
	}

}
