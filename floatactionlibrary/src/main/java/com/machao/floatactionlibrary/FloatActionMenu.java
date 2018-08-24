package com.machao.floatactionlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by：mc on 2018/8/24 15:42
 * email:
 * 浮动弹出菜单
 */
public class FloatActionMenu extends ViewGroup {

    private int viewOffset = 20;//菜单间距
    private int spreadDirection = SPREAD_UP;//展开方向，默认向上展开
    private int duration = 300;//动画持续时间
    private int[] drawables;//图标数组
    private int shadow=5;//阴影间歇

    public static final int SPREAD_UP = 0;//向上伸展
    public static final int SPREAD_LEFT = 1;//向左伸展
    public static final int SPREAD_RIGHT = 2;//向右伸展
    public static final int SPREAD_BOTTOM = 3;//向下伸展
    public static final int STATUS_OPNE = 4;//打开
    public static final int STATUS_CLOSE = 5;//关闭
    private int viewWidth;
    private int viewHeight;
    private Context context;
    private int status = STATUS_CLOSE;//状态 0 收拢 1 展开
    private int spreadSize;

    private List<ViewLocation> locations = new ArrayList<>();//坐标集合
    private List<ValueAnimator> opens = new ArrayList<>();//打开动画集合
    private List<ValueAnimator> closes = new ArrayList<>();//关闭动画集合




    public FloatActionMenu(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public FloatActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }


