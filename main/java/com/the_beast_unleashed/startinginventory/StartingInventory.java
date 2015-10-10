package com.the_beast_unleashed.startinginventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import com.the_beast_unleashed.startinginventory.events.PlayerLoginHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "StartingInventory", version = "1.0.0", acceptableRemoteVersions = "*")
public class StartingInventory
{	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Create file objects for useful folders and files
		ConfigHandler.configFolder = new File(event.getModConfigurationDirectory(), "startingInventory");
		ConfigHandler.worldFolder = DimensionManager.getCurrentSaveRootDirectory();
		
		ConfigHandler.serialisedInventoryFile = new File(ConfigHandler.configFolder, "items.dat");
		ConfigHandler.previousLoginFolder = new File(ConfigHandler.worldFolder, "startingInventory");
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	// Register Forge event handlers
		MinecraftForge.EVENT_BUS.register(new PlayerLoginHandler());
		
		// Load starting inventory from the items.dat file if it exists, by deserialising with the
		// ObjectInputStream utility
		if (ConfigHandler.serialisedInventoryFile.exists())
		{
			// Load InventoryPlayer by deserialising the items.dat file
			InputStream file;
			InputStream buffer;
			ObjectInput input;
			
			try
			{
				file = new FileInputStream(ConfigHandler.serialisedInventoryFile);
				buffer = new BufferedInputStream(file);
				input = new ObjectInputStream(buffer);
				
				ConfigHandler.startingInventory = (InventoryPlayer) input.readObject();
				
				input.close();
				buffer.close();
				file.close();
			}
			
			catch(ClassNotFoundException ex)
			{
				ex.printStackTrace();
			}
			
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
    }
}
