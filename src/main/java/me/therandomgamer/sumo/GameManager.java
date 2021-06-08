package me.therandomgamer.sumo;

public class GameManager {

    private static GameManager _instance = null;
    private GameState state;

    private GameManager(){
        state = GameState.LOBBY;
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
}

enum GameState{
    LOBBY,
}