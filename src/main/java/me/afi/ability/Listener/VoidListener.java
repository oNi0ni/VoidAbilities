package me.afi.ability.Listener;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.afi.ability.VoidAbilities.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerToggleSneakEvent;


public class VoidListener implements Listener {
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);

        if (bplayer.canBend(CoreAbility.getAbility(SleepyDmension.class))) {
            new SleepyDmension(player);
        }
        if (bplayer.canBend(CoreAbility.getAbility(VoidHands.class))) {
            new VoidHands(player);
        }
        if (bplayer.canBend(CoreAbility.getAbility(VoidRing.class))) {
            new VoidRing(player);
        }
        if (bplayer.canBend(CoreAbility.getAbility(VoidTeleport.class))) {
            new VoidTeleport(player);
        }
        if (bplayer.canBend(CoreAbility.getAbility(FlashBeam.class))) {
            new FlashBeam(player);
        }

    }

    @EventHandler
    public void onClick(PlayerAnimationEvent e) {
        if (e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            Player player = e.getPlayer();
            BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);
            String abil = bplayer.getBoundAbilityName();
            switch (abil.toUpperCase()) {
                case "SLEEPYDIMENSION":
                    SleepyDmension.activate(player);
                    break;

                case "VOIDDAGGERS":
                    new VoidBlades(player);
                    break;

                case "VOIDSPEARS":
                    VoidHands.activate(player);
                    break;
                case "FLASHJUMP":
                    new FlashJump(player);
                    break;
                case "FLASHENERGY":
                    new FlashEnergy(player);
                    break;
                case "VOIDFLIGHT":
                    new VoidFlight(player);
                    break;
            }
        }
    }
}

