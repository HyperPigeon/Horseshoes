package net.hyper_pigeon.horseshoes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "horseshoes";
	public static final String MOD_NAME = "Horseshoes";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	private static final ResourceLocation ALLOWED_ID = ResourceLocation.fromNamespaceAndPath("horseshoes","allowed_to_wear_horseshoes");
	public static final TagKey<EntityType<?>> ALLOWED = TagKey.create(Registries.ENTITY_TYPE, ALLOWED_ID);

	public static final ResourceLocation HORSESHOE_BOOST = ResourceLocation.fromNamespaceAndPath("horseshoes", "speed_boost");
	public static final ResourceLocation HORSESHOE_ARMOR_BONUS = ResourceLocation.fromNamespaceAndPath("horseshoes","armor_bonus");
}