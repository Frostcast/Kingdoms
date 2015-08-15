package me.leothepro555.kingdoms.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ChampionManager implements Listener{
	
	public Kingdoms plugin;

	public ChampionManager(Kingdoms plugin) {
		
		this.plugin = plugin;
		
	}
	
	public static void spawnChampion(final String kingdom, final Location location, final Player p, final boolean isNexusDefense, final Kingdoms plugin){
		final Zombie champion = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
		champion.setTarget(p);
		champion.setBaby(false);
		champion.setMaxHealth(plugin.kingdoms.getDouble(kingdom + ".champion.health"));
		champion.setHealth(plugin.kingdoms.getDouble(kingdom + ".champion.health"));
		champion.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		
		if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 0){
			champion.getEquipment().setItemInHand(null);
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 1){
			champion.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 2){
			champion.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 3){
			champion.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 4){
			champion.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		if(plugin.kingdoms.getInt(kingdom + ".champion.drag") > 0){
			
			
			  new BukkitRunnable(){
				   
				   @Override
				   public void run(){
					   if(!p.isDead() && !champion.isDead() && champion.isValid() && p.isOnline()){
				            if(p.getLocation().distance(champion.getLocation()) > 5){
				         	   p.teleport(champion.getLocation());
				         	   p.sendMessage(ChatColor.RED + "The champion dragged you back!");
				           }
				        }else{
				         	this.cancel();
				         }
				   }
				    
				   }.runTaskTimer(plugin, 0L, 1L);
		
		
		}
		
		if(isNexusDefense){
			champion.setMaxHealth(plugin.kingdoms.getDouble(kingdom + ".champion.health") + 200);
			champion.setHealth(plugin.kingdoms.getDouble(kingdom + ".champion.health") + 200);
			 new BukkitRunnable(){
				   
				   @Override
				   public void run(){
					   if(!p.isDead() && !champion.isDead() && champion.isValid() && p.isOnline()){
				           ChampionManager.spawnNexusGuard(kingdom, p.getLocation(), p, plugin);
				        }else{
				         	this.cancel();
				         }
				   }
				    
				   }.runTaskTimer(plugin, 0L, 300L);
		}
		
		champion.getEquipment().setChestplateDropChance(0.0f);
		champion.getEquipment().setItemInHandDropChance(0.0f);
		
		champion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, plugin.kingdoms.getInt(kingdom + ".champion.speed")));
		
		plugin.champions.put(champion.getUniqueId(), location.getChunk());
		plugin.duelpairs.put(champion.getUniqueId(), p.getUniqueId());
	 }
}, 2);
	}
	
	
	@EventHandler
	public void onEDEE(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			Player p = (Player) event.getDamager();
			if(plugin.duelpairs.containsKey(event.getEntity().getUniqueId())){
				if(p.getGameMode() == GameMode.CREATIVE && !p.hasPermission("plugin.kingdoms.admin")){
					p.sendMessage(ChatColor.RED + "You can't hit champions in creative!");
					event.setCancelled(true);
					return;
				}
				if(!plugin.duelpairs.containsValue(event.getDamager().getUniqueId())){
					if(plugin.kingdoms.getInt(getChampionKingdom(event.getEntity()) + ".champion.duel") > 0){
					event.setCancelled(true);
					((Player) event.getDamager()).sendMessage(ChatColor.RED + "You can't damage the champion unless you're the invader!");
				    }
				}
			}
		}
	}
	
	public String getChampionKingdom(Entity champion){
		
		return plugin.plugin.getChunkKingdom(plugin.champions.get(champion.getUniqueId()));
	}
	
	@EventHandler
	public void onBlockAttemptPlace(BlockPlaceEvent event){
		if(plugin.duelpairs.containsValue(event.getPlayer().getUniqueId())){
			UUID championuuid = null;
			for(UUID uuid : plugin.duelpairs.keySet()){
				if(plugin.duelpairs.get(uuid).equals(event.getPlayer().getUniqueId())){
					championuuid = uuid;
					break;
				}
			}
			String kingdom = plugin.plugin.getChunkKingdom(plugin.champions.get(championuuid));
			int mocklevel = plugin.kingdoms.getInt(kingdom + ".champion.mock");
			if(mocklevel > 0){
				for(Entity e: event.getPlayer().getNearbyEntities(mocklevel, mocklevel, mocklevel)){
					if(e.getUniqueId().equals(championuuid)){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "The champion prevents you from building!");
						break;
					}
				}
				}
		}
	}
	
	@EventHandler
	public void onBlockAttemptBreak(BlockBreakEvent event){
		if(plugin.duelpairs.containsValue(event.getPlayer().getUniqueId())){
			UUID championuuid = null;
			for(UUID uuid : plugin.duelpairs.keySet()){
				if(plugin.duelpairs.get(uuid).equals(event.getPlayer().getUniqueId())){
					championuuid = uuid;
					break;
				}
			}
			String kingdom = plugin.plugin.getChunkKingdom(plugin.champions.get(championuuid));
			int mocklevel = plugin.kingdoms.getInt(kingdom + ".champion.mock");
			if(mocklevel > 0){
				for(Entity e: event.getPlayer().getNearbyEntities(mocklevel, mocklevel, mocklevel)){
					if(e.getUniqueId().equals(championuuid)){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "The champion prevents you from breaking!");
						break;
					}
				}
				}
		}
	}
	
	public static void spawnNexusGuard(String kingdom, Location location, Player p, Kingdoms plugin){
		Zombie champion = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
		champion.setTarget(p);
		champion.setMaxHealth(30.0);
		champion.setBaby(false);
		champion.setHealth(30.0);
		champion.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		
		if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 0){
			champion.getEquipment().setItemInHand(null);
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 1){
			champion.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 2){
			champion.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 3){
			champion.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
		}else if(plugin.kingdoms.getInt(kingdom + ".champion.weapon") == 4){
			champion.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		champion.getEquipment().setChestplateDropChance(0.0f);
		champion.getEquipment().setItemInHandDropChance(0.0f);
		
		champion.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, plugin.plugin.kingdoms.getInt(kingdom + ".champion.speed")));
		
		plugin.nexusguards.put(champion.getUniqueId(), kingdom);
		
	}
	
	@EventHandler
	public void onMobAttack(final EntityDamageByEntityEvent event){
		if(plugin.immunity.containsKey(event.getEntity().getUniqueId())){
			event.setDamage(0.0);
			event.getEntity().getVelocity().multiply(2);
			plugin.immunity.remove(event.getEntity().getUniqueId());
			
		}
		
		if(plugin.champions.containsKey(event.getEntity().getUniqueId())){
			if(event.getDamager() instanceof Player){
				Player p = (Player) event.getDamager();
			String kingdom = plugin.plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId()));
			if(plugin.hasKingdom(p)){
				if(plugin.plugin.getKingdom(p).equals(kingdom)){
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You can't attack your champion!");
				}
			}
		}
			
			if(event.getCause() == DamageCause.ENTITY_EXPLOSION||
					event.getCause() == DamageCause.BLOCK_EXPLOSION){
				event.setCancelled(true);
			}
			
			int randomnumber = new Random().nextInt(100) + 1;
			int antiknockbackchance = plugin.plugin.kingdoms.getInt(plugin.plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId())) + ".champion.resist");
			if(antiknockbackchance >= randomnumber){
				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	                 public void run() {
	                     event.getEntity().setVelocity(new Vector());
	                 }
	             }, 1L);
		}
		}
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		if(plugin.duelpairs.containsValue(event.getPlayer().getUniqueId())){
			for(Entity e:event.getPlayer().getNearbyEntities(30, 30, 30)){
				if(plugin.duelpairs.containsKey(e.getUniqueId())){
				if(plugin.duelpairs.get(e.getUniqueId()).equals(event.getPlayer().getUniqueId())){
					e.remove();
					plugin.champions.remove(e.getUniqueId());
					plugin.duelpairs.remove(e.getUniqueId());
				}
				}
				
				if(e instanceof Player){
					Player p = (Player) e;
					p.sendMessage(ChatColor.RED + "The invader " + event.getPlayer().getName() + " has left! Invasion cancelled!");
				}
				
			}
		}
	}
	
	
	
	@EventHandler
	public void onChampionTarget(EntityTargetEvent event){
		if(plugin.champions.containsKey(event.getEntity().getUniqueId())){
			if(event.getTarget() instanceof Player){
				Player p = (Player) event.getTarget();
			String kingdom = plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId()));
			if(plugin.hasKingdom(p)){
				if(plugin.getKingdom(p).equals(kingdom)){
					event.setCancelled(true);
				}
			}
			}
		}else if(plugin.nexusguards.containsKey(event.getEntity().getUniqueId())){
			if(event.getTarget() instanceof Player){
				Player p = (Player) event.getTarget();
			String kingdom = plugin.nexusguards.get(event.getEntity().getUniqueId());
			if(plugin.hasKingdom(p)){
				if(plugin.getKingdom(p).equals(kingdom)){
					event.setCancelled(true);
				}
			}
			}
		}
	}
	
	@EventHandler
	public void onSunDamage(EntityDamageEvent event){
		if(plugin.champions.containsKey(event.getEntity().getUniqueId())||
				plugin.nexusguards.containsKey(event.getEntity().getUniqueId())){
			if(event.getCause() == DamageCause.FIRE_TICK){
				event.setCancelled(true);
				event.getEntity().setFireTicks(0);
			}
		}
	}
	
	@EventHandler
	public void onChampionDeath(EntityDeathEvent event){
		if(plugin.isValidWorld(event.getEntity().getWorld())){
		if(plugin.champions.get(event.getEntity().getUniqueId()) != null){
		if(event.getEntity().getKiller() != null){
			Player p = event.getEntity().getKiller();
			if(plugin.hasKingdom(p) &&
					!plugin.getKingdom(p).equals(plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId())))){
				String kingdom = plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId()));
			 
				
				if(plugin.isNexusChunk(plugin.champions.get(event.getEntity().getUniqueId()))){
				 for(Object obj : plugin.chest.getList(kingdom)){
						if(obj instanceof ItemStack){
							event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),(ItemStack)obj);
						    
						}
					}
				 plugin.getNexusLocation(kingdom).getBlock().setType(Material.AIR);
				 
				 ArrayList<ItemStack> newlist = new ArrayList<ItemStack>();
				 plugin.chest.set(kingdom, newlist);
				 plugin.saveChests();
				 int gained = plugin.rpm.getRp(kingdom);
				 plugin.rpm.addRP(plugin.getKingdom(p), plugin.rpm.getRp(kingdom));
				 plugin.rpm.minusRP(kingdom, plugin.rpm.getRp(kingdom));
				 p.sendMessage(ChatColor.GREEN + "Your kingdom has gained " + gained + " resource points!");
			     
			 }
				if(StructureManager.isPowerCoreChunk(plugin.champions.get(event.getEntity().getUniqueId()), plugin)){
					 StructureManager.removePowerCore(plugin.champions.get(event.getEntity().getUniqueId()), plugin);
					 p.sendMessage(ChatColor.GREEN + "The power core in this chunk was destroyed!");	 
				}
			 plugin.forceClaimCurrentPosition(plugin.champions.get(event.getEntity().getUniqueId()), p);
			 
			 p.sendMessage(ChatColor.GREEN + "Invasion Successful.");
				
				plugin.duelpairs.remove(event.getEntity().getUniqueId());
			 p.sendMessage(ChatColor.GREEN + "You have successfully conquered " + kingdom + "'s land. ");
			 plugin.champions.remove(event.getEntity().getUniqueId());
			}else{
			event.getEntity().getLastDamageCause().setDamage(0.0);
		}
		}else{
			plugin.forceClaimCurrentPosition(plugin.champions.get(event.getEntity().getUniqueId()),Bukkit.getPlayer(plugin.duelpairs.get(event.getEntity().getUniqueId())));
			Bukkit.getPlayer(plugin.duelpairs.get(event.getEntity().getUniqueId())).sendMessage(ChatColor.GREEN + "Invasion Successful.");
			event.getEntity().getKiller().sendMessage(ChatColor.GREEN + "You have successfully conquered " + plugin.getChunkKingdom(plugin.champions.get(event.getEntity().getUniqueId())) + "'s land. ");
		plugin.champions.remove(event.getEntity().getUniqueId());
		plugin.duelpairs.remove(event.getEntity().getUniqueId());
		}


	
			
		}else if(plugin.nexusguards.containsKey(event.getEntity().getUniqueId())){
			plugin.nexusguards.remove(event.getEntity().getUniqueId());
		}
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
		if(plugin.duelpairs.containsValue(p.getUniqueId())){
			for(Entity e:p.getNearbyEntities(30, 30, 30)){
				if(plugin.duelpairs.containsKey(e.getUniqueId())){
				if(plugin.duelpairs.get(e.getUniqueId()).equals(p.getUniqueId())){
					
					e.remove();
					plugin.champions.remove(e.getUniqueId());
					plugin.duelpairs.remove(e.getUniqueId());
				}
				}
				
				if(e instanceof Player){
					Player plr = (Player) e;
					plr.sendMessage(ChatColor.RED + "The invader " + p.getName() + " has lost to the champion! Invasion failed!");
				}
				
			}
		}
	}
	}
	}
	

}
