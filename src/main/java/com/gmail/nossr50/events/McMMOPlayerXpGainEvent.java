package com.gmail.nossr50.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.m;
import com.gmail.nossr50.Users;

public class McMMOPlayerXpGainEvent extends Event {
	private Player player;
	private SkillType skill;
	private int xpGained;

	public McMMOPlayerXpGainEvent(Player player, SkillType skill, int xpGained) {

		PlayerProfile PP = Users.getProfile(player);
		this.player = player;
		this.skill = skill;
		//Checks if player is at max level
		/*if(m.getPowerLevel(player, PP) >= LoadProperties.maxLevel){
                        player.sendMessage("You are at max level");
			this.xpGained = 0;
		}
		else{*/	
                       // player.sendMessage("You have gained xp");
			this.xpGained = xpGained;
		
	}

/**
* @return Player gaining experience (can be null)
*/
	public Player getPlayer() {
		return player;
	}

/**
* @return SkillType that is gaining experience
*/
	public SkillType getSkill() {
		return skill;
	}

/**
* @return The number experience gained in this event
*/
	public int getXpGained() {
		return xpGained;
	}

/** Rest of file is required boilerplate for custom events **/
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
