package me.therandomgamer.sumo.lobby;

import me.therandomgamer.sumo.AnnotationHandler;
import me.therandomgamer.sumo.Manager.GameManager;
import me.therandomgamer.sumo.Manager.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

    @Override
    @AnnotationHandler.Command(subCommand = "join")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player senderPlayer = (Player) sender;
            if(GameManager.getInstance().getState() != GameState.LOBBY){
                senderPlayer.sendMessage(ChatColor.RED+"Game is already in progress");
                return true;
            }

            if(! GameManager.getInstance().isParticipant(senderPlayer)){
                GameManager.getInstance().addParticipant(senderPlayer);
                senderPlayer.sendMessage(ChatColor.AQUA+"You have joined the game");
            }else{
                sender.sendMessage(ChatColor.YELLOW+"You already joined the game");
            }
        }
        return true;
    }
}
