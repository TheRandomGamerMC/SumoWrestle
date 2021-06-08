package me.therandomgamer.sumo.lobby;

import me.therandomgamer.sumo.AnnotationHandler;
import me.therandomgamer.sumo.Manager.GameManager;
import me.therandomgamer.sumo.Manager.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

    @Override
    @AnnotationHandler.Command(subCommand = "leave")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player senderPlayer = (Player) sender;
            if(GameManager.getInstance().isParticipant(senderPlayer)){
                if(GameManager.getInstance().getState() == GameState.LOBBY){
                    GameManager.getInstance().removeParticipant(senderPlayer);
                    sender.sendMessage(ChatColor.YELLOW+"You have left the game");
                } else{
                    sender.sendMessage(ChatColor.YELLOW+"The game is already in progress, you can't leave now");
                }
            }else{
                senderPlayer.sendMessage(ChatColor.YELLOW+"You are not yet in the game");
            }
        }
        return true;
    }
}
