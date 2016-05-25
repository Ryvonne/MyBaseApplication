package com.base.remiany.mybaseapplication.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationAssist {
    private final static String TAG = "AnimationAssist";

    /**
     * 可以控制控件的移入移出
     * <p>
     * 示例：
     * slideview(view, 1, 0);//从左边移入
     * slideview(view, 0, 1);//从左边移出
     * <p>
     * 注意：如果控件是隐藏的，记得要修改属性
     *
     * @param view
     * @param p1
     * @param p2
     */
    public static void slideview(final View view, final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, p1, Animation.RELATIVE_TO_SELF, p2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        //添加了这行代码的作用时，view移动的时候 会有弹性效果
//        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
//        animation.setStartOffset(delayMillis);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft() + (int) (p2 - p1);
                int top = view.getTop();
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left + width, top + height);
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 修改一个控件的制定值
     * "rotationX"
     *
     * @param valueName 要修改的控件的属性，必须要具有set和get的方法，否则无法通过反射获得
     * @param view
     */
    public static void rotateyAnimRun(String valueName, View view) {
        ObjectAnimator//
                .ofFloat(view, valueName, 0.0F, 360.0F)//
                .setDuration(500)//
                .start();
    }

    /**
     * 使用ObjectAnimator做多个动画
     *
     * @param view
     */
    public static void rotateyAnimRun(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "zhy", 1.0F, 0.0F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    /**
     * 使用propertyValuesHolder做多个动画
     *
     * @param view
     */
    public static void propertyValuesHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1000).start();
    }

    /**
     * 抛物线
     *
     * @param view
     */
    public static void paowuxian(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.e(TAG, fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                //这这里更新View的位置
//                mBlueBall.setX(point.x);
//                mBlueBall.setY(point.y);
            }
        });
    }

    /**
     * 动画监听类示意
     *
     * @param animator
     */
    public void addListener(Animator animator) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
