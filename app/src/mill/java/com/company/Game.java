package com.company;

public class Game {
    Board9 board;
    Rules rules;

    private int whose_turn=1;

    Game(Board9 board, Rules rules){
        this.board=board;
        this.rules=rules;
    }

    void makeTurn(String cords){
        if(board.new_mill_cords != -1 && rules.canKill(whose_turn, board.indexToCords(board.new_mill_cords), cords) && board.mustKill){
            board.kill(whose_turn, board.indexToCords(board.new_mill_cords), cords);
            whose_turn = Board9.opponent(whose_turn);
        }else if(rules.canPlace(whose_turn, cords)) {
            board.place(whose_turn, cords);
            whose_turn = Board9.opponent(whose_turn);
        }
        if(board.mustKill){
            board.mustKill=false;
        }
    }

    void makeTurn(String cords1, String cords2){
        if(rules.canMove(whose_turn, cords1, cords2)){
            board.move(whose_turn, cords1, cords2);
            whose_turn = Board9.opponent(whose_turn);
        }else if(rules.canFly(whose_turn, cords1, cords2)){
            board.fly(whose_turn, cords1, cords2);
            whose_turn = Board9.opponent(whose_turn);
        }
    }

    void newMillAppeared(String cords1, String cords2){
        if(board.in_mills(board.cordsToIndex(cords1)) || board.in_mills(board.cordsToIndex(cords2))){
            board.check_mills(board.cordsToIndex(cords1));
            board.check_mills(board.cordsToIndex(cords2));
            board.mustKill=true;
            whose_turn = Board9.opponent(whose_turn);
        }else{
            board.check_mills(board.cordsToIndex(cords1));
            board.check_mills(board.cordsToIndex(cords2));
        }
    }

    boolean needSecondCords(String cords){
        return !rules.canPlace(whose_turn, cords) && !(board.new_mill_cords != -1 && rules.canKill(whose_turn, board.indexToCords(board.new_mill_cords), cords));
    }

    boolean canTurn(String cords){
        return rules.canPlace(whose_turn, cords) || (board.new_mill_cords != -1 && rules.canKill(whose_turn, board.indexToCords(board.new_mill_cords), cords));
    }

    boolean canTurn(String cords1, String cords2){
        if(rules.canMove(whose_turn, cords1, cords2)){
            return true;
        }else if(rules.canFly(whose_turn, cords1, cords2)){
            return true;
        }else{
            return false;
        }
    }
}
