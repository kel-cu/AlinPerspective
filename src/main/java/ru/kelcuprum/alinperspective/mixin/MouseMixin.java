package ru.kelcuprum.alinperspective.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinperspective.AlinPerspective;


@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(method = "turnPlayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/Tutorial;onMouse(DD)V"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void Tutorial$onMouse(double d, CallbackInfo ci, double x, double y){
        if(AlinPerspective.config.getBoolean("ENABLE",false)){
            AlinPerspective.cameraYaw += AlinPerspective.isPlayerRotation() ? (float) (y * 0.15f) : (float) (y/8.0f);
            double xi = (x * (AlinLib.MINECRAFT.options.invertYMouse().get() ? -1 : 1));
            AlinPerspective.cameraPitch += AlinPerspective.isPlayerRotation() ? (float) (x * 0.15f) : (float) (xi / 8.0F);
            if(Math.abs(AlinPerspective.cameraYaw) > 90.0F) AlinPerspective.cameraYaw = AlinPerspective.cameraYaw > 0 ? 90.0F : -90.0F;
        }
    }
    @Inject(method = "turnPlayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"), cancellable = true)
    public void LocalPlayer$turn(CallbackInfo ci){
        if(AlinPerspective.config.getBoolean("ENABLE",false) && !AlinPerspective.isPlayerRotation()) ci.cancel();
    }
}
