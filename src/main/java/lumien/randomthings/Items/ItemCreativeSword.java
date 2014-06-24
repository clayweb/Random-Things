package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemCreativeSword extends ItemCreative
{
	int DAMAGE = 100000;

	public ItemCreativeSword()
	{
		this.setUnlocalizedName("creativeSword");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setMaxStackSize(1);
		this.setTextureName("RandomThings:creativeSword");

		GameRegistry.registerItem(this, "creativeSword");
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityLiving)
		{
			((EntityLiving) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), DAMAGE);
		}
		return false;
	}
}
