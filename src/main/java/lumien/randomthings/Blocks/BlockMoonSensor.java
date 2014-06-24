package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.world.World;

public class BlockMoonSensor extends BlockDaylightDetector
{

	protected BlockMoonSensor()
	{
		this.setCreativeTab(RandomThings.creativeTab);

		this.setBlockName("moonSensor");

		GameRegistry.registerBlock(this, "moonSensor");
		this.setBlockTextureName("RandomThings:moon_sensor");
	}

	@Override
	public void func_149957_e(World worldObj, int posX, int posY, int posZ)
	{
		if (!worldObj.provider.hasNoSky)
		{
			int moon = (int) (worldObj.getCurrentMoonPhaseFactor() * 15F);
			int metadata = worldObj.getBlockMetadata(posX, posY, posZ);

			if (moon != metadata)
			{
				worldObj.setBlockMetadataWithNotify(posX, posY, posZ, moon, 3);
			}
		}
	}

}
