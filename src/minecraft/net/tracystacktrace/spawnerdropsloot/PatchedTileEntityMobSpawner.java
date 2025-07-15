package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.src.*;

public class PatchedTileEntityMobSpawner extends TileEntityMobSpawner {

    static void inject_updateDelay(Object instance) {
        InvokeHelper.invokeVoidE(TileEntityMobSpawner.class, instance, "updateDelay", "d");
    }

    public void updateEntity() {
        yaw2 = yaw;
        if (!anyPlayerInRange()) {
            return;
        }

        if (worldObj.getBlockId(xCoord, yCoord, zCoord) != Block.mobSpawner.blockID) {
            worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
            return;
        }

        double d = (float) xCoord + worldObj.rand.nextFloat();
        double d2 = (float) yCoord + worldObj.rand.nextFloat();
        double d4 = (float) zCoord + worldObj.rand.nextFloat();
        worldObj.spawnParticle("smoke", d, d2, d4, 0.0D, 0.0D, 0.0D);
        worldObj.spawnParticle("flame", d, d2, d4, 0.0D, 0.0D, 0.0D);
        for (yaw += 1000F / ((float) delay + 200F); yaw > 360D;) {
            yaw -= 360D;
            yaw2 -= 360D;
        }

        if (!worldObj.multiplayerWorld) {
            if (delay == -1) {
                inject_updateDelay(this);
            }
            if (delay > 0) {
                delay--;
                return;
            }

            final String mobID = (String) InvokeHelper.getVirtualField(TileEntityMobSpawner.class, this, "mobID", "i");

            byte byte0 = 4;
            for (int i = 0; i < byte0; i++) {
                Entity entity = EntityList.createEntityInWorld(mobID, worldObj);
                EntityLiving entityliving = null;
                if (entity == null) {
                    return;
                }
                if(entity instanceof EntityLiving) {
                    entityliving = (EntityLiving) entity;
                }
                int j = worldObj.getEntitiesWithinAABB(
                        entity.getClass(),
                        AxisAlignedBB.getBoundingBoxFromPool(xCoord, yCoord,
                                        zCoord, xCoord + 1, yCoord + 1, zCoord + 1)
                                .expand(8D, 4D, 8D)).size();
                if (j >= 6) {
                    inject_updateDelay(this);
                    return;
                }
                double d6 = (double) xCoord
                        + (worldObj.rand.nextDouble() - worldObj.rand
                        .nextDouble()) * 4D;
                double d7 = (yCoord + worldObj.rand.nextInt(3)) - 1;
                double d8 = (double) zCoord
                        + (worldObj.rand.nextDouble() - worldObj.rand
                        .nextDouble()) * 4D;
                entity.setLocationAndAngles(d6, d7, d8,
                        worldObj.rand.nextFloat() * 360F, 0.0F);
                if (entityliving != null && !entityliving.getCanSpawnHere()) {
                    continue;
                }
                System.out.println("Spawning entity: " + entity.toString());
                worldObj.entityJoinedWorld(entity);
                for (int k = 0; k < 20; k++) {
                    double d1 = (double) xCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    double d3 = (double) yCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    double d5 = (double) zCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    worldObj.spawnParticle("smoke", d1, d3, d5, 0.0D, 0.0D,
                            0.0D);
                    worldObj.spawnParticle("flame", d1, d3, d5, 0.0D, 0.0D,
                            0.0D);
                }

                if(entityliving != null)
                    entityliving.spawnExplosionParticle();
                inject_updateDelay(this);
            }

        }
    }

}
