package SoulSReborn.gameObjs;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import SoulSReborn.configs.SoulConfig;
import SoulSReborn.creativeTab.SoulTab;
import SoulSReborn.enchant.SoulStealer;
import SoulSReborn.gameObjs.items.ItemCageBlock;
import SoulSReborn.gameObjs.items.SoulShard;
import SoulSReborn.utils.DynamicIds;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ObjHandler 
{
	public static SoulShard soulShard;
	public static SoulCage soulCage;
	public static SoulTab soulTab;
	public static final Enchantment soulStealer = new SoulStealer(SoulConfig.soulStealerID, SoulConfig.soulStealerWeight);
	public static String[] itemNames = {"Unbound Soul Shard", "Soul Shard", "Soul Shard", "Soul Shard", "Soul Shard", "Soul Shard", "Soul Shard"};
	public static String[] blockNames = {"Empty Soul Cage", "Inactive Soul Cage", "Active Soul Cage"};
	
	public static void init()
	{
		soulTab = new SoulTab("Soul Shards");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Soul Shards", "Soul Shards");
		
		if (SoulConfig.autoID)
		{
			soulShard = new SoulShard(DynamicIds.Items());
			soulCage = new SoulCage(DynamicIds.Blocks());
		}
		else
		{
			soulShard = new SoulShard(SoulConfig.soulShardID - 256);
			soulCage = new SoulCage(SoulConfig.soulCageID);
		}
		
		GameRegistry.registerItem(soulShard, "SoulShard", "SSR");
		GameRegistry.registerBlock(soulCage, ItemCageBlock.class, soulCage.getUnlocalizedName().substring(5));
		TileEntity.addMapping(CageTile.class, "soulCage");
		
		for (int i = 0; i < itemNames.length; i++)
			LanguageRegistry.addName(new ItemStack (soulShard, 1, i), itemNames[i]);
		for (int i = 0; i < blockNames.length; i++)
			LanguageRegistry.addName(new ItemStack (soulCage, 1, i), blockNames[i]);
	
		soulStealer.setName("Soul Stealer");
		
		GameRegistry.addShapedRecipe(new ItemStack(soulCage), "iii", "i i", "iii", 'i', Block.fenceIron);	
	}
}
