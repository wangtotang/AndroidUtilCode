package com.blankj.androidutilcode.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.androidutilcode.span.BlurMaskFilterSpan;
import com.blankj.androidutilcode.span.ForegroundAlphaColorSpan;
import com.blankj.androidutilcode.span.ForegroundAlphaColorSpanGroup;
import com.blankj.androidutilcode.span.ShadowSpan;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : Span工具类Demo
 * </pre>
 */
public class SpanActivity extends BaseActivity {

    SpanUtils              mSpanUtils;
    SpannableStringBuilder animSsb;

    int           lineHeight;
    float         textSize;
    ValueAnimator valueAnimator;
    Shader        mShader;
    float         mShaderWidth;
    Matrix        matrix;

    BlurMaskFilterSpan mBlurMaskFilterSpan;

    ShadowSpan mShadowSpan;

    ForegroundAlphaColorSpan mForegroundAlphaColorSpan;

    ForegroundAlphaColorSpanGroup mForegroundAlphaColorSpanGroup;
    String                        mPrinterString;


    float    density;
    TextView tvAboutSpan;
    TextView tvAboutAnimRainbow;
    TextView tvAboutAnimBlur;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_span;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showShort("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        tvAboutSpan = (TextView) findViewById(R.id.tv_about_span);
        tvAboutAnimRainbow = (TextView) findViewById(R.id.tv_about_anim_span);

        // 响应点击事件的话必须设置以下属性
        tvAboutSpan.setMovementMethod(LinkMovementMethod.getInstance());
        lineHeight = tvAboutSpan.getLineHeight();
        textSize = tvAboutSpan.getTextSize();
        density = getResources().getDisplayMetrics().density;

        tvAboutSpan.setText(new SpanUtils()
                .appendLine("SpanUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
                .appendLine("行高顶部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
                .appendLine("行高居中对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("行高底部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("测试段落缩，首行缩进两字，其他行不缩进").setLeadingMargin((int) textSize * 2, 10).setBackgroundColor(Color.GREEN)
                .appendLine("测试引用，后面的字是为了凑到两行的效果").setQuoteColor(Color.GREEN, 10, 10).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试列表项，后面的字是为了凑到两行的效果").setBullet(Color.GREEN, 20, 10).setBackgroundColor(Color.LTGRAY).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标文字顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标文字居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标文字底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("32dp字体").setFontSize(32, true)
                .appendLine("2倍字体").setFontProportion(2)
                .appendLine("横向2倍字体").setFontXProportion(1.5f)
                .appendLine("删除线").setStrikethrough()
                .appendLine("下划线").setUnderline()
                .append("测试").appendLine("上标").setSuperscript()
                .append("测试").appendLine("下标").setSubscript()
                .appendLine("粗体").setBold()
                .appendLine("斜体").setItalic()
                .appendLine("粗斜体").setBoldItalic()
                .appendLine("monospace字体").setFontFamily("monospace")
                .appendLine("自定义字体").setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dnmbhs.ttf"))
                .appendLine("相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .appendLine("居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试").appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试").appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试").appendLine("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .appendLine("颜色渐变").setShader(new LinearGradient(0, 0,
                        64 * density * 4, 0,
                        getResources().getIntArray(R.array.rainbow),
                        null,
                        Shader.TileMode.REPEAT)).setFontSize(64, true)
                .appendLine("图片着色").setFontSize(64, true).setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.cheetah),
                        Shader.TileMode.REPEAT,
                        Shader.TileMode.REPEAT))
                .appendLine("阴影效果").setFontSize(64, true).setBackgroundColor(Color.BLACK).setShadow(24, 8, 8, Color.WHITE)

                .append("测试小图对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_TOP)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_CENTER)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BASELINE)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BOTTOM)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)

                .append("测试大图字体顶部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_TOP)
                .appendLine()

                .append("测试大图字体居中对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_CENTER)
                .appendLine()

                .append("测试大图字体底部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_BOTTOM)
                .appendLine()

                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
                .create());

        initAnimSpan();
        startAnim();
    }

    private void initAnimSpan() {
        mShaderWidth = 64 * density * 4;
        mShader = new LinearGradient(0, 0,
                mShaderWidth, 0,
                getResources().getIntArray(R.array.rainbow),
                null,
                Shader.TileMode.REPEAT);
        matrix = new Matrix();

        mBlurMaskFilterSpan = new BlurMaskFilterSpan(25);

        mShadowSpan = new ShadowSpan(8, 8, 8, Color.WHITE);

        mForegroundAlphaColorSpan = new ForegroundAlphaColorSpan(Color.TRANSPARENT);

        mForegroundAlphaColorSpanGroup = new ForegroundAlphaColorSpanGroup(0);

        mPrinterString = "打印动画，后面的文字是为了测试打印效果...";

        mSpanUtils = new SpanUtils()
                .appendLine("彩虹动画").setFontSize(64, true).setShader(mShader)
                .appendLine("模糊动画").setFontSize(64, true).setSpans(mBlurMaskFilterSpan)
                .appendLine("阴影动画").setFontSize(64, true).setBackgroundColor(Color.BLACK).setSpans(mShadowSpan)
                .appendLine("透明动画").setFontSize(64, true).setSpans(mForegroundAlphaColorSpan);
        for (int i = 0, len = mPrinterString.length(); i < len; ++i) {
            ForegroundAlphaColorSpan span = new ForegroundAlphaColorSpan(Color.TRANSPARENT);
            mSpanUtils.append(mPrinterString.substring(i, i + 1)).setSpans(span);
            mForegroundAlphaColorSpanGroup.addSpan(span);
        }
        animSsb = mSpanUtils.create();
    }

    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // shader
                matrix.reset();
                matrix.setTranslate((Float) animation.getAnimatedValue() * mShaderWidth, 0);
                mShader.setLocalMatrix(matrix);

                // blur
                mBlurMaskFilterSpan.setRadius(25 * (1.00001f - (Float) animation.getAnimatedValue()));

                // shadow
                mShadowSpan.setDx(16 * (0.5f - (Float) animation.getAnimatedValue()));
                mShadowSpan.setDy(16 * (0.5f - (Float) animation.getAnimatedValue()));

                // alpha
                mForegroundAlphaColorSpan.setAlpha((int) (255 * (Float) animation.getAnimatedValue()));

                // printer
                mForegroundAlphaColorSpanGroup.setAlpha((Float) animation.getAnimatedValue());

                // update
                tvAboutAnimRainbow.setText(animSsb);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(600 * 3);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        valueAnimator.cancel();
        super.onDestroy();
    }
}
