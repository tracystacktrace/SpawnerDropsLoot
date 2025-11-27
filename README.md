# Spawner Drops Loot

![Mod Logo](https://github.com/tracystacktrace/SpawnerDropsLoot/raw/master/docs/icon.png)

While attempting to play through a challenge in my NFC world, I end up struggling over spawner-based mob farms.
Entities spawned by spawners don't drop any loot, which is not good!
I understand that this is an intentional gameplay change, but at some point it made me struggle as I needed some mobfarm drop.

This mod aims to return loot drops of entities spawned in spawners.

## Installation

### Compatibility
- Works with NFC versions from `3.4.X` to latest `3.5.2` (and probably beyond)
- Early NFC versions may require additional changes, contact me (`@auntiewate` in Discord) if you need some help
- Server-side mod doesn't require clients to have client-side version pre-installed

### Installation Steps

1. For NFC 3.5+ versions (has built-in ModLoader):
   - Download and put the mod into `mods` folder.
2. For NFC pre-3.5 versions (needs separate ModLoader):
   - Download ModLoader for your NFC version: https://newfrontiercraft.net/thread/179/nfc-v3-4-mods-addons
   - Download and put the mod into `mods` folder.

## Building Mod

### Client-side
1. First of all, follow these instructions: https://github.com/New-Frontier-Craft/New-Frontier-Craft
2. Prepare a `New Frontier Craft v3.4.2` dev environment
3. Download `ModLoader` and extract sources to the environment: https://github.com/VivianTheFox/MC-Addons/raw/main/NFC/NFC%20v3.4/NFC%20v3.4.2/ModLoaderNFC%20v4.6.1.zip
4. Download this mod's source code and extract to the environment

### Server-side
1. First of all, prepare the patched NFC CraftBukkit server! Guide: https://newfrontiercraft.net/thread/158/
2. Put the `craftbukkit_nfc.jar` (patched server) into the `libs` folder, inside server project
3. Open the server project either via Eclipse or IDEA, let Maven handle project initialization
4. Ta-da! Enjoy editing :)

## License

This mod is licensed under [Apache License 2.0](https://github.com/tracystacktrace/SpawnerDropsLoot/blob/master/LICENSE).