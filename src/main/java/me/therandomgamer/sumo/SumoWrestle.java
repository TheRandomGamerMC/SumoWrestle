package me.therandomgamer.sumo;

import org.bukkit.plugin.java.JavaPlugin;

public class SumoWrestle extends JavaPlugin {


    @Override
    public void onEnable() {
        super.onEnable();
        AnnotationHandler annotationHandler = new AnnotationHandler(this, "sumo");
        annotationHandler.registerListeners();
        annotationHandler.registerCommands();
    }
}
