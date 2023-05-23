package com.webcheckers.model;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.*;


import com.webcheckers.ui.PostMoveRoute;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author Hayden Liang
 * @author Greg Godlewski
 */
@Tag("Model-tier")
public class BoardTester {

    private Board CuT;

    List<Row> rows = new ArrayList<Row>();
    Piece piece = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);
    Space space = new Space(piece, Space.SpaceColor.BLACK,1);
    Row row = new Row(8);

    private Piece.PieceColor red = Piece.PieceColor.RED;
    private Piece.PieceColor white = Piece.PieceColor.WHITE;
    private Piece.PieceType single = Piece.PieceType.SINGLE;
    private Piece.PieceType king = Piece.PieceType.KING;
    private Piece redSingle;
    private Piece whtSingle;
    private Piece redKing;
    private Piece whtKing;
    private Position start;
    private Position end;
    private Move move;
    private CheckersGame game;


    @BeforeEach
    public void TestSetUp(){
        redSingle = new Piece(red, single);
        whtSingle = new Piece(white, single);
        redKing = new Piece(red, king);
        whtKing = new Piece(white, king);
        start = mock( Position.class );
        when( start.getRow() ).thenReturn( 5 );
        when( start.getCell() ).thenReturn( 4 );
        end = mock( Position.class );
        when( end.getRow() ).thenReturn( 4 );
        when( end.getCell() ).thenReturn( 3 );
        game = mock( CheckersGame.class );


        CuT = new Board();
    }


    /**
     * Test the main constructor
     */
    @Test
    public void ctor_notNull() {
        final Board CuT = new Board();
        assertNotNull(CuT);
    }



    /**
     * Test the isoffBoard method
     */
    @Test
    public void isoffBoardTester(){
        final Board board = new Board();
        Position pos = new Position(-1,-1);
        assertTrue(board.isOffBoard(pos));
    }

    /**
     * Test the isEmptySpace method
     */
    @Test
    public void isEmptySpaceTester(){
        final Board board = new Board();
        Position pos = new Position(1,1);
        Boolean CuT = board.isEmptySpace(pos);
        assertTrue(CuT);
    }

    /**
     * Test the isEmptySpace method
     */
    @Test
    public void getPieceTester(){
        final Board board = new Board();
        Position pos = new Position(2,2);
        Piece CuT = board.getPiece(pos);
        assertNull(CuT);
    }

    /**
     * Test the setBoardRows method
     */
    @Test
    public void setBoardRowsTester(){
        List<Row> rows = new ArrayList<Row>();
        final Board board = new Board();
        board.setBoardRows(rows);
        List<Row> CuT = board.getRows();
        assertEquals(CuT,rows);
    }

    /**
     * Test the getSpace method
     */
    @Test
    public void getSpaceTester(){
        List<Row> rows = new ArrayList<Row>();
        final Board board = new Board();
        Position pos = new Position(2,2);
        Space CuT = board.getSpace(pos);
        assertNotNull(CuT);
    }

    @Test
    public void testIsOffBoard(){
        assertTrue(CuT.isOffBoard(new Position(-1,0)));
        assertTrue(CuT.isOffBoard(new Position(0,-1)));
        assertTrue(CuT.isOffBoard(new Position(8,-1)));
    }

    @Test
    public void getInvertedBoardTest() {
        Board reversedBoard = Board.getReversedBoard( CuT );
        List<Row> boardRows = CuT.getRows();
        List<Row> invertedRows = reversedBoard.getRows();
        assertNotEquals( boardRows, invertedRows );
    }

    @Test
    public void iteratorTest() {
        CuT.iterator();
        assertTrue(true);
    }

}
