package me.afi.ability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class EnergyBar {

    public BossBar energyBar = Bukkit.createBossBar(ChatColor.DARK_GREEN + "Энергия Пустоты", BarColor.GREEN, BarStyle.SEGMENTED_10);
    public double energy = 100; // Начальная энергия
    public double maxEnergy = 100; // Максимальная энергия
    public double restoreRate = 5.0; // Скорость восстановления энергии (восстанавливаемая энергия за определённый интервал)
    private long lastRegenTime = System.currentTimeMillis(); // Время последнего восстановления энергии

    // Создание босса бара для игрока
    public void bossBarCreate(Player player) {
        energyBar.addPlayer(player);
        energyBar.setProgress(energy / maxEnergy); // Устанавливаем прогресс энергии
    }

    // Удаление босса бара для игрока
    public void deleteBossBar(Player player) {
        energyBar.removePlayer(player);
        energyBar.removeAll();
    }

    // Метод для снижения энергии
    public void BossBarEnergyCost(Player player, double minusDouble) {
        if (energy >= minusDouble) {
            energy -= minusDouble;
            energyBar.setProgress(energy / maxEnergy); // Обновляем прогресс босса бара
            Bukkit.getLogger().info("Energy deducted. Current energy: " + energy);
        }
    }

    // Метод для восстановления энергии
    public void restoreEnergy() {
        // Проверяем, прошло ли достаточное количество времени для восстановления
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastRegenTime;

        // Восстанавливаем энергию, если прошло достаточно времени (например, 1 секунда)
        if (timeDiff >= 10) {  // 1000 миллисекунд = 1 секунда
            if (energy < maxEnergy) {
                energy += restoreRate;
                if (energy > maxEnergy) {
                    energy = maxEnergy; // Ограничиваем восстановление максимальной энергией
                }
                energyBar.setProgress(energy / maxEnergy); // Обновляем прогресс босса бара
                Bukkit.getLogger().info("Energy restored. Current energy: " + energy);
            }

            // Обновляем время последнего восстановления
            lastRegenTime = currentTime;
        }
    }
}
