package com.maktab.twogame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maktab.twogame.model.Game;
import com.maktab.twogame.repository.GameRepository;

import java.util.List;

public class GameListFragment extends Fragment {
    private int grid_size = 5;
    private char[][] my_board = new char[grid_size][grid_size];
    private List<Game> mGameList;
    private Context mContext;
    private char turn = 'X';
    private TextView txt_turn;
    private int index = 0;
    private boolean endGame = false;

    RecyclerView mRecyclerView;


    public GameListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        txt_turn=view.findViewById(R.id.turn);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        List<Game> games = GameRepository.getInstance().getmGame();
        mRecyclerView.setAdapter(new GameAdapter(games, getContext()));
    }


    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mContain;
        private Game mGame;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mContain = itemView.findViewById(R.id.info_text);
            resetBoard();
            itemView.setOnClickListener(this);
        }

        private void bind(Game game) {
            mGame = game;
            mContain.setText(game.getContain());
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(mContext, mGame.getContain(), Toast.LENGTH_LONG).show();
            if (endGame != true) Move(getAdapterPosition());
        }


        private void resetBoard() {
            turn = 'X';
            for (int i = 0; i < grid_size; i++) {
                for (int j = 0; j < grid_size; j++) {
                    my_board[i][j] = ' ';
                }
            }
        }

        private void Move(int pos) {
            int row = (pos / grid_size);
            int col = (pos % grid_size);
            if (my_board[row][col] == ' ') {
                my_board[row][col] = turn;
                if (turn == 'X') {
                    mContain.setBackgroundColor(Color.BLUE);
                    turn = 'O';
                } else if (turn == 'O') {
                    mContain.setBackgroundColor(Color.RED);
                    turn = 'X';
                }
            }

            if (gameStatus() == 0) {
                Log.d("leila", "Turn: Player " + turn);
                txt_turn.setText("Turn: Player " + turn);
            } else if (gameStatus() == -1) {
                Log.d("leila", "This is a Draw match Stop Game");
                    txt_turn.setText("This is a Draw match");
                stopMatch();
            } else {
                Log.d("leila", turn + "  Loses! Stop Game");
                   txt_turn.setText(turn+" Loses!");
                stopMatch();
            }


        }

        



        private int gameStatus() {

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

        private boolean check_Diagonal(char player) {
            int count_Equal1 = 0, count_Equal2 = 0;
            for (int i = 0; i < grid_size; i++)
                if (my_board[i][i] == player)
                    count_Equal1++;
                else {
                    if (((i == 0) || (i == (grid_size - 1))) && my_board[i][i] != player) {

                    } else {
                        if (((i != 0) || (i != (grid_size - 1))) && (my_board[i][i] != player && my_board[i - 1][i - 1] == player && my_board[i + 1][i + 1] == player)) {
                            count_Equal1 = 0;
                        }
                    }
                }


            for (int i = 0; i < grid_size; i++) {
                if (my_board[i][grid_size - 1 - i] == player) {
                    count_Equal2++;
                    Log.d("leila", "is equal player i is " + i + "grid_size-1-i is " + (grid_size - 1 - i) + my_board[i][grid_size - 1 - i] + "count is" + count_Equal2);
                } else {
                    if (((i == 0) || (i == (grid_size - 1))) && my_board[i][grid_size - 1 - i] != player) {

                        Log.d("leila", "first or last " + "count is" + count_Equal2);

                    } else {
                        if (((i != 0) || (i != (grid_size - 1))) && (my_board[i][grid_size - 1 - i] != player && my_board[i - 1][grid_size - i - 1] == player && my_board[i + 1][grid_size - i - 2] == player)) {
                            count_Equal2 = 0;
                            Log.d("leila", "not first or last i is " + i + "grid_size-1-i is " + (grid_size - 1 - i) + my_board[i][grid_size - 1 - i] + "count is" + count_Equal2);
                            Log.d("leila", "not first or last i-1 is " + (i - 1) + "grid_size - i - 1" + (grid_size - i - 1) + my_board[i - 1][grid_size - i - 1] + "count is" + count_Equal2);
                            Log.d("leila", "not first or last i-1 is " + (i + 1) + "grid_size - i - 2" + (grid_size - i - 2) + my_board[i + 1][grid_size - i - 2] + "count is" + count_Equal2);

                        }
                    }
                }
            }


            if (count_Equal1 == 4 || count_Equal2 == 4)
                return true;
            else return false;
        }

        protected boolean check_Row_Equality(int r, char player) {
            int count_Equal = 0;
            for (int i = 0; i < grid_size; i++) {
                if (my_board[r][i] == player)
                    count_Equal++;
                else {
                    if (((i == 0) || (i == (grid_size - 1))) && my_board[r][i] != player) {

                    } else {
                        if (((i != 0) || (i != (grid_size - 1))) && (my_board[r][i] != player && my_board[r][i - 1] == player && my_board[r][i + 1] == player)) {
                            count_Equal = 0;
                        }
                    }
                }
            }
            if (count_Equal == 4)
                return true;
            else
                return false;
        }

        protected boolean check_Column_Equality(int c, char player) {
            int count_Col_Equal = 0;
            for (int i = 0; i < grid_size; i++) {
                if (my_board[i][c] == player)
                    count_Col_Equal++;
                else {
                    if ((i == 0 || i == grid_size - 1) && my_board[i][c] != player) {
                    } else {
                        if (my_board[i][c] != player && my_board[i - 1][c] != player && my_board[i + 1][c] != player) {
                            count_Col_Equal = 0;
                        }
                    }
                }
            }

            if (count_Col_Equal == 4)
                return true;
            else
                return false;
        }

        private void stopMatch() {
            endGame = true;
        }
    }


    public class GameAdapter extends RecyclerView.Adapter<UserHolder> {
        public GameAdapter(List<Game> mGame, Context mContext) {
            mGameList = mGame;
            mContext = mContext;
            for (int i = 0; i < grid_size; i++)
                for (int j = 0; j < grid_size; j++)
                    my_board[i][j] = ' ';
        }


        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item_user_list, parent, false);
            UserHolder userHolder = new UserHolder(view);
            return userHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bind(mGameList.get(position));
        }

        @Override
        public int getItemCount() {
            return mGameList.size();
        }

    }


}