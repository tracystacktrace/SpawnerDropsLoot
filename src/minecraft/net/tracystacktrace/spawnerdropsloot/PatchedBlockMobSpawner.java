package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.src.BlockMobSpawner;
import net.minecraft.src.TileEntity;

public class PatchedBlockMobSpawner extends BlockMobSpawner {
    public PatchedBlockMobSpawner(int id, int texture) {
        super(id, texture);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new PatchedTileEntityMobSpawner();
    }
}
