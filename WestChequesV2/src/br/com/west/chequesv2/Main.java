package br.com.west.chequesv2;

import br.com.west.chequesv2.managers.ChequeManager;
import br.com.west.chequesv2.utils.ClassGeter;
import com.nextplugins.cash.NextCash;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Economy economy = null;
    private ChequeManager chequeManager;
    private static Main instance;

    public void onEnable() {
        instance = this;
        chequeManager = new ChequeManager();
        ClassGeter classGeter = new ClassGeter();
        classGeter.setupCommands();
        classGeter.setupListeners();

        if (!setupEconomy()) {
            Bukkit.getLogger().warning("Vault não encontrado, desligando o plugin!");
            getPluginLoader().disablePlugin(this);
        }

        if(!NextCash.getInstance().isEnabled()){
            Bukkit.getLogger().warning("NextCash não encontrado, desligando o plugin!");
            getPluginLoader().disablePlugin(this);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public String format(Double value) {
        String[] suffix = {
                "K", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N",
                "D", "UN", "DD",
                "TR", "QT", "QN", "SD", "SSD", "OD", "ND",
                "VG", "UVG", "DVG", "TVG", "QVG", "QVN", "SEV", "SPV", "OVG",
                "NVG",
                "TG"};
        int size = (value.intValue() != 0) ? (int) Math.log10(value) : 0;
        if (size >= 3)
            while (size % 3 != 0)
                size--;
        double notation = Math.pow(10.0D, size);
        return (size >= 3) ? (
                String.valueOf(Math.round(value / notation * 100.0D) / 100.0D) + suffix[size / 3 - 1]) : String.valueOf(value.doubleValue());
    }

    public void sendTitle(Player player, String title, String subtitle) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = (craftplayer.getHandle()).playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON,
                1, 1, 1);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

    public Economy getEconomy() {
        return economy;
    }

    public ChequeManager getChequeManager() {
        return chequeManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
