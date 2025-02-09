package me.afi.ability.api;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import org.bukkit.entity.Player;

public abstract  class VoidAbility extends ElementalAbility {
    public VoidAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return VoidElement.VOID;
    }
}
