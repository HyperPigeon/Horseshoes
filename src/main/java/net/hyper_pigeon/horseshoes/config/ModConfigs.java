package net.hyper_pigeon.horseshoes.config;

import com.mojang.datafixers.util.Pair;
import net.hyper_pigeon.horseshoes.Horseshoes;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static float IRON_HORSESHOE_SPEED;
    public static float IRON_HORSESHOE_ARMOR;

    public static float GOLD_HORSESHOE_SPEED;
    public static float GOLD_HORSESHOE_ARMOR;

    public static float DIAMOND_HORSESHOE_SPEED;
    public static float DIAMOND_HORSESHOE_ARMOR;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        //create the config file
        CONFIG = SimpleConfig.of(Horseshoes.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("iron_horseshoe_speed", 0.1F), "Iron Horseshoe Speed Modifier (float)");
        configs.addKeyValuePair(new Pair<>("iron_horseshoe_armor", 3.5F), "Iron Horseshoe Armor Modifier (float)");

        configs.addKeyValuePair(new Pair<>("gold_horseshoe_speed", 0.2F), "Golden Horseshoe Speed Modifier (float)");
        configs.addKeyValuePair(new Pair<>("gold_horseshoe_armor", 2.5F), "Golden Horseshoe Armor Modifier (float)");

        configs.addKeyValuePair(new Pair<>("diamond_horseshoe_speed", 0.15F), "Diamond Horseshoe Speed Modifier (float)");
        configs.addKeyValuePair(new Pair<>("diamond_horseshoe_armor", 5.5F), "Diamond Horseshoe Armor Modifier (float)");
    }

    //Assign the values from the config file to the variables. Default values are used if the config file is missing
    private static void assignConfigs() {

        IRON_HORSESHOE_SPEED = (float) CONFIG.getOrDefault("iron_horseshoe_speed", 0.1F);
        IRON_HORSESHOE_ARMOR = (float) CONFIG.getOrDefault("iron_horseshoe_armor", 3.5F);

        GOLD_HORSESHOE_SPEED = (float) CONFIG.getOrDefault("gold_horseshoe_speed", 0.2F);
        GOLD_HORSESHOE_ARMOR = (float) CONFIG.getOrDefault("gold_horseshoe_armor", 2.5F);

        DIAMOND_HORSESHOE_SPEED = (float) CONFIG.getOrDefault("diamond_horseshoe_speed", 0.15F);
        DIAMOND_HORSESHOE_ARMOR = (float) CONFIG.getOrDefault("diamond_horseshoe_armor", 5.5F);

        System.out.println("All " + configs.getConfigsList().size() + " configs have been set properly");
    }
}
