package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import com.webcheckers.model.Piece.PieceColor;
import com.webcheckers.model.Piece.PieceType;
import com.webcheckers.model.Space.SpaceColor;
import com.webcheckers.util.Message;

/**
 * MoveList validates moves made during a turn by the player. These move are stored in a list of moves
 * in CheckersGame where they are executed.
 *
 * @author Shrif Rai
 * @author Ethan Yaccarino-Mims
 */
public class MoveList {
    public static final Message INVALID_SPACE_MOVE = Message.error("Invalid move: Piece cannot move here.");
    public static final Message INVALID_WHITE_SPACE_MOVE = Message.error("Invalid move: Piece cannot move to a white space.");
    public static final Message INVALID_DIRECTION_MOVE = Message.error("Invalid move: Piece cannot move in that direction");
    public static final Message INVALID_DIAGONAL_MOVE = Message.error("Invalid move: Piece can only move diagonally");
    public static final Message INVALID_WRONG_PIECE_MOVE = Message.error("Invalid move: You cannot move opponent's piece");
    public static final Message INVALID_SPACE_OCCUPIED_MOVE = Message.error("Invalid move: Space is occupied by another piece");
    public static final Message INVALID_NOT_SAME_PIECE_JUMPING = Message.error("Only one piece may be moved per turn.");
    public static final Message INVALID_NON_JUMP = Message.error("Invalid move: You must jump over opponent's piece!");
    public static final Message VALID = Message.info("Move done");

    private PieceColor activeColor;

    private List<Move> moves;

    private Move lastMove;

    /**
     * Constructs a move list
     *
     * @param activeColor
     */
    public MoveList(PieceColor activeColor) {
        this.activeColor = activeColor;
        this.moves = new ArrayList<>();
        lastMove = null;
    }

    /**
     * Validates if a move is a single move
     *
     * @param move
     * @return returns true if single move, returns false otherwise
     */
    public boolean validateSingle(Move move) {
        int rowDistance = Math.abs(move.getStart().getRow() - move.getEnd().getRow());
        int colDistance = Math.abs(move.getStart().getCell() - move.getEnd().getCell());
        return rowDistance == 1 && colDistance == 1;
    }

    /**
     * Gets the captured space
     *
     * @param move the move that captured the space
     * @param game the game being played
     * @return the space which was captured
     */
    private Space getCaptureSpace(Move move, Board game) {
        int checkRow;
        if(move.getStart().getRow() > move.getEnd().getRow())
            checkRow = move.getStart().getRow() - 1;
        else
            checkRow = move.getStart().getRow() + 1;
        // Get the cell to check for space being jumped
        int checkCell;
        if(move.getStart().getCell() > move.getEnd().getCell())
            checkCell = move.getStart().getCell() - 1;
        else
            checkCell = move.getStart().getCell() + 1;

        Position checkPos = new Position(checkRow, checkCell);
        return game.getSpace(checkPos);
    }

    /**
     * Check if move made is diagonal
     *
     * @param move
     * @return true if diagonal move
     */
    public boolean validateDiagonal(Move move) {
        int rowDistance = move.getStart().getRow() - move.getEnd().getRow();
        int colDistance = move.getStart().getCell() - move.getEnd().getCell();
        return Math.abs(rowDistance) == Math.abs(colDistance);
    }

    /**
     * returns the space that was jumped in the move
     *
     * @param board the board relative to the player
     * @param move the jump move the player made
     * @return the piece that the player jumped over
     */
    public Space getJumpedSpace(Board board, Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        int row, col;
        if(start.getRow() > end.getRow())
            row = start.getRow() - 1;
        else
            row = start.getRow() + 1;
        if(start.getCell() > end.getCell())
            col = start.getCell() - 1;
        else
            col = start.getCell() + 1;
        return board.getSpace(new Position(row, col));
    }

    /**
     * Check if piece is being moved to a black space
     *
     * @param board board to check move on
     * @param move move to be checked
     * @return true if piece is moving to black space
     */
    public boolean validateMoveToBlack(Board board, Move move) {
        SpaceColor spaceColor = board.getSpace(move.getEnd()).getColor();
        return spaceColor == SpaceColor.BLACK;
    }

    /**
     * Check if piece is being moved in a valid direction.
     * A single piece can only move towards opponent while a
     * king piece can move in both directions.
     *
     * @param piece piece to check legal move direction
     * @param move move to be checked
     * @return true if direction is valid
     */
    public boolean validateDirection(Piece piece, Move move) {
        int distance = move.getStart().getRow() - move.getEnd().getRow();

        if(piece.getType() == PieceType.KING)
            return true;

        if(piece.getColor() == PieceColor.WHITE)
            return distance < 0;

        return distance > 0;

    }

