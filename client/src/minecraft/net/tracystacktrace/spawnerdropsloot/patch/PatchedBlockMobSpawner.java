package net.tracystacktrace.spawnerdropsloot.patch;

import net.minecraft.src.Block;
import net.minecraft.src.BlockMobSpawner;
import net.minecraft.src.TileEntity;

public class PatchedBlockMobSpawner extends BlockMobSpawner {
    public PatchedBlockMobSpawner(int id, int texture) {
        super(id, texture);
        this.setHardness(5F);
        this.setStepSound(Block.soundMetalFootstep);
        this.disableStats();
        this.setBlockName("mobSpawner");
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new PatchedTileEntityMobSpawner();
    }
}
