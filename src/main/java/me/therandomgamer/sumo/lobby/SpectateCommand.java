package me.therandomgamer.sumo.lobby;

import me.therandomgamer.sumo.AnnotationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpectateCommand implements CommandExecutor {


    @Override
    @AnnotationHandler.Command(subCommand = "spectate")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
