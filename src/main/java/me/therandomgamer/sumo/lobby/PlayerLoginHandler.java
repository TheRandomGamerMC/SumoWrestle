package me.therandomgamer.sumo.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.therandomgamer.sumo.AnnotationHandler;

public class PlayerLoginHandler implements Listener {

    @EventHandler
    public void onPlayerLogIn(PlayerJoinEvent e){
        System.out.println(e.getPlayer().getName()+" has JOINED YEYA");
    }

}
