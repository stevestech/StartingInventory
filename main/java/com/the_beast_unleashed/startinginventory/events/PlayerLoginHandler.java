package com.the_beast_unleashed.startinginventory.events;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.the_beast_unleashed.startinginventory.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerLoginHandler
{
	@SubscribeEvent
	public void entityJoinedWorld(EntityJoinWorldEvent event)
	{
		if (isPlayer(event.entity) && isFirstLogin(event.entity))
		{
			// Give player the starting inventory
			((EntityPlayerMP) event.entity).inventory = ConfigHandler.startingInventory;
			
			// Create a file to indicate that this player has already logged into the server
			File previousLogin = new File(ConfigHandler.previousLoginFolder, event.entity.getUniqueID().toString() + ".txt");
			previousLogin.mkdirs();
			
			PrintWriter writer;
			
			try
			{
				writer = new PrintWriter(previousLogin);
				writer.println(((EntityPlayer) event.entity).getDisplayName() + " was here!");
				writer.close();
			}
			
			catch (FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	public boolean isPlayer(Entity entity)
	{
		return entity instanceof EntityPlayerMP;
	}
	
	
	public boolean isFirstLogin(Entity entity)
	{
		File previousLogin = new File(ConfigHandler.previousLoginFolder, entity.getUniqueID().toString() + ".txt");
		return !previousLogin.exists();
	}
}

//OutputStream file;
//OutputStream buffer;
//ObjectOutput output;
//
//try
//{
//	file = new FileOutputStream(previousLogin);
//	buffer = new BufferedOutputStream(file);
//	output = new ObjectOutputStream(buffer);
//	
//	
//}
