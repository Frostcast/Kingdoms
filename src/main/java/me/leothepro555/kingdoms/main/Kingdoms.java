package me.leothepro555.kingdoms.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import me.leothepro555.kingdoms.commands.CommandAdminDisband;
import me.leothepro555.kingdoms.commands.CommandAdminHelp;
import me.leothepro555.kingdoms.commands.CommandAdminRp;
import me.leothepro555.kingdoms.commands.CommandAdminRpForPlayer;
import me.leothepro555.kingdoms.commands.CommandAdminSelectSafezone;
import me.leothepro555.kingdoms.commands.CommandAdminSelectWarzone;
import me.leothepro555.kingdoms.commands.CommandAdminShow;
import me.leothepro555.kingdoms.commands.CommandAdminToggle;
import me.leothepro555.kingdoms.commands.CommandAdminUnclaimSelection;
import me.leothepro555.kingdoms.commands.CommandAlly;
import me.leothepro555.kingdoms.commands.CommandAutoClaim;
import me.leothepro555.kingdoms.commands.CommandChat;
import me.leothepro555.kingdoms.commands.CommandClaim;
import me.leothepro555.kingdoms.commands.CommandCreate;
import me.leothepro555.kingdoms.commands.CommandDefend;
import me.leothepro555.kingdoms.commands.CommandDemote;
import me.leothepro555.kingdoms.commands.CommandDisband;
import me.leothepro555.kingdoms.commands.CommandDonate;
import me.leothepro555.kingdoms.commands.CommandEnemy;
import me.leothepro555.kingdoms.commands.CommandHelp;
import me.leothepro555.kingdoms.commands.CommandHome;
import me.leothepro555.kingdoms.commands.CommandInfo;
import me.leothepro555.kingdoms.commands.CommandInvade;
import me.leothepro555.kingdoms.commands.CommandInvite;
import me.leothepro555.kingdoms.commands.CommandJoin;
import me.leothepro555.kingdoms.commands.CommandKick;
import me.leothepro555.kingdoms.commands.CommandKing;
import me.leothepro555.kingdoms.commands.CommandLeave;
import me.leothepro555.kingdoms.commands.CommandMap;
import me.leothepro555.kingdoms.commands.CommandMod;
import me.leothepro555.kingdoms.commands.CommandNeutral;
import me.leothepro555.kingdoms.commands.CommandNexus;
import me.leothepro555.kingdoms.commands.CommandSethome;
import me.leothepro555.kingdoms.commands.CommandShow;
import me.leothepro555.kingdoms.commands.CommandTop;
import me.leothepro555.kingdoms.commands.CommandTradable;
import me.leothepro555.kingdoms.commands.CommandUnInvite;
import me.leothepro555.kingdoms.commands.CommandUnclaim;
import me.leothepro555.kingdoms.events.PlayerChangeChunkEvent;
import me.leothepro555.kingdoms.events.PlayerJoinKingdomEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;


public class Kingdoms extends JavaPlugin implements Listener{

