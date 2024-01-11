package br.com.west.chequesv2.commands;

import br.com.west.chequesv2.Main;
import br.com.west.chequesv2.managers.ChequeManager;
import br.com.west.chequesv2.menus.ChequeMenu;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChequeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        ChequeManager manager = Main.getInstance().getChequeManager();

        new ChequeMenu(manager.getPlayerMenuUtility(player)).open();
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
        return false;
    }
}
