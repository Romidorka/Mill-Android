package com.example.mill;

/*
 0(.) - Empty
 1(@)- White player(1)
 2(#)- Black player(2)

 PHASE
 0 - placing
 1 - moving
 */


import java.util.Arrays;

public class Board9 {
    public final static int PHASE_PLACING = 0;
    public final static int PHASE_MOVING = 1;

    int[] board;
    int[][] canvasCords;
    int[] is_mill;
    int[][] neighbors;

    int phase = PHASE_PLACING;
    int[] died_dots = {0, 0};
    int[] placed_dots = {0, 0};
    int new_mill_cords = -1;
    public boolean mustKill=false;
    private int men_count;
    private int men_need_to_fly;

    protected void init(){
        board = new int[24];
        canvasCords = new int[24][2];
        Arrays.fill(canvasCords, new int[]{0, 0});
        is_mill = new int[24];
        neighbors = new int[24][4];

        neighbors[0]= new int[]{1, -1, 9, -1};
        neighbors[1]= new int[]{2, 0, 4, -1};
        neighbors[2]= new int[]{-1, 1, 14, -1};
        neighbors[3]= new int[]{4, -1, 10, -1};
        neighbors[4]= new int[]{5, 3, 7, 1};
        neighbors[5]= new int[]{-1, 4, 13, -1};
        neighbors[6]= new int[]{7, -1, 11, -1};
        neighbors[7]= new int[]{8, 6, -1, 4};
        neighbors[8]= new int[]{-1, 7, 12, -1};
        neighbors[9]= new int[]{10, -1, 21, 0};
        neighbors[10]= new int[]{11, 9, 18, 3};
        neighbors[11]= new int[]{-1, 10, 15, 6};
        neighbors[12]= new int[]{13, -1, 17, 8};
        neighbors[13]= new int[]{14, 12, 20, 5};
        neighbors[14]= new int[]{-1, 13, 23, 2};
        neighbors[15]= new int[]{16, -1, -1, 11};
        neighbors[16]= new int[]{17, 15, 19, -1};
        neighbors[17]= new int[]{-1, 16, -1, 12};
        neighbors[18]= new int[]{19, -1, -1, 10};
        neighbors[19]= new int[]{20, 18, 22, 16};
        neighbors[20]= new int[]{-1, 19, -1, 13};
        neighbors[21]= new int[]{22, -1, -1, 9};
        neighbors[22]= new int[]{23, 21, -1, 19};
        neighbors[23]= new int[]{-1, 22, -1, 14};

        canvasCords[0]= new int[]{60, 0};
        canvasCords[1]= new int[]{353, 0};
        canvasCords[2]= new int[]{646, 0};
        canvasCords[3]= new int[]{176, 106};
        canvasCords[4]= new int[]{356, 106};
        canvasCords[5]= new int[]{544, 106};
        canvasCords[6]= new int[]{292, 229};
        canvasCords[7]= new int[]{356, 229};
        canvasCords[8]= new int[]{426, 229};
        canvasCords[9]= new int[]{60, 295};
        canvasCords[10]= new int[]{176, 295};
        canvasCords[11]= new int[]{292, 295};
        canvasCords[12]= new int[]{426, 295};
        canvasCords[13]= new int[]{544, 295};
        canvasCords[14]= new int[]{646, 295};
        canvasCords[15]= new int[]{292, 361};
        canvasCords[16]= new int[]{356, 361};
        canvasCords[17]= new int[]{426, 361};
        canvasCords[18]= new int[]{176, 458};
        canvasCords[19]= new int[]{356, 458};
        canvasCords[20]= new int[]{544, 458};
        canvasCords[21]= new int[]{60, 595};
        canvasCords[22]= new int[]{356, 595};
        canvasCords[23]= new int[]{646, 595};

    }

    Board9(){
        init();

        for(byte i=0;i<24;i++){
            board[i]=0;
        }
    }

    public void place(int player, String cords){
        int index = cordsToIndex(cords);

        board[index] = (byte)player;
        placed_dots[player-1]+=1;

        if(placed_dots[0] == men_count && placed_dots[1] == men_count){
            phase = PHASE_MOVING;
        }
    }

