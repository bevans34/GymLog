package com.example.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.database.entities.User;
import com.example.gymlog.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private GymLogRepository repository;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyUser()) {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Username may not be blank.", Toast.LENGTH_SHORT).show();
            return false;
        }

        this.user = repository.getUserByUserName(username);

        if (this.user != null) {
            String password = binding.passwordLoginEditText.getText().toString();
            if (password.equals(this.user.getPassword())) {
                return true;
            } else {
                Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        Toast.makeText(this, String.format("No %s found", username), Toast.LENGTH_SHORT).show();
        return false;
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}