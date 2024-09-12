package com.example.student;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText editTextName, editTextRollNo, editTextMarks;
    TextView textViewResult, textViewData;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextRollNo = findViewById(R.id.editTextRollNo);
        editTextMarks = findViewById(R.id.editTextMarks);
        textViewResult = findViewById(R.id.textViewResult);
        textViewData = findViewById(R.id.textViewData);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonView = findViewById(R.id.buttonView);

        buttonInsert.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            int rollNo = Integer.parseInt(editTextRollNo.getText().toString());
            int marks = Integer.parseInt(editTextMarks.getText().toString());

            if (dbHelper.insertStudent(name, rollNo, marks)) {
                textViewResult.setText("Student Inserted");
            } else {
                textViewResult.setText("Insertion Failed");
            }
        });

        buttonUpdate.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            int rollNo = Integer.parseInt(editTextRollNo.getText().toString());
            int marks = Integer.parseInt(editTextMarks.getText().toString());

            if (dbHelper.updateStudent(name, rollNo, marks)) {
                textViewResult.setText("Student Updated");
            } else {
                textViewResult.setText("Update Failed");
            }
        });

        buttonDelete.setOnClickListener(view -> {
            int rollNo = Integer.parseInt(editTextRollNo.getText().toString());

            if (dbHelper.deleteStudent(rollNo)) {
                textViewResult.setText("Student Deleted");
            } else {
                textViewResult.setText("Deletion Failed");
            }
        });

        buttonView.setOnClickListener(view -> {
            Cursor cursor = dbHelper.getAllStudents();
            if (cursor.getCount() == 0) {
                textViewData.setText("No Data Available");
                return;
            }
            StringBuilder data = new StringBuilder();
            while (cursor.moveToNext()) {
                data.append("Name: ").append(cursor.getString(0)).append("\n");
                data.append("Roll No: ").append(cursor.getInt(1)).append("\n");
                data.append("Marks: ").append(cursor.getInt(2)).append("\n\n");
            }
            textViewData.setText(data.toString());
            cursor.close();
        });
    }
}
