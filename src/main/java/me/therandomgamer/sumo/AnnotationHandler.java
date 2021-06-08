package me.therandomgamer.sumo;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


public class AnnotationHandler implements CommandExecutor {


    private JavaPlugin main;
    private HashMap<String, CommandTuple> commandHandlers;
    private String baseCommand;

    public AnnotationHandler(JavaPlugin main, String baseCommand) {
        this.main = main;
        this.commandHandlers = new HashMap<>();
        this.baseCommand = baseCommand;
    }


    void registerListeners() {

        Reflections reflections = new Reflections(main);
        Set<Class<? extends org.bukkit.event.Listener>> classes = reflections.getSubTypesOf(org.bukkit.event.Listener.class);

        for (Class c : classes) {
            if (org.bukkit.event.Listener.class.isAssignableFrom(c)) {
                System.out.println("[SumoWrestle] "+c.getCanonicalName() + " registered");
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
    public @interface Command {
        String subCommand();
    }


    void registerCommands() {
        Reflections reflections = new Reflections(main, new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Command.class);
        for (Method m : methods) {
            String subCommand = m.getAnnotation(Command.class).subCommand();
            try {
                commandHandlers.put(subCommand,new CommandTuple(m.getDeclaringClass().newInstance(),m));
                System.out.println("[SumoWrestle] /sumo "+subCommand+ " registered");

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
            main.getCommand(baseCommand).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(commandHandlers.containsKey(args[0])){
            CommandTuple ct = commandHandlers.get(args[0]);
            Object[] argsToPass = new Object[ct.getMethodToInvoke().getParameterCount()];
            Class[] params = ct.getMethodToInvoke().getParameterTypes();
            for(int i = 0; i< ct.getMethodToInvoke().getParameterCount() ; i++){
                if(CommandSender.class.isAssignableFrom(params[i])){
                    argsToPass[i] = sender;
                }else if(org.bukkit.command.Command.class.isAssignableFrom(params[i])){
                    argsToPass[i] = command;
                }else if(String.class.isAssignableFrom(params[i])){
                    argsToPass[i] = label;
                }else if(String[].class.isAssignableFrom(params[i])){
                    argsToPass[i] = Arrays.copyOfRange(args,1,args.length);
                }else{
                    System.out.println("WARNING: found a not used parameter in a commandhandler "+params[i].toString() );
                }
            }

            try {
                return (Boolean) ct.getMethodToInvoke().invoke(ct.getInvokeObject(),argsToPass);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        sender.sendMessage(ChatColor.RED+"This subcommand does not exist, try /"+baseCommand+" help for a list of available subcommands");
        return true;
    }
}

class CommandTuple{

    private Object invokeObject;
    private Method methodToInvoke;

    public CommandTuple(Object invokeObject, Method methodToInvoke){
        this.invokeObject = invokeObject;
        this.methodToInvoke = methodToInvoke;
    }

    public Object getInvokeObject() {
        return invokeObject;
    }

    public void setInvokeObject(Object invokeObject) {
        this.invokeObject = invokeObject;
    }

    public Method getMethodToInvoke() {
        return methodToInvoke;
    }

    public void setMethodToInvoke(Method methodToInvoke) {
        this.methodToInvoke = methodToInvoke;
    }
}
