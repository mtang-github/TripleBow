package us.danny.triplebow;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static final String lore = "Triple the fun!";
	private static final double rotateRadians = 0.0610865;
	
	public Main() {
		//do nothing
	}

    @Override
    public void onEnable() {        
        PluginManager pluginManager = getServer().getPluginManager();
        
        BowShootListener bowListener = new BowShootListener(lore, rotateRadians);
        pluginManager.registerEvents(bowListener, this);
        
        
        System.out.println("Triple Bow enabled");
    }
    
    @Override
    public void onDisable() {
    	System.out.println("Triple Bow disabled");
    }
}
