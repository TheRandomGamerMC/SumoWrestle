package me.therandomgamer.sumo;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.annotation.*;
import java.util.Set;


public class AnnotationHandler {


    private JavaPlugin main;

    public AnnotationHandler(JavaPlugin main) {
        this.main = main;
    }


    void registerListeners() {

        Reflections reflections = new Reflections(main);
        Set<Class<? extends org.bukkit.event.Listener>> classes = reflections.getSubTypesOf(org.bukkit.event.Listener.class);

        for (Class c : classes) {
            if (org.bukkit.event.Listener.class.isAssignableFrom(c)) {
                System.out.println(c.getCanonicalName()+" registered");
                try {
                    main.getServer().getPluginManager().registerEvents(org.bukkit.event.Listener.class.cast(c.newInstance()), main);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Command Annotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Documented
    public @interface Command {String subCommand();}


    void registerCommand(){



    }

}