    /**
     * Checks if a move is a valid jump move that jumps over opponent's piece.
     *
     * @param board board to check move on
     * @param move move to be checked
     * @return true if valid jump move
     */
    public boolean validateJumpMove(Board board, Move move) {
        if(Math.abs(move.getStart().getRow() - move.getEnd().getRow()) != 2)
            return false;
        Space jumpedSpace = getJumpedSpace(board, move);
        Piece jumpedPiece = jumpedSpace.getPiece();

        if(jumpedPiece == null)
            return false;
        return jumpedPiece.getColor() != activeColor;
    }

    /**
     * Gets a list of possible single moves a piece at the given position can take
     *
     * @param pos the position that we are checking
     * @return a list of single moves possible from the position given
     */
    public List<Position> getSingleMoveList(Position pos){
        List<Position> posList = new ArrayList<>();
        for(int row = pos.getRow() -1; row <= pos.getRow() +1; row += 2){
            for(int col = pos.getCell() -1; col <= pos.getCell() +1; col +=2){
                Position singleMovePos = new Position(row,col);
                if(validatePosition(singleMovePos))
                    posList.add(singleMovePos);
            }
        }
        return posList;
    }

    /**
     * Checks if the piece jumping is one which already jumped
     *
     * @param board board to check move on
     * @param move move to be checked
     * @return true if the piece making a jump move already made one
     */
    public boolean validateMultipleJumpMove(Board board, Move move) {
        if(!validateJumpMove(board, move) || !validateJumpMove(board, lastMove) || lastMove == null) {
            return false;
        }

        for(Move pastMove : moves) {
            if(pastMove.getStart().equals(move.getEnd())) {
                return false;
            }
        }

        return lastMove.getEnd().equals(move.getStart());
    }

    /**
     * Checks if a move is a valid jump move that jumps over opponent's piece.
     *
     * @param board board to check move on
     * @param move  move to be checked
     * @return true if valid jump move
     */
    public boolean validateCanJump(Board board, Move move) {
        if (Math.abs(move.getStart().getRow() - move.getEnd().getRow()) != 2)
            return false;

        if(board.getSpace(move.getEnd()).getPiece() != null)
            return false;

        Space jumpedSpace = getCaptureSpace(move, board);
        Piece jumpedPiece = jumpedSpace.getPiece();

        if (jumpedPiece == null)
            return false;
        return jumpedPiece.getColor() != activeColor;
    }

    /**
     * Checks if a piece's color is the active color so that it can move
     *
     * @param color
     * @return
     */
    public boolean isActiveColor(PieceColor color) {
        return color == activeColor;
    }

    /**
     * Get piece at a specific position
     *
     * @param board
     * @param pos
     * @return piece at pos
     */
    public Piece getPiece(Board board, Position pos) {
        return board.getSpace(pos).getPiece();
    }

    /**
     * Check if the post move space is not occupied by another piece
     *
     * @param board board model
     * @param move move to make
     * @return true if space's piece attribute is null
     */
    public boolean isEmptySpace(Board board, Move move) {
        return board.getSpace(move.getEnd()).getPiece() == null;
    }


    /**
     * Adds the move parameter to the list of moves if it is valid.
     *
     * @param game The game the player is playing in
     * @param move  the move the player made
     * @return a `Message` describing the validity of the move
     */
    public Message validateMove(CheckersGame game, Move move) {
        Board board = game.getGameBoard(PieceColor.RED);
        Piece piece;
        if(moves.size() == 0) {
            piece = getPiece(board, move.getStart());
        }
        else {
            piece = getPiece(board, moves.get(0).getStart());
        }
        if(!isActiveColor(piece.getColor()))
            return INVALID_WRONG_PIECE_MOVE;
        if(!validateMoveToBlack(board, move))
            return INVALID_WHITE_SPACE_MOVE;
        if(!validateDiagonal(move))
            return INVALID_DIAGONAL_MOVE;
        if(!isEmptySpace(board, move))
            return INVALID_SPACE_OCCUPIED_MOVE;
        if(!validateDirection(piece, move))
            return INVALID_DIRECTION_MOVE;
        if(validateSingle(move) && jumpAvailable(board))
            return INVALID_NON_JUMP;
        if(lastMove != null) {
            if(validateMultipleJumpMove(board, move)) {
                lastMove = move;
                moves.add(move);
                return VALID;
            }
            return INVALID_NOT_SAME_PIECE_JUMPING;
        }
        if(!validateSingle(move)) {
            if(validateJumpMove(board, move)){
                lastMove = move;
                moves.add(move);
                return VALID;
            }
            return INVALID_SPACE_MOVE;
        }

        moves.add(move);
        lastMove = move;
        return VALID;

    }

