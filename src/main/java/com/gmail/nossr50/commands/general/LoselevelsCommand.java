/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.nossr50.commands.general;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.Users;
import com.gmail.nossr50.m;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.mcPermissions;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.locale.mcLocale;
import com.gmail.nossr50.skills.Skills;
/**
 *
 * @author Aeris
 */
public class LoselevelsCommand implements CommandExecutor{
	@Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (!(sender instanceof Player)) {
			sender.sendMessage("This command does not support console useage.");
			return true;
            }

            PlayerProfile PP = Users.getProfile(player);


            if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage is /loselevels skillname levels");
                    return true;
            }
            if (args.length == 2) {
                            if (m.isInt(args[1]) && Skills.isSkill(args[0])) {
                                    if(Integer.valueOf(args[1])<0){
                                        player.sendMessage(ChatColor.BLUE+"AW-AW-AW, YOU DIDN'T SAY THE MAGIC WORD!");  
                                        return true;
                                    }
                                    int levels = Users.getProfile(player).getSkillLevel(Skills.getSkillType(args[0])) - Integer.valueOf(args[1]);
                                    if(levels<0){
                                        levels=0;
                                    }
                                    Users.getProfile(player).modifyskill(Skills.getSkillType(args[0]), levels);
                                    player.sendMessage(ChatColor.RED + args[0] + " has been modified.");
                            }
                    } 
            else {
                player.sendMessage(ChatColor.RED + "Usage is /loselevels skillname newvalue");
            }

                    return true;
	}
}






