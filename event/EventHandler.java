package SoulSReborn.event;

import net.minecraftforge.common.MinecraftForge;

public class EventHandler 
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new PlayerKillEvent());
		MinecraftForge.EVENT_BUS.register(new BreakSoulCage());
		MinecraftForge.EVENT_BUS.register(new CreateShardEvent());
		MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
		MinecraftForge.EVENT_BUS.register(new ConsumeSpawnerEvent());
	}
}
