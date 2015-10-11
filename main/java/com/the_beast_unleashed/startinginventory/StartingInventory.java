package com.the_beast_unleashed.startinginventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
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

import com.mojang.authlib.GameProfile;
import com.the_beast_unleashed.startinginventory.commands.SetStartingInventory;
import com.the_beast_unleashed.startinginventory.events.PlayerLoginHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "StartingInventory", version = "1.0.0", acceptableRemoteVersions = "*")
public class StartingInventory
{    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Create file objects for useful folders and files
        ConfigHandler.configFolder = new File(event.getModConfigurationDirectory(), "startingInventory");
        ConfigHandler.NBTInventoryFile = new File(ConfigHandler.configFolder, "inventory.dat");
    }
    
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // Register mod commands
        event.registerServerCommand(new SetStartingInventory());        
    }
    
    
    @EventHandler
    public void serverLoaded(FMLServerStartedEvent event)
    {
        // Create file objects for useful folders and files
        ConfigHandler.worldFolder = DimensionManager.getCurrentSaveRootDirectory();
        ConfigHandler.previousLoginFolder = new File(ConfigHandler.worldFolder, "startingInventory");
        
        // Register Forge event handlers
        MinecraftForge.EVENT_BUS.register(new PlayerLoginHandler());
        
        // Load starting inventory from the inventory.dat file if it exists
        if (ConfigHandler.NBTInventoryFile.exists())
        {
            InputStream input;
            NBTTagCompound nbtCompoundToRead;
            
            try
            {
                // Read the NBT tag compound from the inventory.dat file
                input = new FileInputStream(ConfigHandler.NBTInventoryFile);
                nbtCompoundToRead = CompressedStreamTools.readCompressed(input);
                
                // Get the NBTTagList which describes the InventoryPlayer object
                // Use the ID for NBTTagCompound, as this is the data type that the TagList contains
                ConfigHandler.nbtStartingInventory = nbtCompoundToRead.getTagList("Inventory", nbtCompoundToRead.getId());
                
                input.close();
            }

            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
