package me.leothepro555.kingdoms.commands;

import me.leothepro555.kingdoms.main.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandInfo {
	
	public static void runCommand(Player p, String[] args, Kingdoms plugin){
		
       if(args.length == 1){
		
		p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 1======");
		p.sendMessage(ChatColor.BLUE + "Page 1: Contents");
		p.sendMessage(ChatColor.BLUE + "Page 2: What is Kingdoms?");
		p.sendMessage(ChatColor.BLUE + "Page 3: Starting");
		p.sendMessage(ChatColor.BLUE + "Page 4: The Nexus");
		p.sendMessage(ChatColor.BLUE + "Page 5: Champions and Invading");
		p.sendMessage(ChatColor.BLUE + "Page 6: Turrets");
		p.sendMessage(ChatColor.BLUE + "Page 7: Power Cells");
		p.sendMessage(ChatColor.BLUE + "Do /k info 2 to see the next page");
		
	
       }else if(args[1].equals("1")){
    	   
       }else if(args[1].equals("2")){
    	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 2======");
    	    p.sendMessage(ChatColor.BLUE + "What is Kingdoms?");
   			p.sendMessage(ChatColor.LIGHT_PURPLE + "Kingdoms is a plugin similar to the popular "
   					+ "Factions plugin, but with changes to the gameplay. It allows a player to"
   					+ " create his own Kingdom, protect land with the kingdom, and invade other's"
   					+ " land for items (and for fun). To see all commands, do /k.");
   			p.sendMessage(ChatColor.BLUE + "Do /k info 3 to see the next page");
   			
       }else if(args[1].equals("3")){
   	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 3======");
   	    p.sendMessage(ChatColor.BLUE + "Starting");
  			p.sendMessage(ChatColor.LIGHT_PURPLE + "First off, you would want to find a good place"
  					+ " to build a base, a normal minecraft base. Once you find a nice spot,"
  					+ " do /k create [kingdomname] to start your own kingdom. Then, do /k claim to"
  					+ " claim the land you are standing on.");
  			p.sendMessage(ChatColor.LIGHT_PURPLE + "Kingdoms claims chunks. Chunks are 16x255x16 groups of"
  					+ " blocks in a minecraft world. So a chunk has the height of a world, and the width "
  					+ "and breadth of 16 blocks.");
  			p.sendMessage(ChatColor.LIGHT_PURPLE + "This chunk claimed by your kingdom can only be edited by"
  					+ " you and your kingdom members, so this means non-members can't break, place or interact"
  					+ " with the things in your land. Beware of tnt cannons and invasions though");
  			p.sendMessage(ChatColor.LIGHT_PURPLE + "You don't necessarily have to create your own kingdom. You"
  					+ " can ask another kingdom mod or king to invite you to their kingdom, then you can join their"
  					+ " kingdoms instead. ");
  			p.sendMessage(ChatColor.BLUE + "Do /k info 4 to see the next page");
  			
      }else if(args[1].equals("4")){
     	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 4======");
       	    p.sendMessage(ChatColor.BLUE + "The Nexus");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Once you have claimed your land, do /k nexus to "
      					+ "place your nexus block, which resembles a beacon.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Nexuses can't be broken with your hand, and can be used"
      					+ " to access upgrades, gain resource points, or buy turrets. The nexus is your kingdom's"
      					+ " base of operations. When broken by enemies, the enemies can steal your resource points, "
      					+ "and if the land your nexus is on is sucessfully invaded, the items stored in the nexus,"
      					+ " and all your kingdom resource points will go to the invading kingdom. So ALWAYS protect"
      					+ " your nexus land, and the nexus itself.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "How to trade resourcepoints: First off, do /k tradable");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "In that list, is the list of items you can't trade for"
      					+ " resource points, and the items that give more resourcepoints when traded. To trade resource"
      					+ " points, right-click your nexus, and click the wheat. You will now see an empty GUI to place items"
      					+ " in. Place the items you want to trade for resource points. For this server, " + plugin.getConfig().getInt("items-needed-for-one-resource-point")
      					+ " items gives 1 resource point.");
      			p.sendMessage(ChatColor.BLUE + "Do /k info 5 to see the next page");
      			
          }else if(args[1].equals("5")){
     	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 5======");
       	    p.sendMessage(ChatColor.BLUE + "Champions and Invading");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Now this is where the real difference between Kingdoms and Factions"
      					+ " shows. You are able to invade others land (and vice versa) to claim their land to plunder their"
      					+ " resources, build in that space, or just to vent your anger at a guy who deserves it.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "To invade someone's land, do /k invade while standing on it. By doing so, "
      					+ "you summon their kingdom's champion. You will need to kill the champion in order to claim the land. If the"
      					+ " champion kills you, you don't gain the land, and you die. The strength of a kingdom's champion depends on its"
      					+ " upgrades.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "You can access champion upgrades through the nexus. It is recommended to upgrade"
      					+ " Drag, Mock and Champion Speed first as they are the cheaper effective upgrades for your champion. This does not"
      					+ " mean that those are the only upgrades you need.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "If you happen to invade some other kingdom's nexus land, you will duel a stronger "
      					+ "version of their kingom's champion. This nexus champion can summon nexus guards and has more health and better armour");
      			p.sendMessage(ChatColor.DARK_PURPLE + "Invasions cost 10 resource points each.");
      			p.sendMessage(ChatColor.BLUE + "Do /k info 6 to see the next page");
      			
          }else if(args[1].equals("6")){
     	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 6======");
       	    p.sendMessage(ChatColor.BLUE + "Turrets");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Do you want an auto mob killer? Do you hate people coming into your base"
      					+ " to mine your nexus? Then turrets are the best solution! Nothing says Stay Out like a lovely skull on a fence"
      					+ " that shoots projectiles.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Turrets can be purchased from the nexus, thought most are rather expensive."
      					+ " Most turrets must be placed on top of fences. Turrets will shoot at all mobs, non-allies and non-members.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Mines");
      			p.sendMessage(ChatColor.DARK_PURPLE + "Mines are a one-time-use defense system to deter players from entering, and"
      					+ " careless players to get killed. Or if you just want an invader to explode just before an invasion. "
      					+ " Mines can ONLY BE TRIGGERED BY NON-MEMBER AND NON-ALLY PLAYERS, so mobs don't activate them. If you"
      					+ " plan to repeatedly replace mines, then they are in no means cheap, but if an enemy was to step on"
      					+ "a mine, it would be a sad day for that guy due to the Mine's heavy attack.");
      			p.sendMessage(ChatColor.BLUE + "Do /k info 7 to see the next page");
      			
          }else if(args[1].equals("7")){
     	    p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "======Page 7======");
       	    p.sendMessage(ChatColor.BLUE + "Power Cells");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Don't you hate it when your enemies walk right up to your nexus to invade it?"
      					+ " That can be solved with power cells.");
      			p.sendMessage(ChatColor.LIGHT_PURPLE + "Power cells can be purchased from the nexus at 50 resource points, and is placed"
      					+ " similar to how nexuses are placed");
      			p.sendMessage(ChatColor.DARK_PURPLE + "Invaders can't invade a chunk next to a chunk with a power cell. So they need to "
      					+ "invade the land with the power cell to proceed, making the invasion more costly and time-consuming."
      					+ " However, power cell land does not prevent invaders from invading other power cell land. So remember this"
      					+ " before you buy a power cell for each land you have.");
      			p.sendMessage(ChatColor.BLUE + "This is the last page of /k info, you can report bugs at https://www.spigotmc.org/threads/kingdoms.62759/"
      					+ " and if you still have questions, feel free to ask server staff, or fellow players.");
      			
          }
       
	
	}

}
