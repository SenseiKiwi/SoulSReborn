package SoulSReborn.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SoulCageRender implements ISimpleBlockRenderingHandler
{
	public static int model = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.0D, 0.8125D, 0.0D, 0.1875D, 1.0D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.8174999952316284D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.1875D, 0.8125D, 0.0D, 0.8125D, 1.0D, 0.1875D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);

	    renderer.setRenderBounds(0.0D, 0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.8175D, 0.1875D, 0.0D, 1.0D, 0.8125D, 0.1875D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.0D, 0.1875D, 0.8175D, 0.1875D, 0.8125D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.8174999952316284D, 0.1875D, 0.8175D, 1.0D, 0.8125D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);

	    renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.1875D, 0.1875D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.8174999952316284D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.1875D, 0.0D, 0.0D, 0.8125D, 0.1875D, 0.1875D);
	    renderStandardInvBlock(renderer, block, metadata);
	    renderer.setRenderBounds(0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D, 1.0D);
	    renderStandardInvBlock(renderer, block, metadata);

	    if (metadata > 0)
	    {
	      renderer.setRenderBounds(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);
	      renderStandardInvBlock(renderer, block, metadata);
	    }
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		renderer.setRenderBounds(0.0D, 0.8125D, 0.0D, 0.1875D, 1.0D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.8174999952316284D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.1875D, 0.8125D, 0.0D, 0.8125D, 1.0D, 0.1875D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);

	    renderer.setRenderBounds(0.0D, 0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.8175D, 0.1875D, 0.0D, 1.0D, 0.8125D, 0.1875D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.0D, 0.1875D, 0.8175D, 0.1875D, 0.8125D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.8174999952316284D, 0.1875D, 0.8175D, 1.0D, 0.8125D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);

	    renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.1875D, 0.1875D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.8174999952316284D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.1875D, 0.0D, 0.0D, 0.8125D, 0.1875D, 0.1875D);
	    renderer.renderStandardBlock(block, x, y, z);
	    renderer.setRenderBounds(0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D, 1.0D);
	    renderer.renderStandardBlock(block, x, y, z);

	    if (world.getBlockMetadata(x, y, z) > 0)
	    {
	      renderer.setRenderBounds(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);
	      renderer.renderStandardBlock(block, x, y, z);
	    }

	    return true;
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return model;
	}
	
	public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, 1.0F);
	    renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
	    tessellator.draw();
	    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	 }
}