    public void move(int player, String cords1, String cords2){
        int index1 = cordsToIndex(cords1);
        int index2 = cordsToIndex(cords2);
        board[index1]=0;
        board[index2]=player;
    }

    public void fly(int player, String cords1, String cords2){
        int index1 = cordsToIndex(cords1);
        int index2 = cordsToIndex(cords2);
        board[index1]=0;
        board[index2]=player;
    }

    public void kill(int player, String cords1, String cords2){
        int index1 = cordsToIndex(cords1);
        int index2 = cordsToIndex(cords2);
        board[index2]=0;
        died_dots[player-1]+=1;
    }

    public void check_mills(int index){ // Обновляет массив с мельницами
        int[] n = get_neighbors(index);
        check_mill(index); // Проверяем центральную клетку
        for(int i=0;i<4;i++){ // Проверяем клетки воокруг центральной
            if(n[i] != -1){
                check_mill(n[i]);
            }
        }
    }

    public boolean in_mills(int index){ // Принадлежит ли точка к мельнице
        int[] n = get_neighbors(index);
        for(int i=0;i<4;i++){
            if(n[i] != -1){
                if(in_mill(n[i])) return true;
            }
        }
        return false;
    }

    private boolean in_mill(int index){ // Является ли точка центром мельницы
        int[] n = get_neighbors(index);
        if(n[0]!=-1 && n[1]!=-1) {
            if (board[n[0]] == board[n[1]] && board[n[0]] == board[index] && is_mill[n[0]] != board[n[0]]) {
                return true;
            }
        }
        if(n[2]!=-1 && n[3]!=-1) {
            if (board[n[2]] == board[n[3]] && board[n[2]] == board[index] && is_mill[n[2]] != board[n[2]]) {
                return true;
            }
        }
        return false;
    }

    private void check_mill(int index){
        int[] n = get_neighbors(index);

        if(n[0]!=-1 && n[1]!=-1) {
            if (board[n[0]] == board[n[1]] && board[n[0]] == board[index]) {
                is_mill[n[0]] = board[n[0]];
                is_mill[n[1]] = board[n[0]];
                is_mill[index] = board[n[0]];
                new_mill_cords = index;
            } else {
                is_mill[n[0]] = 0;
                is_mill[n[1]] = 0;
                is_mill[index] = 0;
            }
        }
        if(n[2]!=-1 && n[3]!=-1 && n[0]!=-1) {
            if (board[n[2]] == board[n[3]] && board[n[2]] == board[index]) {
                is_mill[n[2]] = board[n[0]];
                is_mill[n[3]] = board[n[0]];
                is_mill[index] = board[n[0]];
                new_mill_cords = index;
            } else {
                is_mill[n[2]] = 0;
                is_mill[n[3]] = 0;
            }
        }
    }

    public boolean is_neighbor(int index, int neighbor){
        int[] n = get_neighbors(index);
        for(int i=0;i<4;i++){
            if(n[i] == neighbor){
                return true;
            }
        }
        return false;
    }

    public int[] get_neighbors(int index){
        return neighbors[index];
    }

