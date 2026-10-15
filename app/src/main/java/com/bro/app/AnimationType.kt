package com.bro.app

enum class AnimationType(val displayName: String) {
    FadeIn("Fade In"),
    FadeOut("Fade Out"),
    ScaleUp("Scale Up"),
    ScaleDown("Scale Down"),
    RotateClockwise("Rotate Clockwise"),
    RotateCounterClockwise("Rotate Counter‑Clockwise"),
    SlideInFromLeft("Slide In From Left"),
    SlideInFromRight("Slide In From Right"),
    SlideOutToLeft("Slide Out To Left"),
    SlideOutToRight("Slide Out To Right"),
    ColorChange("Color Change"),
    SizePulse("Size Pulse"),
    OpacityPulse("Opacity Pulse"),
    Bounce("Bounce"),
    FlipHorizontal("Flip Horizontal"),
    FlipVertical("Flip Vertical"),
    ExpandWidth("Expand Width"),
    CollapseWidth("Collapse Width"),
    Rotate3D("3‑D Rotate")
}
