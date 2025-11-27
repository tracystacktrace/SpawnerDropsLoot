package net.tracystacktrace.spawnerdropsloot.patch;

import net.minecraft.server.*;

public class PatchedTileEntityMobSpawner extends TileEntityMobSpawner {
    private void customUpdateDelay() {
        this.spawnDelay = 200 + world.random.nextInt(400);
    }

    public void g_() {
        this.c = this.b;

        if (!this.a()) { //anyPlayerInRange
            return;
        }

        if (world.getBlockId(this.x, this.y, this.z) != Block.MOB_SPAWNER.id) {
            world.o(this.x, this.y, this.z);
            return;
        }

        double particleX = (float) this.x + world.random.nextFloat();
        double particleY = (float) this.y + world.random.nextFloat();
        double particleZ = (float) this.z + world.random.nextFloat();

        world.a("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
        world.a("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);

        for (this.b += 1000F / ((float) spawnDelay + 200F); this.b > 360D; ) {
            this.b -= 360D;
            this.c -= 360D;
        }

        if (!world.isStatic) {
            if (spawnDelay == -1) {
                this.customUpdateDelay();
            }

            if (spawnDelay > 0) {
                spawnDelay--;
                return;
            }

            for (int attempt = 0; attempt < 4; attempt++) {
                final Entity entity = EntityTypes.a(this.mobName, world);
                if (entity == null) {
                    return;
                }

                EntityLiving entityliving = null;
                if (entity instanceof EntityLiving) {
                    entityliving = (EntityLiving) entity;
                }

                final int testEntities = world.a(entity.getClass(), AxisAlignedBB.b(this.x, this.y, this.z, this.x + 1, this.y + 1, this.z + 1).b(8D, 4D, 8D)).size();
                if (testEntities >= 6) {
                    this.customUpdateDelay();
                    return;
                }

                final double entityX = (double) this.x + (world.random.nextDouble() - world.random.nextDouble()) * 4D;
                final double entityY = (this.y + world.random.nextInt(3)) - 1;
                final double entityZ = (double) this.z + (world.random.nextDouble() - world.random.nextDouble()) * 4D;

                entity.setPositionRotation(entityX, entityY, entityZ, world.random.nextFloat() * 360F, 0.0F);

                if (entityliving != null && !entityliving.d()) {
                    continue;
                }

                world.addEntity(entity);

                for (int k = 0; k < 20; k++) {
                    particleX = (double) this.x + 0.5D + ((double) world.random.nextFloat() - 0.5D) * 2D;
                    particleY = (double) this.y + 0.5D + ((double) world.random.nextFloat() - 0.5D) * 2D;
                    particleZ = (double) this.z + 0.5D + ((double) world.random.nextFloat() - 0.5D) * 2D;
                    world.a("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                    world.a("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                }

                if (entityliving != null) {
                    entityliving.S();
                }
                this.customUpdateDelay();
            }

        }
    }

}
