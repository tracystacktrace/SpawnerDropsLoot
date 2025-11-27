package net.tracystacktrace.spawnerdropsloot.patch;

import net.minecraft.src.*;
import net.tracystacktrace.spawnerdropsloot.HookTools;

public class PatchedTileEntityMobSpawner extends TileEntityMobSpawner {
    private void customUpdateDelay() {
        this.delay = 200 + worldObj.rand.nextInt(400);
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

        double particleX = (float) xCoord + worldObj.rand.nextFloat();
        double particleY = (float) yCoord + worldObj.rand.nextFloat();
        double particleZ = (float) zCoord + worldObj.rand.nextFloat();

        worldObj.spawnParticle("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
        worldObj.spawnParticle("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);

        for (yaw += 1000F / ((float) delay + 200F); yaw > 360D; ) {
            yaw -= 360D;
            yaw2 -= 360D;
        }

        if (!worldObj.multiplayerWorld) {
            if (delay == -1) {
                this.customUpdateDelay();
            }

            if (delay > 0) {
                delay--;
                return;
            }

            for (int attempt = 0; attempt < 4; attempt++) {
                final Entity entity = EntityList.createEntityInWorld(this.getMobID(), worldObj);
                if (entity == null) {
                    return;
                }

                EntityLiving entityliving = null;
                if (entity instanceof EntityLiving) {
                    entityliving = (EntityLiving) entity;
                }

                final int testEntities = worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBoxFromPool(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(8D, 4D, 8D)).size();
                if (testEntities >= 6) {
                    this.customUpdateDelay();
                    return;
                }

                final double entityX = (double) xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;
                final double entityY = (yCoord + worldObj.rand.nextInt(3)) - 1;
                final double entityZ = (double) zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;

                entity.setLocationAndAngles(entityX, entityY, entityZ, worldObj.rand.nextFloat() * 360F, 0.0F);

                if (entityliving != null && !entityliving.getCanSpawnHere()) {
                    continue;
                }

                worldObj.entityJoinedWorld(entity);

                for (int k = 0; k < 20; k++) {
                    particleX = (double) xCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    particleY = (double) yCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    particleZ = (double) zCoord + 0.5D + ((double) worldObj.rand.nextFloat() - 0.5D) * 2D;
                    worldObj.spawnParticle("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                    worldObj.spawnParticle("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                }

                if (entityliving != null) {
                    entityliving.spawnExplosionParticle();
                }
                this.customUpdateDelay();
            }

        }
    }

}
