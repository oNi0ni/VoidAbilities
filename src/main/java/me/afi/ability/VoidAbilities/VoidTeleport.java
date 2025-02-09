package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import me.afi.ability.EnergyBar;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;

public class VoidTeleport extends VoidAbility implements AddonAbility {
    private int currPoint;
    private Permission perm;
    private EnergyBar energyBar = new EnergyBar();
    public VoidTeleport(Player player) {
        super(player);
        player.getWorld().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 0.28F, 1.98f);
        if (!player.getWorld().getName().equals("Sleep")) player.sendMessage(ChatColor.RED + "You're not in the dream world\n");
        start();
    }

    @Override
    public void progress() {
        if(!bPlayer.isOnline() || !player.isOnline() || player.isDead() || !bPlayer.canBendIgnoreCooldowns(this) || !bPlayer.canBendIgnoreCooldowns(this) || bPlayer.isOnCooldown(this)) {
            this.remove();
            return;
        }
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if(player.isSneaking()) {
            CircleEffect();
            new ColoredParticle(Color.GREEN, 0.35F).display(player.getLocation(), 16,0.6,-0.2,0.6);
        }
        if(System.currentTimeMillis() > getStartTime() + 9000) {
            this.remove();
            return;
        }
        if(player.getWorld().getName().equals("oNi0_Sleep")) {
            if (!player.isSneaking()) {
                for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 7)) {
                    Location locWorld = new Location(Bukkit.getWorld("newworld"), 0, 150, 0);
                    entity.teleport(locWorld);
                }
            }
        }
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
            new ColoredParticle(Color.fromRGB(57, 158, 60), 0.38F).display(loc, 6, 0, 0, 0);
        }
    }

    @Override
    public void remove() {
        super.remove();
        bPlayer.addCooldown(this);
        energyBar.deleteBossBar(player);
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
        return "VoidTeleport";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.voidteleport");
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