    public static int opponent(int player){
        switch (player) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public String indexToCords(int index){
        String[] cordsArray ={
                "7a", "7d", "7g",
                "6b", "6d", "6f",
                "5c", "5d", "5e",
                "4a", "4b", "4c",
                "4e", "4f", "4g",
                "3c", "3d", "3e",
                "2b", "2d", "2f",
                "1a", "1d", "1g"
        };
        return cordsArray[index];
    }

    public int cordsToIndex(String cords){
        String[] cordsArray ={
                "7a", "7d", "7g",
                "6b", "6d", "6f",
                "5c", "5d", "5e",
                "4a", "4b", "4c",
                "4e", "4f", "4g",
                "3c", "3d", "3e",
                "2b", "2d", "2f",
                "1a", "1d", "1g"
        };
        for(int i=0;i<24;i++){
            if(cordsArray[i].equals(cords)){
                return i;
            }
        }
        return -1;
    }

    public int touchToIndex(float x, float y){
        for (int i=0;i<canvasCords.length;i++){
//            System.out.println("X: " + (canvasCords[i][0]-x-60) + "   Y: "+ (canvasCords[i][1]-y));
            if(Math.abs(canvasCords[i][0]-x-60)<=30 && Math.abs(canvasCords[i][1]-y)<=30) {
                return i;
            }
        }
        return -1;
    }

    public void randomize_board(){
        for(int i=0;i<24;i++){
            board[i]=(byte) (Math.random()*3);
        }
    }

    public void setRules(int men_count, int men_need_to_fly){
        this.men_count=men_count;
        this.men_need_to_fly=men_need_to_fly;
    }

    private String[] get_board_with_chars(){
        String[] tmpBoard = new String[24];
        for(int i=0;i<24;i++){
            switch (board[i]) {
                case 0:
                    tmpBoard[i] = "●";
                    break;
                case 1:
                    tmpBoard[i] = Colors.ANSI_GREEN + "⊙" + Colors.ANSI_RESET;
                    break;
                case 2:
                    tmpBoard[i] = Colors.ANSI_RED + "⊙" + Colors.ANSI_RESET;
                    break;
            }
        }
        return tmpBoard;
    }

    private String[] get_board_with_chars_windows(){
        String[] tmpBoard = new String[24];
        for(int i=0;i<24;i++){
            switch (board[i]) {
                case 0:
                    tmpBoard[i] = Colors.ANSI_RESET + "⊙" + Colors.ANSI_RESET;
                    break;
                case 1:
                    tmpBoard[i] = Colors.ANSI_GREEN + "⊙" + Colors.ANSI_RESET;
                    break;
                case 2:
                    tmpBoard[i] = Colors.ANSI_RED + "⊙" + Colors.ANSI_RESET;
                    break;
            }
        }
        return tmpBoard;
    }

    public void clearScreen(){
        for(int i=0;i<100;i++){
            System.out.print("\n");
        }
    }

    public void print_board(){
        String[] strBoard;
        if(!System.getProperty("os.name").startsWith("Windows")) {
            strBoard = get_board_with_chars();
        }else{
            strBoard = get_board_with_chars_windows();
        }

        if(!System.getProperty("os.name").startsWith("Windows")) {
            System.out.printf("7 %s⎻⎻⎻⎻⎻%s⎻⎻⎻⎻⎻%s\n" +
                            "6 | %s⎻⎻⎻%s⎻⎻⎻%s |\n" +
                            "5 | | %s⎻%s⎻%s | |\n" +
                            "4 %s⎻%s⎻%s   %s⎻%s⎻%s\n" +
                            "3 | | %s⎻%s⎻%s | |\n" +
                            "2 | %s⎻⎻⎻%s⎻⎻⎻%s |\n" +
                            "1 %s⎻⎻⎻⎻⎻%s⎻⎻⎻⎻⎻%s\n" +
                            "  a b c d e f g", strBoard[0], strBoard[1], strBoard[2], strBoard[3], strBoard[4], strBoard[5],
                    strBoard[6], strBoard[7], strBoard[8], strBoard[9], strBoard[10], strBoard[11],
                    strBoard[12], strBoard[13], strBoard[14], strBoard[15], strBoard[16], strBoard[17],
                    strBoard[18], strBoard[19], strBoard[20], strBoard[21], strBoard[22], strBoard[23]);
        }else{
            System.out.printf("7 %s-----%s-----%s\n" +
                            "6 |  %s--%s--%s  |\n" +
                            "5 |  |%s-%s-%s|  |\n" +
                            "4 %s⎻⎻%s⎻%s   %s⎻%s⎻%s\n" +
                            "3 |  |%s-%s-%s|  |\n" +
                            "2 |  %s--%s--%s  |\n" +
                            "1 %s-----%s-----%s\n" +
                            "  a  b c d e f  g", strBoard[0], strBoard[1], strBoard[2], strBoard[3], strBoard[4], strBoard[5],
                    strBoard[6], strBoard[7], strBoard[8], strBoard[9], strBoard[10], strBoard[11],
                    strBoard[12], strBoard[13], strBoard[14], strBoard[15], strBoard[16], strBoard[17],
                    strBoard[18], strBoard[19], strBoard[20], strBoard[21], strBoard[22], strBoard[23]);
        }
    }
}
