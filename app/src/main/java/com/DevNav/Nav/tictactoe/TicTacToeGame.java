package com.DevNav.Nav.tictactoe;

import java.util.Random;

/**
 * Created by navi on 2014-11-30.
 */
public class TicTacToeGame {

    //declare board
    private char mBoard [];
    private final static int BOARD_SIZE = 9;

    //declare variables
    public static final char PLAYER_ONE = 'X';
    public static final char PLAYER_TWO = '0';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBOARD_SIZE ()
    {
        return BOARD_SIZE;
    }

    //creates and initializes the board
    public TicTacToeGame()
    {
        mBoard = new char [BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            mBoard[i] = EMPTY_SPACE;
            //might be area of error check part 1 tut
        }

        mRand = new Random();
    }

    //clears the board
    public void clearBoard()
    {
       for (int i = 0; i < BOARD_SIZE; i++)
       {
            mBoard[i] = EMPTY_SPACE;
       }
    }

    //gets the move and puts it in
    public void setMove (char player, int location)
    {
        mBoard[location] = player;
    }

    //AI
    public int getComputerMove()
    {
        int move;
        //if the computer can make a move that makes it win it does it
        for (int i = 0; i < getBOARD_SIZE(); i++)
        {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO)
            {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_TWO;
                if (checkForWinner() == 3)
                {
                    setMove(PLAYER_TWO, i);
                    return i;
                }
                else
                {
                    mBoard[i] = curr;
                }
            }
        }
        //if it cannot win with a move, then it will make a move to stop player from winning
        for (int i = 0; i < getBOARD_SIZE(); i++)
        {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO)
            {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_ONE;
                if (checkForWinner() == 2)
                {
                    setMove(PLAYER_TWO, i);
                    return i;
                }
                else
                {
                    mBoard[i] = curr;
                }
            }
        }
        //otherwise makes a move in a random empty space
        do
        {
         move = mRand.nextInt(getBOARD_SIZE());
        } while (mBoard[move] == PLAYER_ONE || mBoard[move] == PLAYER_TWO );

        setMove(PLAYER_TWO, move);
        return move;
    }

    //checking for winner
    public int checkForWinner ()
    {
        //checks each horizontal combination from left to right
        for (int i = 0; i <= 6; i+= 3)
        {
            // player wins return 2
            if (mBoard[i] == PLAYER_ONE && mBoard[i+1] == PLAYER_ONE && mBoard[i+2] == PLAYER_ONE)
            {
                return 2;
            }

            // computer wins return 3
            if (mBoard[i] == PLAYER_TWO && mBoard[i+1] == PLAYER_TWO && mBoard[i+2] == PLAYER_TWO)
            {
                return 3;
            }
        }

        //checks each vertical combination from left to right
        for (int i = 0; i <= 2; i++)
        {
            // player wins return 2
            if (mBoard[i] == PLAYER_ONE && mBoard[i+3] == PLAYER_ONE && mBoard[i+6] == PLAYER_ONE)
            {
                return 2;
            }

            // computer wins return 3
            if (mBoard[i] == PLAYER_TWO && mBoard[i+3] == PLAYER_TWO && mBoard[i+6] == PLAYER_TWO)
            {
                return 3;
            }
        }

        //checks each diagonal combination from left to right for player
        if ((mBoard[0] == PLAYER_ONE && mBoard[4] == PLAYER_ONE && mBoard[8] == PLAYER_ONE) ||
             mBoard[2] == PLAYER_ONE && mBoard[4] == PLAYER_ONE && mBoard[6] == PLAYER_ONE)
        {
            return 2;
        }

        //checks each diagonal combination from left to right for computer
        if ((mBoard[0] == PLAYER_TWO && mBoard[4] == PLAYER_TWO && mBoard[8] == PLAYER_TWO) ||
                mBoard[2] == PLAYER_TWO && mBoard[4] == PLAYER_TWO && mBoard[6] == PLAYER_TWO)
        {
            return 3;
        }

        //checking if there is still room to make a move if nobody has won yet
        for (int i = 0; i < getBOARD_SIZE(); i++)
        {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO)
            {
                return 0;
            }
        }
        //will fall here if all spaces full, and nobody won (tie game)
        return 1;
    }
}
