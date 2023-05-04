package us.danny.triplebow;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BowShootListener implements Listener
{
    private final String lore;
    private final double rotateRadians;
    
    public BowShootListener(String lore, double rotateRadians) {
        this.lore = lore;
        this.rotateRadians = rotateRadians;
    }
    
    @EventHandler
    public void onShoot(final EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            ItemStack bow = event.getBow();
            
            if (isTripleBow(bow)) {
            	Arrow arrow = (Arrow)event.getProjectile();
            	Arrow left = cloneArrow(arrow);
            	Arrow right = cloneArrow(arrow);
            	rotateArrow(left, rotateRadians);
            	rotateArrow(right, -rotateRadians);
            }
        }
    }
    
    private boolean isTripleBow(ItemStack toTest) {
    	if(toTest.hasItemMeta()) {
    		ItemMeta itemMeta = toTest.getItemMeta();
    		if(itemMeta.hasLore()) {
    			List<String> loreList = itemMeta.getLore();
    			return loreList.contains(lore);
    		}
    	}
    	return false;
    }
    
    private Arrow cloneArrow(Arrow toClone) {
    	Location arrowLocation = toClone.getLocation();
    	Arrow toRet = toClone.getWorld().spawn(arrowLocation, Arrow.class);
    	
    	toRet.setOp(toClone.isOp());
    	toRet.setBounce(toClone.doesBounce());
    	toRet.setShooter(toClone.getShooter());
    	toRet.setVelocity(toClone.getVelocity());
    	toRet.setFireTicks(toClone.getFireTicks());
    	toRet.setKnockbackStrength(toClone.getKnockbackStrength());
    	toRet.setCritical(false);
    	toRet.spigot().setDamage(toClone.spigot().getDamage());
    	return toRet;
    }
    
    private void rotateArrow(Arrow arrow, double angleShift) {
    	Vector velocity = arrow.getVelocity();
    	double initX = velocity.getX();
    	double initZ = velocity.getZ();
    	double magnitude = Math.sqrt(initX * initX + initZ * initZ);
    	double initAngle = Math.atan2(initZ, initX);
    	double newAngle = initAngle + angleShift;
    	double newX = magnitude * Math.cos(newAngle);
    	double newZ = magnitude * Math.sin(newAngle);
    	arrow.setVelocity(new Vector(newX, velocity.getY(), newZ));
    }
}