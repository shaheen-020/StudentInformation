package com.example;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ViewStudentsActivity extends AppCompatActivity {
    TableLayout tableLayout;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        tableLayout = findViewById(R.id.tableLayout);
        db = new DbHelper(this);
        tableLayout.removeAllViews();
        addHeader();

        Cursor c = db.getAllStudents();
        if (c.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            // ✅ Using Iterator Pattern (inner class implementation)
            StudentIterator iterator = new StudentIterator(c);
            while (iterator.hasNext()) {
                StudentRecord student = iterator.next();
                TableRow row = new TableRow(this);

                row.addView(makeText(student.roll));
                row.addView(makeText(student.name));

                Button btnDel = new Button(this);
                btnDel.setText("Delete");
                String roll = student.roll; // Capture for lambda
                btnDel.setOnClickListener(v -> {
                    if (db.deleteStudent(roll)) {
                        Toast.makeText(this, "Deleted " + roll, Toast.LENGTH_SHORT).show();
                        recreate();
                    }
                });

                row.addView(btnDel);
                tableLayout.addView(row);
            }
        }
        c.close();
    }



    //implementation of iterator pattern
    private class StudentIterator implements Iterator<StudentRecord> {
        private Cursor cursor;

        public StudentIterator(Cursor cursor) {
            this.cursor = cursor;
            this.cursor.moveToPosition(-1);
        }

        @Override
        public boolean hasNext() {
            return cursor != null && !cursor.isLast() && cursor.getCount() > 0;
        }

        @Override
        public StudentRecord next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more students");
            }
            cursor.moveToNext();
            return new StudentRecord(
                    cursor.getString(0), // roll
                    cursor.getString(1) // name
            );
        }
    }

    /**
     * Simple data holder for student record
     */
    private static class StudentRecord {
        String roll;
        String name;

        StudentRecord(String roll, String name) {
            this.roll = roll;
            this.name = name;
        }
    }



    void addHeader() {
        TableRow h = new TableRow(this);
        h.addView(makeText("ID", true));
        h.addView(makeText("Name", true));
        h.addView(makeText("Action", true));
        tableLayout.addView(h);
    }

    TextView makeText(String s) {
        return makeText(s, false);
    }

    TextView makeText(String s, boolean hdr) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(16, 10, 16, 10);
        if (hdr)
            tv.setTextSize(18);
        return tv;
    }
}
