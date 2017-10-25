package com.example.huan.myanimation.utils;

/**
 * Created by huan on 2017/10/20.
 */

public class AnimationUtil {

    /**
     * 获取动画
     * @param animationType
     */
    public static Animator getAnimatorByType(int animationType,int w,int h) {
        Animator animator = null;
        switch (animationType){
            case AnimationUtil.AnimationType.HorizontalBlinds:
                animator = AnimationUtil.getHorizontalBlinds(w,h);
                break;
            case AnimationUtil.AnimationType.VerticleBlinds:
                animator = AnimationUtil.getVerticleBlinds(w,h);
                break;
            case AnimationType.BoxOpen:
                animator = AnimationUtil.getBoxOpen(w,h);
                break;
            case AnimationType.BoxClose:
                animator = AnimationUtil.getBoxClose(w,h);
                break;
            case AnimationType.HorizontalChessboard:
                animator = AnimationUtil.getHorizontalChessboard(w,h);
                break;
            case AnimationType.VerticleChessboard:
                animator = AnimationUtil.getVerticleChessboard(w,h);
                break;
            case AnimationType.HorizontalCarding:
                animator = AnimationUtil.getHorizontalCarding(w,h);
                break;
            case AnimationType.VerticleCarding:
                animator = AnimationUtil.getVerticleCarding(w,h);
                break;
            case AnimationType.Dissolution:
                animator = AnimationUtil.getDissolution(w,h);
                break;
            case AnimationType.ActionNews:
                animator = AnimationUtil.getActionNews(w,h);
                break;
            case AnimationType.StochasticHorizontalLine:
                animator = AnimationUtil.getStochasticHorizontalLine(w,h);
                break;
            case AnimationType.StochasticVerticalLine:
                animator = AnimationUtil.getStochasticVerticalLine(w,h);
                break;
            case AnimationType.CircleOpen:
                animator = AnimationUtil.getCircleOpen(w,h);
                break;
            case AnimationType.CircleClose:
                animator = AnimationUtil.getCircleClose(w,h);
                break;
            case AnimationType.DiamondOpen:
                animator = AnimationUtil.getDiamondOpen(w,h);
                break;
            case AnimationType.DiamondClose:
                animator = AnimationUtil.getDiamondClose(w,h);
                break;
            case AnimationType.CrossOpen:
                animator = AnimationUtil.getCrossOpen(w,h);
                break;
            case AnimationType.CrossClose:
                animator = AnimationUtil.getCrossClose(w,h);
                break;
            case AnimationType.DoorOpen:
                animator = AnimationUtil.getDoorOpen(w,h);
                break;
            case AnimationType.DoorClose:
                animator = AnimationUtil.getDoorClose(w,h);
                break;
            case AnimationType.LadderLeftUp:
                animator = AnimationUtil.getLadderLeftUp(w,h);
                break;
            case AnimationType.LadderLeftDown:
                animator = AnimationUtil.getLadderLeftDown(w,h);
                break;
            case AnimationType.LadderRightUp:
                animator = AnimationUtil.getLadderRightUp(w,h);
                break;
            case AnimationType.LadderRightDown:
                animator = AnimationUtil.getLadderRightDown(w,h);
                break;
            case AnimationType.BounceAway:
                animator = AnimationUtil.getBounceAway(w,h);
                break;
            case AnimationType.Tearing:
                animator = AnimationUtil.getTearing(w,h);
                break;
            case AnimationType.Reel:
                animator = AnimationUtil.getReel(w,h);
                break;

        }
        return animator;
    }


    public static Animator getHorizontalBlinds(int w,int h){
        HorizontalBlinds animator = new HorizontalBlinds(8);
        return animator;
    }
    public static Animator getVerticleBlinds(int w,int h){
        VerticleBlinds animator = new VerticleBlinds(8);
        return animator;
    }
    public static Animator getBoxClose(int w,int h){
        BoxClose animator = new BoxClose(8);
        return animator;
    }
    public static Animator getBoxOpen(int w,int h){
        BoxOpen animator = new BoxOpen(8);
        return animator;
    }

    public static Animator getHorizontalChessboard(int w,int h){
        HorizontalChessboard animator = new HorizontalChessboard(8);
        return animator;
    }

