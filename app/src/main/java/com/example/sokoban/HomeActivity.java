package com.example.sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sokoban.model.Game;
import com.example.sokoban.model.Level;

public class HomeActivity extends AppCompatActivity {
    private Game game = new Game();
    public static final String EXTRA_MESSAGE = "com.example.sokoban.extra.MESSAGE";
    private static MediaPlayer game_music = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main Menu");
        setContentView(R.layout.activity_home);
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

        String[] levels = new String[game.allMyLevels.size()];
        for (int i = 0; i < game.allMyLevels.size(); i++) {
            Level alevel = game.allMyLevels.get(i);
            String level = alevel.getLevelName();
            levels[i] = level;
        }
        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, levels);

        ListView listView = (ListView) findViewById(R.id.level_list);
        listView.setAdapter(levelsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedLevel = (Integer) parent.getPositionForView(view);
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, selectedLevel);
                startActivity(intent);
            }
        });

        startMusic();
    }


    public void startMusic() {
        if (game_music == null) {
            game_music = MediaPlayer.create(this, R.raw.background_music);
            game_music.setLooping(true);
            game_music.start();
        }
    }
}
