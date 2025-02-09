package me.afi.ability.api;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import net.md_5.bungee.api.ChatColor;

public class VoidElement {
    public static final Element VOID;

    public VoidElement() {
    }

    static {
        VOID = new Element("Void", Element.ElementType.BENDING, ProjectKorra.plugin) {

            @Override
            public ChatColor getColor() {
                return ChatColor.of("#4ba14a");
            }
        };
    }
}
