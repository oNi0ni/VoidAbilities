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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;


public class FlashBeam extends VoidAbility implements AddonAbility {
    private boolean charged = false;
    public static boolean clicked = false;
    private Location loc;
    private Permission perm;
    private Vector direction;
    public FlashBeam(Player player) {
        super(player);
        loc = player.getEyeLocation();
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
        if(System.currentTimeMillis() > getStartTime() + 7000) {
            this.remove();
            return;
        }
        if(player.isSneaking()) {
            if(System.currentTimeMillis() > getStartTime() + 1500) {
                charged = true;
                if(charged) {
                    new ColoredParticle(Color.YELLOW, 0.48F).display(player.getLocation(), 2, 0.6, -0.2, 0.6);
                }
            }
       }
           if(!player.isSneaking()) {
               if (charged) {
                   if (!player.isSneaking()) {
                       Vector currentDirection = player.getLocation().getDirection();

                       Vector controlDirection = new Vector(0, 0, 0);
                       Vector newDirection = currentDirection.add(controlDirection.multiply(6.23f)).normalize();
                       loc.add(newDirection);
                       new ColoredParticle(Color.YELLOW, 0.48F).display(loc, 1, 0, 0, 0);
                       new ColoredParticle(Color.YELLOW, 0.48F).display(loc.add(2,0,0), 1, 0, 0, 0);
                       new ColoredParticle(Color.YELLOW, 0.48F).display(loc.add(0,0,2), 1, 0, 0, 0);
                       new ColoredParticle(Color.YELLOW, 0.48F).display(loc.add(0,1,0), 1, 0, 0, 0);
                       for (Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 3)) {
                           if(entity != player) {
                               DamageHandler.damageEntity(entity, 0.5f, this, false);
                               this.remove();
                           }
                       }
                   }
               }
           }
     }
     @Override
     public void remove() {
        super.remove();
        bPlayer.addCooldown(this);
        charged = false;
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
        return 5900;
    }

    @Override
    public String getName() {
        return "FlashBlast";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.flashbeam");
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
