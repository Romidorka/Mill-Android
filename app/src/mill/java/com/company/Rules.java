package com.company;

public class Rules {
    public int men_count;
    public int men_need_to_fly;
    public boolean allow_flying;
    public boolean can_move_while_placing;

    private Board9 board;

    Rules(Board9 board,int men_count, int men_need_to_fly, boolean allow_flying, boolean can_move_while_placing){
        this.board = board;
        board.setRules(men_count, men_need_to_fly);
        this.men_count = men_count;
        this.men_need_to_fly = men_need_to_fly;
        this.allow_flying = allow_flying;
        this.can_move_while_placing = can_move_while_placing;
    }

    public boolean is_win(int player){
        for(int i=0;i<2;i++){
            if(men_count - board.died_dots[i] < 3 && i+1 == player){
                return true;
            }
        }
        return false;
    }

    public boolean canPlace(int player, String cords){
        int index = board.cordsToIndex(cords);
        return board.board[index] == 0 && board.phase == Board9.PHASE_PLACING;
    }

    public boolean canMove(int player, String cords1, String cords2){
        int index1 = board.cordsToIndex(cords1);
        int index2 = board.cordsToIndex(cords2);
        return board.board[index1] == player && board.board[index2] == 0 && board.is_neighbor(index1, index2) &&
                (board.phase==Board9.PHASE_MOVING || can_move_while_placing);
    }

    public boolean canFly(int player, String cords1, String cords2){
        int index1 = board.cordsToIndex(cords1);
        int index2 = board.cordsToIndex(cords2);
        return board.board[index1] == player && board.board[index2] == 0 && board.is_neighbor(index1, index2) &&
                (board.phase==Board9.PHASE_MOVING || can_move_while_placing) && allow_flying &&
                men_count-board.died_dots[player-1] == men_need_to_fly;
    }

    public boolean canKill(int player, String cords1, String cords2){
        int index1 = board.cordsToIndex(cords1);
        int index2 = board.cordsToIndex(cords2);
        return board.board[index1] == player && board.board[index2] != player && board.board[index2] != 0 && board.mustKill;

    }
}
