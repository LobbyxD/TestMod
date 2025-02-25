package com.bruno.testmod.entity.client;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.custom.BrunoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BrunoModel<T extends BrunoEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "bruno"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public BrunoModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = root.getChild("root").getChild("base").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = base.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.5F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-4.25F, 2.0F, -2.0F, 1.25F, 0.75F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 8).addBox(-2.5F, 4.0F, -2.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(2.5F, 4.0F, -2.0F, 2.25F, 0.5F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).addBox(2.0F, 0.5F, -2.5F, 2.0F, 3.5F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(4.0F, 1.0F, -2.5F, 3.0F, 3.25F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legs = torso.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition front_legs = legs.addOrReplaceChild("front_legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_front = front_legs.addOrReplaceChild("left_leg_front", CubeListBuilder.create(), PartPose.offset(-1.7F, 5.0F, -2.0F));

        PartDefinition left_left_front_base = left_leg_front.addOrReplaceChild("left_left_front_base", CubeListBuilder.create().texOffs(0, 25).addBox(-1.05F, -1.25F, -0.75F, 2.0F, 1.25F, 1.75F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_left_front_leg = left_leg_front.addOrReplaceChild("left_left_front_leg", CubeListBuilder.create().texOffs(8, 27).addBox(-0.55F, -0.5F, -0.5F, 1.25F, 3.5F, 1.25F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_front = front_legs.addOrReplaceChild("right_leg_front", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_front_base = right_leg_front.addOrReplaceChild("right_leg_front_base", CubeListBuilder.create().texOffs(26, 12).addBox(-2.75F, 3.75F, 0.0F, 2.0F, 1.25F, 1.75F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_front_leg = right_leg_front.addOrReplaceChild("right_leg_front_leg", CubeListBuilder.create().texOffs(26, 15).addBox(-0.45F, 0.0F, -0.75F, 1.25F, 4.0F, 1.25F, new CubeDeformation(0.0F)), PartPose.offset(-1.8F, 4.0F, 1.0F));

        PartDefinition back_legs = legs.addOrReplaceChild("back_legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_back = back_legs.addOrReplaceChild("left_leg_back", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_back_base = left_leg_back.addOrReplaceChild("left_leg_back_base", CubeListBuilder.create().texOffs(12, 20).addBox(4.75F, 3.75F, -2.75F, 2.25F, 1.75F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg_back_leg = left_leg_back.addOrReplaceChild("left_leg_back_leg", CubeListBuilder.create().texOffs(12, 27).addBox(-0.5F, -0.5F, -0.55F, 1.25F, 3.5F, 1.25F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 5.0F, -1.7F));

        PartDefinition right_leg_back = back_legs.addOrReplaceChild("right_leg_back", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_back_base = right_leg_back.addOrReplaceChild("right_leg_back_base", CubeListBuilder.create().texOffs(20, 20).addBox(4.75F, 3.75F, -0.25F, 2.25F, 1.75F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg_back_leg = right_leg_back.addOrReplaceChild("right_leg_back_leg", CubeListBuilder.create().texOffs(16, 27).addBox(-0.5F, 0.5F, -0.7F, 1.25F, 3.5F, 1.25F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 4.0F, 0.7F));

        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-5.75F, -1.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 4).addBox(-5.5F, -1.5F, -1.75F, 2.5F, 0.5F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_ear = ears.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(8, 21).addBox(-0.75F, -0.75F, -2.5F, 1.0F, 1.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -1.0F, 0.0F));

        PartDefinition ear_r1 = left_ear.addOrReplaceChild("ear_r1", CubeListBuilder.create().texOffs(20, 27).addBox(-0.75F, -0.9F, -1.25F, 1.0F, 1.0F, 2.02F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, -2.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition right_ear = ears.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(28, 0).addBox(-4.75F, -1.75F, 0.5F, 1.0F, 1.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ear_r2 = right_ear.addOrReplaceChild("ear_r2", CubeListBuilder.create().texOffs(26, 27).addBox(1.0F, -0.9F, -0.77F, 1.0F, 1.0F, 2.02F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, -0.5F, 1.25F, -0.7854F, 0.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(18, 24).addBox(-7.25F, 0.5F, -1.25F, 2.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail2 = base.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 3).addBox(7.0F, 2.0F, -1.25F, 2.0F, 1.25F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_r1 = tail2.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 1.0F, -0.5F, 0.25F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.5F, -0.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition tail_r2 = tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 2).addBox(-3.0F, -0.75F, -0.5F, 2.0F, 0.75F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 0.5F, -0.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition tail_r3 = tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(0, 2).addBox(-3.0F, -1.0F, -0.5F, 2.0F, 1.25F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, 2.0F, -0.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition tail = base.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(7.0F, 3.0F, -0.5F));

        PartDefinition tail_r4 = tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(8, 24).addBox(-3.0F, -1.0F, -0.75F, 3.0F, 1.25F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, 0.0F));

        PartDefinition tail_r5 = tail_tip.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(0, 28).addBox(-0.75F, 0.0F, -0.75F, 0.75F, 3.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.5F, 0.0F, 0.0F, 0.0F, 0.3927F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(BrunoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animateWalk(BrunoAnimations.ANIM_BRUNO_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, BrunoAnimations.ANIM_BRUNO_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0f, 30.0f);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0f, 45.0f);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180f);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
