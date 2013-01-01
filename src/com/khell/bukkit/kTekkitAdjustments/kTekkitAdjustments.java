package com.khell.bukkit.kTekkitAdjustments;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.server.ItemStack;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ic2.api.Ic2Recipes;

public class kTekkitAdjustments extends JavaPlugin implements Listener {
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        try {
            // NetherOres missing macerator recipes
            // https://github.com/mushroomhostage/exphc/issues/76
            Ic2Recipes.addMaceratorRecipe(new net.minecraft.server.ItemStack(135, 1, 2), new net.minecraft.server.ItemStack(net.minecraft.server.Block.GOLD_ORE, 1, 1)); // nether gold ore -> 1 x gold ore
            Ic2Recipes.addMaceratorRecipe(new net.minecraft.server.ItemStack(135, 1, 3), new net.minecraft.server.ItemStack(net.minecraft.server.Block.IRON_ORE, 1, 1)); // nether iron ore -> 1 x iron ore
        } catch (Exception e) {
            System.out.println("kTekkitAdjustments: Not adding NetherOres macerator recipes: " + e);
        }

        // Nerf NetherOres in general --- just disable maceration for now
                                        //diamond					lapis					redstone					copper					tin
        ItemStack[] netherOres = {new ItemStack(135, 1, 1), new ItemStack(135, 1, 4), new ItemStack(135, 1, 5), new ItemStack(135, 1, 6), new ItemStack(135, 1, 7)};
        List recipes = Ic2Recipes.getMaceratorRecipes();
        for (int i = 0; i < netherOres.length; i++) {
            for (Iterator iterator = recipes.iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry)iterator.next();

                if ((((ItemStack)entry.getKey()).doMaterialsMatch(netherOres[i])) && (netherOres[i].count >= ((ItemStack)entry.getKey()).count)) {
                    //found, nerf
                    entry.setValue(netherOres[i]);
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("kTekkitAdjustments")){ // If the player typed /kGlobal then do the following...
            if(!(sender instanceof Player))
                return false;

            Player player = (Player)sender;
            player.sendMessage(getDescription().getName() + ", version " + getDescription().getVersion());
            return true;
        }

        return false; 
    }
}