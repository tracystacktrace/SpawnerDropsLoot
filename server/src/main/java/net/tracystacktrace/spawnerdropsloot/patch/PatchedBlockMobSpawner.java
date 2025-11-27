package net.tracystacktrace.spawnerdropsloot.patch;


import net.minecraft.server.Block;
import net.minecraft.server.BlockMobSpawner;
import net.minecraft.server.TileEntity;

public class PatchedBlockMobSpawner extends BlockMobSpawner {
    public PatchedBlockMobSpawner(int id, int texture) {
        super(id, texture);
        this.c(5F); //setHardness
        this.a(Block.i); //setStepSound -> metal step sound idk
        this.n(); //disableStats
        this.a("mobSpawner"); //setBlockName
    }

    @Override
    protected TileEntity a_() { //getBlockEntity
        return new PatchedTileEntityMobSpawner();
    }
}
