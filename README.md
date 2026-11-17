![Title Image](https://cdn.modrinth.com/data/cached_images/d5eb6f23205f1101ae1e31ecc05dfac345be9b58.png)

<center>
<a href="https://modrinth.com/mod/resource-cubes">
<img alt="modrinth" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/modrinth_46h.png">
</a>
<img alt="fabric-api" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/requires/fabric-api_46h.png">
<a href="https://github.com/ajcool-space/resource-cubes">
<img alt="github" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/github_46h.png">
</a>
</center>

## About
This mod adds a new survival-friendly feature to vanilla Sulfur Cubes, allowing them to passively produce resources depending on the block they swallow! The resources that can be swallowed and produced by Sulfur Cubes are defined through Minecraft's built-in datapack structure, making this mod easily compatible with other mods and ready for modpack integration.

## Features

### Passive Resource Farming
- Sulfur Cubes that have swallowed a farmable block (see below) will drop that resource on a timer.
- There are five rarity tiers with timers ranging from 2-4 minutes (Very Common) to 14-20 minutes (Very Rare).
- Custom functionality for bee hives, making it so the number of bees in the hive when it got silk touched changes the rarity, and in turn the drop timer.
- Sckulk blocks drop experience.

### More Sulfur Cube Swallowables
- Several vanilla blocks are now swallowable when they weren't before (Creaking heart, Beehives, Bee Nest, etc.).
- These blocks also get a physics archetype defined, mostly matching the current vanilla theme where I could. (Wood = bouncy, etc.)

### Datapack Support
- `#resource-cubes:resource_cubes_farmable` defines which blocks can be farmed.
- `#resource-cubes:resource_cubes_rarity/<tier>` sets the rarity tier for a resource.
- `#resource-cubes:resource_cubes_drop_experience` makes a block drop XP, currently only drops the normal xp a block would have produced.
- `resource-cubes:loot_table/<namespace>/<block>` override the vanilla loot table.

## Base Farmable Resources

The rarity defines the random span of time between drops.

- Very Common (2-4 min)
- Common (5-10 min)
- Uncommon (8-12 min)
- Rare (11-15 min)
- Very Rare (14-20 min)

All ores support both normal stone and deepslate varients. One one drop is chosen if the loot table has multiple items defined. Loot tables roughly match vanilla drop weights. Most of the blocks need to be gathered using silk touch.

| Block | Rarity | Item |
|---|---|---|
| Coal Ore | Very Common | Coal |
| Clay | Very Common | Clay Ball |
| Gravel | Very Common | Flint |
| Melon | Very Common | Melon Slice |
| Brown Mushroom Block | Very Common | Brown Mushroom |
| Red Mushroom Block | Very Common | Red Mushroom |
| Bee Nest / Hive | Depends on number of bees: 1 = Rare, 2 = Uncommon, 3 = Common | Honeycomb |
| Oak / Dark Oak Leaves | Very Common | Sapling, Stick, or Apple |
| Spruce / Birch / Acacia / Pale Oak / Cherry / Poplar Leaves | Very Common | Sapling or Stick |
| Azalea Leaves | Very Common | Azalea or Stick |
| Flowering Azalea Leaves | Very Common | Flowering Azalea or Stick |
| Jungle Leaves | Very Common | Jungle Sapling or Stick |
| Mangrove Leaves | Very Common (2-4 min) | Stick |
| Nether Quartz Ore | Very Common | Nether Quartz |
| Nether Gold Ore | Common (5-10 min) | Gold Nugget |
| Sculk | Common (5-10 min) | Experience Orb |
| Copper Ore | Common (5-10 min) | Raw Copper |
| Iron Ore | Common (5-10 min) | Raw Iron |
| Gold Ore | Rare (11-15 min) | Raw Gold |
| Redstone Ore | Uncommon (8-12 min) | Redstone Dust |
| Emerald Ore | Uncommon (8-12 min) | Emerald |
| Lapis Ore | Rare (11-15 min) | Lapis Lazuli |
| Diamond Ore | Very Rare (14-20 min) | Diamond |
| Amethyst Cluster | Uncommon (8-12 min) | Amethyst Shard |
| Creaking Heart | Rare (11-15 min) | Resin Clump |


## License
You are free to use this mod in your Modrinth-hosted modpacks without asking for permission! Please do not redistribute the mods files anywhere else. All Rights Reserved.