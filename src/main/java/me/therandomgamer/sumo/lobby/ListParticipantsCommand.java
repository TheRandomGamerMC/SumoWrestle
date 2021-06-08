package me.therandomgamer.sumo.lobby;

import me.therandomgamer.sumo.AnnotationHandler;
import me.therandomgamer.sumo.Manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListParticipantsCommand implements CommandExecutor {


    @Override
    @AnnotationHandler.Command(subCommand = "listparticipants")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.AQUA+"Current participants are: ");
        StringBuilder msg = new StringBuilder();
        for(Player p : GameManager.getInstance().getParticipants()){
            msg.append(p.getDisplayName());
            msg.append(", ");
        }

        if(msg.length() > 2){
            msg.delete(msg.length()-2,msg.length());
        }
        sender.sendMessage(ChatColor.AQUA+ msg.toString());
        return true;
    }
}
