package me.afi.ability.VoidAbilities;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.ColoredParticle;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.afi.ability.Listener.VoidListener;
import me.afi.ability.api.VoidAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Map;

public class FlashJump extends VoidAbility implements AddonAbility {
    private Vector direction;
    private Permission perm;
    public FlashJump(Player player) {
        super(player);
          direction = player.getEyeLocation().getDirection().multiply(1.0f);
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
        if(System.currentTimeMillis() > getStartTime() + 300){
            ParticleEffect.FLASH.display(player.getLocation(),1,0,0,0,0f);
            this.remove();
            return;
        }
       player.setVelocity(direction);
        ParticleEffect.END_ROD.display(player.getLocation(),1,Math.random(),Math.random(),Math.random(),0f);
        new ColoredParticle(Color.YELLOW, 1.0F).display(player.getLocation(), 2,0.6,-0.2,0.6);
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
        return 4500;
    }

    @Override
    public String getName() {
        return "FlashJump";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new VoidListener(), ProjectKorra.plugin);
        perm = new Permission("bending.ability.flashjump");
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
