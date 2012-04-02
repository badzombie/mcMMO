/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.nossr50.datatypes;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.events.McMMOPlayerXpGainEvent;
import com.gmail.nossr50.party.Party;
import com.gmail.nossr50.Users;
import com.gmail.nossr50.m;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.skills.Skills;

import java.io.*;
/**
 *
 * @author Aeris
 */
public class DeityProfile {
    String deityName;
    String location = "plugins/mcMMO/FlatFileStuff/mcmmo.deities";
    String directory = "plugins/mcMMO/FlatFileStuff/";
    
    public DeityProfile(String name)
    {
        new File(directory).mkdir();
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
		
		deityName = name;

    }
    public boolean create(String dName, String posOne, String posTwo, String negOne, String negTwo){
        
        mcMMO.database.write("INSERT INTO "+LoadProperties.MySQLtablePrefix + "deities VALUES ("+dName+") ("+posOne+") ("+posTwo+") ("+negOne+") ("+negTwo+") (");
        
        //Add a deity onto the end of the file
        return true;
    }
    public boolean worship(PlayerProfile PP, Player p, String deity){
        int exists;
        deity.replaceAll("'", "`");
        exists=mcMMO.database.getInt("SELECT count(*) AS exists FROM "+ LoadProperties.MySQLtablePrefix+"deities WHERE deity = '"+deity+"'");
        if(exists>0){
            PP.setDeityName(deity);
            p.sendMessage("You are now worshipping "+deity);
            update();
            return true;
        }
        else{
            p.sendMessage("This deity does not exist");
        }
        return false;
    }
    public boolean leave(){
        
        return false;
    }
    public void printDeity(){
        
    }
    public void update(){
        
    }
}
