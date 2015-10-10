package com.the_beast_unleashed.startinginventory.commands;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class SetStartingInventory implements ICommand {

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "setStartingInventory";
	}

	@Override
	public String getCommandUsage(ICommandSender commandSender) {
		return "Uses your current inventory to configure the StartingInventory mod. New players will receive these items.";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] parameters) {
		 		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		// TODO Auto-generated method stub
		MinecraftServer.getServer().getConfigurationManager().get
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		// TODO Auto-generated method stub
		return false;
	}

}
