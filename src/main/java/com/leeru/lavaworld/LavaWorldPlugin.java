package com.leeru.lavaworld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LavaWorldPlugin extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        if (this.getCommand("멸망") != null) {
            this.getCommand("멸망").setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        World world = player.getWorld();
        Location pLoc = player.getLocation();
        
        int RADIUS = 40; 
        int startX = pLoc.getBlockX() - RADIUS;
        int endX = pLoc.getBlockX() + RADIUS;
        int startZ = pLoc.getBlockZ() - RADIUS;
        int endZ = pLoc.getBlockZ() + RADIUS;

        player.sendMessage("§c[경고] 지상 공간에 빈틈없이 용암 비가 내립니다.");

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                int maxHeight = world.getMaxHeight() - 2;
                int minHeight = world.getBottomY();
                if (world.getEnvironment() == World.Environment.NETHER) {
                    maxHeight = 125;
                }
                int surfaceY = minHeight;
                for (int y = maxHeight; y >= minHeight; y--) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR && block.getType() != Material.CAVE_AIR && block.getType() != Material.VOID_AIR) {
                        surfaceY = y;
                        break;
                    }
                }
                for (int y = surfaceY + 1; y <= maxHeight; y++) {
                    Block targetBlock = world.getBlockAt(x, y, z);
                    if (targetBlock.getType() == Material.AIR || targetBlock.getType() == Material.CAVE_AIR || targetBlock.getType() == Material.VOID_AIR) {
                        targetBlock.setType(Material.LAVA, false);
                    }
                }
            }
        }
        player.sendMessage("§a[완료] 용암 배치가 완료되었습니다!");
        return true;
    }
}
