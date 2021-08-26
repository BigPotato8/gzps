package com.augurit.agmobile.agwater5.gcjspad.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.augurit.agmobile.busi.map.utils.DensityUtil;

public class StageItemView extends View {
    private String stageName;//阶段名称
    private int fontClor;//字体颜色
    private int stageNum;//阶段数量
    private int stageIndex;//第几个阶段
    private int finishedColor;//已完成的颜色
    private int doingColor;//正在做的颜色
    private int unfinishColor;//未完成的颜色
    private int spaceColor = 0xffffffff;//间隔颜色，白色
    private int textSize = 12;//字体大小，12dp
    private int radiusSize = 5;//圆角半径，5dp

    public StageItemView(Context context) {
        super(context);
    }

    public StageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StageItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAttr(String stageName, int fontClor,
                        int stageNum,int stageIndex,
                        int finishedColor,int doingColor,
                        int unfinishColor,int spaceColor,int textSize){
        this.stageName = stageName;
        this.fontClor = fontClor;
        this.stageNum = stageNum;
        this.stageNum = stageNum;
        this.stageIndex = stageIndex;
        this.finishedColor = finishedColor;
        this.doingColor = doingColor;
        this.unfinishColor = unfinishColor;
        this.spaceColor = spaceColor;
        this.textSize = textSize;

        invalidate();//刷新
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int halfHeight = (getHeight() + 1) / 2;
        float triangleHeight = (float) (halfHeight * Math.tan(Math.PI / 6));//30°的直角边，得对边长度

        int sc = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

        //阶段数量，1.画已完成，2.画正在进行，3.画未完成，4.画间隔线，5.画字体
        //绘制文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(fontClor);
        textPaint.setTextSize(DensityUtil.dp2px(getContext(), textSize));
        float textWidth = textPaint.measureText(stageName) + DensityUtil.dp2px(getContext(), 2);//预留2dp的padding
        String printText = "";
        if (textWidth > (width / 2)) {//长度大于一半，替换为省略号
            float size = (stageName.length() * (width / 2) / (textWidth));
            printText = stageName.substring(0, (int) size-1) + "...";
        } else {
            printText = stageName;
        }
        float textWidthFinal = textPaint.measureText(printText);//最终确定的字体大小
        float spaceLineWidth = (width - textWidthFinal) / (stageNum);//每个阶段间隔
        float lineWidth = (width - textWidthFinal);//阶段的长度，不包括文字
        float finishedWidth = stageIndex == 1 ? 0 : lineWidth * (stageIndex-1) / stageNum;//已完成阶段长度
//        float unfinishWidth = lineWidth * (stageNum - stageIndex) / stageNum;//未完成阶段长度

        if (finishedWidth != 0) {//已完成的阶段不为0，绘制五边形
            Path path = new Path();
            path.moveTo(0, 0);//起点
            path.lineTo(finishedWidth, 0);
            path.lineTo(finishedWidth + triangleHeight, halfHeight);
            path.lineTo(finishedWidth, height);
            path.lineTo(0, height);
            path.lineTo(0, 0);
            path.close(); // 使这些点构成封闭的多边形

            Paint finishPaint = new Paint();
            finishPaint.setAntiAlias(true);
            finishPaint.setColor(finishedColor);
            finishPaint.setStyle(Paint.Style.FILL);//实心
            canvas.drawPath(path, finishPaint);
        }
        Path doingPath = new Path();//正在进行的阶段
        doingPath.moveTo(finishedWidth, 0);//起点
        doingPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth, 0);
        doingPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth + triangleHeight, halfHeight);
        doingPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth, height);
        doingPath.lineTo(finishedWidth, height);
        if (finishedWidth != 0) {//如果第一个阶段不是当前阶段，需要画箭头
            doingPath.lineTo(finishedWidth + triangleHeight, halfHeight);
        }
        doingPath.lineTo(finishedWidth, 0);
        doingPath.close(); // 使这些点构成封闭的多边形

        Paint doingPaint = new Paint();
        doingPaint.setAntiAlias(true);
        doingPaint.setColor(doingColor);
        doingPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(doingPath, doingPaint);

        Path unfinishPath = new Path();//未完成的阶段
        unfinishPath.moveTo(finishedWidth + textWidthFinal, 0);//起点
        unfinishPath.lineTo(width, 0);
        unfinishPath.lineTo(width, height);
        unfinishPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth, height);
        unfinishPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth + triangleHeight, halfHeight);
        unfinishPath.lineTo(finishedWidth + textWidthFinal + spaceLineWidth, 0);
        unfinishPath.close();

        Paint unfinishPaint = new Paint();
        unfinishPaint.setAntiAlias(true);
        unfinishPaint.setColor(unfinishColor);
        unfinishPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(unfinishPath, unfinishPaint);

        //绘制间隔线

        Paint spacePiant = new Paint();
        spacePiant.setAntiAlias(true);
        spacePiant.setColor(spaceColor);
        spacePiant.setStrokeWidth(DensityUtil.dp2px(getContext(), 1));
        spacePiant.setStyle(Paint.Style.FILL);
        if (finishedWidth != 0) {//已完成间隔线
            for (int i = 1; i < stageIndex; i++) {
                canvas.drawLine(i * spaceLineWidth,0,i * spaceLineWidth + triangleHeight,halfHeight,spacePiant);
                canvas.drawLine(i * spaceLineWidth + triangleHeight,halfHeight,i * spaceLineWidth,height,spacePiant);
            }
        }

        for (int i = stageIndex; i < stageNum; i++) {//未完成间隔线

            canvas.drawLine(i * spaceLineWidth + textWidthFinal,0,i * spaceLineWidth + textWidthFinal + triangleHeight,halfHeight,spacePiant);
            canvas.drawLine(i * spaceLineWidth + textWidthFinal + triangleHeight,halfHeight,i * spaceLineWidth + textWidthFinal,height,spacePiant);

        }
        //绘制文本，垂直居中
        Paint.FontMetricsInt fmi = textPaint.getFontMetricsInt();
        int baseLine = halfHeight - (fmi.bottom - fmi.top) / 2 - fmi.top;//字体基准线
        canvas.drawText(printText,(stageIndex-1)*spaceLineWidth + triangleHeight*3/2 , baseLine ,textPaint);

        //绘制圆角
        Path pathBase = new Path();
        float radiusSizeFinal = DensityUtil.dp2px(getContext(), radiusSize);
        float[] radiusArray = {radiusSizeFinal,radiusSizeFinal,radiusSizeFinal,radiusSizeFinal,
                radiusSizeFinal,radiusSizeFinal,radiusSizeFinal,radiusSizeFinal};
        pathBase.addRoundRect(new RectF(0, 0, width, height), radiusArray, Path.Direction.CW);
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        bitmapPaint.setColor(Color.GREEN); // 颜色随意，不要有透明度。
        canvas.drawPath(pathBase, bitmapPaint);
        canvas.restoreToCount(sc);
    }
}
