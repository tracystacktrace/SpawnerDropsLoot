package net.minecraft.server;

import net.tracystacktrace.spawnerdropsloot.HookTools;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedBlockMobSpawner;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedTileEntityMobSpawner;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class mod_SpawnerDropsLoot extends BaseModMp {
    public static void log(String s) {
        System.out.printf("[Spawner Drops Loot] %s\n", s);
    }

    @Override
    public String Version() {
        return "0.3";
    }

    @Override
    public boolean hasClientSide() {
        return false;
    }

    public mod_SpawnerDropsLoot() {}

    static {
        log("The mod is attempting to replace instances of required fields!");

        //process tileentity
        log("Replacing \"MobSpawner\" tileentity with custom one");
        final Map map1 = (Map) HookTools.getStaticField(TileEntity.class, "b", null);
        final Map map2 = (Map) HookTools.getStaticField(TileEntity.class, "a", null);

        map1.remove(TileEntityMobSpawner.class);
        map2.remove("MobSpawner");

        map1.put(PatchedTileEntityMobSpawner.class, "MobSpawner");
        map2.put("MobSpawner", PatchedTileEntityMobSpawner.class);

        //process block
        log("Replacing \"Mob Spawner\" block with custom one");
        final int mobSpawnerBlockID = Block.MOB_SPAWNER.id;

        Block.byId[mobSpawnerBlockID] = null;
        final PatchedBlockMobSpawner customMobSpawner = new PatchedBlockMobSpawner(mobSpawnerBlockID, 65);

        HookTools.setStaticFinalField(Block.class, "MOB_SPAWNER", null, customMobSpawner);
        log("Success! Enjoy your lootable spawners!");
    }
}
