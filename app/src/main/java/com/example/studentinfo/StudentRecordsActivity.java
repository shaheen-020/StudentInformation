package com.example.studentinfo;
import android.content.Context; // Use 'Context' for clarity
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.studentinfo.R;

import java.util.List;

public class StudentRecordsActivity extends AppCompatActivity {

    private EditText etQuery;
    private Button btnSearch;
    private TableLayout tableLayout;
    private DbHelper db;
    private StudentService studentService;
    private StudentResultAdapter adapter;

    // This field was declared but not used. It can be removed if not needed.
    // private SearchStrategy searchStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The R class will now be found correctly from your project's resources
        setContentView(com.example.studentinfo.R.layout.activity_student_records);

        etQuery = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tableLayout = findViewById(R.id.tableLayoutRecords);
        db = new DbHelper(this);
        studentService = new StudentService(db);
        adapter = new TableLayoutAdapter(this, tableLayout);

        btnSearch.setOnClickListener(v -> {
            String q = etQuery.getText().toString().trim();
            if (q.isEmpty()) {
                Toast.makeText(this, "Enter ID or name", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String[]> students = studentService.searchStudents(q);
            if (students.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                // Clear previous results if nothing is found
                tableLayout.removeAllViews();
            } else {
                adapter.displayStudents(students);
            }
        });
    }

    // Interface for displaying student results
    interface StudentResultAdapter {
        void displayStudents(List<String[]> students);
    }

    // Inner class implementing the adapter to display data in a TableLayout
    class TableLayoutAdapter implements StudentResultAdapter {
        private Context context;
        private TableLayout tableLayout;

        public TableLayoutAdapter(Context context, TableLayout tableLayout) {
            this.context = context;
            this.tableLayout = tableLayout;
        }

        @Override
        public void displayStudents(List<String[]> students) {
            tableLayout.removeAllViews(); // Clear previous results
            addHeader();

            for (String[] student : students) {
                TableRow row = new TableRow(context);
                // Assuming student[0] is ID and student[1] is Name
                row.addView(createTextView(student[0], false));
                row.addView(createTextView(student[1], false));
                tableLayout.addView(row);
            }
        }

        private void addHeader() {
            TableRow header = new TableRow(context);
            header.addView(createTextView("ID", true));
            header.addView(createTextView("Name", true));
            tableLayout.addView(header);
        }

        private TextView createTextView(String text, boolean isHeader) {
            TextView tv = new TextView(context);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(16, 12, 16, 12); // Adjusted padding for better spacing
            if (isHeader) {
                tv.setTextAppearance(android.R.style.TextAppearance_Medium);
                tv.setTypeface(null, android.graphics.Typeface.BOLD);
            }
            return tv;
        }
    }
}