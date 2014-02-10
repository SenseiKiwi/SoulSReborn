package SoulSReborn.gameObjs;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulCage extends BlockMobSpawner
{
	@SideOnly(Side.CLIENT)
	public Icon[] icons;
	
	public SoulCage(int id) 
	{
		super(id);
		this.setUnlocalizedName("SoulCage");
		this.setCreativeTab(ObjHandler.soulTab);
		this.blockHardness = 3.0F;
		this.blockResistance = 3.0F;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		if (!world.isRemote)
		{
			if (world.getBlockMetadata(x, y, z) != 0)
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			if (player.getHeldItem() != null)
			{
				CageTile tile = (CageTile) world.getBlockTileEntity(x, y, z);
				if (player.getHeldItem().getItem() == ObjHandler.soulShard && tile != null)
				{
					ItemStack stack = player.getHeldItem();
					if (stack.hasTagCompound())
					{
						int tier = stack.stackTagCompound.getInteger("Tier");
						String ent = stack.stackTagCompound.getString("EntityType");
						String entId = stack.stackTagCompound.getString("entId");
						if (tier > 0 && !ent.equals("empty") && !entId.equals("empty") && tile.tier == 0)
						{
							tile.entName = ent;
							tile.entId = entId;
							tile.tier = tier;
							if (!player.capabilities.isCreativeMode)
								stack.stackSize--;
							world.setBlockMetadataWithNotify(x, y, z, 1, 2);
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) 
	{
		CageTile tile = (CageTile) world.getBlockTileEntity(x, y, z);
		if (tile != null && tile.tier == 5)
		{
			if (world.isBlockIndirectlyGettingPowered(x, y, z))
				tile.isPowered = true;
			else
				tile.isPowered = false;
		}
			
	}
	
	@Override
	public void dropXpOnBlockBreak(World par1World, int par2, int par3, int par4, int par5)
	{
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new CageTile();
	}
	
	@Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }
    
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    @Override
    public int damageDropped(int par1)
    {
		return 0;  	
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.blockID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs par2CreativeTabs, List par3List)
    {
          for(int i = 0; i < 3; i++)
        	  par3List.add(new ItemStack(id, 1, i));
    }
    
    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	return icons[meta];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
      String[] textureNames = { "soulCage", "cageUnlit", "cageLit" };
      icons = new Icon[textureNames.length];

      for (int i = 0; i < this.icons.length; i++)
    	  icons[i] = iconRegister.registerIcon(String.format("%s:%s", new Object[] { "ssr", textureNames[i]}));
    }
}
