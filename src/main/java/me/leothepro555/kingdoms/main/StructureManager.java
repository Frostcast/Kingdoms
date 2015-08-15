package me.leothepro555.kingdoms.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StructureManager implements Listener{
	
	public Kingdoms plugin;
	
	public StructureManager(Kingdoms plugin){
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if(event.getPlayer().getItemInHand() != null){
			if(event.getPlayer().getItemInHand().getItemMeta() != null){
				if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null){
					if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
					    if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Power Cell")){
					    	
					    	if(plugin.getChunkKingdom(event.getClickedBlock().getChunk()) != null){
					    		if(plugin.getChunkKingdom(event.getClickedBlock().getChunk()).equals(plugin.getKingdom(event.getPlayer()))){
						    		
					    			if(StructureManager.isPowerCoreChunk(event.getClickedBlock().getChunk(), plugin)){
					    				event.getPlayer().sendMessage(ChatColor.RED + "You can't place multiple power cells in the same piece of land!");
					    				return;
					    			}
					    			
					    			List<String> blacklist = plugin.getConfig().getStringList("unreplaceableblocks");
									if(blacklist.contains(event.getClickedBlock().getType().toString())){
										event.getPlayer().sendMessage(ChatColor.RED + "You can't replace " + event.getClickedBlock().getType().toString());
										return;
									}
									event.getPlayer().setItemInHand(null);
					    			
					    			
					    			StructureManager.setPowerCore(event.getClickedBlock().getLocation(), plugin);
						    	}else{
						    		event.getPlayer().sendMessage(ChatColor.RED + "You can only place power cells on your own land!");
						    	}
					    	}else{
					    		event.getPlayer().sendMessage(ChatColor.RED + "You can only place power cells on your own land!");
					    	}
							
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockDestroy(BlockBreakEvent event){
		if(event.isCancelled()){
			return;
		}
		if(StructureManager.isPowerCore(event.getBlock(), plugin)){
			StructureManager.removePowerCore(event.getBlock().getChunk(), plugin);
			
			ItemStack i1 = new ItemStack(Material.MAGMA_CREAM);
			ItemMeta i1m = i1.getItemMeta();
			i1m.setDisplayName("Power Cell");
			ArrayList<String> i1l = new ArrayList<String>();
			i1l.add(ChatColor.GREEN + "Chunks next to the chunk with a power cell");
			i1l.add(ChatColor.GREEN + "cannot be invaded without invading the power");
			i1l.add(ChatColor.GREEN + "cell first. Does not work on other power cell land");
			i1l.add(ChatColor.RED+ "Can't be placed on power cell chunks");
			i1m.setLore(i1l);
			i1.setItemMeta(i1m);
			
		    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), i1);
			
		}		
		
	}
	
	
	public static void setPowerCore(Location loc, Kingdoms plugin){
		Chunk c = loc.getChunk();
		plugin.structures.set(c.getX() + " , " + c.getZ(), "powercore - " + TechnicalMethods.locationToStringTurret(loc));
		plugin.saveStructures();
		loc.getBlock().setType(Material.REDSTONE_LAMP_ON);
	}
	
	public static boolean isPowerCore(Block b, Kingdoms plugin){
		Chunk c = b.getChunk();
		if(StructureManager.isPowerCoreChunk(c, plugin)){
			String[] split = plugin.structures.getString(c.getX() + " , " + c.getZ()).split(" - ");
			Location loc = TechnicalMethods.stringToLocation(split[1]);
			if(b.getLocation().equals(loc)){
				return true;
			}
		}
		return false;
	}
	
	public static Block getPowerCoreFromChunk(Chunk c, Kingdoms plugin){
		if(StructureManager.isPowerCoreChunk(c, plugin)){
		   String[] split = plugin.structures.getString(c.getX() + " , " + c.getZ()).split(" - ");
		   Location loc = TechnicalMethods.stringToLocation(split[1]);
		      Block b = loc.getBlock();
			  return b;
		      
		}
		
		return null;
	}
	
	public static void removePowerCore(Chunk c, Kingdoms plugin){
		StructureManager.getPowerCoreFromChunk(c, plugin).setType(Material.AIR);
		plugin.structures.set(c.getX() + " , " + c.getZ(), null);
		plugin.saveStructures();
	}
	
	
	
	public static boolean isPowerCoreChunk(Chunk c, Kingdoms plugin){
		
		if(!plugin.structures.isSet(c.getX() + " , " + c.getZ())){
			return false;
		}
		
		if(plugin.structures.getString(c.getX() + " , " + c.getZ()).startsWith("powercore")){
			return true;
		}
		
		return false;
	}
	
	public static boolean isProtected(Chunk c, Kingdoms plugin){
		int x = c.getX();
		int z = c.getZ();
		World w = c.getWorld();
		if(StructureManager.isPowerCoreChunk(c, plugin)){
			
			return false;
		}
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x + 1, z), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x + 1, z)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x - 1, z), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x - 1, z)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x, z + 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x, z + 1)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x, z - 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x, z - 1)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x + 1, z - 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x + 1, z - 1)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x - 1, z - 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x - 1, z - 1)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x + 1, z + 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x + 1, z + 1)))){
				return true;
			}
		}
		
		if(StructureManager.isPowerCoreChunk(w.getChunkAt(x - 1, z + 1), plugin)){
			if(plugin.getChunkKingdom(c).equals(plugin.getChunkKingdom(w.getChunkAt(x - 1, z + 1)))){
				return true;
			}
		}
		
		
		return true;
	}
	
	

}
