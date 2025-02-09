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

public class VoidRing extends VoidAbility implements AddonAbility {
    private boolean charged = false;
    private int currPoint;
    private Permission perm;
    public VoidRing(Player player) {
        super(player);
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.28F, 1.98f);
        start();
    }

    @Override
    public void progress() {
        if(System.currentTimeMillis() > getStartTime() + 6500) {
            this.remove();
            return;
        }
        if(!bPlayer.isOnline() || !player.isOnline() || player.isDead() || !bPlayer.canBendIgnoreCooldowns(this) || bPlayer.isOnCooldown(this)) {
            this.remove();
            return;
        }
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if(player.isSneaking()) {
            if(System.currentTimeMillis() > getStartTime() + 1500) {
                charged = true;
                if(charged) {
                    new ColoredParticle(Color.GREEN, 0.39F).display(player.getLocation(), 2,0.6,-0.2,0.6);
                }
            }
        }
        if(!player.isSneaking()) {
            if(charged) {
                  applyEffects2();
            }
        }
    }

    private void applyEffects() {
            for (int i = 0; i < 1.3; i++) {
                this.currPoint += 360 / 50;
                if (this.currPoint > 360)
                    this.currPoint = 0;
                double angle = this.currPoint * Math.PI / 180.0D;
                for(int radius = 0; radius <= 5; radius++) {
                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);
                    Location loc = this.player.getLocation().add(x, 1.0D, z);
                    new ColoredParticle(Color.fromRGB(57, 158, 60), 0.38F).display(loc, 2, 0, 0, 0);
                    for(Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 6)) {
                        if (entity != player) {
                            DamageHandler.damageEntity(entity, 0.3f, this, false);
                        }
                    }
                    player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.28F, 1.98f);
                }
            }
    }
    private void applyEffects2() {
        for(int radius = 0; radius < 10; radius++) {
            for (int i = 0; i < 1.3; i++) {
                this.currPoint += 360 / 50;
                if (this.currPoint > 360)
                    this.currPoint = 0;
                double angle = this.currPoint * Math.PI / 180.0D;
                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);
                Location loc = this.player.getLocation().add(x, 1.0D, z);
                new ColoredParticle(Color.fromRGB(57, 158, 60), 0.28f).display(loc, 2, 0, 0, 0);
                for(Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 6)) {
                    if (entity != player) {
                        DamageHandler.damageEntity(entity, 0.3f, this, false);
                    }
                }
                player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.28F, 1.98f);
            }
        }
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
        return 6500;
    }

    @Override
    public String getName() {
        return "VoidRing";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.voidring");
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
        return "Bending v1.0";
    }
}
