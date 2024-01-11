package br.com.west.chequesv2.listeners;

import br.com.west.chequesv2.Main;
import com.nextplugins.cash.NextCash;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK) &&
                player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {

            ItemStack nmsItem = CraftItemStack.asNMSCopy(player.getItemInHand());

            if (nmsItem.hasTag() && nmsItem.getTag().hasKey("cash")) {
                double amount = nmsItem.getTag().getDouble("cash");
                int itemAmount = player.isSneaking() ? player.getItemInHand().getAmount() : 1;

                if (player.getItemInHand().getAmount() > itemAmount) {
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - itemAmount);
                } else {
                    player.setItemInHand(null);
                }

                NextCash.getInstance().getAccountStorage().findAccount(player).depositAmount(amount * itemAmount);
                Main.getInstance().sendTitle(player, "§6§lCHEQUE DE CASH!", "§fVocê acabou de ativar §6" + itemAmount + "§f cheque(s) de cash dando um total de §6✪" + Main.getInstance().format(amount * itemAmount) + "§f.");
                player.sendMessage("§aVocê ativou §f" + itemAmount + "§a cheque(s) de cash.");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }

            if (nmsItem.hasTag() && nmsItem.getTag().hasKey("coins")) {
                double amount = nmsItem.getTag().getDouble("coins");
                int itemAmount = player.isSneaking() ? player.getItemInHand().getAmount() : 1;

                if (player.getItemInHand().getAmount() > itemAmount) {
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - itemAmount);
                } else {
                    player.setItemInHand(null);
                }
                Main.getInstance().getEconomy().depositPlayer(player, amount * itemAmount);
                Main.getInstance().sendTitle(player, "§a§lCHEQUE DE COINS!", "§fVocê acabou de ativar §a" + itemAmount + "§f cheque(s) de coins dando um total de §2$§a" + Main.getInstance().format(amount * itemAmount) + "§f.");
                player.sendMessage("§aVocê ativou §f" + itemAmount + "§a cheque(s) de coins.");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);


            }

        }
    }
}
