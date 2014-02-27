package SoulSReborn.gameObjs.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCageBlock extends ItemBlock 
{

	public ItemCageBlock(int par1) 
	{
		super(par1);
		this.setHasSubtypes(true);
	}
	
	 public String getUnlocalizedName(ItemStack stack)
     {
           String name = "";
           switch(stack.getItemDamage())
           {
                  case 0:
                      name = "Empty Soul Cage";
                      break;
                  case 1:
                	  name = "Inactive Soul Cage";
                      break;
                  case 2:
                	  name = "Active Soul Cage";
                	  break;
           }
           return getUnlocalizedName() + "." + name;
     }
    
     public int getMetadata(int par1)
     {
           return par1;
     }

}
