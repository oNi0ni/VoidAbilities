package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
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

public class VoidHands extends VoidAbility implements AddonAbility {
    private static Location loc;
    private static Location loc2;
    private static Location loc3;
    private Permission perm;
    private static Location loc4;
    private static Vector direction;
    private int disctanceTravveled;
    private boolean shoot = false;
    public VoidHands(Player player) {
        super(player);
        direction = player.getEyeLocation().getDirection();
        loc = player.getLocation();
        loc2 = player.getLocation();
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 0.28F, 1.98f);
        loc3 = player.getLocation();
        loc4 = player.getLocation();
        disctanceTravveled = 0;
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
        if(System.currentTimeMillis() > getStartTime() + 3500) {
            this.remove();
            return;
        }
        if(loc.getBlock().getType().isSolid()) {
            this.remove();
            return;
        }
        if(loc2.getBlock().getType().isSolid()) {
            this.remove();
            return;
        }
        if(loc3.getBlock().getType().isSolid()) {
            this.remove();
            return;
        }
        if(loc4.getBlock().getType().isSolid()) {
            this.remove();
            return;
        }

       if(player.isSneaking()) {
           new ColoredParticle(Color.GREEN, 0.58F).display(GeneralMethods.getLeftSide(loc, 3).add(0, 5, 0), 15, 0, 0, 0);
           new ColoredParticle(Color.GREEN, 0.78F).display(GeneralMethods.getRightSide(loc2, 3).add(0, 5, 0), 16, 0, 0, 0);
           new ColoredParticle(Color.GREEN, 0.58F).display(GeneralMethods.getLeftSide(loc3, 4).add(0, 4, 0), 15, 0, 0, 0);
           new ColoredParticle(Color.GREEN, 0.78F).display(GeneralMethods.getRightSide(loc4, 4).add(0, 4, 0), 16, 0, 0, 0);
       }
        if(shoot) {
            direction.clone().multiply(1.23F).normalize();
            loc.add(direction);
            loc2.add(direction);
            loc3.add(direction);
            loc4.add(direction);
            Vector currentDirection = player.getLocation().getDirection();

            Vector controlDirection = new Vector(0, 0, 0);
            Vector newDirection = currentDirection.add(controlDirection.multiply(6.23f)).normalize();
            loc.add(newDirection);
            loc2.add(newDirection);
            loc3.add(newDirection);
            loc4.add(newDirection);
            for(Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 1.2)) {
                if (entity != player) {
                    DamageHandler.damageEntity(entity, 1.0f, this, false);
                }
            }
            for(Entity entity : GeneralMethods.getEntitiesAroundPoint(loc2, 1.4)) {
                if (entity != player) {
                    DamageHandler.damageEntity(entity, 1.0f, this, false);
                }
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        bPlayer.addCooldown(this);
        shoot = false;
    }

    public static void activate(Player player) {
        if (CoreAbility.hasAbility(player, VoidHands.class)) {
            VoidHands lp = CoreAbility.getAbility(player, VoidHands.class);
            lp.shoot = true;
        }
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
        return 5600;
    }

    @Override
    public String getName() {
        return "VoidSpears";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.voidspears");
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
        return "BendingVerse 1.0v";
    }
}
