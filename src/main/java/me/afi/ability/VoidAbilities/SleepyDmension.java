package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;

public class SleepyDmension extends VoidAbility implements AddonAbility {
    private boolean charged = false;
    private Permission perm;
    private int currPoint;
    public SleepyDmension(Player player) {
        super(player);
        player.getWorld().playSound(player, Sound.BLOCK_PORTAL_AMBIENT, 0.78F, 0.98f);
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 0.28F, 1.98f);
        start();
    }

    @Override
    public void progress() {
        if(!player.isOnline() || !bPlayer.isOnline() || player.isDead() || !bPlayer.canBendIgnoreCooldowns(this) || bPlayer.isOnCooldown(this)) {
            this.remove();
        }
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if(System.currentTimeMillis() > getStartTime() + 8500) {
            this.remove();
            return;
        }
        if(player.isSneaking()) {
           if(System.currentTimeMillis() > getStartTime() + 1500) {
               charged = true;
           }
        }
        if(charged) {
            new ColoredParticle(Color.GREEN, 0.38F).display(player.getLocation(), 17,0.6,-0.2,0.6);
            CircleEffect();
            if(!player.isSneaking()) {
                for(Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 8)) {
                    Location locWorld = new Location(Bukkit.getWorld("oNi0_Sleep"), -2.006, 66, -2.737);
                    entity.teleport(locWorld);
                    entity.getWorld().playSound(entity, Sound.BLOCK_PORTAL_TRAVEL, 0.78F, 0.98f);
                }
            }
        }
    }

    public static void activate(Player player) {
        if (CoreAbility.hasAbility(player, SleepyDmension.class)) {
            SleepyDmension lp = CoreAbility.getAbility(player, SleepyDmension.class);
            lp.TeleportPlayer();
        }
    }

    public void TeleportPlayer() {
        Location locWorld = new Location(Bukkit.getWorld("oNi0_Sleep"), -2.006, 66, -2.737);
        player.teleport(locWorld);
        ParticleEffect.FLASH.display(player.getLocation(), 1,0,0,0,0f);
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_BREAK, 0.38F, 0.98f);
    }



    private void CircleEffect() {
        for (int i = 0; i < 1.3; i++) {
            this.currPoint += 360 / 50;
            if (this.currPoint > 360)
                this.currPoint = 0;
            double angle = this.currPoint * Math.PI / 180.0D;
            double x = 6 * Math.cos(angle);
            double z = 6 * Math.sin(angle);
                Location loc = this.player.getLocation().add(x, 1.0D, z);
                new ColoredParticle(Color.fromRGB(57, 158, 60), 0.78F).display(loc, 2, 0,0,0);
        }
    }

    @Override
    public void remove() {
        bPlayer.addCooldown(this);
        super.remove();
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
        return 11000;
    }

    @Override
    public String getName() {
        return "SleepyDimension";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.sleepydimension");
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
