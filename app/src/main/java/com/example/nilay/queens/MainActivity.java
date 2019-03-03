package com.example.nilay.queens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import java.util.concurrent.TimeUnit;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static LinearLayout array[];
    private static TextView textViewArray[];
    int n = 4;
    LinearLayout linearLayout;
    static MainActivity mn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mn = MainActivity.this;


        linearLayout = findViewById(R.id.GridLayout);


    }

    public void start(View view) {
        EditText editText = findViewById(R.id.editText);
        n = Integer.parseInt(editText.getText().toString());
        array = new LinearLayout[n];

        for (int i = 0; i < n; i++) {
            array[i] = new LinearLayout(mn);
            array[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(array[i]);
        }
        textViewArray = new TextView[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                textViewArray[j] = new TextView(mn);
                textViewArray[j].setTextSize(50);
                textViewArray[j].setPadding(32, 8, 32, 8);
                mainui(textViewArray[j],"0");
                array[i].addView(textViewArray[j]);
            }
        }

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int board[][] = new int[n][n];
                solveQueen(board, 0, n);
            }
        };
        t.start();
    }

    static boolean isSafe(int board[][], int row, int col, int N) {
        int i, j;
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    static boolean solveQueen(int board[][], int col, int N) {
        if (col >= N)
            return true;

        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col, N)) {
                board[i][col] = 1;
                TextView view = (TextView) array[i].getChildAt(col);
                //view.setText("1");
                mainui(view,"1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (solveQueen(board, col + 1, N) == true)
                    return true;

                board[i][col] = 0;
                view = (TextView) array[i].getChildAt(col);
                //view.setText("0");
                mainui(view,"0");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static void mainui(final TextView textView, final String s) {
        mn.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
            }
        });
    }
}


