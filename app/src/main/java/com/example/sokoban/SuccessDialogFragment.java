package com.example.sokoban;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SuccessDialogFragment extends DialogFragment {
    private static final String EXTRA_MESSAGE = "com.example.sokoban.extra.MESSAGE";
    TextView moveCount;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.success_dialog, null))
                .setPositiveButton(R.string.replay_string, new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {
                        backToLevel();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.menu_string, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        backToMenu();
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void backToLevel() {
        Intent intent = new Intent(SuccessDialogFragment.this.getActivity(), MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, MainActivity.selectedLevel);
        startActivity(intent);
    }

    public void backToMenu() {
        Intent intent = new Intent(SuccessDialogFragment.this.getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}
