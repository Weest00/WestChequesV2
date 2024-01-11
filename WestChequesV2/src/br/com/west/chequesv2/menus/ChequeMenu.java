package br.com.west.chequesv2.menus;

import br.com.west.chequesv2.Main;
import br.com.west.chequesv2.enums.ChequeType;
import br.com.west.chequesv2.managers.ChequeManager;
import br.com.west.chequesv2.models.PlayerMenuUtility;
import br.com.west.chequesv2.utils.ItemBuilder;
import br.com.west.chequesv2.utils.Menu;
import br.com.west.chequesv2.utils.SkullFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ChequeMenu extends Menu {
    public ChequeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§8Criação de Cheques";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        switch (slot) {
            case 12:
                Main.getInstance().sendTitle(player, "§b§lCRIAR CHEQUE!", "§fDigite a quantia de coins desejada no chat.");
                Main.getInstance().getChequeManager().getChequeType().put(player.getName(), ChequeType.COINS);

                player.sendMessage("§aDigite a quantia do valor do cheque de coins. \npara cancelar a ação digite §7§ncancelar§a.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
                break;
            case 14:
                Main.getInstance().sendTitle(player, "§b§lCRIAR CHEQUE!", "§fDigite a quantia de cash desejada no chat.");
                Main.getInstance().getChequeManager().getChequeType().put(player.getName(), ChequeType.CASH);

                player.sendMessage("§aDigite a quantia do valor do cheque de cash. \npara cancelar a ação digite §7§ncancelar§a.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
                break;
            case 31:
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1f, 1f);
                break;
        }

    }

    @Override
    public void setMenuItems() {
        Player player = playerMenuUtility.getViewer();
        ChequeManager manager = Main.getInstance().getChequeManager();

        inventory.setItem(4, new ItemBuilder(Material.SKULL_ITEM).durability(3).owner(playerMenuUtility.getViewer().getName())
                .name("§bSuas informações")
                .lore("§7Veja suas informações de saldo de", "§7coins e cash que você possui atualmente.", "", "§7 Saldo de coins: §2$§f" + manager.getPlayerFormattedCoins(player), "§7 Saldo de cash: §6✪§f" + manager.getPlayerFormattedCash(player)).build());

        inventory.setItem(12, SkullFactory.set("§aCheque de Coins", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTc5YTVjOTVlZTE3YWJmZWY0NWM4ZGMyMjQxODk5NjQ5NDRkNTYwZjE5YTQ0ZjE5ZjhhNDZhZWYzZmVlNDc1NiJ9fX0=", "§7Clique para criar um cheque de coins."));

        inventory.setItem(14, SkullFactory.set("§6Cheque de Cash", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTdkMGEwMjEyZDhkMTk3ZWViNzZhZmIyM2NhMjM3Mzc4ZWFhNjQyNTQwNWM5MzkxYjZkYmE4OTg1NmNlZTM5YyJ9fX0=", "§7Clique para criar um cheque de cash."));


        inventory.setItem(31, SkullFactory.set("§bFechar Menu", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU0Y2U4MTU3ZTcxZGNkNWI2YjE2NzRhYzViZDU1NDkwNzAyMDI3YzY3NWU1Y2RjZWFjNTVkMmZiYmQ1YSJ9fX0=", "§7Clique para fechar o menu."));
    }
}
