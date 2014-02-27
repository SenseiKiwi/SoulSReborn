package SoulSReborn.utils;

import java.util.logging.Level;

import SoulSReborn.configs.SoulConfig;

public class TierHandling
{
	private final static int[] defaultMin = {0,64,128,256,512,1024};
	private final static int[] defaultMax = {63,127,255,511,1023,1024};
	private static int[] min = new int[defaultMin.length];
	private static int[] max = new int[defaultMax.length];
	
	public static void init()
	{
		boolean fault = false;
		min[0] = 0;
		
		for (int i = 1; i < min.length; i++)
		{
			min[i] = SoulConfig.killReq[i - 1];
			if (min[i] <= min[i - 1])
			{
				fault = true;
				break;
			}
		}
		
		if (!fault)
			for (int i = 0; i < max.length; i++)
			{
				if (i == max.length - 1)
					max[i] = min[i];
				else max[i] = min[i + 1] - 1;
				
			}
		else
			for (int i = 0; i < min.length; i++)
			{
				if (i != 0)
					min[i] = defaultMin[i];
				max[i] = defaultMax[i];				
			}
		
		SoulLogger.log(Level.INFO, "Loaded custom tier settings.");
		if (fault)
			SoulLogger.log(Level.SEVERE, "Custom tier settings are not accepted. Resetting defaults..");
		for (int i = 0; i < min.length; i++)
		{
			SoulLogger.log(Level.INFO, "Tier: "+i);
			SoulLogger.log(Level.INFO, min[i]+":"+max[i]);
		}
	}
	
	public static int getMin(int tier)
	{
		return min[tier];
	}
	
	public static int getMax(int tier)
	{
		return max[tier];
	}
	
	public static boolean isInBounds(int tier, int kills)
	{
		boolean result = true;
		if (kills < min[tier] || kills > max[tier])
			result = false;
		return result;
	}
	
	public static int updateTier(int kills)
	{
		int result = 0;
		for (int i = 0; i < min.length; i++)
			if (kills >= min[i] && kills <= max[i])
			{
				result = i;
				break;
			}
		return result;
	}
}