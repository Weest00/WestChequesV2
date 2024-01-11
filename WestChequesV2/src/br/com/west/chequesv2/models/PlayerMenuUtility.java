package br.com.west.chequesv2.models;


import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private final Player viewer;

    public PlayerMenuUtility(Player viewer) {
        this.viewer = viewer;
    }

    public Player getViewer() {
        return viewer;
    }
}
