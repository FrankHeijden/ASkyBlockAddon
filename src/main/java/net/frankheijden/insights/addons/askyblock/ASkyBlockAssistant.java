package net.frankheijden.insights.addons.askyblock;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.Settings;
import net.frankheijden.insights.entities.CacheAssistant;
import net.frankheijden.insights.entities.Selection;
import org.bukkit.Location;
import org.bukkit.World;

public class ASkyBlockAssistant extends CacheAssistant {

    public ASkyBlockAssistant() {
        super("ASkyBlock", "ASkyBlock", "island", "1.0.2");
    }

    public Selection adapt(Island island) {
        if (island == null) return null;
        return new Selection(getMin(island), getMax(island));
    }

    public Location getMin(Island island) {
        return new Location(
                island.getCenter().getWorld(),
                island.getMinProtectedX(),
                0,
                island.getMinProtectedZ()
        );
    }

    public Location getMax(Island island) {
        World world = island.getCenter().getWorld();
        return new Location(
                world,
                island.getMinProtectedX() + Settings.islandProtectionRange,
                world.getMaxHeight() - 1,
                island.getMinProtectedZ() + Settings.islandProtectionRange
        );
    }

    @Override
    public Selection getSelection(Location location) {
        if (location == null) return null;
        Island island;
        try {
            island = ASkyBlockAPI.getInstance().getIslandAt(location);
        } catch (Throwable th) {
            return null;
        }
        return adapt(island);
    }
}