    /**
     * A getter method for the list of moves
     *
     * @return the list of moves
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * captures the pieces that have been jumped over
     *
     * @param board the board from the players perspective
     */
    public boolean jumpAvailable(Board board) {
        List<Position> posList;
        posList = board.getAllPieceLocations(activeColor);
        List<Position> startPositions = new ArrayList<>();
        for(Move move : moves) {
            startPositions.add(move.getStart());
        }
        for(Position pos: posList)
            if(!startPositions.contains(pos) && pieceCanJump(board, pos))
                return true;
        return false;
    }

    /**
     * Makes sure that at east one piece can move
     *
     * @param game the game being played
     * @param board the board that the game is being played on
     * @return if at least one piece can move
     */
    public boolean piecesNotStuck(CheckersGame game, Board board) {
        return jumpAvailable(board) || playerHasSimpleMove(game, board, activeColor);
    }

    /**
     * Captures all of the pieces that have been jumped over by the MoveList
     *
     * @param board the board in reference to the player jumping
     */
    public void capturePieces(Board board) {
        // check for captured pieces and set it to null
        for (Move singleMove : moves) {
            if (validateJumpMove(board, singleMove)) {
                Space capturedSpace = getJumpedSpace(board, singleMove);
                capturedSpace.setPiece(null);
            }
        }
    }

    /**
     * Removes the last move from the MoveList
     *
     * @return - returns the move that was backed up
     */
    public Move backUp(){
        Move move = null;

        if(!moves.isEmpty()){
            move = moves.get(moves.size() - 1);
            moves.remove(moves.size() - 1);
            if(!moves.isEmpty()) {
                lastMove = moves.get(moves.size() - 1);
            }
            else {
                lastMove = null;
            }
        }
        return move;
    }


    /**
     * Checks to see if the position given exists within the board
     *
     * @param position the position which we are checking
     * @return if the position exists within the board
     */
    public boolean validatePosition(Position position) {
        return (position.getRow() >= 0 && position.getRow() < 8 &&
                position.getCell() >= 0 && position.getCell() < 8);
    }


    /**
     * Grabs a list of moves that an AI can make with a piece
     *
     * @param piece the piece that is being moved
     * @param pos the position of the piece
     * @return a list of possible jump moves
     */
    public List<Position> getJumpMoveList(Piece piece,Position pos) {
        List<Position> positions = new ArrayList<>();
        PieceColor color = piece.getColor();
        int startRow = pos.getRow();
        int startCol = pos.getCell();
        int topRow, bottomRow;
        if (color != PieceColor.RED)
            topRow = startRow + (1 * 2);
        else
            topRow = startRow + (-1 * 2);
        if (color != PieceColor.RED)
            bottomRow = startRow - (1 * 2);
        else
            bottomRow = startRow - (-1 * 2);
        int rightCol = startCol + 2;
        int leftCol = startCol - 2;
        positions.add(new Position(topRow, rightCol));
        positions.add(new Position(topRow, leftCol));
        if(piece.getType() == PieceType.KING) {
            positions.add(new Position(bottomRow, rightCol));
            positions.add(new Position(bottomRow, leftCol));
        }
        else {
            positions.add(pos);
            positions.add(pos);
        }
        return positions;
    }
    /**
     * Checks if the piece at the given position can jump
     *
     * @param board the board from the view of the player currently playing
     * @param pos the position of the peace which we are checking
     * @return if the piece has a jump move available
     */
    public boolean pieceCanJump(Board board, Position pos) {
        if (!validatePosition(pos))
            return false;
        Piece piece = getPiece(board, pos);
        if (piece == null)
            return false;
        int startRow = pos.getRow();
        int startCol = pos.getCell();
        int topRow, bottomRow;
        if (this.activeColor != PieceColor.RED)
            topRow = startRow + (1 * 2);
        else
            topRow = startRow + (-1 * 2);
        if (this.activeColor != PieceColor.RED)
            bottomRow = startRow - (1 * 2);
        else
            bottomRow = startRow - (-1 * 2);
        int rightCol = startCol + 2;
        int leftCol = startCol - 2;
        Position topRight = new Position(topRow, rightCol);
        Position topLeft = new Position(topRow, leftCol);
        Position bottomRight, bottomLeft;
        if(piece.getType() == PieceType.KING) {
            bottomRight = new Position(bottomRow, rightCol);
            bottomLeft = new Position(bottomRow, leftCol);
        }
        else {
            bottomRight = pos;
            bottomLeft = pos;
        }

        boolean lastTopLeft = true;
        boolean lastTopRight = true;
        boolean lastBottomLeft = true;
        boolean lastBottomRight = true;
        if(lastMove != null) {
            lastTopRight = !topRight.equals(lastMove.getStart());
            lastTopLeft = !topLeft.equals(lastMove.getStart());
            lastBottomRight = !bottomRight.equals(lastMove.getStart());
            lastBottomLeft = !bottomLeft.equals(lastMove.getStart());
        }

        boolean jumpIsAvailable = validatePosition(topRight) &&
                validateCanJump(board, new Move(pos, topRight)) &&
                lastTopRight ||
                validatePosition(topLeft) &&
                        validateCanJump(board, new Move(pos, topLeft)) &&
                lastTopLeft ||
                validatePosition(bottomRight) &&
                        validateCanJump(board, new Move(pos, bottomRight)) &&
                lastBottomRight ||
                validatePosition(bottomLeft) &&
                        validateCanJump(board, new Move(pos, bottomLeft)) &&
                lastBottomLeft;

        return jumpIsAvailable;
    }


