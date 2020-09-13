package com.maktab.twogame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TicDetailFragment extends Fragment {
    private static final String BUNDLE_KEY_MY_BOARD1 = "myborad1";
    private static final String BUNDLE_KEY_MY_BOARD2 = "myborad2";
    private static final String BUNDLE_KEY_GAMEBORAD = "gameborad";
    private int grid_size;
    TableLayout gameBoard;
    TextView txt_turn;
    char[][] my_board;
    boolean flag = false;
    char turn;
    Button reset_btn;

    public TicDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grid_size = Integer.parseInt(getString(R.string.size_of_board));
        my_board = new char[grid_size][grid_size];

        if (savedInstanceState != null) {
            turn = savedInstanceState.getChar("turn");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tic_detail, container, false);
        findview(view);
        resetBoard();


        txt_turn.setText("Turn: " + turn);
        initBoard();


        if (savedInstanceState != null) {
            turn = savedInstanceState.getChar("turn");
            my_board = (char[][]) savedInstanceState.getSerializable(BUNDLE_KEY_MY_BOARD1);

            txt_turn.setText("Turn: " + turn);

            for (int i = 0; i < gameBoard.getChildCount(); i++) {
                TableRow row = (TableRow) gameBoard.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    TextView tv = (TextView) row.getChildAt(j);
                    tv.setText(""+my_board[i][j]);
                    tv.setOnClickListener(Move(i, j, tv));
                }
            }

        }


        gameStatus();
        setListeners();
        return view;

        // return inflater.inflate(R.layout.fragment_tic_detail, container, false);
    }


    private void initBoard(){
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }
    }


    private void findview(View view) {

        gameBoard = view.findViewById(R.id.mainBoard);
        txt_turn = view.findViewById(R.id.turn);
        reset_btn = view.findViewById(R.id.reset);

    }


    private void setListeners() {

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current = getActivity().getIntent();
                getActivity().finish();
                startActivity(current);
            }
        });
    }


    protected void resetBoard() {
        turn = 'X';

        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                my_board[i][j] = ' ';
            }
        }


    }

    protected int gameStatus() {

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for (int i = 0; i < grid_size; i++) {
            if (check_Row_Equality(i, 'X'))
                return 1;
            if (check_Column_Equality(i, 'X'))
                return 1;
            if (check_Row_Equality(i, 'O'))
                return 2;
            if (check_Column_Equality(i, 'O'))
                return 2;
            if (check_Diagonal('X'))
                return 1;
            if (check_Diagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (my_board[i][j] == ' ')
                    boardFull = false;
            }
        }
        if (boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player) {
        int count_Equal1 = 0, count_Equal2 = 0;
        for (int i = 0; i < grid_size; i++)
            if (my_board[i][i] == player)
                count_Equal1++;
        for (int i = 0; i < grid_size; i++)
            if (my_board[i][grid_size - 1 - i] == player)
                count_Equal2++;
        if (count_Equal1 == grid_size || count_Equal2 == grid_size)
            return true;
        else return false;
    }

    protected boolean check_Row_Equality(int r, char player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size; i++) {
            if (my_board[r][i] == player)
                count_Equal++;
        }

        if (count_Equal == grid_size)
            return true;
        else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size; i++) {
            if (my_board[i][c] == player)
                count_Equal++;
        }

        if (count_Equal == grid_size)
            return true;
        else
            return false;
    }

    protected boolean Cell_Set(int r, int c) {
        return !(my_board[r][c] == ' ');
    }

    protected void stopMatch() {
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    View.OnClickListener Move(final int r, final int c, final TextView tv) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Cell_Set(r, c)) {
                    my_board[r][c] = turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn = 'O';
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                    }
                    if (gameStatus() == 0) {
                        txt_turn.setText("Turn: Player " + turn);
                    } else if (gameStatus() == -1) {
                        txt_turn.setText("This is a Draw match");
                        stopMatch();
                    } else {
                        txt_turn.setText(turn + " Loses!");
                        stopMatch();
                    }
                }

            }
        };
    }

    /*
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d("LEILA", "in block");
            my_board[0] = savedInstanceState.getCharArray(BUNDLE_KEY_MY_BOARD1);
            my_board[1] = savedInstanceState.getCharArray(BUNDLE_KEY_MY_BOARD2);
            turn = savedInstanceState.getChar("turn");
            Log.d("LEILA", "my_board[0][0]" + my_board[0][0]);
            Log.d("LEILA", "my_board[0][1]" + my_board[0][1]);

            for (int i = 0; i < gameBoard.getChildCount(); i++) {
                TableRow row = (TableRow) gameBoard.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    TextView tv = (TextView) row.getChildAt(j);
                    Log.d("leila", "myborad[i][j] " + my_board[i][j] + " i and j " + i + " " + j);
                    tv.setText("" + my_board[i][j]);
                    String temp;
                    temp = tv.getText().toString();
                    Log.d("leila", "textview is" + temp);
                    //  tv.setBackgroundColor(Color.RED);
                    //   tv.setOnClickListener(Move(i, j, tv));
                }
            }

        }

    }
     */


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_KEY_MY_BOARD1,my_board);
//        outState.putCharArray(BUNDLE_KEY_MY_BOARD1, my_board[0]);
//        outState.putCharArray(BUNDLE_KEY_MY_BOARD2, my_board[1]);
        outState.putChar("turn", turn);
    }


}