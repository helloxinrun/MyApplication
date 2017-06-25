package com.example.luoxinrun.myapplication.bubbleview;

/**
 * Created by luoxinrun on 2017/6/24.
 */

public interface BubbleViewAttrs {
    /**
     * 设置箭头的方向
     */
    enum ArrowLocation {
        LEFT(0x00), RIGHT(0x01), TOP(0x02), BOTTOM(0x03);

        private int mValue;

        ArrowLocation(int value) {
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int state) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (state == value.getValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowLocation getDefault() {
            return LEFT;
        }

        public int getValue() {
            return mValue;
        }
    }

    /**
     * 设置箭头在某个方向上的相对原始点
     */
    enum ArrowRelative {
        BEGIN(0x00), CENTER(0x01), END(0x02);

        private int mValue;

        ArrowRelative(int value) {
            this.mValue = value;
        }

        public static ArrowRelative mapIntToValue(int state) {
            for (ArrowRelative value : ArrowRelative.values()) {
                if (state == value.getValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowRelative getDefault() {
            return BEGIN;
        }

        public int getValue() {
            return mValue;
        }
    }

    /**
     * 设置气泡背景类型
     */
    enum BubbleBgType {
        COLOR(0x00), BITMAP(0x01);

        private int mValue;

        BubbleBgType(int value) {
            this.mValue = value;
        }

        public static BubbleBgType mapIntToValue(int state) {
            for (BubbleBgType value : BubbleBgType.values()) {
                if (state == value.getValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static BubbleBgType getDefault() {
            return COLOR;
        }

        public int getValue() {
            return mValue;
        }
    }

    /**
     * 确定箭头的宽高
     *
     * @param arrowWidth
     * @param arrowHeight
     */
    void setArrowWidthHeight(float arrowWidth, float arrowHeight);

    float getArrowWidth();

    float getArrowHeight();

    /**
     * 确定箭头的位置
     *
     * @param arrowLocation 箭头方向
     * @param arrowRelative 某个方向上的相对原始点
     * @param arrowPosition 距离原始点的距离
     */
    void setArrowPos(ArrowLocation arrowLocation, ArrowRelative arrowRelative, float arrowPosition);

    ArrowLocation getArrowLocation();

    ArrowRelative arrowRelative();

    float getArrowPosition();

    /**
     * 确定气泡的圆角半径
     *
     * @param bubbleRadius
     */
    void setBubbleRadius(float bubbleRadius);

    void setBubbleRadius(float leftTopRadiu, float rightTopRadiu, float leftBottomRadiu,
                         float rightBottomRadiu);

    float getBubbleLeftTopRadiu();

    float getBubbleRightTopRadiu();

    float getBubbleLeftBottomRadiu();

    float getBubbleRightBottomRadiu();

    /**
     * 确定气泡的背景颜色
     *
     * @param color
     */
    void setBubbleColor(int color);

    int getBubbleColor();

    /**
     * 确定气泡的背景类型
     *
     * @param bubbleBgType
     */
    void setBubbleBgType(BubbleBgType bubbleBgType);

    BubbleBgType getBubbleBgType();

}
