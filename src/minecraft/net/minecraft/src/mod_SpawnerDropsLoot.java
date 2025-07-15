package net.minecraft.src;

import net.tracystacktrace.spawnerdropsloot.FieldInvoker;
import net.tracystacktrace.spawnerdropsloot.MethodInvoker;
import net.tracystacktrace.spawnerdropsloot.PatchedBlockMobSpawner;
import net.tracystacktrace.spawnerdropsloot.PatchedTileEntityMobSpawner;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class mod_SpawnerDropsLoot extends BaseMod {

    @Override
    public String Version() {
        return "0.1";
    }

    static {
        System.out.println("[mod_SpawnerDropsLoot] The mod is attempting to replace instances of required fields!");

        //process tileentity
        System.out.println("[mod_SpawnerDropsLoot] Replacing \"MobSpawner\" tileentity with custom one");
        final Map map1 = (Map) FieldInvoker.getStaticField(TileEntity.class, "classToNameMap", "b");
        final Map map2 = (Map) FieldInvoker.getStaticField(TileEntity.class, "nameToClassMap", "a");

        map1.remove(TileEntityMobSpawner.class);
        map2.remove("MobSpawner");

        map1.put(PatchedTileEntityMobSpawner.class, "MobSpawner");
        map2.put("MobSpawner", PatchedTileEntityMobSpawner.class);


        //process block
        System.out.println("[mod_SpawnerDropsLoot] Replacing \"Mob Spawner\" block with custom one");
        final int mobSpawnerBlockID = Block.mobSpawner.blockID;

        Block.blocksList[mobSpawnerBlockID] = null;
        Block customMobSpawner = new PatchedBlockMobSpawner(mobSpawnerBlockID, 65).setBlockName("mobSpawner");

        MethodInvoker.invoke_setHardness(customMobSpawner, 5F);
        MethodInvoker.invoke_setStepSound(customMobSpawner, Block.soundMetalFootstep);
        MethodInvoker.invoke_disableStats(customMobSpawner);
        FieldInvoker.setStaticFinalField(Block.class, "mobSpawner", "at", customMobSpawner);

        System.out.println("[mod_SpawnerDropsLoot] Success! Enjoy your lootable spawners!");
    }
}
