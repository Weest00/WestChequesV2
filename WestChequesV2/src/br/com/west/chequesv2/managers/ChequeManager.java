package br.com.west.chequesv2.managers;

import br.com.west.chequesv2.Main;
import br.com.west.chequesv2.enums.ChequeType;
import br.com.west.chequesv2.models.PlayerMenuUtility;
import com.nextplugins.cash.NextCash;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class ChequeManager {

    private final HashMap<Player, PlayerMenuUtility> playerMenuUtility = new HashMap<>();
    private final HashMap<String, ChequeType> chequeType = new HashMap<>();

    public HashMap<Player, PlayerMenuUtility> getPlayerMenuUtility() {
        return playerMenuUtility;
    }

    public HashMap<String, ChequeType> getChequeType() {
        return chequeType;
    }


    public String getPlayerFormattedCoins(Player player) {
        return Main.getInstance().format(Main.getInstance().getEconomy().getBalance(player));
    }

    public String getPlayerFormattedCash(Player player) {
        return Main.getInstance().format(NextCash.getInstance().getAccountStorage().findAccount(player).getBalance());
    }

    public PlayerMenuUtility getPlayerMenuUtility(Player viewer) {
        PlayerMenuUtility playerMenuUtility;
        if (!(getPlayerMenuUtility().containsKey(viewer))) {

            playerMenuUtility = new PlayerMenuUtility(viewer);
            getPlayerMenuUtility().put(viewer, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return getPlayerMenuUtility().get(viewer);
        }
    }
}
