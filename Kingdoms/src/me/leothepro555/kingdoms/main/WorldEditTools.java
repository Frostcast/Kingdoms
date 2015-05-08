package me.leothepro555.kingdoms.main;

import java.util.Map;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldEditTools {
	
	private Kingdoms plugin;
	
	public WorldEditTools(Kingdoms plugin){
		this.plugin = plugin;
	}
	

	public boolean isInRegion(Location loc)
	{
		try{
	    WorldGuardPlugin guard = (WorldGuardPlugin) plugin.getWorldGuard();
	    com.sk89q.worldedit.Vector v = BukkitUtil.toVector(loc);
	    RegionManager manager = guard.getRegionManager(loc.getWorld());
	    ApplicableRegionSet set = manager.getApplicableRegions(v);
	    return set.size() > 0;
		}catch(NullPointerException e){
			return false;
		}catch(IncompatibleClassChangeError e){
			Map<String, ProtectedRegion> rgs = WorldGuardPlugin.inst().getRegionManager(loc.getWorld()).getRegions();
			for(ProtectedRegion rg: rgs.values()){
				boolean inside = rg.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
				if(inside){
					
					return true;
					
				}
			}
			
		}
		return false;
	}
	
}
