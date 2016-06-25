package com.DevNav.Nav.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends ActionBarActivity {

    private TicTacToeGame mGame;

    private Button mBoardButtons [];

    private Button mNewButton;

    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;

    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;

    private boolean startingPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getWindow().setBackgroundDrawableResource(R.drawable.wall);

        boolean mGameType = getIntent().getExtras().getBoolean("gameType");

        //populating buttons
        mBoardButtons = new Button[TicTacToeGame.getBOARD_SIZE()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mNewButton = (Button) findViewById(R.id.NewGame);

        mInfoTextView = (TextView) findViewById(R.id.information);

        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mPlayerOneCount.setTextColor(Color.BLUE);

        mTieCount = (TextView) findViewById(R.id.tiesCount);

        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerTwoCount.setTextColor(Color.RED);

        //takes integer value of counter and makes it a string
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);

        mGame = new TicTacToeGame();
        startNewGame(mGameType);
    }
    //gets board ready to go to start the new game
    private void startNewGame(boolean isSingle)
    {
        this.mIsSinglePlayer = isSingle;

        mGame.clearBoard();

        mNewButton.setEnabled(true);
        mNewButton.setOnClickListener(new ButtonClickListener1());

        for (int i = 0; i < mBoardButtons.length; i++)
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled (true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        startingPlayer = mIsPlayerOneTurn;
        if (mIsSinglePlayer)
        {
            mPlayerOneText.setText("You:");
            mPlayerTwoText.setText("Android:");

            if (mPlayerOneFirst)
            {
                mInfoTextView.setText(R.string.first_human);
                mPlayerOneFirst = false;
            }
            else
            {
                mInfoTextView.setText(R.string.turn_human);
                int move = mGame.getComputerMove();
                setMove(mGame.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        }
        else
        {
            mPlayerOneText.setText("Player One:");
            mPlayerTwoText.setText("Player Two:");

            if (mPlayerOneFirst)
            {
                mInfoTextView.setText(R.string.turn_player_one);
                mPlayerOneFirst = false;
            }
            else
            {
                mInfoTextView.setText(R.string.turn_player_two);
                mPlayerOneFirst = true;
            }
        }
        mGameOver = false;
    }

    //when player makes a move and clicks....
    private class ButtonClickListener implements View.OnClickListener
    {
        int location;

        //gets button that was clicked
        public ButtonClickListener(int location)
        {
            this.location = location;
        }

        //Making the move based on the click
        public void onClick(View view)
        {
            //checks to see fi game still on
            if (!mGameOver)
            {
                //if its a free space
                if (mBoardButtons[location].isEnabled())
                {
                    //singlepl;ayer logic
                    if (mIsSinglePlayer)
                    {
                        //registers the move
                        setMove(mGame.PLAYER_ONE, location);
                        //and checks status of the game
                        int result = mGame.checkForWinner();

                        //if there is still room to move and nobody won sets turn to computer
                        if (result == 0)
                        {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move = mGame.getComputerMove();
                            setMove(mGame.PLAYER_TWO, move);
                            result = mGame.checkForWinner();
                        }
                        //if still room, sets to human turn
                        if (result == 0)
                        {
                            mInfoTextView.setText(R.string.turn_human);

                        }
                        //now if its a tie
                        else if (result == 1)
                        {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        }
                        //if player wins
                        else if (result == 2)
                        {
                            mInfoTextView.setText(R.string.result_human_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;

                        }
                        //computer wins
                        else
                        {
                            mInfoTextView.setText(R.string.result_android_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;

                        }
                    }
                    //multiplayer logic
                    else
                    {

                        if (mIsPlayerOneTurn)
                        {
                            setMove(mGame.PLAYER_ONE, location);
                            mIsPlayerOneTurn = false;
                        }
                        else
                        {
                            setMove(mGame.PLAYER_TWO, location);
                            mIsPlayerOneTurn = true;
                        }

                        int result = mGame.checkForWinner();

                        //if there is still room to move and nobody won sets turn to computer
                        if (result == 0)
                        {
                            if(mIsPlayerOneTurn)
                            {
                                mInfoTextView.setText(R.string.turn_player_one);
                            }
                            else
                            {
                                mInfoTextView.setText(R.string.turn_player_two);
                            }
                        }
                        //now if its a tie
                        else if (result == 1)
                        {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                            if(startingPlayer)
                                mIsPlayerOneTurn = false;
                            else
                                mIsPlayerOneTurn = true;
                        }
                        //if player wins
                        else if (result == 2)
                        {
                            mInfoTextView.setText(R.string.result_player_one_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                            if(startingPlayer)
                                mIsPlayerOneTurn = false;
                            else
                                mIsPlayerOneTurn = true;


                        }
                        //player2 wins
                        else
                        {
                            mInfoTextView.setText(R.string.result_player_two_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                            if(startingPlayer)
                                mIsPlayerOneTurn = false;
                            else
                                mIsPlayerOneTurn = true;
                        }
                    }
                }
            }
        }
    }

    //appropriates buttons based on who moved and where
    private void setMove(char player, int location)
    {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);    //disables button after it is used
        mBoardButtons[location].setText(String.valueOf(player));        // makes it an x or an o look at other class

        //makes it greeen if a human
        if (player == mGame.PLAYER_ONE)
        {
            mBoardButtons[location].setTextColor(Color.BLUE);
        }
        else
        {
            mBoardButtons[location].setTextColor(Color.RED);
        }

    }

    private class ButtonClickListener1 implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(startingPlayer)
                mIsPlayerOneTurn = false; // TEST OUT THE SWITCH BACK,LOOOOOK AT SCREENSHOTS
            else
                mIsPlayerOneTurn = true;
            startNewGame(mIsSinglePlayer);
            mGameOver = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.action_about:
                aboutMenuItem();
                break;

            case R.id.exitGame:
                MyActivity.this.finish();
                break;
        }
        return true;
    }

    private void aboutMenuItem()
    {
        new AlertDialog.Builder(this)
        .setTitle(R.string.about_heading)
        .setMessage(R.string.about_info)
        .setNeutralButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        }).show();
    }
}