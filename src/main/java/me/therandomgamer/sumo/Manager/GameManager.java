package me.therandomgamer.sumo.Manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager _instance = null;
    private GameState state;
    private List<Player> participants;

    private GameManager(){
        state = GameState.LOBBY;
        participants = new ArrayList<>();
    }


    //Singleton handler to create the initial instance
    private synchronized static void createInstance(){
        if(_instance == null){
            _instance = new GameManager();
        }
    }

    // gets the only instance of this manager
    public static GameManager getInstance(){
        if(_instance == null){
            createInstance();
        }
        return _instance;
    }


    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }


    public void addParticipant(Player p){
        participants.add(p);
    }
    public void removeParticipant(Player p){
        participants.remove(p);
    }
    public boolean isParticipant(Player p){
        return participants.contains(p);
    }

    public List<Player> getParticipants() {
        return participants;
    }
}