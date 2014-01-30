package SoulSReborn.utils;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**NOT YET IMPLEMENTED**/
public class SoulPacket implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, 
			Packet250CustomPayload packet, Player player) 
	{
		if (packet.channel.equals("SoulShardsReborn"))
		{
			
		}
	}
}
