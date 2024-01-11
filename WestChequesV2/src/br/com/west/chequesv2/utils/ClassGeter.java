package br.com.west.chequesv2.utils;


import br.com.west.chequesv2.Main;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class ClassGeter {

    public void setupListeners() {
        try {
            ImmutableSet<ClassPath.ClassInfo> infoList = ClassPath.from(Main.class.getClassLoader()).getTopLevelClassesRecursive("br.com.west.chequesv2.listeners");
            for (ClassPath.ClassInfo info : infoList) {
                Class<?> cls = Class.forName(info.getName());
                if (Listener.class.isAssignableFrom(cls)) {
                    Class<Listener> ex = (Class<Listener>) cls;
                    Bukkit.getPluginManager().registerEvents(ex.newInstance(), Main.getInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupCommands() {
        try {
            ImmutableSet<ClassPath.ClassInfo> infoList = ClassPath.from(Main.class.getClassLoader()).getTopLevelClassesRecursive("br.com.west.chequesv2.commands");
            for (ClassPath.ClassInfo info : infoList) {
                Class<?> cls = Class.forName(info.getName());
                if (CommandExecutor.class.isAssignableFrom(cls)) {
                    Class<CommandExecutor> ex = (Class<CommandExecutor>) cls;
                    Main.getInstance().getCommand(info.getSimpleName().substring(0, info.getSimpleName().length() - 7).toLowerCase()).setExecutor(ex.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
