package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Board9 board = new Board9();
        Rules rules = new Rules(board, 9, 3, true, false);
        Game game = new Game(board, rules);
        Scanner scanner = new Scanner(System.in);
        String cords1;
        String cords2;

        Arrays.fill(board.board, 0);

        while (true){
            board.clearScreen();
            board.print_board();
            try {
                System.out.print("\nВаш ход: ");
                cords1 = scanner.nextLine();
                if(cords1.equals("exit") || cords1.equals("quit") || cords1.equals("end")){
                    System.exit(0);
                }
                if (game.needSecondCords(cords1)) {
                    System.out.print("\nВаш ход: ");
                    cords2 = scanner.nextLine();
                    if (game.canTurn(cords1, cords2)) {
                        game.makeTurn(cords1, cords2);
                        game.newMillAppeared(cords1, cords2);
                    }
                } else {
                    if (game.canTurn(cords1)) {
                        game.makeTurn(cords1);
                        game.newMillAppeared(cords1, cords1);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                System.out.println(Colors.ANSI_RED + "Неверные кординаты" + Colors.ANSI_RESET);
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }
}
