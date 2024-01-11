package br.com.west.chequesv2.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class SkullFactory {

    public static ItemStack set(String title, String textures, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(Arrays.asList(lore));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", textures));
            field.set(meta, profile);
        } catch (Exception ignored) {
        }
        item.setItemMeta(meta);
        return item;
    }
}
