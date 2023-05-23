package com.webcheckers.model;

import com.sun.source.tree.AssertTree;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * MoveList test
 *
 * @author Greg Godlewski
 * @author Ethan Yaccarino-Mims
 */
@Tag("Model-tier")
public class MoveListTest {

    private MoveList CuT;

    @BeforeEach
    public void setup() {
        CuT = new MoveList(Piece.PieceColor.RED);
    }

    @Test
    public void constructorTest() {
        assertEquals(CuT.getMoves().size(), 0);

        assertTrue(CuT.isActiveColor(Piece.PieceColor.RED));

        CuT = new MoveList(Piece.PieceColor.WHITE);
        assertTrue(CuT.isActiveColor(Piece.PieceColor.WHITE));
    }

    @Test
    public void validateSingleTest() {
        Move move = new Move(new Position(0, 0), new Position(1, 1));
        assertTrue(CuT.validateSingle(move));

        move = new Move(new Position(1, 1), new Position(2, 0));
        assertTrue(CuT.validateSingle(move));
    }

    @Test
    public void validateDiagonalTest() {
        Move move = new Move(new Position(0, 0), new Position(1, 1));
        assertTrue(CuT.validateDiagonal(move));

        move = new Move(new Position(1, 1), new Position(4, 4));
        assertTrue(CuT.validateDiagonal(move));

        move = new Move(new Position(3, 3), new Position(6, 0));
        assertTrue(CuT.validateDiagonal(move));
    }

    /**@Test
    public void getJumpedSpaceTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Space space = game.getSpace(new Position(3,3));
        Move move = new Move(new Position(2, 2), new Position(4, 4));

        assertEquals(CuT.getJumpedSpace(game.getGameBoard(Piece.PieceColor.RED), move), space);
    }**/

    @Test
    public void validateMoveToBlackTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(2, 2), new Position(3, 3));

        assertFalse(CuT.validateMoveToBlack(game.getGameBoard(Piece.PieceColor.RED), move));
    }

    @Test
    public void validateJumpMoveTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(2, 2), new Position(4, 4));

        assertFalse(CuT.validateJumpMove(game.getGameBoard(Piece.PieceColor.RED), move));

        //game.getSpace(new Position(3, 3)).setPiece(
               // new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE));

        assertFalse(CuT.validateJumpMove(game.getGameBoard(Piece.PieceColor.RED), move));
    }

    @Test
    public void isActiveColorTest() {
        assertTrue(CuT.isActiveColor(Piece.PieceColor.RED));
    }

    @Test
    public void getPieceTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Position pos = new Position(1, 1);

        assertEquals(CuT.getPiece(game.getGameBoard(Piece.PieceColor.RED), pos),
                game.getGameBoard(Piece.PieceColor.RED).getRows().get(1).getSpaces().get(1).getPiece());
    }

    @Test
    public void isEmptySpaceTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(0, 0), new Position(1, 0));

        assertFalse(CuT.isEmptySpace(game.getGameBoard(Piece.PieceColor.RED), move));
    }

    @Test
    public void validateMultipleJumpMoveTest() {
        Piece e1 = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE);
        Piece e2 = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE);
        Board board = new Board();
        board.getSpace(new Position(1, 3)).setPiece(e1);
        board.getSpace(new Position(3, 5)).setPiece(e2);
        board.getSpace(new Position(4,5)).setPiece(null);
    }

    /**@Test
    public void validateMoveTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(0, 0), new Position(1, 1));

        assertEquals(CuT.validateMove(game, move), MoveList.INVALID_SPACE_OCCUPIED_MOVE);

        move = new Move(new Position(2, 0), new Position(3, 0));
        assertEquals(CuT.validateMove(game, move), MoveList.INVALID_WHITE_SPACE_MOVE);

        move = new Move(new Position(2, 0), new Position(4, 0));
        assertEquals(CuT.validateMove(game, move), MoveList.INVALID_DIAGONAL_MOVE);
    }**/

    /**
     * Tests the `getMoves` method
     */
    @Test
    public void getMovesTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(2, 2), new Position(3, 3));
        assertEquals(CuT.getMoves().size(), 0);
        //CuT.validateMove(game, move);
        assertEquals(CuT.getMoves().size(), 0);
        //assertEquals(CuT.getMoves().get(1), move);
    }

    /**
     * Tests the multiple jump move story

    @Test
    public void validateMultipleJumpMovesTest() {
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        CheckersGame game = new CheckersGame(p1,p2);
        Board board = game.getGameBoard(Piece.PieceColor.RED);

        Move move = new Move(new Position(5,2), new Position(3,4));
        board.getSpace(new Position(4,3)).setPiece(new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE));

        board.getSpace(new Position(2,5)).emptySpace();

        Message message = CuT.validateMove(game, move);
        assertEquals(message.getText(), "Move done");

        move = new Move(new Position(3,4), new Position(2,5));

        message = CuT.validateMove(game, move);
        assertEquals(message, MoveList.INVALID_NOT_SAME_PIECE_JUMPING);

        Piece piece = board.getPiece(new Position(1,6));
        board.getSpace(new Position(1,6)).emptySpace();
        assertNotNull(piece);

        board.getSpace(new Position(2,5)).setPiece(piece);
        move = new Move(new Position(3,4), new Position(1,6));
        message = CuT.validateMove(game, move);
        assertEquals(message.getText(), "Move done");

    }
    */

    /**
     * Makes sure that the king cannot reverse its jumps as another jump

    @Test
    public void kingCannotVisitPriorSpaces() {
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        CheckersGame game = new CheckersGame(p1,p2);
        game.getGameBoard(Piece.PieceColor.RED).getSpace(new Position(4,3)
        ).setPiece(new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE));
        Move lastMove = new Move(new Position(5, 2), new Position(3, 4));
        Message message = CuT.validateMove(game, lastMove);
        assertEquals(message.getText(), "Move done");
        game.getGameBoard(Piece.PieceColor.RED).getSpace(new Position(3,4)
        ).setPiece(new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE));
        Move move = new Move(new Position(3,4), new Position(5,2));
        message = CuT.validateMove(game, move);
        assertEquals(message, MoveList.INVALID_NOT_SAME_PIECE_JUMPING);
    }
    */




    /**@Test
    public void capturedPiecesTest() {
        CheckersGame game = new CheckersGame(new Player("p1"), new Player("p2"));
        Move move = new Move(new Position(2, 2), new Position(4, 4));
        game.getSpace(new Position(3, 3)).setPiece(game.getPiece(new Position(7, 7)));
        CuT.validateMove(game, move);
        CuT.capturePieces(game.getGameBoard(Piece.PieceColor.RED));
        assertNull(game.getPiece(new Position(3, 3)));
    }**/
}