    public FloatActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatActionMenu, defStyleAttr, 0);
        viewOffset=a.getDimensionPixelOffset(R.styleable.FloatActionMenu_viewOffset,20);
        duration=a.getInteger(R.styleable.FloatActionMenu_duration,300);
        spreadDirection=a.getInteger(R.styleable.FloatActionMenu_spreadDirection,SPREAD_UP);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setSpreadSize();
        if (status == STATUS_CLOSE) {
            setMeasuredDimension(viewWidth+2*shadow, viewHeight+2*shadow);
        } else {
            if (spreadDirection == SPREAD_UP || spreadDirection == SPREAD_BOTTOM) {
                setMeasuredDimension(viewWidth, spreadSize);
            } else {
                setMeasuredDimension(spreadSize, viewHeight);
            }
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for (int j = 0; j < getChildCount(); j++) {
            View view = getChildAt(j);
            if (status == STATUS_CLOSE) {
                view.layout(shadow, shadow, viewWidth, viewHeight);
            } else {
                view.layout(locations.get(locations.size()-1).getLeft(), locations.get(locations.size()-1).getTop(), locations.get(locations.size()-1).getRight(), locations.get(locations.size()-1).getBottom());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        addAnimater();

    }

    /**
     * 开始动画
     */
    private void startAnimater() {
        if (status == STATUS_OPNE) {
            for (int i = 0; i < opens.size(); i++) {
                final int finalI = i;
                opens.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int offset = (int) valueAnimator.getAnimatedValue();
                        switch (spreadDirection) {
                            case SPREAD_LEFT:
                            case SPREAD_RIGHT:
                                getChildAt(finalI).layout(offset, locations.get(finalI).getTop(), offset + viewWidth, locations.get(finalI).getBottom());
                                break;
                            case SPREAD_UP:
                            case SPREAD_BOTTOM:
                                getChildAt(finalI).layout(locations.get(finalI).getLeft(), offset, locations.get(finalI).getRight(), offset + viewHeight);
                                break;
                        }
                    }
                });
                opens.get(i).start();
            }
        } else {
            for (int i = 0; i < closes.size(); i++) {
                final int finalI = i;
                closes.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int offset = (int) valueAnimator.getAnimatedValue();
                        switch (spreadDirection) {
                            case SPREAD_LEFT:
                            case SPREAD_RIGHT:
                                getChildAt(finalI).layout(offset, locations.get(finalI).getTop(), offset + viewWidth, locations.get(finalI).getBottom());
                                break;
                            case SPREAD_UP:
                            case SPREAD_BOTTOM:
                                getChildAt(finalI).layout(locations.get(finalI).getLeft(), offset, locations.get(finalI).getRight(), offset + viewHeight);
                                break;
                        }
                    }
                });
                closes.get(i).start();
            }
        }
    }

    private void addAnimater() {
        if (opens.size() > 0) return;
        for (int i = 0; i < locations.size()-1; i++) {
            ValueAnimator openAnimator = null;
            ValueAnimator closeAnimator = null;
            switch (spreadDirection) {
                case SPREAD_LEFT:
                case SPREAD_RIGHT:
                    openAnimator = ValueAnimator.ofInt(locations.get(locations.size()-1).getLeft(), locations.get(i).getLeft());
                    openAnimator.setInterpolator(new BounceInterpolator());
                    closeAnimator = ValueAnimator.ofInt(locations.get(i).getLeft(), locations.get(locations.size()-1).getLeft());
                    break;
                case SPREAD_UP:
                case SPREAD_BOTTOM:
                    openAnimator = ValueAnimator.ofInt(locations.get(locations.size()-1).getTop(), locations.get(i).getTop());
                    openAnimator.setInterpolator(new BounceInterpolator());
                    closeAnimator = ValueAnimator.ofInt(locations.get(i).getTop(), locations.get(locations.size()-1).getTop());
                    break;
            }
            assert openAnimator != null;
            openAnimator.setDuration(duration);
            closeAnimator.setDuration(duration);
            opens.add(openAnimator);
            closes.add(closeAnimator);
        }
    }

    /**
     * 添加图标
     *
     * @param drawable
     */
    public FloatActionMenu setMenuDrawable(int... drawable) {
        drawables = drawable;
        addChild();
        return this;
    }

    private void addChild() {
        for (int i = drawables.length - 1; i >= 0; i--) {
            FloatingActionButton button = new FloatingActionButton(context);
            button.setScaleType(ImageView.ScaleType.CENTER);
            button.setImageResource(drawables[i]);
            final int finalI = i;
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0) {
                        status = status == STATUS_OPNE ? STATUS_CLOSE : STATUS_OPNE;
                        if (status == STATUS_OPNE) {
                            requestLayout();
                            invalidate();
                        }
                        startAnimater();
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (status == STATUS_CLOSE) {
                                    requestLayout();
                                    invalidate();
                                }
                            }
                        }, duration);
                    } else {
                        itemClick.itemClick(finalI);
                    }
                }
            });
            this.addView(button);
        }
    }

    /**
     * 设置图标展开后的宽高
     */
    private void setSpreadSize() {
        locations.clear();
        spreadSize = drawables.length * viewWidth + viewOffset * (drawables.length - 1);
        for (int i = 0; i < drawables.length; i++) {
            ViewLocation location = new ViewLocation();
            switch (spreadDirection) {
                case SPREAD_LEFT:
                    location.setLeft(i * (viewWidth + viewOffset));
                    location.setTop(0);
                    location.setBottom(viewHeight);
                    location.setRight(i * (viewWidth + viewOffset)+viewWidth);
                    break;
                case SPREAD_RIGHT:
                    location.setLeft((drawables.length-1)*(viewWidth+viewOffset)-i * (viewWidth + viewOffset));
                    location.setTop(0);
                    location.setBottom(viewHeight);
                    location.setRight((drawables.length-1)*(viewWidth+viewOffset)-i * (viewWidth + viewOffset) + viewWidth);
                    break;
                case SPREAD_UP:
                    location.setTop(i * (viewHeight + viewOffset));
                    location.setLeft(0);
                    location.setRight(viewWidth);
                    location.setBottom(i * (viewHeight + viewOffset) + viewHeight);
                    break;
                case SPREAD_BOTTOM:
                    location.setLeft(0);
                    location.setRight(viewWidth);
                    location.setTop((drawables.length - 1) * (viewHeight + viewOffset) - i * (viewHeight + viewOffset));
                    location.setBottom((drawables.length-1)*(viewHeight+viewOffset)-i * (viewWidth + viewOffset) + viewHeight);
                    break;
            }
            locations.add(location);
        }
    }

    private OnItemClick itemClick;

    /**
     * 回调
     */
    public interface OnItemClick{
        void itemClick(int item);
    }

    /**
     * item点击事件
     * @param itemClick
     */
    public void setOnItemClickListener(OnItemClick itemClick){
        this.itemClick=itemClick;
    }
}
