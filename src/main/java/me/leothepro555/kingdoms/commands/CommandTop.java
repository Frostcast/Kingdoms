package me.leothepro555.kingdoms.commands;

import java.util.HashMap;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandTop {
	
	public static void runCommand(Player p, Kingdoms plugin){
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for(String kingdom : plugin.kingdoms.getKeys(false)){
			map.put(plugin.kingdoms.getInt(kingdom + ".might"), kingdom);
		}
		HashMap<Integer, String> sortedmap = plugin.sortHashList(map);
		int count = 1;
		//for(int n = 0; n < sortedmap.size(); n++){
		//p.sendMessage(ChatColor.AQUA + "" + count + ". " + sortedmap.get(i) + " : " + i + " might");
		//count++;
		//}
		
	}

}
