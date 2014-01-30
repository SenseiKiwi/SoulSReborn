package SoulSReborn.proxies;

import SoulSReborn.render.SoulCageRender;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void initRenderers()
	{
		RenderingRegistry.registerBlockHandler(new SoulCageRender());
	}
	
	@Override
	public void initSounds()
	{
		
	}
}
