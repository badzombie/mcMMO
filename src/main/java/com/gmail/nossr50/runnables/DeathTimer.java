/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.nossr50.runnables;

import com.gmail.nossr50.Users;
import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.datatypes.AbilityType;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.mcMMO;
import org.bukkit.entity.Player;
/**
 *
 * @author Aeris
 */
public class DeathTimer implements Runnable{
    private final mcMMO plugin;
    public DeathTimer(final mcMMO plugin) 
    {
        this.plugin = plugin;
    }
    static int TIME_CONVERSION_FACTOR=1000;
    public static int calculateTimeLeft(long deactivatedTimeStamp, int cooldown) {
        return (int) (((deactivatedTimeStamp + (cooldown * TIME_CONVERSION_FACTOR)) - (System.currentTimeMillis()) / TIME_CONVERSION_FACTOR));
    }
    public static boolean cooldownOver(long oldTime, int cooldown){
        long currentTime = System.currentTimeMillis();

        if (currentTime - oldTime >= (cooldown * TIME_CONVERSION_FACTOR)) {
            return true;
        }
        else {
            return false;
        }
    }
     public static void watchCooldown(Player player, PlayerProfile PP, long curTime) {
        if (curTime - (PP.getDeathDATS() * TIME_CONVERSION_FACTOR) >= (LoadProperties.deathLossCooldown * TIME_CONVERSION_FACTOR)) {
            player.sendMessage("Death penalty is back up");
        }
    }
    @Override
    public void run(){
      for(Player player : plugin.getServer().getOnlinePlayers())
		{
                    long curTime = System.currentTimeMillis();
			if(player == null)
				continue;
			PlayerProfile PP = Users.getProfile(player);
			
			if(PP == null)
				continue;
			
                        watchCooldown(player, PP, curTime);
                }
       
    }
}
