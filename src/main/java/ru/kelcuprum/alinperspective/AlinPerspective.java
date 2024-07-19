package ru.kelcuprum.alinperspective;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.api.KeyMappingHelper;
import ru.kelcuprum.alinlib.api.events.client.ClientTickEvents;
import ru.kelcuprum.alinlib.config.Config;

public class AlinPerspective implements ClientModInitializer {
    public static Config config = new Config("./config/alinperspective.json");
    public static KeyMapping toogleMode;
    public static KeyMapping rotatePlayer;

    public static float cameraPitch = 0;
    public static float cameraYaw = 0;

    @Override
    public void onInitializeClient() {
        toogleMode = KeyMappingHelper.register(new KeyMapping(
                "alinperspective.toggle",
                GLFW.GLFW_KEY_F4,
                "alinperspective"
        ));

        rotatePlayer = KeyMappingHelper.register(new KeyMapping(
                "alinperspective.rotatePlayer",
                GLFW.GLFW_KEY_LEFT_ALT,
                "alinperspective"
        ));
        ClientTickEvents.START_CLIENT_TICK.register((s) -> {
            boolean pmrEnable = config.getBoolean("ENABLE", false);
            if(AlinLib.MINECRAFT.player == null) return;
            if(toogleMode.consumeClick()){
                pmrEnable = !pmrEnable;
                cameraPitch = AlinLib.MINECRAFT.player.getYRot();
                cameraYaw = AlinLib.MINECRAFT.player.getXRot();
                config.setBoolean("ENABLE", pmrEnable);
                AlinLib.MINECRAFT.options.setCameraType(pmrEnable ? CameraType.THIRD_PERSON_BACK : CameraType.FIRST_PERSON);
            }

            if(pmrEnable && AlinLib.MINECRAFT.options.getCameraType() != CameraType.THIRD_PERSON_BACK){
                pmrEnable = false;
                config.setBoolean("ENABLE", pmrEnable);
            }
        });
    }

    public static boolean isPlayerRotation(){
        return config.getBoolean("ENABLE.PLAYER_ROTATION", true) && rotatePlayer.isDown();
    }
}
