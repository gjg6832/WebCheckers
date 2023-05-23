package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import com.webcheckers.model.Piece.PieceColor;
import com.webcheckers.util.Message;

/**
 * The checkers game model
 *
 * @author <a href='mailto:eay4074@rit.edu'>Ethan Yaccarino-Mims</a>
 * @author Greg Godlewski
 */
public class CheckersGame {

    private Player white;
    private Player red;
    private Board gameBoard;
    private List<Row> rows;
    private Piece.PieceColor activeColor;
    private Player currentPlayer;
    private MoveList moveList;

    /**
     * Starts the game model
     *
     * @param p1 the first player is white
     * @param p2 the second player is red
     */
    public CheckersGame(Player p1, Player p2) {
        //sets the ordered players
        red = p1;
        activeColor = PieceColor.RED;
        currentPlayer = p1;
        white = p2;
        gameBoard = new Board();
        moveList = new MoveList(activeColor);
    }

    /**
     * Switches the game to the next players turn
     */
    public void nextTurn() {
        if (currentPlayer.equals(white)) {
            currentPlayer = red;
            activeColor = PieceColor.RED;
        } else {
            currentPlayer = white;
            activeColor = PieceColor.WHITE;
        }
    }

    public PieceColor getActiveColor() {
        return this.activeColor;
    }

    /**
     * Get the space in a specific position
     * @param pos
     * @return space at pos
     */
    public Space getSpace(Position pos) {
        return rows.get(pos.getRow()).getSpace(pos.getCell());
    }


    /**
     * Get piece at position
     * @param pos Position
     * @return Piece at pos
     */
    public Piece getPiece(Position pos) {
        return this.getSpace(pos).getPiece();
    }

    /**
     * Execute ai's turn
     * @return void
     */
    public void aiTurn() {
        AIBot aiPlayer = (AIBot) white;
        Thread aiMove = new Thread ( () -> aiPlayer.makeMove(this, this.activeColor));
        aiMove.start();
    }

    /**
     * Returns the game board
     *
     * @return the game board
     */
    public Board getGameBoard(Piece.PieceColor color) {
        if( color.equals(Piece.PieceColor.RED)){
            return gameBoard;
        } else {
            return Board.getReversedBoard(gameBoard);
        }
    }

    /**
     * Checks if it is a certain players turn
     *
     * @param name the player name we are checking against
     * @return if it's the players turn
     */
    public boolean isPlayersTurn(String name) {
        return name.equals(currentPlayer.getName());
    }

    /**
     * Return the color of the wanted player
     *
     * @param player - the wanted player
     * @return the color
     */
    public Piece.PieceColor getColor(Player player) {
        if (player.equals(red)){
            return Piece.PieceColor.RED;
        } else {
            return Piece.PieceColor.WHITE;
        }
    }


    /**
     * Validates a move
     *
     * @param move - the move to validated
     */
    public Message validateMove(Move move) {
        return moveList.validateMove(this, move);
    }

    public void makeMove() {
        List<Move> moves = moveList.getMoves();
        // check if there are moves to make
        if(moves.isEmpty()) {
            return;
        }
        Move move = moves.get(0);
        Move endMove = moves.get(moves.size()-1);

        gameBoard.movePiece( move.getStart(), endMove.getEnd() );

        moveList.capturePieces(getGameBoard(PieceColor.RED));

        nextTurn();

        this.moveList = new MoveList(activeColor);

        if( activeColor == PieceColor.WHITE && white instanceof AIBot)
            aiTurn();
    }

    /**
     * reverse the move that the current player just made
     *
     */
    public boolean backupMove(){
        if(moveList == null)
        {
            return false;
        } else {
            moveList.backUp();
        }
        return true;
    }

    /**
     * Gets the red player
     *
     * @return red player
     */
    public Player getRedPlayer(){
        return red;
    }

    /**
     * If the player can make a jump or not
     *
     * @return - true or false
     */
    public boolean canMakeJump() {
        List<Move> moves = moveList.getMoves();
        Move lastMove = moves.get(moves.size() - 1);
        Piece piece = gameBoard.getPiece(moves.get(0).getStart());

        if(moveList.validateSingle(lastMove)) {
            return false;
        }

        Position pos = lastMove.getEnd();
        gameBoard.getSpace(pos).setPiece(new Piece(activeColor, piece.getType()));
        boolean pieceMustJump = moveList.pieceCanJump(gameBoard, lastMove.getEnd());
        gameBoard.getSpace(pos).emptySpace();
        return pieceMustJump;
    }

    public boolean piecesStuck(PieceColor color) {
        MoveList tempList = new MoveList(color);
        return !tempList.piecesNotStuck(this, gameBoard);
    }

    /**
     * Gets the white player
     *
     * @return white player
     */
    public Player getWhitePlayer(){
        return white;
    }

    /**
     * whether player has moves in the moveList
     *
     * @return - ture or has
     */
    public boolean hasMove() {
        return moveList.getMoves().size() > 0;
    }

}
