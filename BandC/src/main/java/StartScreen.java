import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StartScreen {
    private Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {
        StartScreen startScreen = new StartScreen();
        startScreen.run();
    }

    private boolean showing0s = false;

    public void run() {
        while (true) {
            System.out.println("0) Switch to '0' grid");
            System.out.println("1) Battleship");
            System.out.println("2) Chess");


            String input = myScanner.nextLine();


            if (input.equals("0")) {
                showing0s = !showing0s;
            }

            if (input.equals("1")) while (true) {
                System.out.println("B) Back");
                System.out.println("1) One player One grid");
                System.out.println("2) One player Two grids(AI)");
                System.out.println("3) Two players");
                input = myScanner.nextLine();
                if (input.equalsIgnoreCase("B")) {
                    break;
                }
                if (input.equals("1")) {
                    solo1Grid();
                }
                if (input.equals("2")) {
                    while (true) {
                        System.out.println("B) Back");
                        System.out.println("1) Easy");
                        System.out.println("2) Normal");
                        System.out.println("3) Unfair");
                        System.out.println("4) Impossible");
                        input = myScanner.nextLine();
                        if (input.equalsIgnoreCase("B")) {
                            break;
                        }
                        if (input.equals("1")) solo2Grid(0);
                        if (input.equals("2")) solo2Grid(3);
                        if (input.equals("3")) solo2Grid(5);
                        if (input.equals("4")) solo2Grid(10);
                    }
                }
                if (input.equals("3")) {
                    twoplayer();
                }


            }


            if(input.equals("2")){
                    chess();
            }

            if(input.equals("test")){
                chessAi();
            }
        }
    }

    public void chess() {
        ChessGrid grid = new ChessGrid();
        boolean whiteTurn = true;
        while (true) {
            if (chessTurn(grid, whiteTurn)) continue;

            if(grid.checkForCheck(whiteTurn)){
                System.out.print("CHECK");
                if(!checkForCheckmate(grid.drawGrid(false,true), grid, whiteTurn)){
                    System.out.print("MATE!");
                    System.out.println();
                    break;
                }
                System.out.println();
            }

//             dangerSquares = grid.getDangerSpaces(!whiteTurn);
//            for(ChessSquare currentSquare : dangerSquares){
//                if(currentSquare.getChessUnitHere() != null){
//                    if(currentSquare.getChessUnitHere().isKing() && currentSquare.getChessUnitHere().colorIsWhite != whiteTurn){
//                        System.out.println("CHECK");
//                        checkForCheckmate(grid.drawGrid(false,true), grid, whiteTurn);
//                    }
//                }
//            }

            whiteTurn = !whiteTurn;


        }
    }

    public void chessAi() {
        ChessGrid grid = new ChessGrid();
        ChessBot chessBot = new ChessBot();
        boolean whiteTurn = true;
        while (true) {
            if(whiteTurn) {
                if (chessTurn(grid, whiteTurn)) continue;
            }else {
                double[] botMove = chessBot.chooseBestMove(grid.drawGrid(false,true),false);
                System.out.println(Arrays.toString(botMove));
                grid.moveUnitToNewSpace((int)botMove[0],(int) botMove[1], (int)botMove[2], (int)botMove[3]);
                System.out.println("ai turn");
            }

            if(grid.checkForCheck(whiteTurn)){
                System.out.print("CHECK");
                if(!checkForCheckmate(grid.drawGrid(false,true), grid, whiteTurn)){
                    System.out.print("MATE!");
                    System.out.println();
                    break;
                }
                System.out.println();
            }



            whiteTurn = !whiteTurn;


        }
    }

    private boolean chessTurn(ChessGrid grid, boolean whiteTurn) {
        printGridToScreen(grid.drawGrid(false, showing0s));

        System.out.println("\nSelect Unit to move:");
        String stringInput = myScanner.nextLine();
        int[] userInput;
        try {
            userInput = convertToCord(stringInput);
        } catch (NumberFormatException | NullPointerException e) {
            return true;
        }
        ChessPiece selectedUnit = null;
        try {
            selectedUnit = grid.getUnit(userInput[0], userInput[1]);
        } catch (NullPointerException e) {
            //change this maybe custom exception
            System.out.println("only use correct grid placement");
            return true;
        }

        if (selectedUnit == null) {
            return true;
        }

        if (selectedUnit.colorIsWhite != whiteTurn) {
            System.out.println("wrong colored unit");
            return true;
        }

        List<ChessSquare> possableSquares = selectedUnit.getPossibleSquares();
        boolean checkForCastling = false;

        if(selectedUnit.isKing() && !selectedUnit.hasMoved()) checkForCastling = true;
        if (possableSquares.size() == 0) {
            System.out.println("no possible moves");
        }
        for (ChessSquare square : possableSquares) {
            System.out.print(convertToStringNotation(square.getCords()) + " ");
        }
        System.out.println("\nSelect where to move unit to:");
        stringInput = myScanner.nextLine();
        int[] endUserInput;
        try {
            endUserInput = convertToCord(stringInput);
        } catch (NumberFormatException e) {
            return true;
        }
        boolean correct = false;
        for (ChessSquare square : possableSquares) {
            int[] squareCord = square.getCords();
            if (squareCord[0] == endUserInput[0] && squareCord[1] == endUserInput[1]) {
                correct = true;
                break;
            }
        }
        if (!correct) {
            System.out.println("illegal space");
            return true;
        }
        boolean undoMoved = false;
        if(!selectedUnit.hasMoved()){
            undoMoved = true;
        }
        grid.moveUnitToNewSpace(userInput[0], userInput[1], endUserInput[0], endUserInput[1]);
        if(checkForCastling){

            if(endUserInput[0] == 2){
                if(endUserInput[1] == 7){
                    grid.moveUnitToNewSpace(0, 7, 3, 7);
                }else {
                    grid.moveUnitToNewSpace(0, 0, 3, 0);
                }
            }else {
                if(endUserInput[1] == 7){
                    grid.moveUnitToNewSpace(7, 7, 5, 7);
                }else {
                    grid.moveUnitToNewSpace(7, 0, 5, 0);
                }
            }
        }


        boolean restart = false;
        List<ChessSquare> dangerSquares = grid.getDangerSpaces(whiteTurn);
        for(ChessSquare currentSquare : dangerSquares){
            if(currentSquare.getChessUnitHere() != null){
                if(currentSquare.getChessUnitHere().isKing() && currentSquare.getChessUnitHere().colorIsWhite == whiteTurn){
                    grid.moveUnitToNewSpace(endUserInput[0], endUserInput[1], userInput[0], userInput[1]);
                    System.out.println("Your king would be in check");
                    if(undoMoved){
                        selectedUnit.undoMove();
                    }
                    restart =true;
                }
            }
        }
        if(restart) return true;

        ChessSquare promtingPawnSpace = grid.promotePawnsCheck();
        if(promtingPawnSpace != null){
            while (true) {
                System.out.println("Promote your pawn to (Q)ueen (R)ook (B)ishop or (K)night:");
                String userPromotion = myScanner.nextLine();
                if(userPromotion.equalsIgnoreCase("Q")){
                    promtingPawnSpace.addChessPiece(new Queen(whiteTurn, promtingPawnSpace, promtingPawnSpace.getCords()[0], promtingPawnSpace.getCords()[1], grid));
                } else if(userPromotion.equalsIgnoreCase("R")){
                    promtingPawnSpace.addChessPiece(new Rook(whiteTurn, promtingPawnSpace, promtingPawnSpace.getCords()[0], promtingPawnSpace.getCords()[1], grid));
                }else if(userPromotion.equalsIgnoreCase("B")){
                    promtingPawnSpace.addChessPiece(new Bishop(whiteTurn, promtingPawnSpace, promtingPawnSpace.getCords()[0], promtingPawnSpace.getCords()[1], grid));
                }else if(userPromotion.equalsIgnoreCase("K")){
                    promtingPawnSpace.addChessPiece(new Knight(whiteTurn, promtingPawnSpace, promtingPawnSpace.getCords()[0], promtingPawnSpace.getCords()[1], grid));
                }else continue;
                break;
            }
        }
        return false;
    }

    private boolean checkForCheckmate(char[][] layout ,ChessGrid realGrid, boolean color) {
        for(int y = 0; y<layout[0].length; y++ ){
            for(int x = 0; x<layout.length; x++ ){
                ChessPiece unit = realGrid.getUnit(x,y);
                if(unit != null && unit.colorIsWhite != color){
                    List<ChessSquare> unitPosMoves =  unit.getPossibleSquares();
                    for(ChessSquare nextMoveSquare: unitPosMoves){
                        ChessGrid tempGrid = new ChessGrid(layout);
                        tempGrid.moveUnitToNewSpace(unit.getxCord(), unit.getyCord(), nextMoveSquare.getCords()[0], nextMoveSquare.getCords()[1]);
                        if(!tempGrid.checkForCheck(color)) return true;
                    }
                }
            }
        }

        return false;
    }



    public void twoplayer() {
        BattleshipGrid battleshipGridP1 = null;
        BattleshipGrid battleshipGridP2 = null;

        Ship[] fleat = new Ship[5];
        fleat[0] = new Ship("Battleship", 4, 'B');
        fleat[1] = new Ship("Carrier", 5, 'C');
        fleat[2] = new Ship("Cruiser", 3, 'c');
        fleat[3] = new Ship("Submarine", 3, 'S');
        fleat[4] = new Ship("Destroyer", 2, 'D');
        battleshipGridP1 = new BattleshipGrid(10, 10, fleat);
        for (Ship ship : fleat) {
            ship.setGrid(battleshipGridP1);
        }

        int shipNum = 0;
        System.out.println("Do you want you ships to be randomized? (enter Y if so)");
        String userInput = myScanner.nextLine();
        if (userInput.toLowerCase().equals("y")) {
            battleshipGridP1.computerPlaceShips();
        } else while (shipNum < fleat.length) {
            printGridToScreen(battleshipGridP1.drawGrid(true, showing0s));
            //battleshipGridP1.drawGrid(true,showing0s);
            System.out.printf("\nPlace your %s(%d) by selecting the starting square:\n", fleat[shipNum].getName(), fleat[shipNum].getLength());
            String startString = myScanner.nextLine();
            if (startString.length() < 2) {
                continue;
            }
            int[] startCord;
            try {
                startCord = convertToCord(startString);
            } catch (NumberFormatException e) {
                continue;
            }
            System.out.println("End square?");
            String endString = myScanner.nextLine();
            if (endString.length() < 2) {
                continue;
            }
            int[] endCord;
            try {
                endCord = convertToCord(endString);
            } catch (NumberFormatException e) {
                continue;
            }
            if (fleat[shipNum].placeShips(startCord[0], endCord[0], startCord[1], endCord[1])) {
                shipNum++;
            } else {
                System.out.println("Unable to place ship");
            }
        }
        Ship[] fleat2 = new Ship[5];
        fleat2[0] = new Ship("Battleship", 4, 'B');
        fleat2[1] = new Ship("Carrier", 5, 'C');
        fleat2[2] = new Ship("Cruiser", 3, 'c');
        fleat2[3] = new Ship("Submarine", 3, 'S');
        fleat2[4] = new Ship("Destroyer", 2, 'D');
        battleshipGridP2 = new BattleshipGrid(10, 10, fleat2);
        for (Ship ship : fleat2) {
            ship.setGrid(battleshipGridP2);
        }
        shipNum = 0;
        System.out.println("Do you want you ships to be randomized? (enter Y if so)");
        userInput = myScanner.nextLine();
        if (userInput.toLowerCase().equals("y")) {
            battleshipGridP2.computerPlaceShips();
        } else while (shipNum < fleat2.length) {
            printGridToScreen(battleshipGridP2.drawGrid(true, showing0s));
            // battleshipGridP2.drawGrid(true,showing0s);
            System.out.printf("\nPlace your %s(%d) by selecting the starting square:\n", fleat2[shipNum].getName(), fleat2[shipNum].getLength());
            String startString = myScanner.nextLine();
            if (startString.length() < 2) {
                continue;
            }
            int[] startCord;
            try {
                startCord = convertToCord(startString);
            } catch (NumberFormatException e) {
                continue;
            }
            System.out.println("End square?");
            String endString = myScanner.nextLine();
            if (endString.length() < 2) {
                continue;
            }
            int[] endCord;
            try {
                endCord = convertToCord(endString);
            } catch (NumberFormatException e) {
                continue;
            }
            if (fleat2[shipNum].placeShips(startCord[0], endCord[0], startCord[1], endCord[1])) {
                shipNum++;
            } else {
                System.out.println("Unable to place ship");
            }
        }
        boolean endOfGame = false;
        while (!endOfGame) {
            printGridToScreen(battleshipGridP2.drawGrid(false, showing0s));
            //  battleshipGridP2.drawGrid(false,showing0s);
            System.out.println("\nPlayer1's turn");
            while (true) {

                System.out.println("\ninput:");
                String stringInput = myScanner.nextLine();
                if (stringInput.equals("cheat")) {
                    printGridToScreen(battleshipGridP2.drawGrid(true, showing0s));
                    // battleshipGridP2.drawGrid(true, showing0s);
                }
                if (stringInput.length() < 2) {
                    continue;
                }
                int[] userCords;
                try {
                    userCords = convertToCord(stringInput);
                } catch (NumberFormatException e) {
                    continue;
                }
                if (!checkCord(battleshipGridP2, userCords)) continue;

                char itemHit;
                try {
                    itemHit = battleshipGridP2.getSquare(userCords[0], userCords[1]).Fire();
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (itemHit != '0') {
                    System.out.println("Hit");

                    if (!battleshipGridP2.checkShipStatus(itemHit)) {
                        Ship shipHit = battleshipGridP2.getShip(itemHit);

                        System.out.println(shipHit.getSinkMessage());
                        if (battleshipGridP2.checkVictory()) {
                            System.out.println("P1 Victory");
                            endOfGame = true;
                        }
                    }
                } else {
                    System.out.println("Miss");
                }
                break;


            }
            myScanner.nextLine();
            printGridToScreen(battleshipGridP1.drawGrid(false, showing0s));
            //battleshipGridP1.drawGrid(false,showing0s);
            System.out.println("\nPlayer2's turn");
            if (endOfGame) break;
            while (true) {

                System.out.println("\ninput:");
                String stringInput = myScanner.nextLine();
                if (stringInput.equals("cheat")) {
                    printGridToScreen(battleshipGridP1.drawGrid(true, showing0s));
                    //  battleshipGridP1.drawGrid(true, showing0s);
                }
                if (stringInput.length() < 2) {
                    continue;
                }
                int[] userCords;
                try {
                    userCords = convertToCord(stringInput);
                } catch (NumberFormatException e) {
                    continue;
                }
                if (!checkCord(battleshipGridP1, userCords)) continue;

                char itemHit;
                try {
                    itemHit = battleshipGridP1.getSquare(userCords[0], userCords[1]).Fire();
                } catch (IllegalArgumentException e) {
                    continue;
                }

                if (itemHit != '0') {
                    System.out.println("Hit");

                    if (!battleshipGridP1.checkShipStatus(itemHit)) {
                        Ship shipHit = battleshipGridP1.getShip(itemHit);

                        System.out.println(shipHit.getSinkMessage());
                        if (battleshipGridP1.checkVictory()) {
                            System.out.println("P2 Victory");
                            endOfGame = true;
                        }
                    }
                } else {
                    System.out.println("Miss");
                }
                myScanner.nextLine();
                break;

            }


        }
    }

    public void solo1Grid() {
        //Scanner myScanner = new Scanner(System.in);
        BattleshipGrid battleshipGrid = null;
        //grid.drawGrid();
        //   grid.getSquare(3,3).placeUnitHere('L');
        Ship[] fleat = new Ship[5];
        fleat[0] = new Ship("Battleship", 4, 'B');
        fleat[1] = new Ship("Carrier", 5, 'C');
        fleat[2] = new Ship("Cruiser", 3, 'c');
        fleat[3] = new Ship("Submarine", 3, 'S');
        fleat[4] = new Ship("Destroyer", 2, 'D');
        battleshipGrid = new BattleshipGrid(10, 10, fleat);
        for (Ship ship : fleat) {
            ship.setGrid(battleshipGrid);
        }

        battleshipGrid.computerPlaceShips();


//        char[][] printing =  battleshipGrid.drawGrid(false,showing0s);
        printGridToScreen(battleshipGrid.drawGrid(false, showing0s));


        while (true) {
            System.out.println("\ninput:");
            String stringInput = myScanner.nextLine();
            if (stringInput.equals("cheat")) {
                printGridToScreen(battleshipGrid.drawGrid(true, showing0s));
            }
            if (stringInput.length() < 2) {
                continue;
            }
            int[] userInput;
            try {
                userInput = convertToCord(stringInput);
            } catch (NumberFormatException e) {
                continue;
            }

            char itemHit;
            try {
                itemHit = battleshipGrid.getSquare(userInput[0], userInput[1]).Fire();
            } catch (IllegalArgumentException e) {
                continue;
            }

            if (itemHit != '0') {
                System.out.println("Hit");

                if (!battleshipGrid.checkShipStatus(itemHit)) {
                    Ship shipHit = battleshipGrid.getShip(itemHit);

                    System.out.println(shipHit.getSinkMessage());
                    if (battleshipGrid.checkVictory()) {
                        System.out.println("Victory");
                    }
                }
            } else {
                System.out.println("Miss");
            }
            myScanner.nextLine();
            printGridToScreen(battleshipGrid.drawGrid(false, showing0s));

        }
    }


    public void solo2Grid(int aiLevel) {


        BattleshipGrid battleshipGridPlayer = null;
        BattleshipGrid battleshipGridAi = null;

        Ship[] fleat = new Ship[5];
        fleat[0] = new Ship("Battleship", 4, 'B');
        fleat[1] = new Ship("Carrier", 5, 'C');
        fleat[2] = new Ship("Cruiser", 3, 'c');
        fleat[3] = new Ship("Submarine", 3, 'S');
        fleat[4] = new Ship("Destroyer", 2, 'D');
        battleshipGridPlayer = new BattleshipGrid(10, 10, fleat);
        for (Ship ship : fleat) {
            ship.setGrid(battleshipGridPlayer);
        }
        int shipNum = 0;
        System.out.println("Do you want you ships to be randomized? (enter Y if so)");
        String userInput = myScanner.nextLine();
        if (userInput.toLowerCase().equals("y")) {
            battleshipGridPlayer.computerPlaceShips();
        } else while (shipNum < fleat.length) {
            printGridToScreen(battleshipGridPlayer.drawGrid(true, showing0s));
            System.out.printf("\nPlace your %s(%d) by selecting the starting square:\n", fleat[shipNum].getName(), fleat[shipNum].getLength());
            String startString = myScanner.nextLine();
            if (startString.length() < 2) {
                continue;
            }
            int[] startCord;
            try {
                startCord = convertToCord(startString);
            } catch (NumberFormatException e) {
                continue;
            }
            System.out.println("End square?");
            String endString = myScanner.nextLine();
            if (endString.length() < 2) {
                continue;
            }
            int[] endCord;
            try {
                endCord = convertToCord(endString);
            } catch (NumberFormatException e) {
                continue;
            }
            if (fleat[shipNum].placeShips(startCord[0], endCord[0], startCord[1], endCord[1])) {
                shipNum++;
            } else {
                System.out.println("Unable to place ship");
            }
        }
        Ship[] fleatAi = new Ship[5];
        fleatAi[0] = new Ship("Battleship", 4, 'B');
        fleatAi[1] = new Ship("Carrier", 5, 'C');
        fleatAi[2] = new Ship("Cruiser", 3, 'c');
        fleatAi[3] = new Ship("Submarine", 3, 'S');
        fleatAi[4] = new Ship("Destroyer", 2, 'D');
        battleshipGridAi = new BattleshipGrid(10, 10, fleatAi);
        for (Ship ship : fleatAi) {
            ship.setGrid(battleshipGridAi);
        }
        battleshipGridAi.computerPlaceShips();
        printGridToScreen(battleshipGridAi.drawGrid(false, showing0s));
        while (true) {

            System.out.println("\ninput:");
            String stringInput = myScanner.nextLine();
            if (stringInput.equals("cheat")) {
                printGridToScreen(battleshipGridAi.drawGrid(true, showing0s));
            }
            if (stringInput.length() < 2) {
                continue;
            }
            int[] userCords;
            try {
                userCords = convertToCord(stringInput);
            } catch (NumberFormatException e) {
                continue;
            }
            if (!checkCord(battleshipGridAi, userCords)) continue;

            char itemHit;
            try {
                itemHit = battleshipGridAi.getSquare(userCords[0], userCords[1]).Fire();
            } catch (IllegalArgumentException e) {
                continue;
            }

            if (itemHit != '0') {
                System.out.println("Hit");

                if (!battleshipGridAi.checkShipStatus(itemHit)) {
                    Ship shipHit = battleshipGridAi.getShip(itemHit);

                    System.out.println(shipHit.getSinkMessage());
                    if (battleshipGridAi.checkVictory()) {
                        System.out.println("Victory");
                        break;
                    }
                }
            } else {
                System.out.println("Miss");
            }
            myScanner.nextLine();
            int[] aiShot = aiFire(battleshipGridPlayer, aiLevel);

            printGridToScreen(battleshipGridPlayer.drawGrid(true, showing0s));
            System.out.println("\nThe AI shot at: " + convertToStringNotation(aiShot));

            if (battleshipGridPlayer.checkVictory()) {
                System.out.println("Defeat");
                break;
            }
            myScanner.nextLine();
            System.out.println("\n\n");
            printGridToScreen(battleshipGridAi.drawGrid(false, showing0s));

        }
    }

    public int[] convertToCord(String input) {
        input = input.toUpperCase();
        if(input.length() <2) return  new int[] {-1,-1};
        char letter = input.charAt(0);
        int letterToInt = 0;
        int number = Integer.parseInt(input.substring(1)) - 1;
        char check = 'A';
        int out = 0;
        while (check != ('Z' + 1)) {
            if (letter == check) {
                letterToInt = out;
            }
            check++;
            out++;
        }
        return new int[]{letterToInt, number};
    }

    public String convertToStringNotation(int[] cord){
        char start ='A';
        start = (char) (start + cord[0]);
        String out = "" + start;
        out += (cord[1]+1);
        return out;

    }


    private int[] lastHit = new int[]{-1, -1, 0};
    private char lastHitId = '_';


    public int[] aiFire(BattleshipGrid battleshipGrid, int aiLevel) {
        if(aiLevel == 3){
            int[] attempt = advanceAiChecks(battleshipGrid);
            if(attempt[0] != -1){
                return attempt;
            }
        }

        while (true) {
            int randomX = (int) (battleshipGrid.getxAxis() * Math.random());
            int randomY = (int) (battleshipGrid.getyAxis() * Math.random());
            BattleshipSquare targetSquare = battleshipGrid.getSquare(randomX, randomY);
            if (targetSquare.getUnitHere() == 'X' || targetSquare.getUnitHere() == '@') {
                continue;
            }
            if (aiLevel == 10 && targetSquare.getUnitHere() == '0') {
                continue;
            }
            if (aiLevel == 5 && lastHit[2] == 1) {

                if (battleshipGrid.checkShipStatus(lastHitId)) {

                    if (targetSquare.getUnitHere() != lastHitId) {
                        continue;//put this in one if statement?
                    }
                }
            }
            if (targetSquare.getUnitHere() != '0') {
                lastHit[2] = 1;
                lastHitId = targetSquare.getUnitHere();
            } else {
                lastHit[2] = 0;
            }
            try {
                targetSquare.Fire();
            } catch (IllegalArgumentException e) {
                continue;
            }
            return new int[]{randomX, randomY};

        }


    }

    private void printGridToScreen(char[][] printing) {
        for (int i = printing[0].length - 1; i >= 0; i--) {
            for (int ii = 0; ii < printing.length; ii++) {
                System.out.print(printing[ii][i] + " ");
            }
            System.out.print((i + 1) + "  ");
            System.out.println();
        }
        char test = 'A';
        System.out.print("");
        for (int i = printing.length - 1; i >= 0; i--) {


            System.out.print(test + " ");
            test++;
        }
    }

    public boolean checkCord(BattleshipGrid battleshipGrid, int[] cords) {
        if (battleshipGrid.getxAxis() < cords[0] || battleshipGrid.getyAxis() < cords[1]) { //maybe add the zero check too
            return false;
        }
        return true;
    }

    private int[] advanceAiChecks(BattleshipGrid battleshipGrid) {
        char[][] grid = battleshipGrid.drawGrid(false, true);

        for (int i = 0; i < grid.length; i++) {
            for (int ii = 0; ii < grid[0].length; ii++) {
                if (grid[i][ii] == 'X') {

//                    if (i != 0 && grid[i - 1][ii] != '0') {
//
//                    } else if (ii != 0 && grid[i][ii - 1] != '0') {
//
//                    } else if (i != grid.length - 1 && grid[i + 1][ii] == '0') {
//
//                    } else if (ii != grid[i].length - 1 && grid[i][ii + 1] != '0') {
//
//                    } else {
//                        continue;
//                    } //dont think this does anything
                    double randDir = Math.random();
                    int upOrDown = -1;
                    int adder = 0;
                    if(randDir <.5){
                        upOrDown = 1;
                    }

                    if (i != 0 && grid[i - 1][ii] == 'X') {
                        try {
                            while (true) {//add a try catch
                                adder += upOrDown;
                                if (grid[i + adder][ii] == '@') {
                                    break;
                                }
                                if (grid[i + adder][ii] == '0') {
                                    battleshipGrid.getSquare(i + adder, ii).Fire();
                                    return new int[]{i + adder, ii};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }
                        adder = 0;
                        try {
                            while(true){//add a try catch
                                adder -= upOrDown;
                                if(grid[i+adder][ii] == '@'){
                                    break;
                                }
                                if(grid[i+adder][ii] == '0'){
                                    battleshipGrid.getSquare(i + adder , ii).Fire();
                                    return new int[]{i + adder, ii};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }


                    } else if (ii != 0 && grid[i][ii - 1] == 'X') {
                        try {
                            while (true) {//add a try catch
                                adder += upOrDown;
                                if (grid[i ][ii+adder] == '@') {
                                    break;
                                }
                                if (grid[i][ii+adder] == '0') {
                                    battleshipGrid.getSquare(i, ii+adder).Fire();
                                    return new int[]{i , ii+adder};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }
                        adder = 0;
                        try {
                            while(true){//add a try catch
                                adder -= upOrDown;
                                if(grid[i][ii+adder] == '@'){
                                    break;
                                }
                                if(grid[i][ii+adder] == '0'){
                                    battleshipGrid.getSquare(i  , ii+adder).Fire();
                                    return new int[]{i , ii+adder};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }

                    } else if (i != grid.length - 1 && grid[i + 1][ii] == 'X') {
                        try {
                            while (true) {//add a try catch
                                adder += upOrDown;
                                if (grid[i + adder][ii] == '@') {
                                    break;
                                }
                                if (grid[i + adder][ii] == '0') {
                                    battleshipGrid.getSquare(i + adder, ii).Fire();
                                    return new int[]{i + adder, ii};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }
                        adder = 0;
                        try {
                            while(true){//add a try catch
                                adder -= upOrDown;
                                if(grid[i+adder][ii] == '@'){
                                    break;
                                }
                                if(grid[i+adder][ii] == '0'){
                                    battleshipGrid.getSquare(i + adder , ii).Fire();
                                    return new int[]{i + adder, ii};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }

                    } else if (ii != grid[i].length - 1 && grid[i][ii + 1] == 'X') {
                        try {
                            while (true) {//add a try catch
                                adder += upOrDown;
                                if (grid[i ][ii+adder] == '@') {
                                    break;
                                }
                                if (grid[i][ii+adder] == '0') {
                                    battleshipGrid.getSquare(i, ii+adder).Fire();
                                    return new int[]{i , ii+adder};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }
                        adder = 0;
                        try {
                            while(true){//add a try catch
                                adder -= upOrDown;
                                if(grid[i][ii+adder] == '@'){
                                    break;
                                }
                                if(grid[i][ii+adder] == '0'){
                                    battleshipGrid.getSquare(i  , ii+adder).Fire();
                                    return new int[]{i , ii+adder};
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            //just continue
                        }
                    } else {
                        while (true) {
                            try {

                                randDir = Math.random();
                                if (randDir < .25) {
                                    battleshipGrid.getSquare(i + 1, ii).Fire();
                                    return new int[]{i + 1, ii};
                                } else if (randDir < .50) {
                                    battleshipGrid.getSquare(i - 1, ii).Fire();
                                    return new int[]{i - 1, ii};
                                } else if (randDir < .75) {
                                    battleshipGrid.getSquare(i, ii + 1).Fire();
                                    return new int[]{i, ii + 1};
                                } else {
                                    battleshipGrid.getSquare(i, ii - 1).Fire();
                                    return new int[]{i, ii - 1};
                                }
                            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                                //just retry
                            }
                        }
                    }

                }
            }
        }
        return new int[] {-1,1};
    }

}