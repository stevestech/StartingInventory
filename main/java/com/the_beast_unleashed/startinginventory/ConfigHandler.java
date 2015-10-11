package com.the_beast_unleashed.startinginventory;

import java.io.File;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagList;

public class ConfigHandler
{
    public static File configFolder;
    public static File worldFolder;
    
    public static File NBTInventoryFile;
    public static File previousLoginFolder;
    
    public static NBTTagList nbtStartingInventory;
}
