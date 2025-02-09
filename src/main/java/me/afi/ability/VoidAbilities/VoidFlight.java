package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;

public class VoidFlight extends VoidAbility implements AddonAbility {
    private Permission perm;
    public VoidFlight(Player player) {
        super(player);

        start();
    }

    @Override
    public void progress() {
        if(!bPlayer.isOnline() || !player.isOnline() || player.isDead() || bPlayer.isOnCooldown(this)) {
            this.remove();
            return;
        }
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if(System.currentTimeMillis() > getStartTime() + 7500) {
            this.remove();
            return;
        }
        new ColoredParticle(Color.GREEN, 0.38F).display(player.getLocation(), 17,0.6,-0.2,0.6);
        allowFlight();
    }
    private void allowFlight() {
        player.setFlySpeed((float) 0.1F);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    private void removeFlight() {
        player.setFlySpeed(0.1F);
        player.setAllowFlight(false);
        player.setFlying(false);
    }
    @Override
    public void remove() {
        removeFlight();
        super.remove();
        bPlayer.addCooldown(this);
    }
    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 7500;
    }

    @Override
    public String getName() {
        return "VoidFlight";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.voidflight");
        ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(new VoidListener());
        ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
    }

    @Override
    public String getAuthor() {
        return "oNi0_";
    }

    @Override
    public String getVersion() {
        return "BendingVerse v1.0";
    }
}
