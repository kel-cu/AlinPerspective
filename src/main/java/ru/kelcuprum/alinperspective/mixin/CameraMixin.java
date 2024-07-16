package ru.kelcuprum.alinperspective.mixin;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import ru.kelcuprum.alinperspective.AlinPerspective;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow
    private float yRot;
    @Shadow
    private float xRot;

    @Inject(method = "setup", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(FFF)V", ordinal = 0))
    public void setup$move(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info){
        if(AlinPerspective.config.getBoolean("ENABLE", false)){
            this.yRot = AlinPerspective.cameraPitch;
            this.xRot = AlinPerspective.cameraYaw;
        }
    }

    @ModifyArgs(method = "setup", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", ordinal = 0))
    public void setup$setRotation(Args args){
        if(AlinPerspective.config.getBoolean("ENABLE", false)){
            args.set(0, AlinPerspective.cameraPitch);
            args.set(1, AlinPerspective.cameraYaw);
        }
    }
}
