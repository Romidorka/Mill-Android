package com.example.mill;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.icu.number.NumberFormatter;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class MyCanvas extends View{

    private Board9 board;
    private Rules rules;
    private Game game;
    private Paint paint;
    private Button restart_btn;
    String cords1="";
    String cords2="";
    float posX;
    float posY;
    int zeroX;
    int zeroY;

    public MyCanvas(Context context, Board9 board, Rules rules, Game game) {
        super(context);
        super.setBackgroundResource(R.drawable.background_gradient);
        this.board=board;
        this.rules=rules;
        this.game=game;
    }

    void drawBoard9(Canvas canvas){
        Bitmap bitmapSrcBoard9 = BitmapFactory.decodeResource(getResources(), R.drawable.board9);;
        Matrix matrixBoard9 = new Matrix();


        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap bitmapBoard9;
        int croppedWidth = Math.min(width, height);


        if (croppedWidth >= 1000) {
            bitmapBoard9 = Bitmap.createScaledBitmap(bitmapSrcBoard9, 1000, 1000, false);
        } else {
            bitmapBoard9 = Bitmap.createScaledBitmap(bitmapSrcBoard9, croppedWidth-100, croppedWidth-100, false);
        }
        zeroX = (width - bitmapBoard9.getWidth())/2;
        zeroY = (height - bitmapBoard9.getHeight()) /4;

        canvas.drawBitmap(bitmapBoard9, zeroX, zeroY, paint);
    }
    void drawBitmap(Canvas canvas, int res, float x, float y, int BitmapWidth, int BitmapHeight){
        Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.board9);;
        Matrix matrix = new Matrix();


        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap bitmap;
        int croppedWidth = Math.min(width, height);


        if (croppedWidth >= BitmapWidth) {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, BitmapWidth, BitmapHeight, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, croppedWidth-100, croppedWidth-100, false);
        }
        canvas.translate((width - bitmap.getWidth())/2, (height - bitmap.getHeight()) /4);

        canvas.drawBitmap(bitmap, dpToPixel(x), dpToPixel(y), paint);
    }

    void drawDot(float x, float y, int color, Canvas canvas){
        Bitmap bitmapSrcBlack = BitmapFactory.decodeResource(getResources(), R.drawable.black_dot);
        Bitmap bitmapSrcWhite = BitmapFactory.decodeResource(getResources(), R.drawable.white_dot);
        Matrix matrixBoard9 = new Matrix();

        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap bitmapBlack;
        Bitmap bitmapWhite;
        int croppedWidth = Math.min(width, height);

        if (croppedWidth >= 64) {
            bitmapBlack = Bitmap.createScaledBitmap(bitmapSrcBlack, 64, 64, false);
            bitmapWhite = Bitmap.createScaledBitmap(bitmapSrcWhite, 64, 64, false);
        } else {
            bitmapBlack = Bitmap.createScaledBitmap(bitmapSrcBlack, croppedWidth, croppedWidth, false);
            bitmapWhite = Bitmap.createScaledBitmap(bitmapSrcWhite, croppedWidth, croppedWidth, false);
        }

        if(color==1){
            canvas.drawBitmap(bitmapWhite, dpToPixel(x) - bitmapBlack.getWidth()/4 + zeroX, dpToPixel(y) - bitmapBlack.getHeight()/4 + zeroY, paint);
        }else if(color==2){
            canvas.drawBitmap(bitmapBlack, dpToPixel(x) - bitmapBlack.getWidth()/4 + zeroX, dpToPixel(y) - bitmapBlack.getHeight()/4 + zeroY, paint);
        }
    }

    void drawRestartButton(Canvas canvas){
        Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.restart);
        Matrix matrix = new Matrix();

        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap bitmap;
        int croppedWidth = Math.min(width, height);

        if (croppedWidth >= 128) {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, 128, 128, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, croppedWidth-100, croppedWidth-100, false);
        }
        float x = (width - bitmap.getWidth())/2;
        float y = (height - bitmap.getHeight())/4+245;
        board.restartButtonCords[0] = x;
        board.restartButtonCords[1] = y;

        canvas.drawBitmap(bitmap, dpToPixel(x), dpToPixel(y), paint);
    }

    void drawHomeButton(Canvas canvas){
        Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.home);
        Matrix matrix = new Matrix();

        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bitmap bitmap;
        int croppedWidth = Math.min(width, height);

        if (croppedWidth >= 128) {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, 128, 128, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(bitmapSrc, croppedWidth-100, croppedWidth-100, false);
        }
        float x = (width - bitmap.getWidth())/2-115;
        float y = (height - bitmap.getHeight())/4+245;
        board.homeButtonCords[0] = x;
        board.homeButtonCords[1] = y;

        canvas.drawBitmap(bitmap, dpToPixel(x), dpToPixel(y), paint);
    }

    void check_win(){
        if(rules.is_win(1)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("First player won!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();
        }else if(rules.is_win(2)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("First player won!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();
        }
    }



    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        float scale = (float) (float) getResources().getDisplayMetrics().scaledDensity/2;


        drawBoard9(canvas);
        System.out.println("X: " + dpToPixel(posX));
        System.out.println("Y: " + dpToPixel(posY) + "\n\n");
        for(int i=0;i<board.board.length;i++){
            drawDot(pixelsToDp(board.canvasCords[i][0]-60),pixelsToDp(board.canvasCords[i][1]), board.board[i], canvas);
        }
        drawRestartButton(canvas);
        drawHomeButton(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        posX = pixelsToDp(event.getX() - zeroX);
        posY = pixelsToDp(event.getY() - zeroY);

        if(board.restartButtonCalc(pixelsToDp(posX), pixelsToDp(posY))){
            board.restart();
            invalidate();
            return super.onTouchEvent(event);
        }

        if(board.homeButtonCalc(pixelsToDp(posX), pixelsToDp(posY))){
            Intent main_activity = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(main_activity);
            return super.onTouchEvent(event);
        }

        int index = board.touchToIndex(dpToPixel(posX), dpToPixel(posY));
        System.out.println("==================================");
        System.out.println(index);
        try {
            if (index != -1 && cords1.equals("")) {
                System.out.println("!!!!!!!");
                cords1 = board.indexToCords(index);
                if (!game.needSecondCords(cords1) && game.canTurn(cords1)) {
                    System.out.println("!!!!!!!!!!!!!!");
                    game.makeTurn(cords1);
                    game.newMillAppeared(cords1, cords1);
                    cords1 = "";
                    cords2 = "";
                }
            } else if (game.needSecondCords(cords1) && cords2.equals("")) {
                cords2 = board.indexToCords(index);
                if (game.canTurn(cords1, cords2)) {
                    game.makeTurn(cords1, cords2);
                    game.newMillAppeared(cords1, cords2);
                }
                cords1 = "";
                cords2 = "";
            }
        }catch (Exception e){
//            e.printStackTrace();
        }
        System.out.println("cords1: " + cords1);
        System.out.println("cords2: " + cords2);
        System.out.println(Arrays.toString(board.board));
        System.out.println("==================================");
        invalidate();
        check_win();
        return super.onTouchEvent(event);
    }

    public float dpToPixel(float dp){
        return dp * ((float) getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT) + 0.5f;
    }

    public float pixelsToDp(float px){
        return px / ((float) getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    static public float dpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT) + 0.5f;
    }

    static public float pixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
