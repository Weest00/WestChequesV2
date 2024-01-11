package br.com.west.chequesv2.listeners;

import br.com.west.chequesv2.Main;
import br.com.west.chequesv2.enums.ChequeType;
import br.com.west.chequesv2.managers.ChequeManager;
import br.com.west.chequesv2.utils.ChequeFactory;
import com.nextplugins.cash.NextCash;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message = event.getMessage();
        ChequeManager manager = Main.getInstance().getChequeManager();
        ChequeType chequeType = manager.getChequeType().get(playerName);

        if (chequeType == null) return;
        event.setCancelled(true);

        if (message.equalsIgnoreCase("cancelar")) {
            player.sendMessage("§cOperação cancelada com sucesso.");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);

            manager.getChequeType().remove(playerName);
            return;
        }

        if (!isNumber(message)) {
            player.sendMessage("§cNão foi possível identificar o número.");
            player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
            return;
        }

        double amount = Double.parseDouble(message);

        if (amount <= 0 || message.equals("NaN")) {
            player.sendMessage("§cNão foi possível identificar o número.");
            player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
            return;
        }

        switch (chequeType) {
            case CASH:
                if (isInventoryFull(player)) {
                    player.sendMessage("§cSeu inventário está cheio, libere um slot para criar o cheque.");
                    player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                    return;
                }


                if (!NextCash.getInstance().getAccountStorage().findAccount(player).hasAmount(amount)) {
                    player.sendMessage("§cVocê não possui cash suficiente para efetuar a criação do cheque.");
                    player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                    return;
                }

                NextCash.getInstance().getAccountStorage().findAccount(player).withdrawAmount(amount);
                ChequeFactory.giveCheque(player, amount, playerName, "cash", Material.GOLD_INGOT, "§6§lCHEQUE DE CASH", "§7Use este cheque para receber cash", "§7no seu saldo.", "", "§7Criado por: §f" + playerName, "§7Valor do cheque: §6✪" + Main.getInstance().format(amount), "", "§aClique para usar o cheque.");
                Main.getInstance().sendTitle(player, "§6§lCHEQUE CRIADO!", "§fCheque de cash no valor de §6✪" + Main.getInstance().format(amount) + "§f criado com sucesso.");
                player.sendMessage("§aVocê criou um cheque de cash no valor de §6✪" + Main.getInstance().format(amount) + "§a com sucesso.");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                manager.getChequeType().remove(playerName);
                break;

            case COINS:
                if (isInventoryFull(player)) {
                    player.sendMessage("§cSeu inventário está cheio, libere um slot para criar o cheque.");
                    player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                    return;
                }

                if (!Main.getInstance().getEconomy().has(player, amount)) {
                    player.sendMessage("§cVocê não possui coins suficiente para efetuar a criação do cheque.");
                    player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                    return;
                }

                Main.getInstance().getEconomy().withdrawPlayer(player, amount);
                ChequeFactory.giveCheque(player, amount, playerName, "coins", Material.QUARTZ, "§2§lCHEQUE DE COINS", "§7Use este cheque para receber coins", "§7no seu saldo.", "", "§7Criado por: §f" + playerName, "§7Valor do cheque: §2$§f" + Main.getInstance().format(amount), "", "§aClique para usar o cheque.");
                Main.getInstance().sendTitle(player, "§a§lCHEQUE CRIADO!", "§fCheque de coins no valor de §2$§a" + Main.getInstance().format(amount) + "§f criado com sucesso.");
                player.sendMessage("§aVocê criou um cheque de coins no valor de §2$§f" + Main.getInstance().format(amount) + "§a com sucesso.");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                manager.getChequeType().remove(playerName);
                break;
        }
    }

    private boolean isNumber(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    private boolean isInventoryFull(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                return false;
            }
        }
        return true;
    }

}
