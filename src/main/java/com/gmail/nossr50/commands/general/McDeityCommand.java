/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.nossr50.commands.general;

import com.gmail.nossr50.Users;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.skills.Skills;
import com.gmail.nossr50.mcPermissions;
import com.gmail.nossr50.config.LoadProperties;


import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Aeris
 */
public class McDeityCommand implements CommandExecutor{

            String location = "plugins/mcMMO/FlatFileStuff/mcmmo.deities";
 //           String deityList="plugins/mcMMO/FlatFileStuff/mcmmo.deityList";
    
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
               File theDir = new File(location);

                if (!theDir.exists()) {
                    try {
                        FileWriter writer = new FileWriter(theDir);
                        writer.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                File dList = new File(deityList);
//                if (!dList.exists()) {
//                    try {
//                        FileWriter writer = new FileWriter(dList);
//                        writer.close();
//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                
                Player player = null;
                if (sender instanceof Player) {
                    player = (Player) sender;
                }
                if (!(sender instanceof Player)) {
			sender.sendMessage("This command does not support console useage.");
			return true;
                }
                
                
                
                PlayerProfile PP = Users.getProfile(player);


                if (args.length < 1) {
                        player.sendMessage("Usage is /deity create");
                        player.sendMessage("/deity remove");
                        player.sendMessage("/deity worship");
                        player.sendMessage("/deity leave");
                        player.sendMessage("/deity list");
                        return true;
                }
                
                        if(args[0].equals("create")){
                            if (player != null && !mcPermissions.getInstance().mmoedit(player)) {
                                sender.sendMessage("This command requires permissions. Only Admins may create deities");
                                return true;
                            }
                            if(args.length!=5){
                                player.sendMessage(ChatColor.RED + " Usage: /deity create deityname PositiveSkill PositiveSkill NegativeSkill NegativeSkill");
                                return true;
                            }
                            if(!Skills.isSkill(args[2]) || !Skills.isSkill(args[3]) || !Skills.isSkill(args[4]) || !Skills.isSkill(args[5])){
                                player.sendMessage(ChatColor.RED + " Usage: /deity create deityname PositiveSkill PositiveSkill NegativeSkill NegativeSkill");
                                return true;
                            }
                            else{
                                try{
                                    FileWriter file = new FileWriter(location, true);
                                    BufferedWriter out = new BufferedWriter(file);
                                    //dName
                                    out.append(args[1]+":");
                                    //pos One
                                    out.append(args[2]+":");
                                    //pos two
                                    out.append(args[3]+":");
                                    //neg one
                                    out.append(args[4]+":");
                                    //neg two
                                    out.append(args[5]+":");
                                    //out.newLine();
                                    out.close();
                                
                                } catch (Exception e) {
                                    Bukkit.getLogger().severe("Exception while writing to " + location + " (Are you sure you formatted it correctly?)" + e.toString());
                                }
                                
                                player.sendMessage("You have created a deity");
                                
                            }
                        } 
                        else if(args[0].equals("worship")){
                            if(args.length>2){
                                player.sendMessage(ChatColor.RED + "Usage is /deity worship <deityname>");
                                return true;
                            }
                            if(!PP.getDeityName().equals("")){
                                player.sendMessage("You must first leave your current deity");
                                return true;
                            }
                            else{
                                try {
                                    FileReader file = new FileReader(location);
                                    //BufferedReader in = new BufferedReader(file);
                                    //StringBuilder writer = new StringBuilder();
                                    Scanner src = new Scanner(file);
//                                    String tempD =args[1].replaceAll(" ", "");
//                                    tempD =tempD.replaceAll("\n", "");
                                    src.useDelimiter(":");
                                        while(src.hasNext()){
                                    //        player.sendMessage(tempD);
                                            if(src.next().equals(args[1])){

                                            //get the skills

                                                Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), LoadProperties.deityBonus);
                                                Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), LoadProperties.deityBonus);
                                                Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), -LoadProperties.deityBonus);
                                                Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), -LoadProperties.deityBonus);
                                                PP.setDeityName(args[1]);
                                                file.close();
                                                player.sendMessage("You have started worshiping "+args[1]);
                                                return true;
                                            }
                                        }
                                        player.sendMessage("That deity does not exist");
                                        return true;
                                        //System.out.println(src.next());
                                       // src.next();

                            	        	
                                } catch (Exception e) {
                                    Bukkit.getLogger().severe("Exception while writing to " + location + " (Are you sure you formatted it correctly?)" + e.toString());
                                }
                            }
                        }
                        else if(args[0].equals("leave")){
                            if(args.length>2){
                                player.sendMessage(ChatColor.RED + "Usage is /deity leave");
                                return true;
                            }
                            if(PP.getDeityName().equals("")){
                                player.sendMessage("You must first have a deity to leave");
                                return true;
                            }
                            else{
                                try {
                                    FileReader file = new FileReader(location);
                                    //BufferedReader in = new BufferedReader(file);
                                    //StringBuilder writer = new StringBuilder();
                                    Scanner src = new Scanner(file);
                                    src.findInLine(PP.getDeityName());

                                    if (src.hasNext()){
                                        //System.out.println(src.next());
                                       // src.next();
                                        src.reset();
                                        src.useDelimiter(":");
                                       
                                       //get the skills
                                        
                                        Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), -LoadProperties.deityBonus);
                                        Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), -LoadProperties.deityBonus);
                                        Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), LoadProperties.deityBonus);
                                        Users.getProfile(player).addLevels(Skills.getSkillType(src.next()), LoadProperties.deityBonus);
                                        PP.setDeityName("");
                                        file.close();
                                        player.sendMessage("You have forsaken your deity");
                                        return true;
                                    }
                                    else{
                                        player.sendMessage("That deity does not exist");
                                        return true;
                                    }
                                    
                            //While not at the end of the file

                            	        	
                                } catch (Exception e) {
                                    Bukkit.getLogger().severe("Exception while writing to " + location + " (Are you sure you formatted it correctly?)" + e.toString());
                                }
                            }
                            
                        } 
                        else if(args[0].equals("list")){
                            try {
                                FileReader file = new FileReader(location);

                                Scanner src = new Scanner(file);

                                src.reset();
                                src.useDelimiter(":");

                                while (src.hasNext()){
                                    //System.out.println(src.next());
                                    //src.next();

                                    player.sendMessage(ChatColor.GOLD+src.next()+ChatColor.YELLOW+": Positive Domains: "+ChatColor.BLUE+src.next() + ", "+ src.next()+ChatColor.YELLOW+", Neagtive Domains: "+ChatColor.RED+ src.next()+", "+src.next());


                                }
                                return true;



                                } catch (Exception e) {
                                    //Bukkit.getLogger().severe("Exception while writing to " + location + " (Are you sure you formatted it correctly?)" + e.toString());
                                }
                            
                            
                            
                            
                            
                            
                        }else if(args[0].equals("remove")){
                            
                            if (player != null && !mcPermissions.getInstance().mmoedit(player)) {
                                sender.sendMessage("This command requires permissions. Only Admins may create deities");
                                return true;
                            }    
                            if(args.length>2){
                                player.sendMessage(ChatColor.RED + "Usage is /deity remove <deityname>");
                                return true;
                            }
                            try {
                            //Open the file
                            FileReader file = new FileReader(location);
                            BufferedReader in = new BufferedReader(file);
                            StringBuilder writer = new StringBuilder();
                            String line = "";

                            //While not at the end of the file
                            while((line = in.readLine()) != null)
                            {
                                    //Read the line in and copy it to the output it's not the player
                                    //we want to edit
                                    if(!line.split(":")[0].equalsIgnoreCase(args[1]))
                                    {
                                        writer.append(line).append("\r\n");

                            //Otherwise write the new player information
                                    } else {

                                    }
                            }
                            in.close();
                            //Write the new file
                            FileWriter out = new FileWriter(location);
                            out.write(writer.toString());
                            out.close();
	        } catch (Exception e) {
	            Bukkit.getLogger().severe("Exception while writing to " + location + " (Are you sure you formatted it correctly?)" + e.toString());
	        }
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                        }
                        else{
                            player.sendMessage("Usage is /deity create");
                            player.sendMessage("/deity remove");
                            player.sendMessage("/deity worship");
                            player.sendMessage("/deity leave");
                            player.sendMessage("/deity list");
                            return true;
                        }

                 


                
                
                
            return true;
    }
}
