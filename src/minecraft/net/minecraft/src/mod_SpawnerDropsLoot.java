package net.minecraft.src;

import net.tracystacktrace.spawnerdropsloot.HookTools;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedBlockMobSpawner;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedTileEntityMobSpawner;

import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class mod_SpawnerDropsLoot extends BaseMod {

    @Override
    public String Version() {
        return "0.2";
    }

    public String Name() {
        return "Spawner Drops Loot";
    }

    public String Description() {
        return "Spawner entities now drop loot!";
    }

    public String Icon() {
        return "/net/tracystacktrace/spawnerdropsloot/icon.png";
    }

    static {
        System.out.println("[mod_SpawnerDropsLoot] The mod is attempting to replace instances of required fields!");

        //process tileentity
        System.out.println("[mod_SpawnerDropsLoot] Replacing \"MobSpawner\" tileentity with custom one");
        final Map map1 = (Map) HookTools.getStaticField(TileEntity.class, "classToNameMap", "b");
        final Map map2 = (Map) HookTools.getStaticField(TileEntity.class, "nameToClassMap", "a");

        map1.remove(TileEntityMobSpawner.class);
        map2.remove("MobSpawner");

        map1.put(PatchedTileEntityMobSpawner.class, "MobSpawner");
        map2.put("MobSpawner", PatchedTileEntityMobSpawner.class);


        //process block
        System.out.println("[mod_SpawnerDropsLoot] Replacing \"Mob Spawner\" block with custom one");
        final int mobSpawnerBlockID = Block.mobSpawner.blockID;

        Block.blocksList[mobSpawnerBlockID] = null;
        final PatchedBlockMobSpawner customMobSpawner = new PatchedBlockMobSpawner(mobSpawnerBlockID, 65);

        HookTools.setStaticFinalField(Block.class, "mobSpawner", "at", customMobSpawner);
        System.out.println("[mod_SpawnerDropsLoot] Success! Enjoy your lootable spawners!");
    }
}
