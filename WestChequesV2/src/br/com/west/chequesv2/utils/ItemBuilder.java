package br.com.west.chequesv2.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemBuilder {

    private final Material material;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    private int amount = 1;

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    private short durability = 0;

    public ItemBuilder durability(int durability) {
        this.durability = (short) durability;
        return this;
    }

    private HashMap<Enchantment, Integer> enchantments = new HashMap<>();

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    private boolean glowing = false;

    public ItemBuilder glowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    private String displayName;

    public ItemBuilder name(String displayName) {
        this.displayName = displayName;
        return this;
    }

    private String owner;

    public ItemBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    private List<String> lore;

    public ItemBuilder lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    private ItemFlag[] flags;

    public ItemBuilder flags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, durability);
        if (glowing && enchantments.size() == 0) enchantments.put(Enchantment.LUCK, 1);
        if (enchantments.size() > 0) item.addUnsafeEnchantments(enchantments);
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof SkullMeta) ((SkullMeta) meta).setOwner(owner);
        if (displayName != null) meta.setDisplayName(displayName);
        if (lore != null) meta.setLore(lore);
        if (glowing && flags == null) flags = new ItemFlag[]{ItemFlag.HIDE_ENCHANTS};
        if (flags != null) meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }
}
