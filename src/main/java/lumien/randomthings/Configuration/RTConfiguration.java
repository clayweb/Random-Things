package lumien.randomthings.Configuration;

import lumien.randomthings.Handler.MagneticForceHandler;
import lumien.randomthings.Library.PotionIds;
import lumien.randomthings.Library.Reference;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RTConfiguration
{
	public static Configuration config;

	// Settings
	public static Property growthIndicator;
	public static Property spiritChance;
	public static Property spiritChanceSword;
	public static Property fixedBackground;
	public static Property spectreDimensionID;
	public static Property wirelessLeverRange;
	public static Property spectreImbueChance;
	public static Property fertilizedDirtGrowthModifier;
	public static Property imbueDuration;
	public static Property biomeChargeTime;

	// PotionIds
	public static Property piImbuePoison;
	public static Property piImbueExperience;
	public static Property piImbueFire;
	public static Property piImbueWither;
	public static Property piImbueWeakness;
	public static Property piImbueSpectre;

	public static void init(FMLPreInitializationEvent event)
	{
		RTConfiguration.config = new Configuration(event.getSuggestedConfigurationFile());

		// Settings
		growthIndicator = config.get("Settings", "FertilizedDirtGrowthIndicator", false, "Bonemeal particles will appear whenever fertilized dirt boosts the plant");
		spiritChance = config.get("Settings", "SpiritChance", 0.02, "The chance of a spirit spawning when you don't use a Spectre Sword (0-1)");
		spiritChanceSword = config.get("Settings", "SpiritChanceSword", 0.1, "The chance of a spirit spawning when you have a spirit binder in your inventory and kill the entity with a spectre sword. (0-1)");
		wirelessLeverRange = config.get("Settings", "WirelessLeverRange", 10, "The range of a wireless lever in blocks");
		fixedBackground = config.get("VanillaChanges", "fixedBackground", "", "If this is not empty the options background will not be random but the one specified here. This has to be the name of a block texture without the .png").setRequiresMcRestart(true);
		spectreDimensionID = config.get("Settings", "SpectreDimensionID", -1, "The Dimension ID of the spectre World. On first run and when you set this setting to -1 RandomThings will try to find a dimensionID itself").setRequiresMcRestart(true);
		spectreImbueChance = config.get("Settings", "SpectreImbueChance", 0.1, "The chance of phasing through an attack while being imbued with spectre").setMinValue(0).setMaxValue(1);
		fertilizedDirtGrowthModifier = config.get("Settings", "FertilizedDirtGrowthModifier", 3, "How often should Fertilize Dirt tick the plant above it when it's ticked itself?");
		imbueDuration = config.get("Settings", "ImbueDuration", 20 * 60 * 5, "The duration of imbues (in ticks)");
		biomeChargeTime = config.get("Settings", "BiomeChargeTime", 20, "The amount of ticks the biome capsule needs to collect 1 charge");

		// PotionIds
		ConfigCategory c = config.getCategory("potionids");
		c.setComment("Setting these to -1 will auto resolve them. If RandomThings finds a potion at the position of the specified id it will try to find a free one. It will also dynamically increase the size of the potion array if necessary.");
		piImbuePoison = getPotionID("ImbuePoison");
		piImbueExperience = getPotionID("ImbueExperience");
		piImbueFire = getPotionID("ImbueFire");
		piImbueWither = getPotionID("ImbueWither");
		piImbueWeakness = getPotionID("ImbueWeakness");
		piImbueSpectre = getPotionID("ImbueSpectre");
	}

	public static void onConfigChange(OnConfigChangedEvent event)
	{
		if (event.modID.equals(Reference.MOD_ID))
			syncConfig();
	}

	public static void syncConfig()
	{
		// Blocks
		ConfigBlocks.playerInterface = isBlockEnabled("PlayerInterface");
		ConfigBlocks.fluidDisplay = isBlockEnabled("FluidDisplay");
		ConfigBlocks.fertilizedDirt = ConfigBlocks.fertilizedDirtTilled = isBlockEnabled("FertilizedDirt");
		ConfigBlocks.itemCollector = isBlockEnabled("ItemCollector");
		ConfigBlocks.onlineDetector = isBlockEnabled("OnlineDetector");
		ConfigBlocks.moonSensor = isBlockEnabled("MoonSensor");
		ConfigBlocks.notificationInterface = isBlockEnabled("NotificationInterface");
		ConfigBlocks.lapisLamp = isBlockEnabled("LapisLamp");
		ConfigBlocks.carpentryBench = isBlockEnabled("CarpentryBench");
		ConfigBlocks.dyeingMachine = isBlockEnabled("DyeingMachine");
		ConfigBlocks.imbuingStation = isBlockEnabled("ImbuingStation");
		ConfigBlocks.customWorkbench = isBlockEnabled("CustomWorkbench");

		// Items
		ConfigItems.biomeCapsule = isItemEnabled("BiomeSolution");
		ConfigItems.biomePainter = isItemEnabled("BiomePainter");
		ConfigItems.whitestone = isItemEnabled("Whitestone");
		ConfigItems.magneticForce = isItemEnabled("MagneticForce");
		ConfigItems.voidStone = isItemEnabled("VoidStone");
		ConfigItems.dropFilter = isItemEnabled("DropFilter");
		ConfigItems.enderLetter = isItemEnabled("EnderLetter");
		ConfigItems.spectreKey = isItemEnabled("SpectreKey");
		ConfigItems.soundRecorder = isItemEnabled("SoundRecorder");
		ConfigItems.spectreArmor = isItemEnabled("SpectreArmor");
		ConfigItems.spectreSword = isItemEnabled("SpectreSword");
		ConfigItems.spiritBinder = isItemEnabled("SpiritBinder");
		ConfigItems.creativeSword = isItemEnabled("CreativeSword");
		ConfigItems.creativeGrower = isItemEnabled("CreativeGrower");
		ConfigItems.creativeChestGenerator = isItemEnabled("CreativeChestGenerator");
		ConfigItems.imbue = isItemEnabled("Imbues");
		ConfigItems.demorpher = isItemEnabled("Demorpher");

		// Dungeon Loot
		ConfigDungeonLoot.WHITESTONE_CHANCE = config.get("DungeonLoot", "WhiteStone", 3).getInt();

		// Vanilla Changes
		VanillaChanges.MODIFIED_BACKGROUND = config.get("VanillaChanges", "ModifiedBackgrounds", true, "The normal dirt background will be replaced with a different block each start").setRequiresMcRestart(true).getBoolean(true);
		VanillaChanges.LOCKED_GAMMA = config.get("VanillaChanges", "LockedGamma", false, "Locks the Gamma to 0").setRequiresMcRestart(true).getBoolean(false);
		VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges", "FasterLeaveDecay", true, "Leaves will decay much faster when no longer connected to a log").getBoolean(true);
		VanillaChanges.HARDCORE_DARKNESS = config.get("VanillaChanges", "Hardcore Darkness", false, "The minimum light will be removed so if there's no light source it's actually going to be completely black").setRequiresMcRestart(true).getBoolean(false);
		VanillaChanges.THROWABLES_MOTION = config.get("VanillaChanges", "ThrowableMotion", false, "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(false);

		// Settings
		Settings.FERTILIZEDDIRT_GROWTHINDICATOR = growthIndicator.getBoolean(false);
		Settings.SPIRIT_CHANCE = spiritChance.getDouble(0.02);
		Settings.SPIRIT_CHANCE_SWORD = spiritChanceSword.getDouble(0.1);
		Settings.WIRELESSLEVER_RANGE = wirelessLeverRange.getDouble(10);
		Settings.FIXED_BACKGROUND = fixedBackground.getString();
		Settings.SPECTRE_DIMENSON_ID = spectreDimensionID.getInt();
		Settings.SPECTRE_IMBUE_CHANCE = spectreImbueChance.getDouble();
		Settings.FERTILIZED_DIRT_GROWTH = fertilizedDirtGrowthModifier.getInt();
		Settings.IMBUE_DURATION = imbueDuration.getInt();
		Settings.BIOME_CHARGE_TIME = biomeChargeTime.getInt();

		MagneticForceHandler.TELEPORT_LENGTH = RTConfiguration.config.get("Settings", "MagneticForceTeleportLength", 200, "In ticks (20=1 Second)").getInt();

		if (RTConfiguration.config.hasChanged())
			RTConfiguration.config.save();
	}

	private static boolean isBlockEnabled(String block)
	{
		return RTConfiguration.config.get("Blocks", block, true).getBoolean(true);
	}

	private static boolean isItemEnabled(String item)
	{
		return RTConfiguration.config.get("Items", item, true).getBoolean(true);
	}

	private static Property getPotionID(String name)
	{
		return config.get("PotionIds", name, -1);
	}

	public static void save()
	{
		config.save();
	}
}