	public void onEnable(){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new KingdomPowerups(this), this);
		pm.registerEvents(new NexusBlockManager(this), this);
		pm.registerEvents(new TurretManager(this), this);
		pm.registerEvents(new TechnicalMethods(this), this);
		pm.registerEvents(new ChampionManager(this), this);
		pm.registerEvents(new StructureManager(this), this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		this.kingdoms.options().copyDefaults(false);
		saveKingdoms();
		this.land.options().copyDefaults(false);
		saveClaimedLand();
		this.players.options().copyDefaults(false);
		savePlayers();
		this.powerups.options().copyDefaults(false);
		savePowerups();
		this.misupgrades.options().copyDefaults(false);
		saveMisupgrades();
		this.chest.options().copyDefaults(false);
		saveChests();
		this.turrets.options().copyDefaults(false);
		saveTurrets();
		this.structures.options().copyDefaults(false);
		saveStructures();
		
		for(String location:turrets.getKeys(false)){
			final Block turret = TechnicalMethods.stringToLocation(location).getBlock();
			final BlockState skulltype = turret.getState();
			if(skulltype instanceof Skull){
				if(((Skull) skulltype).getSkullType().equals(SkullType.SKELETON)){
					TurretManager.startArrowTurret(turret, this);
					
			}else if(((Skull) skulltype).getSkullType().equals(SkullType.WITHER)){
				
				TurretManager.startFireArrowTurret(turret, this);
				
			}
		}
					}
		
		
		int num = 0;
		
		for(String kingdom: kingdoms.getKeys(false)){
			num++;
			if(kingdoms.getString(kingdom + ".nexus-block") != null){
			if(hasNexus(kingdom)){
			if(TechnicalMethods.stringToLocation(kingdoms.getString(kingdom + ".nexus-block")) != null){
				
			Location loc = TechnicalMethods.stringToLocation(kingdoms.getString(kingdom + ".nexus-block"));
		
			Block b = loc.getBlock();
			b.setMetadata("nexusblock", new FixedMetadataValue(this, "ok."));
		}
		}
		}else{
			kingdoms.set(kingdom + ".nexus-block", 0);
			Bukkit.getLogger().severe(ChatColor.RED + "The kingdom in your config file, " + kingdom + " is corrupted. "
					+ "Did you modify the kingdoms.yml file lately? If " + kingdom + " is not supposed to exist, "
					+ "delete it in the kingdoms.yml file.");
		}
				int landnum = 0;
				for(String s : land.getKeys(false)){
					if(land.getString(s).equals(kingdom)){
						landnum ++;
					}
				}
				kingdoms.set(kingdom + ".land", landnum);
				saveKingdoms();
				
			
			if(!kingdoms.isSet(kingdom + ".chestsize")){
				kingdoms.set(kingdom + ".chestsize", 9);
				saveKingdoms();
			}
			
			if(!chest.isSet(kingdom)){
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				chest.set(kingdom, items);
				saveChests();
			}
			
			if(misupgrades.get(kingdom + ".anticreeper") == null){
				misupgrades.set(kingdom + ".antitrample", false);
				misupgrades.set(kingdom + ".anticreeper", false);
				misupgrades.set(kingdom + ".nexusguard", false);
				misupgrades.set(kingdom + ".glory", false);
				misupgrades.set(kingdom + ".bombshards", false);
				saveMisupgrades();
			}
			
		}
		Bukkit.getLogger().info("Loaded " + num + " kingdoms.");
		if(getWorldGuard() != null){
			this.hasWorldGuard = true;
			Bukkit.getLogger().info("World Guard found, enabling WorldGuard support.");
			wet = new WorldEditTools(this);
		}else{
			this.hasWorldGuard = false;
			Bukkit.getLogger().info("World Guard not found, disabling WorldGuard support.");
		}
		
		if(getConfig().getBoolean("no-region-claim")){
			this.noRegionClaiming = true;
		}else{
			this.noRegionClaiming = false;
		}
		
		for(String s: getConfig().getStringList("resource-point-trade-blacklist")){
			if(Material.getMaterial(s) != null){
				blacklistitems.add(Material.getMaterial(s));
			}else{
				Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade blacklist is invalid!");
			}
		}
		
        for(String s: getConfig().getStringList("whitelist-items")){
			
			String[] split = s.split(",");
			if(split.length == 2){
			if(Material.getMaterial(split[0]) != null){
				try{
				whitelistitems.put(Material.getMaterial(split[0]), Integer.parseInt(split[1]));
				}catch(NumberFormatException e){
					Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade whitelist is invalid!");
				}
			}else{
				Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade whitelist is invalid!");
			}
			}else{
				Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade whitelist is invalid!");
			}
		}
		
		for(String s: getConfig().getStringList("special-item-cases")){
			
			String[] split = s.split(",");
			if(split.length == 2){
			if(Material.getMaterial(split[0]) != null){
				try{
				specialcaseitems.put(Material.getMaterial(split[0]), Integer.parseInt(split[1]));
				}catch(NumberFormatException e){
					Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade speciallist is invalid!");
				}
			}else{
				Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade speciallist is invalid!");
			}
			}else{
				Bukkit.getLogger().severe(ChatColor.RED + "Your Material, " + s + " typed under the trade speciallist is invalid!");
			}
		}
		
		}
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("k") ||
				cmd.getName().equalsIgnoreCase("kingdom")||
				cmd.getName().equalsIgnoreCase("kingdoms")){
			Player p = null;
			if(sender instanceof Player){
				p = (Player) sender;
			}else{
				
				return false;
			}
			if(isValidWorld(p.getWorld())){
			
			if(args.length <= 0){
				
				CommandHelp.runCommand(p);
				
			}else if(args[0].equalsIgnoreCase("admin")){
				if(p.hasPermission("kingdoms.admin")){
		      if(args.length == 1){
		    	  
		    	  CommandAdminHelp.runCommand(p);
		    	  
		      }
		      if(args.length >= 2){
		      if(args[1].equalsIgnoreCase("toggle")){
		    	  
		    	  CommandAdminToggle.runCommand(p, this);
		    	  
		      }else if(args[1].equalsIgnoreCase("safezone")){
		    		  claimSafezoneCurrentPosition(p);
		      }else if(args[1].equalsIgnoreCase("disband")){
		    	  
		    	  CommandAdminDisband.runCommand(p, this, args);
		    	  
		      }else if(args[1].equalsIgnoreCase("warzone")){
		    		  claimWarzoneCurrentPosition(p);
		    	  
		      }else if(args[1].equalsIgnoreCase("selectsafezone")){
		    	  
		    	  CommandAdminSelectSafezone.runCommand(p, plugin);
		    	  
		      }else if(args[1].equalsIgnoreCase("selectwarzone")){
		    	  
		    	  CommandAdminSelectWarzone.runCommand(p, plugin);
		    	  
		      }else if(args[1].equalsIgnoreCase("unclaimselection")){
		    	  
		    	  CommandAdminUnclaimSelection.runCommand(p, plugin);
		    	  
		      }else if(args[1].equalsIgnoreCase("unclaim")){
		    	  if(getChunkKingdom(p.getLocation().getChunk()) != null){
		    		  forceUnclaimCurrentPosition(p.getLocation().getChunk(), p, true);
		    	  }
		      }else if(args[1].equalsIgnoreCase("rp")){
		    	  
		    	  CommandAdminRp.runCommand(p, plugin, args);
		    	  
		      }else if(args[1].equalsIgnoreCase("rpforplayer")){
		    	  
		    	  CommandAdminRpForPlayer.runCommand(p, plugin, args);
		    	  
		      }else if(args[1].equalsIgnoreCase("show")){
		    	  
		    	  CommandAdminShow.runCommand(p, plugin, args);
		    	  
		      }
				}
			}else{
				p.sendMessage(ChatColor.RED + "You don't have the permission to use this command.");
			}
	     	}else if(args[0].equalsIgnoreCase("info")){
	     		
	     		CommandInfo.runCommand(p, args, this);
	     		
	     	}else if(args[0].equalsIgnoreCase("tradable")){
	     		
	     		CommandTradable.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("create")){
	     		
	     		CommandCreate.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("nexus")){
	     		
	     		CommandNexus.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("claim")){
	     		
	     		CommandClaim.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("autoclaim")){
	     		
	     		CommandAutoClaim.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("unclaim")){
	     		
	     		CommandUnclaim.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("show")){
	     		
	     		CommandShow.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("join")){
	     		
	     		CommandJoin.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("leave")){
	     		
	     		CommandLeave.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("invite")){
	     		
	     		CommandInvite.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("uninvite")){
	     		
	     		CommandUnInvite.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("kick")){
	     		
	     		CommandKick.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("sethome")){
	     		
	     		CommandSethome.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("chat")||
					args[0].equalsIgnoreCase("c")){
	     		
	     		CommandChat.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("mod")){
	     		
	     		CommandMod.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("king")){
	     		
	     		CommandKing.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("demote")){
	     		
	     		CommandDemote.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("home")){
	     		
	     		CommandHome.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("ally")){
	     		
	     		CommandAlly.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("enemy")){
	     		
	     		CommandEnemy.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("neutral")){
	     		
	     		CommandNeutral.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("invade")){
	     		
	     		CommandInvade.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("disband")){
	     		
	     		CommandDisband.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("map")){
	     		
	     		CommandMap.runCommand(p, plugin, args);
	     		
	     	}else if(args[0].equalsIgnoreCase("defend")){
	     		
	     		CommandDefend.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("top")){
	     		
	     		CommandTop.runCommand(p, plugin);
	     		
	     	}else if(args[0].equalsIgnoreCase("donate")||
	     			args[0].equalsIgnoreCase("transfer")){
	     		
	     		CommandDonate.runCommand(p, plugin, args);
	     		
	     	}else{
				p.sendMessage(ChatColor.RED + "[Kingdoms] Unknown command. Do /k for commands.");
			}
			
		}else{
			p.sendMessage(ChatColor.RED + "Kingdoms is disabled in this world.");
		}
		}else if(cmd.getName().equalsIgnoreCase("krpforplayer")){
			
			CommandAdminRpForPlayer.runConsoleCommand(sender, plugin, args);
			
		}

		return false;
		
	}
	
	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent event){
		String[] array = event.getMessage().split(" ");
		if(duelpairs.containsValue(event.getPlayer().getUniqueId())){
		if(array[0].startsWith("/")){
				  event.setCancelled(true);
				  event.getPlayer().sendMessage(ChatColor.RED + "You cannot use commands while dueling a champion! If you have already killed the champion or vice versa, simply relog to solve the problem.");
				}
	}
		
		if(getChunkKingdom(event.getPlayer().getLocation().getChunk()) != null&&
				!getChunkKingdom(event.getPlayer().getLocation().getChunk()).equals("SafeZone")&&
				!getChunkKingdom(event.getPlayer().getLocation().getChunk()).equals("WarZone")){
			if(!hasKingdom(event.getPlayer())){
				if(getConfig().getStringList("denied-commands-neutral").contains(array[0])){
					  event.setCancelled(true);
					  event.getPlayer().sendMessage(ChatColor.RED + "You cannot use " + array[0] + " in neutral territory!");
				return;	
				}
			}else{
				if(plugin.isEnemy(getChunkKingdom(event.getPlayer().getLocation().getChunk()), event.getPlayer())){
					if(getConfig().getStringList("denied-commands-enemy").contains(array[0])){
				  event.setCancelled(true);
				  event.getPlayer().sendMessage(ChatColor.RED + "You cannot use " + array[0] + " in enemy territory!");
				}
			}
		}
			
		}
		
	}
	

	

	
	
	public boolean isValidWorld(World world){
		if(getConfig().getStringList("enabled-worlds").contains(world.getName())){
			return true;
		}else{
			return false;
		}
	}
	
	
	@EventHandler
	public void onNexusPlace(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if(this.placingnexusblock.contains(p.getUniqueId())){
			
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				Location loc = event.getClickedBlock().getLocation();
				if(plugin.turrets.getKeys(false).contains(TechnicalMethods.locationToStringTurret(loc))){
					p.sendMessage(ChatColor.RED + "You can't replace turrets with your nexus!");
					placingnexusblock.remove(p.getUniqueId());
					return;
				}
				try{
				if(hasWorldGuard){
					if(wet.isInRegion(event.getClickedBlock().getLocation())){
						if(noRegionClaiming){
						p.sendMessage(ChatColor.RED + "You can't place your nexus in a region.");
						return;
					}
					}
				}
			}catch(NoClassDefFoundError e){
				Bukkit.getLogger().severe(ChatColor.RED + "Your worldguard is not the latest! Players will still be able to place a nexus in regioned areas! To prevent this, use /k admin safezone or /k admin warzone to protect an area from claiming.");
			}
				List<String> blacklist = getConfig().getStringList("unreplaceableblocks");
				if(!blacklist.contains(event.getClickedBlock().getType().toString())){
				if(!hasNexus(getKingdom(p))){
			if(getChunkKingdom(loc.getChunk()) != null &&
					getChunkKingdom(loc.getChunk()).equals(getKingdom(p))){
				
				placeNexus(loc, getKingdom(p));
				placingnexusblock.remove(p.getUniqueId());
				p.sendMessage(ChatColor.GREEN + "Nexus placed.");
			}else{
				p.sendMessage(ChatColor.RED + "Your nexus block can only be placed in your own land!");
			}
			}else{
				if(getChunkKingdom(loc.getChunk()) != null){
				if(getChunkKingdom(loc.getChunk()).equals(getKingdom(p))){
					Location lastnexus = TechnicalMethods.stringToLocation(kingdoms.getString(getKingdom(p) + ".nexus-block"));
					lastnexus.getBlock().setType(Material.AIR);
					lastnexus.getBlock().removeMetadata("nexusblock", this);
					placeNexus(loc, getKingdom(p));
					placingnexusblock.remove(p.getUniqueId());
					p.sendMessage(ChatColor.GREEN + "Nexus moved.");
				}else{
					p.sendMessage(ChatColor.RED + "Your nexus block can only be placed in your own land!");
				}
			}else{
				p.sendMessage(ChatColor.RED + "Your nexus block can only be placed in your own land!");
			}
			}
			}else{
				p.sendMessage(ChatColor.RED + "You can't replace " + event.getClickedBlock().getType().toString().toUpperCase() + " with your nexus. Nexus placing cancelled");
			if(placingnexusblock.contains(p.getUniqueId())){	
				placingnexusblock.remove(p.getUniqueId());
			}
			}
			
		}else if(event.getAction() == Action.LEFT_CLICK_BLOCK||
				event.getAction() == Action.LEFT_CLICK_AIR){
			placingnexusblock.remove(p.getUniqueId());
			p.sendMessage(ChatColor.RED + "Nexus placing cancelled.");
		}
		}
		
	}
	
	@EventHandler
	public void onChunkChange(final PlayerChangeChunkEvent event){
		Player p = event.getPlayer();
		if(mapmode.contains(event.getPlayer().getUniqueId())){
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                 public void run() {
                	 displayMap(event.getPlayer());
                 }
             }, 1L);
			
			
		}
		if(rapidclaiming.contains(event.getPlayer().getUniqueId())){
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                 public void run() {
                	 claimCurrentPosition(event.getPlayer());
                 }
             }, 1L);
			
		}
		
		if(getChunkKingdom(event.getToChunk()) != null){
		if(!getChunkKingdom(event.getToChunk()).equals(getChunkKingdom(event.getFromChunk()))){
			if(getChunkKingdom(event.getToChunk()).equals("SafeZone")){
				p.sendMessage(ChatColor.GOLD + "Entering a Safezone. You are now safe from pvp and monsters.");
				return;
			}
			
            if(getChunkKingdom(event.getToChunk()).equals("WarZone")){
				p.sendMessage(ChatColor.RED + "Entering a Warzone! Not the safest place to be.");
            	return;
			}
            
            if(kingdoms.getKeys(false).contains(getChunkKingdom(event.getToChunk()))){
			p.sendMessage(ChatColor.AQUA + "Entering " + ChatColor.YELLOW + getChunkKingdom(event.getToChunk()));
            }else if(!kingdoms.getKeys(false).contains(getChunkKingdom(event.getToChunk())) &&
           		 !getChunkKingdom(event.getToChunk()).equals("SafeZone")&&
           		 !getChunkKingdom(event.getToChunk()).equals("WarZone")){
            	if(getChunkKingdom(event.getFromChunk()) != null){
            		p.sendMessage(ChatColor.AQUA + "Entering unoccupied land");
            		}
            	emptyCurrentPosition(event.getToChunk());
            }
		
			
		
	}
	}else{
		if(getChunkKingdom(event.getFromChunk()) != null){
		p.sendMessage(ChatColor.AQUA + "Entering unoccupied land");
		}
	}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent event){
		if(isValidWorld(event.getEntity().getWorld())){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(event.getDamager() instanceof Player){
				Player damager = (Player) event.getDamager();
				
				
					if(getChunkKingdom(p.getLocation().getChunk()) != null){
						if(hasKingdom(p)){
					if(getChunkKingdom(p.getLocation().getChunk()).equals(getKingdom(p))){
						if(!isEnemy(getKingdom(p),damager)){
							
							event.setCancelled(true);
							damager.sendMessage(ChatColor.RED + "You can't harm members of " + getKingdom(p) + " in their own territory unless your kingdom is an enemy!");
							return;
						}
						}
					}
				
				
				}
					
					if(hasKingdom(damager)){
						if(getKingdom(damager).equals(getKingdom(p))){
							event.setCancelled(true);
							damager.sendMessage(ChatColor.RED + "You can't damage your kingdom members!");
							return;
						}
					}
					
					if(isAlly(getKingdom(p), damager)){
						event.setCancelled(true);
						damager.sendMessage(ChatColor.RED + "You can't damage your allies!");
						return;
					}
					
				
			}else if(event.getDamager() instanceof Projectile){
				if(((Projectile) event.getDamager()).getShooter() instanceof Player){
				Player damager = (Player) ((Projectile) event.getDamager()).getShooter();
				
				
				if(getChunkKingdom(p.getLocation().getChunk()) != null){
					if(hasKingdom(p)){
				if(getChunkKingdom(p.getLocation().getChunk()).equals(getKingdom(p))){
					if(!isEnemy(getKingdom(p),damager)){
						
						event.setCancelled(true);
						damager.sendMessage(ChatColor.RED + "You can't harm members of " + getKingdom(p) + " in their own territory unless your kingdom is an enemy!");
						return;
					}
					}
				}
			
				if(hasKingdom(damager)){
					if(getKingdom(damager).equals(getKingdom(p))){
						event.setCancelled(true);
						damager.sendMessage(ChatColor.RED + "You can't damage your kingdom members!");
						return;
					}
				}
				
				if(isAlly(getKingdom(p), damager)){
					event.setCancelled(true);
					damager.sendMessage(ChatColor.RED + "You can't damage your allies!");
					return;
				}
				
			}
			}
			}
		}
	}
	}
	
	@EventHandler
	public void onBlockUse(PlayerInteractEvent event){
		if(adminmode.contains(event.getPlayer().getUniqueId())){
			return;
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getClickedBlock().getType() == Material.BEACON){
				if(event.getClickedBlock().hasMetadata("nexusblock")){
				return;	
				}
				}
				if(getChunkKingdom(event.getClickedBlock().getChunk()) != null){
					if(event.getPlayer().getItemInHand() != null){
						if(event.getPlayer().isSneaking()){
						if(event.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL ||
								event.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG ||
								event.getPlayer().getItemInHand().getType() == Material.MONSTER_EGGS ||
								event.getPlayer().getItemInHand().getType() == Material.EGG ||
								event.getPlayer().getItemInHand().getType() == Material.BOW){
							return;
						}
					}
					}
					
					
					
					if(getKingdom(event.getPlayer()) == null){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "You can't interact in " + getChunkKingdom(event.getClickedBlock().getLocation().getChunk()) + "'s land!");
						return;
						
						
						
					
					}
						
					if(!getChunkKingdom(event.getClickedBlock().getChunk()).equals(getKingdom(event.getPlayer()))){
						event.getPlayer().sendMessage(ChatColor.RED + "You can't interact in " + getChunkKingdom(event.getClickedBlock().getLocation().getChunk()) + "'s land!");
			        	event.setCancelled(true);
				        return;
					}
				}
			
		}
			
		
		
		
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player p = event.getPlayer();
		 if(getChunkKingdom(event.getBlock().getChunk()) != null){
        if(kingdoms.getKeys(false).contains(getChunkKingdom(event.getBlock().getChunk()))){
		
         }else if(!kingdoms.getKeys(false).contains(getChunkKingdom(event.getBlock().getChunk())) &&
        		 !getChunkKingdom(event.getBlock().getChunk()).equals("SafeZone")&&
        		 !getChunkKingdom(event.getBlock().getChunk()).equals("WarZone")){
         	if(getChunkKingdom(event.getBlock().getChunk()) != null){
         		p.sendMessage(ChatColor.AQUA + "Entering unoccupied land");
         		}
         	emptyCurrentPosition(event.getBlock().getChunk());
         }
	}
		if(placingnexusblock.contains(event.getPlayer().getUniqueId())){
			event.setCancelled(true);
			return;
		}
		if(adminmode.contains(p.getUniqueId())){
			return;
		}
		if(land.getString(chunkToString(event.getBlock().getLocation().getChunk())) != null){
			if(!getChunkKingdom(event.getBlock().getLocation().getChunk()).equals(getKingdom(p))){
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You cannot place blocks in " +getChunkKingdom(event.getBlock().getLocation().getChunk()) + "'s land!");
			}
		}
		
	
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player p = event.getPlayer();
		if(getChunkKingdom(event.getBlock().getChunk()) != null){
		  if(kingdoms.getKeys(false).contains(getChunkKingdom(event.getBlock().getChunk()))){
				
	         }else if(!kingdoms.getKeys(false).contains(getChunkKingdom(event.getBlock().getChunk())) &&
	        		 !getChunkKingdom(event.getBlock().getChunk()).equals("SafeZone")&&
	        		 !getChunkKingdom(event.getBlock().getChunk()).equals("WarZone")){
	         	if(getChunkKingdom(event.getBlock().getChunk()) != null){
	         		p.sendMessage(ChatColor.AQUA + "Entering unoccupied land");
	         		}
	         	emptyCurrentPosition(event.getBlock().getChunk());
	         }
	}
		if(event.getBlock().getType() == Material.BEACON){
			if(event.getBlock().hasMetadata("nexusblock")){
				
				event.setCancelled(true);
				if(event.getPlayer().getGameMode() != GameMode.CREATIVE){
				if(!getChunkKingdom(event.getBlock().getLocation().getChunk()).equals(getKingdom(p))){
					String kingdom = getChunkKingdom(event.getBlock().getLocation().getChunk());
					messageKingdomPlayers(kingdom, ChatColor.RED + "");
					if(hasMisUpgrade(getChunkKingdom(event.getBlock().getLocation().getChunk()), "nexusguard")){
					ChampionManager.spawnNexusGuard(kingdom, p.getLocation(), p, this);
					p.sendMessage(ChatColor.RED + "A nexus guard has been summoned!");
					}
					
					if(!isAlly(getChunkKingdom(event.getBlock().getLocation().getChunk()), p)){
						if(isEnemy(getChunkKingdom(event.getBlock().getLocation().getChunk()), p)){
							int i = rpm.minusRP(getChunkKingdom(event.getBlock().getLocation().getChunk()), 20);
							p.sendMessage(ChatColor.GREEN + "Plundered " + i + " resource points!");
							if(hasKingdom(p)){
								rpm.addRP(getKingdom(p), i);
							}
							return;
						}else{
					int i = rpm.minusRP(getChunkKingdom(event.getBlock().getLocation().getChunk()), 10);
					p.sendMessage(ChatColor.GREEN + "Plundered " + i + " resource points!");
					if(hasKingdom(p)){
						rpm.addRP(getKingdom(p), i);
					}
					return;
						}
				}else{
					p.sendMessage(ChatColor.RED + "You can't steal resourcepoints from an ally!");
				}
				}
			}else{
				p.sendMessage(ChatColor.RED + "Creative players cannot destroy nexus blocks.");
			}
			}
		}
		
		
		
		if(land.getString(chunkToString(event.getBlock().getLocation().getChunk())) != null){
			if(!getChunkKingdom(event.getBlock().getLocation().getChunk()).equals(getKingdom(p))){
				if(adminmode.contains(p.getUniqueId())){
					return;
				}
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You cannot break blocks in " +getChunkKingdom(event.getBlock().getLocation().getChunk()) + "'s land!");
			
			}
		}
		

	
	}
	
	@EventHandler
	public void onBlockExplode(EntityExplodeEvent event){
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(Block b: event.blockList()){
			if(b.getType() == Material.BEACON){
				if(b.hasMetadata("nexusblock")){
					blocks.add(b);
				}
			}else if(turrets.getString(TechnicalMethods.locationToStringTurret(b.getLocation())) != null){
				if(!blocks.contains(b)){
				blocks.add(b);
				}
			}
		}
		
		for(Block b: blocks){
			event.blockList().remove(b);
		}
		
		
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(getChunkKingdom(p.getLocation().getChunk()) != null){
				if(getChunkKingdom(p.getLocation().getChunk()).equals("SafeZone")){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityAttackEntity(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			if(event.getDamager() instanceof Player){
			Player p = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			
			if(adminmode.contains(damager.getUniqueId())){
				return;
			}
			if(getChunkKingdom(p.getLocation().getChunk()) != null){
				if(getChunkKingdom(p.getLocation().getChunk()).equals("SafeZone")){
					event.setCancelled(true);
					damager.sendMessage(ChatColor.RED + "You can't damage a player while he is in a safezone");
				return;
				}
			}
			if(getChunkKingdom(damager.getLocation().getChunk()) != null){
				if(getChunkKingdom(damager.getLocation().getChunk()).equals("SafeZone")){
					event.setCancelled(true);
					damager.sendMessage(ChatColor.RED + "You can't damage a player while you are in a safezone");
			return;	
				}
			}
		}
		}
	}
	

	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player p = event.getPlayer();
		if(event.getTo().getChunk() != event.getFrom().getChunk()){
			
			PlayerChangeChunkEvent pcce = new PlayerChangeChunkEvent(p, event.getFrom().getChunk(), event.getTo().getChunk());
			Bukkit.getServer().getPluginManager().callEvent(pcce);
			

		}
		
	}
	

	
	
	public void newKingdom(UUID king, String tag){
		if(tag.length() <= getConfig().getInt("kingdom-char-tag-limit")||
		getConfig().getInt("kingdom-char-tag-limit") == 0){
		List<String> list = new ArrayList<String>();
		kingdoms.set(tag + ".king", king.toString());
		kingdoms.set(tag + ".might", 0);
		kingdoms.set(tag + ".nexus-block", 0);
		kingdoms.set(tag + ".home", 0);
		kingdoms.set(tag + ".chestsize", 9);
		kingdoms.set(tag + ".members", list);
		kingdoms.set(tag + ".mods", list);
		kingdoms.set(tag + ".enemies", list);
		kingdoms.set(tag + ".allies", list);
		kingdoms.set(tag + ".resourcepoints", 5);
		kingdoms.set(tag + ".champion.health", 100);
		kingdoms.set(tag + ".champion.damage", 5);
		kingdoms.set(tag + ".champion.specials", 0);
		kingdoms.set(tag + ".champion.speed", 0);
		kingdoms.set(tag + ".champion.resist", 0);
		saveKingdoms();
		
		powerups.set(tag + ".dmg-reduction", 0);
		powerups.set(tag + ".regen-boost", 0);
		powerups.set(tag + ".dmg-boost", 0);
		powerups.set(tag + ".double-loot-chance", 0);
		savePowerups();

		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		chest.set(tag, items);
		saveChests();
		
		misupgrades.set(tag + ".antitrample", false);
		misupgrades.set(tag + ".anticreeper", false);
		misupgrades.set(tag + ".nexusguard", false);
		misupgrades.set(tag + ".glory", false);
		misupgrades.set(tag + ".bombshards", false);
		saveMisupgrades();
		Player p = Bukkit.getPlayer(king);
		p.sendMessage(ChatColor.GREEN + "You created a new kingdom: " + tag);
		players.set(p.getUniqueId().toString() + ".kingdom", tag);
		savePlayers();
		Bukkit.broadcastMessage(ChatColor.DARK_RED + "A new kingdom, " + tag + " has been founded by " + p.getName());
		
		for(String s: land.getKeys(false)){
			if(land.getString(s).equals(tag)){
				land.set(s, null);
			}
		}
		saveClaimedLand();
	}else{
		Bukkit.getPlayer(king).sendMessage(ChatColor.RED + "Your kingdom name cannot exceed " + getConfig().getInt("kingdom-char-tag-limit") + " characters!");
	}
	}
	
	public boolean disbandKingdom(String tag){
		try{
		ArrayList<OfflinePlayer> members = getKingdomMembers(tag);
		if(hasNexus(tag)){
		getNexusLocation(tag).getBlock().setType(Material.AIR);
		getNexusLocation(tag).getBlock().removeMetadata("nexusblock", this);
	}
		kingdoms.set(tag, null);
		if(members.size() > 0){
		for(OfflinePlayer p: members){
			if(p != null){
			players.set(p.getUniqueId().toString() + ".kingdom", "");
			if(p.isOnline()){
				((Player) p).sendMessage(ChatColor.RED + "Your kingdom was disbanded!");
			}
		}
		}
	}
		saveKingdoms();
		savePlayers();
		return true;
	}catch(NullPointerException e){
		return false;
	}
	}
	
	public void modPlayer(String kingdom, UUID uuid){
		List<String> mods = kingdoms.getStringList(kingdom + ".mods");
		if(!mods.contains(uuid.toString())){
		mods.add(uuid.toString());
		kingdoms.set(kingdom + ".mods", mods);
	}
		saveKingdoms();
	}
	
	public void kingPlayer(String kingdom, UUID uuid){
		String oldking = kingdoms.getString(kingdom + ".king");
		modPlayer(kingdom, UUID.fromString(oldking));
		unModPlayer(kingdom, uuid);
		joinKingdom(kingdom, Bukkit.getPlayer(UUID.fromString(oldking)));
		List<String> members = kingdoms.getStringList(kingdom + ".members");
		members.remove(uuid.toString());
		kingdoms.set(kingdom + ".members", members);
		kingdoms.set(kingdom + ".king", uuid.toString());
		Bukkit.broadcastMessage(ChatColor.GOLD + Bukkit.getPlayer(UUID.fromString(oldking)).getName() + " has passed leadership of " + kingdom + " to " + Bukkit.getPlayer(uuid).getName());
		saveKingdoms();
	}
	
	
	public void unModPlayer(String kingdom, UUID uuid){
		List<String> mods = kingdoms.getStringList(kingdom + ".mods");
	if(mods.contains(uuid.toString())){	
		mods.remove(uuid.toString());
	}
		kingdoms.set(kingdom + ".mods", mods);
		saveKingdoms();
	}
	
	public void joinKingdom(String kingdom, Player p){
		PlayerJoinKingdomEvent pcce = new PlayerJoinKingdomEvent(p, kingdom, getKingdom(p));
		Bukkit.getServer().getPluginManager().callEvent(pcce);
		String puuid = p.getUniqueId().toString();
		List<String> members= kingdoms.getStringList(kingdom + ".members");
		members.add(puuid);
		kingdoms.set(kingdom + ".members", members);
		saveKingdoms();
		
		players.set(p.getUniqueId().toString() + ".kingdom", kingdom);
		savePlayers();
		
	}
	
	public void quitKingdom(String kingdom, OfflinePlayer p){
		PlayerJoinKingdomEvent pcce = new PlayerJoinKingdomEvent(p, null, kingdom);
		Bukkit.getServer().getPluginManager().callEvent(pcce);
		if(kingdoms.getStringList(kingdom + ".members") != null){
		if(kingdoms.getStringList(kingdom + ".members").contains(p.getUniqueId().toString())){
		String puuid = p.getUniqueId().toString();
		unModPlayer(kingdom, p.getUniqueId());
		List<String> members= kingdoms.getStringList(kingdom + ".members");
		members.remove(puuid);
		kingdoms.set(kingdom + ".members", members);
		
		
		
		saveKingdoms();
		
		players.set(p.getUniqueId().toString() + ".kingdom", "");
		savePlayers();
	}
	}else{
		players.set(p.getUniqueId().toString() + ".kingdom", "");
		savePlayers();
	}
	}
	
	public void invitePlayer(String kingdom, Player p){
		p.setMetadata("kinv " + kingdom, new FixedMetadataValue(this, ""));
	}
	
	public void deinvitePlayer(String kingdom, Player p){
		if(p.hasMetadata("kinv " + kingdom)){
		p.removeMetadata("kinv " + kingdom, this);
	}
	}
	

	
	public void messageKingdomPlayers(String kingdom, String message){
		for(Player p:getKingdomOnlineMembers(kingdom)){
			p.sendMessage(message);
		}
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if(getChatOption(p).equals("kingdom")){
			event.setCancelled(true);
			this.messageKingdomPlayers(getKingdom(p), ChatColor.GREEN + "[" + p.getName() + "]: " + event.getMessage());
		}else if(getChatOption(p).equals("ally")){
			event.setCancelled(true);
			
			this.messageKingdomPlayers(getKingdom(p), ChatColor.LIGHT_PURPLE + "[" + getKingdom(p) + "][" + p.getName() + "]: " + event.getMessage());
			
			for(String ally: getAllies(getKingdom(p))){
			    messageKingdomPlayers(ally, ChatColor.LIGHT_PURPLE + "[" + getKingdom(p) + "][" + p.getName() + "]: " + event.getMessage());
			}
		}
	}
	
	public ArrayList<Player> getKingdomOnlineMembers(String kingdom){
		ArrayList<Player> members = new ArrayList<Player>();
		List<String> uuids = kingdoms.getStringList(kingdom + ".members");
		uuids.add(kingdoms.getString(kingdom + ".king"));
		for(String s:uuids){
			try{
			if(UUID.fromString(s) != null){
			if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
				members.add(Bukkit.getPlayer(UUID.fromString(s)));
			}
		}
		}catch(NullPointerException e){
			
		}
		}
		return members;
		
	}
	
	public ArrayList<Player> getKingdomOfflineMembers(String kingdom){
		ArrayList<Player> members = new ArrayList<Player>();
		List<String> uuids = kingdoms.getStringList(kingdom + ".members");
		uuids.add(kingdoms.getString(kingdom + ".king"));
		for(String s:uuids){
			if(!Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline()){
				members.add(Bukkit.getPlayer(UUID.fromString(s)));
			}
		}
		return members;
		
	}
	
	public ArrayList<OfflinePlayer> getKingdomMembers(String kingdom){
		ArrayList<OfflinePlayer> members = new ArrayList<OfflinePlayer>();
		List<String> uuids = kingdoms.getStringList(kingdom + ".members");
		uuids.add(kingdoms.getString(kingdom + ".king"));
		for(String s:uuids){
				members.add(Bukkit.getPlayer(UUID.fromString(s)));
			
		}
		return members;
		
	}
	
	public int getKingdomMemberCount(String kingdom){
		return getKingdomMembers(kingdom).size();
	}
	
	public boolean hasNexus(String kingdom){
		if(TechnicalMethods.stringToLocation(kingdoms.getString(kingdom + ".nexus-block")) == null){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean hasHome(String kingdom){
		if(TechnicalMethods.stringToLocation(kingdoms.getString(kingdom + ".home")) == null){
			return false;
		}else{
			return true;
		}
	}

	
	
	public boolean hasKingdom(OfflinePlayer p){
		boolean boo = true;
		if(players.getString(p.getUniqueId() .toString() + ".kingdom") != null){
		if(players.getString(p.getUniqueId() .toString() + ".kingdom").equals("")){
			boo = false;
		}
	}else{
		players.set(p.getUniqueId().toString() + ".ign", p.getName());
		players.set(p.getUniqueId().toString() + ".kingdom", "");
		savePlayers();
		Bukkit.getLogger().info("Added " + p.getName() + "'s info to players.yml");
		boo = false;
	}
		return boo;
		
	}
	
	public String getKingdom(OfflinePlayer p){
		return players.getString(p.getUniqueId().toString() + ".kingdom");
	}
	
	public boolean isMod(String kingdom, OfflinePlayer p){
		boolean boo = false;
		
		if(hasKingdom(p)){
			if(kingdoms.getStringList(kingdom + ".mods").contains(p.getUniqueId().toString())){
				boo = true;
			}else{
				boo = false;
			}
		}else{
			boo = false;
		}
		
		return boo;
	}
	
	public void allyKingdom(String kingdom, String ally){
	

		List<String> enemies = kingdoms.getStringList(kingdom + ".enemies");
		List<String> allies = kingdoms.getStringList(kingdom + ".allies");
	if(enemies.contains(ally)){
		
		enemies.remove(ally);
		
	}
	
	if(allies.contains(ally)){	
		return;
	}else{
		allies.add(ally);
	}
         		kingdoms.set(kingdom + ".allies", allies);
         		kingdoms.set(kingdom + ".enemies", enemies);
         		saveKingdoms();
             
		
		
		
		
	}
	
	
	public void enemyKingdom(String kingdom, String enemy){
		

		List<String> enemies = kingdoms.getStringList(kingdom + ".enemies");
		List<String> allies = kingdoms.getStringList(kingdom + ".allies");
	if(enemies.contains(enemy)){
		
	}else{
		enemies.add(enemy);
	}
	
	if(allies.contains(enemy)){
		allies.remove(enemy);
	}
	
	List<String> enemyenemies = kingdoms.getStringList(enemy + ".enemies");
	List<String> enemyallies = kingdoms.getStringList(enemy + ".allies");
	
	if(enemyenemies.contains(kingdom)){
		
	}else{
		enemyenemies.add(kingdom);
	}
	
	if(enemyallies.contains(kingdom)){	
		enemyallies.remove(kingdom);
	}
         		kingdoms.set(enemy + ".allies", enemyallies);
         		kingdoms.set(enemy + ".enemies", enemyenemies);
         		kingdoms.set(kingdom + ".allies", allies);
         		kingdoms.set(kingdom + ".enemies", enemies);
         		saveKingdoms();
             
		
		
		
		
	}
	
	public void neutralizeKingdom(String kingdom, String nkingdom){
		List<String> enemies = kingdoms.getStringList(kingdom + ".enemies");
		List<String> allies = kingdoms.getStringList(kingdom + ".allies");
	if(enemies.contains(nkingdom)){	
		
				enemies.remove(nkingdom);
			
		
		
	}
	
	if(allies.contains(nkingdom)){	
				allies.remove(nkingdom);
			
		
	}
		kingdoms.set(kingdom + ".enemies", enemies);
		kingdoms.set(kingdom + ".allies", allies);
		saveKingdoms();
	}
	
	public boolean isKAlly(String kingdom, String ally){
		boolean boo = false;
			if(kingdoms.getStringList(kingdom + ".allies").contains(ally)){
				boo = true;
			}else{
				boo = false;
			
	}
	
		
		return boo;
	}
	
	public boolean isKEnemy(String kingdom, String enemy){
		boolean boo = false;
		if(kingdoms.getStringList(kingdom + ".enemies").contains(enemy)){
			boo = true;
		}else{
			boo = false;
		}
		
		return boo;
	}
	
	public boolean isAlly(String kingdom, OfflinePlayer p){
		boolean boo = false;
		if(hasKingdom(p)){
			if(kingdoms.getStringList(kingdom + ".allies").contains(getKingdom(p))){
				boo = true;
			}else{
				boo = false;
			}
	}
	
		
		return boo;
	}
	
	public boolean isEnemy(String kingdom, OfflinePlayer p){
		boolean boo = false;
		if(hasKingdom(p)){
		if(kingdoms.getStringList(kingdom + ".enemies").contains(getKingdom(p))){
			boo = true;
		}else{
			boo = false;
		}
	}

	
	return boo;
}
	
	public boolean isNexusChunk(Chunk c){
		boolean boo = false;
		
		if(getChunkKingdom(c) != null){
			String kingdom = getChunkKingdom(c);
			if(hasNexus(kingdom)){
			if(getNexusLocation(kingdom).getChunk().equals(c)){
				boo = true;
			}
		}
		}
		
		return boo;
	}
	
	public void placeNexus(Location loc, String kingdom){
		loc.getBlock().setType(Material.BEACON);
		loc.getBlock().setMetadata("nexusblock", new FixedMetadataValue(this, "ok."));
		String sloc = TechnicalMethods.locationToString(loc);
		kingdoms.set(kingdom + ".nexus-block", sloc);
		saveKingdoms();
	}
	
	public String getChatOption(Player p){
		String option = "public";
		if(this.chatoption.containsKey(p.getUniqueId())){
			return this.chatoption.get(p.getUniqueId());
		}else{
			return option;
		}
		
		
	}
	
	public void setChatOption(Player p, String s){
		chatoption.put(p.getUniqueId(), s);
	}
	
	public void displayMap(Player p){
		String com1 = "";
		String com2 = "";
		String com3 = "";
		String[] row = {"", "", "", "", "", ""};

		ArrayList<String> compass = AsciiCompass.getAsciiCompass(AsciiCompass.getCardinalDirection(p), ChatColor.AQUA, ChatColor.GRAY + "");
		
		com1 = "\\W/";
		com2 = "N+S";
		com3 = "/E\\";
			String cck = ChatColor.AQUA + "Unoccupied";
			HashMap<String, ChatColor> detected = new HashMap<String, ChatColor>();
			if(getChunkKingdom(p.getLocation().getChunk()) != null){
			    if(getChunkKingdom(p.getLocation().getChunk()).equals(getKingdom(p))){
			    	cck = ChatColor.GREEN + getKingdom(p);
			    }else if(isKEnemy(getKingdom(p), getChunkKingdom(p.getLocation().getChunk()))){
			    	cck = ChatColor.RED + getChunkKingdom(p.getLocation().getChunk());
			    	detected.put(getChunkKingdom(p.getLocation().getChunk()), ChatColor.RED);
			    }else if(isKAlly(getKingdom(p), getChunkKingdom(p.getLocation().getChunk()))){
			    	cck = ChatColor.LIGHT_PURPLE + getChunkKingdom(p.getLocation().getChunk());
			    	detected.put(getChunkKingdom(p.getLocation().getChunk()), ChatColor.LIGHT_PURPLE);
			    }else if(getChunkKingdom(p.getLocation().getChunk()).equals("SafeZone")){
			    	cck = ChatColor.GOLD + getChunkKingdom(p.getLocation().getChunk());
			    	detected.put(getChunkKingdom(p.getLocation().getChunk()), ChatColor.GOLD);
			    }else if(getChunkKingdom(p.getLocation().getChunk()).equals("WarZone")){
			    	cck = ChatColor.RED + getChunkKingdom(p.getLocation().getChunk());
			    	detected.put(getChunkKingdom(p.getLocation().getChunk()), ChatColor.RED);
			    }else{
			    	ChatColor rc = ChatColor.GRAY;
			    	cck = rc + getChunkKingdom(p.getLocation().getChunk());
			    	detected.put(getChunkKingdom(p.getLocation().getChunk()), rc);
			    }
			}
			p.sendMessage(ChatColor.AQUA + "============[" +  cck + ChatColor.AQUA + "]============");
			p.sendMessage(com1 + "          " + ChatColor.GREEN + "Your Kingdom" + "                 " + ChatColor.GRAY + "Unidentified Kingdom");
			p.sendMessage(com2 + "          " + ChatColor.RED + "Enemies of your Kingdom" + "   " + ChatColor.AQUA + "Unoccupied land");
			p.sendMessage(com3 + "          " + ChatColor.LIGHT_PURPLE + "Allies of your Kingdom" + "      " + ChatColor.WHITE + "You");
			//North: -Z
			//South: +Z
			//East: +X
			//West: -X
			int orix = p.getLocation().getChunk().getX();
			int oriz = p.getLocation().getChunk().getZ();
			for(int xc = 0; xc <= 4; xc++){
				int x = xc-2;
				for(int zc = 0; zc <= 12; zc++){
					int z = zc-6;
				String chunkcolor = mapIdentifyChunk(p.getWorld().getChunkAt(orix + x, oriz + z), p);
				   if(x == 0 && z == 0){
					chunkcolor = ChatColor.WHITE + "+";
				   }
				   
				   row[xc] += chunkcolor;
				   
				}
				p.sendMessage(row[xc]);
			}
			
			
			p.sendMessage(ChatColor.AQUA + "=======================================");
			
			
			
	}
	
	public String mapIdentifyChunk(Chunk c, Player p){
		String cck = "x";
		if(getChunkKingdom(c) != null){
		    if(getChunkKingdom(c).equals(getKingdom(p))){
		    	cck = ChatColor.GREEN + "x";
		    }else if(isKEnemy(getKingdom(p), getChunkKingdom(c))){
		    	cck = ChatColor.RED + "x";
		    }else if(isKAlly(getKingdom(p), getChunkKingdom(c))){
		    	cck = ChatColor.LIGHT_PURPLE + "x";
		    }else if(getChunkKingdom(c).equals("SafeZone")){
		    	cck = ChatColor.GOLD + "x";
		    }else if(getChunkKingdom(c).equals("WarZone")){
		    	cck = ChatColor.RED + "x";
		    }else{
		    	cck = ChatColor.GRAY + "x";
		    	
		    }
		}else{
			cck = ChatColor.AQUA + "x";
		}
		return cck;
	}
	
	
	public ArrayList<Chunk> getNearbyChunks(Player p){
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		
		
		return chunks;
	}
	
	public boolean isKing(Player p){
         boolean boo = false;
		
		if(hasKingdom(p)){
			if(UUID.fromString(kingdoms.getString(getKingdom(p) + ".king")).equals(p.getUniqueId())){
				boo = true;
			}else{
				boo = false;
			}
		}else{
			boo = false;
		}
		
		return boo;
	}
	
	
	
	
	
	public void invadeCurrentPosition(Player p){
		Chunk c = p.getLocation().getChunk();
		if(getAmtLand(getKingdom(p)) > ((getConfig().getInt("land-per-member") * getKingdomMemberCount(getKingdom(p))))){
		p.sendMessage(ChatColor.RED + "With " + getKingdomMemberCount(getKingdom(p)) + " members, you can only claim up to " + (getConfig().getInt("land-per-member") * getKingdomMemberCount(getKingdom(p))) + " land.");
		return;
		}
		if(getChunkKingdom(c) != null){
		if(!getChunkKingdom(c).equals(getKingdom(p))){
			if(!getChunkKingdom(c).equals("SafeZone")&&
					!getChunkKingdom(c).equals("WarZone")){
				
			if(!isKAlly(getKingdom(p), getChunkKingdom(c))){
			if(rpm.hasAmtRp(getKingdom(p), 10)){
				boolean boo = false;
				if(isNexusChunk(c)){
					boo = true;
				}
			if(StructureManager.isProtected(c, this)){
				p.sendMessage(ChatColor.RED + "You must invade surrounding power core chunks before invading this chunk!");
			    return;
			}
			p.sendMessage(ChatColor.GREEN + "Invading land! " + getChunkKingdom(c) + " is summoning their champion to defend their land!");
			p.sendMessage(ChatColor.RED + "Defeat their champion to gain their land!");
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BATTLE!");
			this.immunity.put(p.getUniqueId(), 60);
			p.sendMessage(ChatColor.RED + "10 resourcepoints spent");
			ChampionManager.spawnChampion(getChunkKingdom(c), p.getLocation(), p, boo, this);
			kingdoms.set(getKingdom(p) + ".land", kingdoms.getInt(getKingdom(p) + ".land") + 1);
			for(Player player : this.getKingdomOnlineMembers(getChunkKingdom(c))){
				this.invasiondef.put(player.getUniqueId(),p.getLocation());
			}
			this.messageKingdomPlayers(getChunkKingdom(c), ChatColor.RED + p.getName() + " is invading your land! Do /k defend to protect it!");
			rpm.minusRP(getKingdom(p), 10);
		}else{
			p.sendMessage(ChatColor.RED + "You need at least 10 resource points to attempt an invasion!");
		}
		}else{
			p.sendMessage(ChatColor.RED + "You can't invade an ally!");
		}
			
		}else{
			p.sendMessage(ChatColor.RED + "You can't invade a safezone or a warzone!");
		}
		}else if(getChunkKingdom(c).equals(getKingdom(p))){
			p.sendMessage(ChatColor.AQUA + "You can't invade your own kingdom!");
		}
	}else{
		p.sendMessage(ChatColor.RED + "This land is unoccupied. Do /k claim to claim unoccupied land.");
	}
	}
	
	public void forceClaimCurrentPosition(Chunk c, Player p){
		if(this.land.getString(chunkToString(c)) != null){
			if(!getChunkKingdom(c).equals(getKingdom(p))){
			kingdoms.set(getChunkKingdom(c) + ".land", kingdoms.getInt(getChunkKingdom(c) + ".land") - 1);
			kingdoms.set(getKingdom(p) + ".land", kingdoms.getInt(getKingdom(p) + ".land") + 1);
			saveKingdoms();
			land.set(chunkToString(c), getKingdom(p));
			saveClaimedLand();
			
			rpm.addMight(getKingdom(p), 5);
		
		}
		}
	}
	
	public ArrayList<String> getAllies(String kingdom){
		ArrayList<String> allies = new ArrayList<String>();
          for(String ally: kingdoms.getStringList(kingdom + ".allies")){
        	  allies.add(ally);
          }
		return allies;
	}
	
	public Location getNexusLocation(String kingdom){
		Location loc = null;
		
		if(kingdoms.get(kingdom) != null){
			if(hasNexus(kingdom)){
				loc = TechnicalMethods.stringToLocation(kingdoms.getString(kingdom + ".nexus-block"));
			}
		}
		
		return loc;
		
	}
	
	public void claimSafezoneChunk(Chunk c){
		if(this.land.getString(chunkToString(c)) == null){
			land.set(chunkToString(c), "SafeZone");
			saveClaimedLand();
		}
	}
	
	
	public void claimWarzoneChunk(Chunk c){
		if(this.land.getString(chunkToString(c)) == null){
			land.set(chunkToString(c), "WarZone");
			saveClaimedLand();
		}
	}
	
	public void claimSafezoneCurrentPosition(Player p){
		Chunk c = p.getLocation().getChunk();
		if(this.land.getString(chunkToString(c)) == null){
			land.set(chunkToString(c), "SafeZone");
			saveClaimedLand();
			p.sendMessage(ChatColor.GREEN + "Safezone Claimed");
		}else if(getChunkKingdom(c) != null){
			p.sendMessage(ChatColor.RED + "This land is owned by " + getChunkKingdom(c) + ". You can't claim this chunk as safezone.");
		}
	}
	
	public void claimWarzoneCurrentPosition(Player p){
		Chunk c = p.getLocation().getChunk();
		if(this.land.getString(chunkToString(c)) == null){
			land.set(chunkToString(c), "WarZone");
			saveClaimedLand();
			p.sendMessage(ChatColor.GREEN + "Warzone Claimed");
		}else if(getChunkKingdom(c) != null){
			p.sendMessage(ChatColor.RED + "This land is owned by " + getChunkKingdom(c) + ". You can't claim this chunk as warzone.");
		}
	}
	
	public void changeLandOwner(String kingdom, Chunk c){
		
		if(this.land.getString(chunkToString(c)) == null){
			land.set(chunkToString(c), kingdom);
			saveClaimedLand();
		}
	}
	
	public int getAmtLand(String kingdom){
		return kingdoms.getInt(kingdom + ".land");
	}
	
	public void claimCurrentPosition(Player p){
		Chunk c = p.getLocation().getChunk();
		if(getAmtLand(getKingdom(p)) >= (getConfig().getInt("land-per-member") * getKingdomMemberCount(getKingdom(p)))){
		p.sendMessage(ChatColor.RED + "With " + getKingdomMemberCount(getKingdom(p)) + " members, you can only claim up to " + (getConfig().getInt("land-per-member") * getKingdomMemberCount(getKingdom(p))) + " land.");
		return;
		}
		try{
		if(hasWorldGuard){
			if(wet.isInRegion(p.getLocation())){
				if(noRegionClaiming){
				p.sendMessage(ChatColor.RED + "You can't claim land in a region.");
				return;
			}
			}
		}
	}catch(NoClassDefFoundError e){
		Bukkit.getLogger().severe(ChatColor.RED + "Your worldguard is not the latest! Players will still be able to place a nexus in regioned areas! To prevent this, use /k admin safezone or /k admin warzone to protect an area from claiming.");
	}
		
		if(this.land.getString(chunkToString(c)) == null){
			if(rpm.hasAmtRp(getKingdom(p), 5)){
			land.set(chunkToString(c), getKingdom(p));
			saveClaimedLand();
			p.sendMessage(ChatColor.GREEN + "Land Claimed");
			p.sendMessage(ChatColor.RED + "5 resourcepoints spent");
			rpm.addMight(getKingdom(p), 5);
			rpm.minusRP(getKingdom(p), 5);
			kingdoms.set(getKingdom(p) + ".land", kingdoms.getInt(getKingdom(p) + ".land") + 1);
			saveKingdoms();
		}else{
			p.sendMessage(ChatColor.RED + "You don't have enough resource points.");
		}
		}else if(getChunkKingdom(c).equals(getKingdom(p))){
			p.sendMessage(ChatColor.AQUA + "Your kingdom already owns this land.");
		}else if(!getChunkKingdom(c).equals(getKingdom(p))){
			p.sendMessage(ChatColor.RED + "This land is owned by " + getChunkKingdom(c) + ". Do /k invade to challenge this kingdom's champion.");
		}
	}
	
	public void unclaimCurrentPosition(Player p){
		Chunk c = p.getLocation().getChunk();
		if(getChunkKingdom(c) != null){
		if(getChunkKingdom(c).equals(getKingdom(p))){
			land.set(chunkToString(c), null);
			saveClaimedLand();
			p.sendMessage(ChatColor.RED + "Land Unclaimed");
			p.sendMessage(ChatColor.RED + "5 resourcepoints returned. 5 might lost.");
			rpm.minusMight(getKingdom(p), 5);
			rpm.addRP(getKingdom(p), 5);
			kingdoms.set(getKingdom(p) + ".land", kingdoms.getInt(getKingdom(p) + ".land") - 1);
			saveKingdoms();
		}else if(!getChunkKingdom(c).equals(getKingdom(p))){
			p.sendMessage(ChatColor.RED + "This land is owned by " + getChunkKingdom(c) + ". You can't unclaim this land.");
		}
	}else{
		p.sendMessage(ChatColor.AQUA + "You can't unclaim land that you don't occupy.");
	}
	}
	
	public void emptyCurrentPosition(Chunk c){
		land.set(chunkToString(c), null);
		saveClaimedLand();
		if(getChunkKingdom(c) != null){
			if(!getChunkKingdom(c).equals("Safezone")&& !getChunkKingdom(c).equals("Warzone")){
				rpm.minusMight(getChunkKingdom(c), 5);
				rpm.addRP(getChunkKingdom(c), 5);
				kingdoms.set(getChunkKingdom(c) + ".land", kingdoms.getInt(getChunkKingdom(c) + ".land") - 1);
				saveKingdoms();
				
			}
		}
	}
	
	public void forceUnclaimCurrentPosition(Chunk c2, Player p, boolean b){
		Chunk c = p.getLocation().getChunk();
		if(!getChunkKingdom(c).equals("")){
                 if(b){
			p.sendMessage(ChatColor.RED + "Land Unclaimed");
		        }
			if(getChunkKingdom(c).equals("SafeZone")||
					getChunkKingdom(c).equals("WarZone")||
					getChunkKingdom(c).equals("")){
				land.set(chunkToString(c), null);
				saveClaimedLand();
				return;
			}else{
				rpm.minusMight(getChunkKingdom(c), 5);
				rpm.addRP(getChunkKingdom(c), 5);
				land.set(chunkToString(c), null);
				saveClaimedLand();
				
				kingdoms.set(getChunkKingdom(c) + ".land", kingdoms.getInt(getChunkKingdom(c) + ".land") - 1);
				messageKingdomPlayers(getChunkKingdom(c), p.getName() + " unclaimed your land. Refudning resource points. 5 might lost.");
			return;
			}
			
			
		}else{
			if(b){
			p.sendMessage(ChatColor.AQUA + "You can't unclaim land that is empty");
			}
		}
	}

	
	public String getChunkKingdom(Chunk c){
		String s = null;
		if(c != null){
		if(land.getString(chunkToString(c)) != null){
			s = land.getString(chunkToString(c));
		}
	}
		return s;
	}
	
	public String chunkToString(Chunk c){
		
		String s = "" + c.getX()  +" , "+ c.getZ() +" , "+ c.getWorld().getName();
		
		
		return s;
		
	}
	
	public Chunk stringToChunk(String s){
		Chunk c = null;
		String[] split = s.split(" , ");
		try{
		c = Bukkit.getWorld(split[2]).getChunkAt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
		}catch(NullPointerException e){
			Bukkit.getLogger().severe(ChatColor.RED + "One of the land claim areas is invalid! Did you change the claimedchunks config recently?");
		}catch(NumberFormatException e){
			Bukkit.getLogger().severe(ChatColor.RED + "One of the land claim areas is invalid! Did you change the claimedchunks config recently?");
		}
		return c;
	}
	
	public static HashMap<Integer, String> sortHashList(HashMap<Integer, String> map) {
		
		Object[] list = map.keySet().toArray();
		Arrays.sort(list);
		HashMap<Integer, String> sorted = new HashMap<Integer, String>();
		for(Object i: list){
			if(i instanceof Integer){
			sorted.put((Integer) i, map.get((Integer) i));
		}
		}
		
		return sorted;
	}
	
	public boolean hasMisUpgrade(String kingdom, String upgrade){
		boolean boo = misupgrades.getBoolean(kingdom + "." + upgrade);
		
		return boo;
	}
	

	
	
	
	public static List<Entity> getNearbyEntities(Location where, int range) {
		List<Entity> found = new ArrayList<Entity>();
		 
		for (Entity entity : where.getWorld().getEntities()) {
		if (isInBorder(where, entity.getLocation(), range)) {
		found.add(entity);
		}
		}
		return found;
		}
	
	public static boolean isInBorder(Location center, Location notCenter, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
		int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();
		 
		if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
		return false;
		}
		return true;
		}
	
	
	
	
	public File kingdomsfile = new File("plugins/Kingdoms/kingdoms.yml");
	  public FileConfiguration kingdoms = YamlConfiguration.loadConfiguration(this.kingdomsfile);
	  
	  public void saveKingdoms() {
		    try {
		      this.kingdoms.save(this.kingdomsfile);
		      this.kingdoms = YamlConfiguration.loadConfiguration(this.kingdomsfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File playersfile = new File("plugins/Kingdoms/players.yml");
	  public FileConfiguration players = YamlConfiguration.loadConfiguration(this.playersfile);
	  
	  public void savePlayers() {
		    try {
		      this.players.save(this.playersfile);
		      this.players = YamlConfiguration.loadConfiguration(this.playersfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File claimedlandfile = new File("plugins/Kingdoms/claimedchunks.yml");
	  public FileConfiguration land = YamlConfiguration.loadConfiguration(this.claimedlandfile);
	  
	  public void saveClaimedLand() {
		    try {
		      this.land.save(this.claimedlandfile);
		      this.land = YamlConfiguration.loadConfiguration(this.claimedlandfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File powerupsfile = new File("plugins/Kingdoms/powerups.yml");
	  public FileConfiguration powerups = YamlConfiguration.loadConfiguration(this.powerupsfile);
	  
	  public void savePowerups() {
		    try {
		      this.powerups.save(this.powerupsfile);
		      this.powerups = YamlConfiguration.loadConfiguration(this.powerupsfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File turretsfile = new File("plugins/Kingdoms/turrets.yml");
	  public FileConfiguration turrets = YamlConfiguration.loadConfiguration(this.turretsfile);
	  
	  public void saveTurrets() {
		    try {
		      this.turrets.save(this.turretsfile);
		      this.turrets = YamlConfiguration.loadConfiguration(this.turretsfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File misupgradesfile = new File("plugins/Kingdoms/miscellaneousupgrades.yml");
	  public FileConfiguration misupgrades = YamlConfiguration.loadConfiguration(this.misupgradesfile);
	  
	  public void saveMisupgrades() {
		    try {
		      this.misupgrades.save(this.misupgradesfile);
		      this.misupgrades = YamlConfiguration.loadConfiguration(this.misupgradesfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File chestfile = new File("plugins/Kingdoms/kingdomchests.yml");
	  public FileConfiguration chest = YamlConfiguration.loadConfiguration(this.chestfile);
	  
	  public void saveChests() {
		    try {
		      this.chest.save(this.chestfile);
		      this.chest = YamlConfiguration.loadConfiguration(this.chestfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public File structuresfile = new File("plugins/Kingdoms/structures.yml");
	  public FileConfiguration structures = YamlConfiguration.loadConfiguration(this.structuresfile);
	  
	  public void saveStructures() {
		    try {
		      this.structures.save(this.structuresfile);
		      this.structures = YamlConfiguration.loadConfiguration(this.structuresfile);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public boolean usewhitelist = getConfig().getBoolean("use-whitelist");
		public int rpi = getConfig().getInt("items-needed-for-one-resource-point");
		public HashMap<Material, Integer> specialcaseitems = new HashMap<Material, Integer>();
		
		public ArrayList<Material> blacklistitems = new ArrayList<Material>();
		public HashMap<Material, Integer> whitelistitems = new HashMap<Material, Integer>();
		public HashMap<UUID, Location> click1 = new HashMap<UUID, Location>();
		public HashMap<UUID, Location> click2 = new HashMap<UUID, Location>();
		public ArrayList<UUID> placingnexusblock = new ArrayList<UUID>();
		public HashMap<UUID, Integer> immunity = new HashMap<UUID, Integer>();
		public HashMap<UUID, Chunk> champions = new HashMap<UUID, Chunk>();
		public HashMap<UUID, String> nexusguards = new HashMap<UUID, String>();
		public HashMap<UUID, Location> invasiondef = new HashMap<UUID, Location>();
		public HashMap<UUID, UUID> duelpairs = new HashMap<UUID, UUID>();
		public ArrayList<UUID> adminmode = new ArrayList<UUID>();
		public ArrayList<UUID> mapmode = new ArrayList<UUID>();
		public ArrayList<UUID> rapidclaiming = new ArrayList<UUID>();
		public HashMap<UUID, String> chatoption = new HashMap<UUID, String>();
		public ArrayList<UUID> firearrows = new ArrayList<UUID>();
		
		public Plugin getWorldGuard() {
		    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		 
		    if (plugin == null) {
		        return null; 
		    }
		 
		    return plugin;
		}
		
		public boolean hasWorldEdit(){
			if(Bukkit.getPluginManager().getPlugin("WorldEdit") != null){
				return true;
			}
			return false;
		}
		

		public boolean hasWorldGuard = false;
		public boolean noRegionClaiming = true;
		public WorldEditTools wet;
		public Kingdoms plugin = this;
		public AsciiCompass compass = new AsciiCompass();
		public RpmManager rpm= new RpmManager(this);
	
	
}
