package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FlashEnergy extends VoidAbility implements AddonAbility {

    private Vector direction;
    private Location location;
    private Permission perm;
    public FlashEnergy(Player player) {
        super(player);
        location = player.getEyeLocation();
        direction = player.getEyeLocation().getDirection();
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
      player.addPotionEffect(PotionEffectType.GLOWING.createEffect(3,690));
      player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(2,690));
      player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(2,690));
      new ColoredParticle(Color.YELLOW, 0.48F).display(player.getLocation(), 2,0.6,-0.2,0.6);
      if(System.currentTimeMillis() > getStartTime() + 8900) {
          this.remove();
          return;
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
        return 7500;
    }

    @Override
    public String getName() {
        return "FlashEnergy";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
         ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.flashenergy");
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
