# 🌾 SimpleNoCropTrample

Plugin that prevents players/mobs from destroying farmland by jumping on it.

Works with every version from 1.7 to 1.19 and all softwares supporting spigot plugins.

## Config
```yaml
// If true, mobs won't be able to trample crops
disable-mob-trampling: true

// If true, player won't be able to trample crops
disable-player-trampling: true

// List of blocks that count as farmland (FARMLAND for 1.13+ and SOIL for <1.13)
farmlands-blocks:
- FARMLAND
- SOIL

```

## API
This plugin has a simple API that can be used to manipulate crop trampeling for different players/mobs.

You can listen to the `CropTrampleEvent` and implement your custom logic to add features like permissions or bypasses.

The plugin also uses the event to disable crop trample if the config settings are enabled.

**Example**
```java
@EventHandler
public void onTrample(CropTrampleEvent event) {
	if (event.getCause() == CropTrampleEvent.TrampleCause.PLAYER) {
		Player player = (Player) event.getTrampler();
		player.sendMessage("You're not able to destroy " + event.getBlock().getType() + "!");
	}
}
```

`TrampleCause` can either be MOB or PLAYER, depending on what entity tries to destroy the farmland.

## Stats (bStats)
![](https://bstats.org/signatures/bukkit/simplenocroptrample.svg)