    /**
     * Checks if the player of the given color can make a single move
     *
     * @param game the game model
     * @param board the bard from the players view
     * @param color the color of the player
     * @return if the player can make a single move
     */
    public boolean playerHasSimpleMove(CheckersGame game, Board board, PieceColor color) {
        boolean movePossible = false;
        int row, col;
        Position start;

        for(row = 0; row < 8 && !movePossible; row++){
            for(col = 0; col < 8 && !movePossible; col++){
                start = new Position(row, col);
                if(!board.isEmptySpace(start)){
                    if( board.getPieceColor(start) == color){
                        movePossible = pieceCanSimpleMove(game, board, start);
                    }
                }
            }
        }
        return movePossible;
    }

    /**
     * Checks if the given piece can make a single move
     *
     * @param game the game model
     * @param board the board from the players view
     * @param pos the position of the piece being checked
     * @return if the piece can make a single move
     */
    public boolean pieceCanSimpleMove(CheckersGame game, Board board, Position pos){
        boolean movePossible = false;
        int row, col;
        Position end;
        Move move;
        row = pos.getRow();
        col = pos.getCell();

        end = new Position(row + 1, col + 1 );
        move = new Move(pos, end);
        if(validatePosition(end)) {
            if(validateMove(game, move) == VALID){
                moves.remove(move);
                movePossible = true;
            }
        }


        end = new Position(row + 1, col - 1 );
        move = new Move(pos, end);
        if(validatePosition(end)) {
            if(validateMove(game, move) == VALID){
                moves.remove(move);
                movePossible = true;
            }
        }

        end = new Position(row - 1, col + 1 );
        move = new Move(pos, end);
        if(validatePosition(end)) {
            if(validateMove(game, move) == VALID){
                moves.remove(move);
                movePossible = true;
            }
        }

        end = new Position(row - 1, row - 1 );
        move = new Move(pos, end);
        if(validatePosition(end)) {
            if(validateMove(game, move) == VALID){
                moves.remove(move);
                movePossible = true;
            }
        }

        return movePossible;
    }

    /**
     * Finds if the AI move is done
     *
     * @param game the game that the ai is playing on
     * @return if the jump is finished
     */
    public boolean jumpFinished(CheckersGame game) {
        Board board = game.getGameBoard(PieceColor.RED);
        Move startMove = moves.get(0);
        if(moves.size() == 1 && validateSingle(startMove))
            return true;
        Move endMove = moves.get(moves.size() - 1);
        Piece piece = board.getSpace(endMove.getStart()).getPiece();

        List<Position> moveList = getJumpMoveList(piece,endMove.getStart());
        moveList.remove(endMove.getStart());
        for(Position possibleJump: moveList)
            if(validateCanJump(board, new Move(endMove.getEnd(), possibleJump)))
                return false;

        return true;
    }
}

