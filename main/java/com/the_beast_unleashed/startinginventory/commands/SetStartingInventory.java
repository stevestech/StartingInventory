package com.the_beast_unleashed.startinginventory.commands;

import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.mojang.authlib.GameProfile;
import com.the_beast_unleashed.startinginventory.ConfigHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class SetStartingInventory implements ICommand {

	@Override
	public int compareTo(Object arg0)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "setStartingInventory";
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender)
	{
		return "Uses your current inventory to configure the StartingInventory mod. New players will receive these items.";
	}

	@Override
	public List getCommandAliases()
	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] parameters)
	{
		if (commandSender instanceof EntityPlayerMP)
		{
			// Get inventory of the player who used this command
			InventoryPlayer inventory = ((EntityPlayerMP) commandSender).inventory; 
			
			// Get the inventory object as an NBT tag list object
			ConfigHandler.nbtStartingInventory = new NBTTagList();
			inventory.writeToNBT(ConfigHandler.nbtStartingInventory);
			
			// Only NBTTagCompounds can be written to disk apparently, so make one containing the NBTTagList
			NBTTagCompound nbtCompoundToWrite = new NBTTagCompound();
			nbtCompoundToWrite.setTag("Inventory", ConfigHandler.nbtStartingInventory);
			
			// Write the NBT tags to inventory.dat
			OutputStream output;
			
			try
			{
				output = new FileOutputStream(ConfigHandler.NBTInventoryFile);
				CompressedStreamTools.writeCompressed(nbtCompoundToWrite, output);
				output.close();
				
				// Notify user that the command worked successfully.
				commandSender.addChatMessage(new ChatComponentText("You have successfully set the starting inventory template."));
			}
			
			catch (FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
			
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender)
	{
		GameProfile gameProfile = ((EntityPlayerMP) commandSender).getGameProfile();
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(gameProfile);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return false;
	}
}
