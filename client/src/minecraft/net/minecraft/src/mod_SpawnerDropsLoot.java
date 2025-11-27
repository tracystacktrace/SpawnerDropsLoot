package net.minecraft.src;

import net.tracystacktrace.spawnerdropsloot.HookTools;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedBlockMobSpawner;
import net.tracystacktrace.spawnerdropsloot.patch.PatchedTileEntityMobSpawner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class mod_SpawnerDropsLoot extends BaseMod {
    private static final DateTimeFormatter HHMMSS_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void log(String message) {
        System.out.printf("[%s] [Spawner Drops Loot] %s\n", LocalTime.now().format(HHMMSS_FORMAT), message);
    }

    @Override
    public String Version() {
        return "0.3";
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
        log("The mod will attempt to replace instances of mob spawner block + tile entity!");

        //process tileentity
        log("1/2: Replacing \"MobSpawner\" TileEntity with a patched version...");
        final Map map1 = (Map) HookTools.getStaticField(TileEntity.class, "classToNameMap", "b");
        final Map map2 = (Map) HookTools.getStaticField(TileEntity.class, "nameToClassMap", "a");

        map1.remove(TileEntityMobSpawner.class);
        map2.remove("MobSpawner");

        map1.put(PatchedTileEntityMobSpawner.class, "MobSpawner");
        map2.put("MobSpawner", PatchedTileEntityMobSpawner.class);


        //process block
        log("2/2: Replacing \"mobSpawner\" block with a patched version...");
        final int mobSpawnerBlockID = Block.mobSpawner.blockID; //take the block ID

        Block.blocksList[mobSpawnerBlockID] = null; //make it null to safely opt-out
        final PatchedBlockMobSpawner customMobSpawner = new PatchedBlockMobSpawner(mobSpawnerBlockID, 65);

        //we have to put it into the private static field (with absurd tools) so the game would adequately function later
        HookTools.setStaticFinalField(Block.class, "mobSpawner", "at", customMobSpawner);
        log("Success! Enjoy your lootable spawners!");
    }
}