    private static Animator getVerticleChessboard(int w, int h) {
        VerticleChessboard animator = new VerticleChessboard(8);
        return animator;
    }
    private static Animator getHorizontalCarding(int w, int h) {
        HorizontalCarding animator = new HorizontalCarding(8);
        return animator;
    }
    private static Animator getVerticleCarding(int w, int h) {
        VerticleCarding animator = new VerticleCarding(8);
        return animator;
    }
    private static Animator getDissolution(int w, int h) {
        Dissolution animator = new Dissolution(16);
        return animator;
    }
    private static Animator getActionNews(int w, int h) {
        ActionNews animator = new ActionNews(16);
        return animator;
    }
    private static Animator getStochasticHorizontalLine(int w, int h) {
        Animator animator = new StochasticHorizontalLine(16);
        return animator;
    }
    private static Animator getStochasticVerticalLine(int w, int h) {
        Animator animator = new StochasticVerticalLine(16);
        return animator;
    }
    private static Animator getCircleOpen(int w, int h) {
        Animator animator = new CircleOpen(16);
        return animator;
    }
    private static Animator getCircleClose(int w, int h) {
        Animator animator = new CircleClose(16);
        return animator;
    }
    private static Animator getDiamondOpen(int w, int h) {
        Animator animator = new DiamondOpen(16);
        return animator;
    }
    private static Animator getDiamondClose(int w, int h) {
        Animator animator = new DiamondClose(16);
        return animator;
    }
    private static Animator getCrossOpen(int w, int h) {
        Animator animator = new CrossOpen(16);
        return animator;
    }
    private static Animator getCrossClose(int w, int h) {
        Animator animator = new CrossClose(16);
        return animator;
    }

    private static Animator getDoorOpen(int w, int h) {
        Animator animator = new DoorOpen(2);
        return animator;
    }
    private static Animator getDoorClose(int w, int h) {
        Animator animator = new DoorClose(2);
        return animator;
    }
    private static Animator getLadderLeftDown(int w, int h) {
        Animator animator = new LadderLeftDown(8);
        return animator;
    }
    private static Animator getLadderLeftUp(int w, int h) {
        Animator animator = new LadderLeftUp(8);
        return animator;
    }
    private static Animator getLadderRightUp(int w, int h) {
        Animator animator = new LadderRightUp(8);
        return animator;
    }
    private static Animator getLadderRightDown(int w, int h) {
        Animator animator = new LadderRightDown(8);
        return animator;
    }
    private static Animator getBounceAway(int w, int h) {
        Animator animator = new BounceAway(8);
        return animator;
    }
    private static Animator getTearing(int w, int h) {
        Animator animator = new Tearing(32);
        return animator;
    }
    private static Animator getReel(int w, int h) {
        Animator animator = new Reel(16);
        return animator;
    }



    public interface AnimationType{
         /**
          * 水平百叶窗
          */
         int HorizontalBlinds = 1;
         //垂直百叶窗
         int VerticleBlinds = 2;
        //盒状收缩
        int BoxOpen = 3;
        //盒状收缩
        int BoxClose = 4;
        //横向棋盘
        int HorizontalChessboard = 5;
        //纵向棋盘
        int VerticleChessboard =6;
        //水平梳理
        int HorizontalCarding =7;
        //吹制梳理
        int VerticleCarding =8;
        //溶解
        int Dissolution = 9;
        //新闻快报
        int ActionNews = 10;
        //随机水平线
        int StochasticHorizontalLine = 11;
        //随机垂直线
        int StochasticVerticalLine = 12;
        //圆形展开
        int CircleOpen = 13;
        //圆形关闭
        int CircleClose = 14;
        //菱形展开
        int DiamondOpen = 15;
        //菱形关闭
        int DiamondClose = 16;
        //十字交叉展开
        int CrossOpen = 17;
        //十字交叉关闭
        int CrossClose = 18;
        //开门效果
        int DoorOpen = 19;
        //关门效果
        int DoorClose = 20;
        //阶梯状左上方展开
        int LadderLeftUp = 21;
        //LadderLeftDown
        int LadderLeftDown = 22;
        //LadderLeftDown
        int LadderRightUp = 23;
        //LadderLeftDown
        int LadderRightDown = 24;
        //弹跳小球
        int BounceAway = 25;
        //撕裂效果
        int Tearing = 26;
        //卷轴效果
        int Reel = 27;
     }

}
