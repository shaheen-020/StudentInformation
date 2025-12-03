package com.example;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {
    EditText etRoll, etNewName;
    Button btnUpdate;
    private UpdateStudentFacade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        etRoll = findViewById(R.id.etUpdRoll);
        etNewName = findViewById(R.id.etUpdName);
        btnUpdate = findViewById(R.id.btnUpdateStudent);

        facade = new UpdateStudentFacade(this);

        btnUpdate.setOnClickListener(v -> {
            String roll = etRoll.getText().toString().trim();
            String newName = etNewName.getText().toString().trim();
            facade.updateStudent(roll, newName);
        });
    }

    // Facade Inner Class
    private class UpdateStudentFacade {
        private DbHelper dbHelper;
        private android.content.Context context;

        public UpdateStudentFacade(android.content.Context context) {
            this.context = context;
            this.dbHelper = new DbHelper(context);
        }

        public void updateStudent(String roll, String newName) {
            if (roll.isEmpty() || newName.isEmpty()) {
                Toast.makeText(context, "Enter ID and new name", Toast.LENGTH_SHORT).show();
            } else {
                boolean ok = dbHelper.updateStudent(roll, newName);
                Toast.makeText(context, ok ? "Updated" : "Student not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
