package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

public class VoidBlades extends VoidAbility implements AddonAbility {
    private Vector direction;
    private Location location;
    private int distanceSquared;
    private Permission perm;
    public VoidBlades(Player player) {
        super(player);
        location = player.getEyeLocation();
        direction = player.getLocation().getDirection();
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 0.28F, 1.98f);
        distanceSquared = 0;
        start();
    }

    @Override
    public void progress() {
            if(!bPlayer.isOnline() || !player.isOnline() || player.isDead() || !bPlayer.canBendIgnoreCooldowns(this) || bPlayer.isOnCooldown(this)) {
                this.remove();
                return;
            }
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if(System.currentTimeMillis() > getStartTime() + 4500) {
            this.remove();
            return;
        }
        if(location.getBlock().getType().isSolid()) {
            this.remove();
            return;
        }

            new ColoredParticle(Color.GREEN, 0.38F).display(GeneralMethods.getLeftSide(location, 1).add(0,-0.7,0), 6,0,0,0);
            new ColoredParticle(Color.GREEN, 0.38F).display(location, 4,0,0,0);
            new ColoredParticle(Color.GREEN, 0.38F).display(GeneralMethods.getRightSide(location, 1).add(0,-0.7,0),7,0,0,0);

        Vector currentDirection = player.getLocation().getDirection();

        Vector controlDirection = new Vector(0, 0, 0);
        Vector newDirection = currentDirection.add(controlDirection.multiply(6.23f)).normalize();
            location.add(newDirection);

            for(Entity entity : GeneralMethods.getEntitiesAroundPoint(location,4)) {
                if (entity != player) {
                    DamageHandler.damageEntity(entity, 1.3, this, false);
                    this.remove();
                }
            }
        distanceSquared += direction.length();
    }

    @Override
    public void remove() {
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
        return 9500;
    }

    @Override
    public String getName() {
        return "VoidDaggers";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.voiddaggers");
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
