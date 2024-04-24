package bku.iot.demoiot;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText keyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameEditText = findViewById(R.id.username);
        keyEditText = findViewById(R.id.key);

        Button loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String key = keyEditText.getText().toString();

                // Validate the login information
                // If valid, start MainActivity
                if (validateLogin(username, key)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("key", key);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validateLogin(String username, String key) {
        // Validate the username and password
        // Return true if valid, false otherwise
        // This is just a placeholder, replace with your own validation logic
        return username.equals("admin") && key.equals("key");
    }
}
