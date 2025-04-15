package com.example.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "GymLog";
    ActivityMainBinding binding;
    private GymLogRepository repository;

    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });
    }

    private void getInformationFromDisplay() {
        this.mExercise = binding.exerciseInputEditText.getText().toString();

        try {
            this.mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight edit text.");
        }

        try {
            this.mReps = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Reps edit text.");
        }
    }

    private void insertGymLogRecord() {
        GymLog gymLog = new GymLog(this.mExercise, this.mWeight, this.mReps);
        repository.insertGymLog(gymLog);
    }

    private void updateDisplay() {
        String currentInfo = binding.logDisplayTextView.getText().toString();
        Log.d(TAG, "current info: " + currentInfo);
        String newDisplay = String.format(Locale.US, "Exercise: %s%nWeight: %.2f%nReps: %s%n=-=-=-=%n", mExercise,mWeight,mReps);
        newDisplay += currentInfo;

        binding.logDisplayTextView.setText(newDisplay);

        Log.i(TAG, repository.getAllLogs().toString());
    }
}