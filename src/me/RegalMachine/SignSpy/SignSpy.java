package me.RegalMachine.SignSpy;

import java.util.ArrayList;
import java.util.List;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
//import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class SignSpy extends JavaPlugin implements Listener{
	
	public static List<String> ssToggledString = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		if(label.equalsIgnoreCase("signspy")){
			
			if(sender.hasPermission("signspy.use")){
				if(ssToggledString.contains(sender.getName())){
					ssToggledString.remove(sender.getName());
					sender.sendMessage("Sign Spy turned off!");
				}else{
					ssToggledString.add(sender.getName());
					sender.sendMessage("Sign Spy turned on!");
				}
				
			}else{
				sender.sendMessage(ChatColor.RED + "You cant use SignSpy!");
			}
			
			return true;
		}
		return false;
	}
	
	@EventHandler
	public static void onLogin(PlayerLoginEvent e){
		Player player = e.getPlayer();
		if(player.hasPermission("signspy.use")){
			ssToggledString.add(player.getName());
		}
		
	}
	
	@EventHandler
	public static void onLogOut(PlayerQuitEvent e){
		if(ssToggledString.contains(e.getPlayer().getName())){
			ssToggledString.remove(e.getPlayer().getName());
		}
	}
	
	
	@EventHandler
	public static void onSignChangeEvent(SignChangeEvent e){
		
		String message = ChatColor.GRAY + "[SignSpy]" + e.getPlayer().getDisplayName() + ": 1:" + e.getLine(0) + ChatColor.RESET + " 2:" + e.getLine(1) +  ChatColor.RESET + " 3:" + e.getLine(2) +  ChatColor.RESET + " 4:" + e.getLine(3);		
		
		for(String s: ssToggledString){
			Player player = Bukkit.getPlayer(s);
			if(player.isOnline()){
				player.sendMessage(message);
			}
		}
	}
}
