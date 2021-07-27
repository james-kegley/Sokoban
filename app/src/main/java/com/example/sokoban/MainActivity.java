package com.example.sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sokoban.model.Crate;
import com.example.sokoban.model.Direction;
import com.example.sokoban.model.Game;
import com.example.sokoban.model.Level;
import com.example.sokoban.model.Placeable;
import com.example.sokoban.model.Target;
import com.example.sokoban.model.Wall;
import com.example.sokoban.model.Worker;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    public static int selectedLevel;
    GridView level_grid;
    private Game game = new Game();
    private int moveCount;
    private int completedCount;
    Level currentLevel;
    String[] placeableArray;
    int[] placeableId;

    public void fillPlaceables() {
        placeableArray = new String[currentLevel.getHeight()*currentLevel.getWidth()];
        int k = 0;
        for (int y = 0; y < currentLevel.getHeight(); y++) {
            for (int x = 0; x < currentLevel.getWidth(); x++) {
                placeableArray[k++] = currentLevel.allPlaceables[y][x].toString();
            }
        }
        placeableId = new int[placeableArray.length];
        for (int i = 0; i < placeableArray.length; i++) {
            switch (placeableArray[i]) {
                case "x":
                    placeableId[i] = (R.drawable.crate);
                    break;
                case "X":
                    placeableId[i] = (R.drawable.crate_on_target);
                    break;
                case "w":
                    placeableId[i] = (R.drawable.worker);
                    break;
                case "W":
                    placeableId[i] = (R.drawable.worker_on_target);
                    break;
                case "+":
                    placeableId[i] = (R.drawable.target);
                    break;
                case "#":
                    placeableId[i] = (R.drawable.wall);
                    break;
                case ".":
                    placeableId[i] = (R.drawable.empty);
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addLevels();

        Intent intent = getIntent();
        selectedLevel = intent.getIntExtra(HomeActivity.EXTRA_MESSAGE, 0);
        currentLevel = game.allMyLevels.get(selectedLevel);

        drawLevel(selectedLevel);

        setTitle("Level " + (selectedLevel + 1));
    }

    public void addLevels() {
        game.allMyLevels.clear();

        game.addLevel("Test1", 5, 6,
                "######" +
                        "#+...#" +
                        "#..x.#" +
                        "#w...#" +
                        "######" );


        game.addLevel("Test2", 6, 5,
                "#####" +
                        "#...#" +
                        "#.xw#" +
                        "#+..#" +
                        "#...#"+
                        "#####" );

        game.addLevel("Test3", 6, 7,
                "#######" +
                        "#+....#" +
                        "#..x..#" +
                        "#...x+#" +
                        "#w....#"+
                        "#######" );
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void drawLevel(Integer selectedLevel) {
        fillPlaceables();
        currentLevel = game.allMyLevels.get(selectedLevel);

        int levelNumber = selectedLevel + 1;
        int levelCount = game.getLevelCount();
        int targetCount = currentLevel.getTargetCount();
        completedCount = currentLevel.getCompletedCount();
        moveCount = currentLevel.getMoveCount();

        if (targetCount == completedCount) {
            displaySuccessDialog();
        }

        TextView level_name = findViewById(R.id.level_name);
        String levelName = getString(R.string.level_string, levelNumber, levelCount);
        level_name.setText(levelName);

        TextView target_count = findViewById(R.id.target_count);
        String levelTargetCount = getString(R.string.target_count_string, completedCount, targetCount);
        target_count.setText(levelTargetCount);

        TextView move_count = findViewById(R.id.move_count);
        String levelMoveCount = getString(R.string.move_count_string, moveCount);
        move_count.setText(levelMoveCount);

        PlaceableGrid adapter = new PlaceableGrid(MainActivity.this, placeableArray, placeableId);
        level_grid=(GridView)findViewById(R.id.level_grid);
        level_grid.setNumColumns(currentLevel.getWidth());
        level_grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /* My first attempt at a GridView - no adapter
        ConstraintLayout layout = findViewById(R.id.main_activity_layout);

        Level currentLevel = game.allMyLevels.get(selectedLevel);

        int levelHeight = currentLevel.getHeight();
        int levelWidth = currentLevel.getWidth();
        int levelNumber = selectedLevel + 1;
        int levelCount = game.getLevelCount();
        int targetCount = currentLevel.getTargetCount();
        int completedCount = currentLevel.getCompletedCount();
        moveCount = currentLevel.getMoveCount();

        if (targetCount == completedCount) {
            displaySuccess();
        }

        TextView level_name = findViewById(R.id.level_name);
        String levelName = getString(R.string.level_string, levelNumber, levelCount);
        level_name.setText(levelName);

        TextView target_count = findViewById(R.id.target_count);
        String levelTargetCount = getString(R.string.target_count_string, completedCount, targetCount);
        target_count.setText(levelTargetCount);

        TextView move_count = findViewById(R.id.move_count);
        String levelMoveCount = getString(R.string.move_count_string, moveCount);
        move_count.setText(levelMoveCount);

        ImageView placeableView;
        ConstraintLayout.LayoutParams lp;
        int id;
        int[][] idArray = new int[levelHeight][levelWidth];
        ConstraintSet cs = new ConstraintSet();

        // Add our views to the ConstraintLayout.
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                placeableView = new ImageView(this);
                lp = new ConstraintLayout.LayoutParams(ConstraintSet.MATCH_CONSTRAINT,
                        ConstraintSet.MATCH_CONSTRAINT);
                id = View.generateViewId();
                idArray[y][x] = id;
                placeableView.setId(id);
                placeableView.setForegroundGravity(Gravity.CENTER);
                layout.removeView(placeableView);
                if (currentLevel.allPlaceables[y] [x] instanceof Crate) {
                    placeableView.setImageResource(R.drawable.crate);
                }
                else if (currentLevel.allPlaceables[y] [x] instanceof Target) {
                    placeableView.setImageResource(R.drawable.target);
                    if (currentLevel.allPlaceables[y][x].toString().equals("X")) {
                        placeableView.setImageResource(R.drawable.crate_on_target);
                    }
                    if (currentLevel.allPlaceables[y][x].toString().equals("W")) {
                        placeableView.setImageResource(R.drawable.worker_on_target);
                    }
                }
                else if (currentLevel.allPlaceables[y] [x] instanceof Wall) {
                    placeableView.setImageResource(R.drawable.wall);
                }
                else if (currentLevel.allPlaceables[y] [x] instanceof Worker) {
                    placeableView.setImageResource(R.drawable.worker);
                }
                else {
                    placeableView.setImageResource(R.drawable.empty);
                }
                layout.addView(placeableView, lp);
            }
        }

        // Create horizontal chain for each row and set the 1:1 dimensions.
        // but first make sure the layout frame has the right ratio set.
        cs.clone(layout);
        cs.setDimensionRatio(R.id.level_grid, levelWidth + ":" + levelHeight);
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                id = idArray[y][x];
                cs.setDimensionRatio(id, "1:1");
                if (y == 0) {
                    // Connect the top row to the top of the frame.
                    cs.connect(id, ConstraintSet.TOP, R.id.level_grid, ConstraintSet.TOP);
                } else {
                    // Connect top to bottom of row above.
                    cs.connect(id, ConstraintSet.TOP, idArray[y-1][0], ConstraintSet.BOTTOM);
                }
            }
            // Create a horizontal chain that will determine the dimensions of our squares.
            // Could also be createHorizontalChainRtl() with START/END.
            cs.createHorizontalChain(R.id.level_grid, ConstraintSet.LEFT,
                    R.id.level_grid, ConstraintSet.RIGHT,
                    idArray[y], null, ConstraintSet.CHAIN_PACKED);
        }
        cs.applyTo(layout);
        */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void restartLevel(View view) {
        addLevels();
        currentLevel = game.allMyLevels.get(selectedLevel);
        drawLevel(selectedLevel);
    }

    public void playMoveSound() {
        final MediaPlayer move_sound = MediaPlayer.create(this, R.raw.move_sound);
        move_sound.start();
        move_sound.reset();
        move_sound.release();
    }

    public void displaySuccessDialog() {
        DialogFragment newFragment = new SuccessDialogFragment();
        newFragment.show(getSupportFragmentManager(), "success");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void move(View view) {
        playMoveSound();
        switch(view.getId()) {
            case R.id.up_button:
                game.move(Direction.UP);
                break;
            case R.id.down_button:
                game.move(Direction.DOWN);
                break;
            case R.id.left_button:
                game.move(Direction.LEFT);
                break;
            case R.id.right_button:
                game.move(Direction.RIGHT);
                break;
        }
        drawLevel(selectedLevel);
    }
}
