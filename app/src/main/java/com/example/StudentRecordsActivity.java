package com.example;

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
import static android.os.Build.VERSION_CODES.R;
public class StudentRecordsActivity extends AppCompatActivity {

    EditText etQuery;
    Button btnSearch;
    TableLayout tableLayout;
    DbHelper db;
    StudentService studentService;
<<<<<<< HEAD
    StudentResultAdapter adapter;
=======
    SearchStrategy searchStrategy; // new field for strategy
>>>>>>> 941bbb08e31497ded0793ebf2d82edf038fb46e2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records);

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
            } else {
                adapter.displayStudents(students);
            }
        });
    }

    interface StudentResultAdapter {
        void displayStudents(List<String[]> students);
    }

    class TableLayoutAdapter implements StudentResultAdapter {
        private android.content.Context context;
        private TableLayout tableLayout;

        public TableLayoutAdapter(android.content.Context context, TableLayout tableLayout) {
            this.context = context;
            this.tableLayout = tableLayout;
        }

        @Override
        public void displayStudents(List<String[]> students) {
            tableLayout.removeAllViews();
            addHeader();

            for (String[] student : students) {
                TableRow row = new TableRow(context);
                row.addView(makeText(student[0]));
                row.addView(makeText(student[1]));
                tableLayout.addView(row);
            }
        }

        private void addHeader() {
            TableRow header = new TableRow(context);
            header.addView(makeText("ID", true));
            header.addView(makeText("Name", true));
            tableLayout.addView(header);
        }

        private TextView makeText(String text) {
            return makeText(text, false);
        }

        private TextView makeText(String text, boolean isHeader) {
            TextView tv = new TextView(context);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(16, 10, 16, 10);
            if (isHeader)
                tv.setTextSize(18);
            return tv;

        }
    }
}