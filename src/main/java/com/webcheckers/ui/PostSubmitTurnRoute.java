package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/***
 * Called when Submit Turn button is pressed
 *
 * @author Shrif Rai
 * @author Ethan Yaccarino-Mims
 */
public class PostSubmitTurnRoute implements Route{

    public static final Message MOVE_NOT_MADE = Message.error("No moves made. Make a move!");
    public static final Message ILLEGAL_TURN = Message.error("It's not your turn to move!");
    public static final Message MOVE_MADE = Message.info("Your move has been made!");
    public static final Message MUST_TAKE_JUMP = Message.error("You must take the available jump move!");
    GameCenter gameCenter;
    Gson parser;

    /**
     * The UI Controller to submit and render moves made by players
     *
     * @param gameCenter GameCenter to keep track of games
     * @param parser Gson for parsing messages
     */
    public PostSubmitTurnRoute(GameCenter gameCenter, Gson parser) {
        this.gameCenter = gameCenter;
        this.parser = parser;
    }

    /**
     * the handle method for the post submit turn route
     *
     * @param request the http request
     * @param response the http response
     * @return the Json of the message that will be displayed
     */
    @Override
    public String handle(Request request, Response response){
        Session newSession = request.session();
        Player p1 = newSession.attribute(GetHomeRoute.PLAYER);
        CheckersGame game = gameCenter.retrieveGame(p1);
        if(game.getActiveColor() != game.getColor(p1)){
            return parser.toJson(ILLEGAL_TURN);
        }

        if(!game.hasMove()) {
            return parser.toJson(MOVE_NOT_MADE);
        }

        if(game.canMakeJump()) {
            return parser.toJson(MUST_TAKE_JUMP);
        }

        game.makeMove();

        Board board = game.getGameBoard(Piece.PieceColor.RED);
        if(board.getAllPieceLocations(Piece.PieceColor.RED).size() == 0 ||
                game.piecesStuck(Piece.PieceColor.RED)) {
            gameCenter.setMessage(game.getRedPlayer().getName(), "White Player Wins!");
            gameCenter.setMessage(game.getWhitePlayer().getName(), "White Player Wins!");
            gameCenter.endGame(game);
            return parser.toJson(new Message(("White Player Wins!"), Message.Type.INFO));
        } else if(board.getAllPieceLocations(Piece.PieceColor.WHITE).size() == 0 ||
                game.piecesStuck(Piece.PieceColor.WHITE)) {
            gameCenter.setMessage(game.getRedPlayer().getName(), "Red Player wins!");
            gameCenter.setMessage(game.getWhitePlayer().getName(), "Red Player wins!");
            gameCenter.endGame(game);
            return parser.toJson(new Message(("Red Player wins!"), Message.Type.INFO));
        }

        return parser.toJson(MOVE_MADE);

    }

}