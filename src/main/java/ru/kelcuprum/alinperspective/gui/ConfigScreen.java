package ru.kelcuprum.alinperspective.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.alinperspective.AlinPerspective;

public class ConfigScreen {
    public static Screen getScreen(Screen parent){
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("alinperspective"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("alinperspective.config")));
        builder.addWidget(new TextBox(Component.translatable("alinperspective.config")))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("alinperspective.config.enable"), false).setConfig(AlinPerspective.config, "ENABLE"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("alinperspective.config.enable.player_rotate"), true).setConfig(AlinPerspective.config, "ENABLE.PLAYER_ROTATION"));
        return builder.build();
    }
}
