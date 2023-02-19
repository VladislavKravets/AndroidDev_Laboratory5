package com.example.laboratory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final List<Student> students = new ArrayList<>();
    private boolean edited = false;

    private TextView selection;
    private TextView infoInput;
    private Button button;
    private ConstraintLayout editedLayout;
    private int pos = 1; // текущия выбраная позиция пользователём 1 по умолчанию

    private TextInputEditText nameInput;
    private TextInputEditText surnameInput;
    private TextInputEditText entitiesInput;

    private ArrayAdapter<String> adapter;
    private ListView studentList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        students.add(new Student("Софія","Микитюк", new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5))));
        students.add(new Student("Мирослав","Крамарчук", new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5))));
        students.add(new Student("Валерія","Захарчук", new ArrayList<>(Arrays.asList(1, 2, 7, 4, 5))));

        ConstraintLayout buttonLayout = findViewById(R.id.buttonLayout);
        for (int i = 0; i < buttonLayout.getChildCount(); i++) {
            findViewById(buttonLayout.getChildAt(i).getId()).setOnClickListener(this);
        }

        selection = findViewById(R.id.selection);
        infoInput = findViewById(R.id.infoInput);
        editedLayout = findViewById(R.id.editedLayout);
        button = findViewById(R.id.editButton);

        nameInput = findViewById(R.id.nameInput);
        surnameInput = findViewById(R.id.surnameInput);
        entitiesInput = findViewById(R.id.entitiesInput);

        studentList = findViewById(R.id.studentsList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, replaceNameAndSurnameStudentsToStringArray(students));

        studentList.setAdapter(adapter);

        studentList.setOnItemClickListener((parent, view, position, id) -> {
            pos = position;
            selection.setText(students.get(position).toString());
        });

        button.setOnClickListener( e -> {
            if(edited) {
                students.remove(pos);
            }
            students.add(pos,
                    new Student(nameInput.getText().toString(),
                            surnameInput.getText().toString(),
                            replaceStringToListInteger(
                                    entitiesInput.getText().toString()
                                            .replace("\"", "")
                            )
                    )
            );
            updateDataApp();
        });
    }

    /* Вытягиваем из класа только имя и фамилию для списка */
    private List<String> replaceNameAndSurnameStudentsToStringArray(List<Student> students) {
        List<String> resultStudent = new ArrayList<>();
        for(Student name : students) {
            resultStudent.add(name.getName() + " " + name.getSurname());
        }
        return resultStudent;
    }

    /* из стринца в лист */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> replaceStringToListInteger(String string){
        return Arrays.stream(string.split(","))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    /* */

    private void updateDataApp(){
        adapter.clear();
        adapter.addAll(replaceNameAndSurnameStudentsToStringArray(students));
        studentList.setAdapter(adapter);
        selection.setText("");
        adapter.notifyDataSetChanged();
    }

    /* Метод обработки кнопок (имплементация OnClickListener)  */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addEstimate: {
                edited = false;
                infoInput.setText("Додавання студента");
                button.setText("Додавання");
                editedLayout.setVisibility(View.VISIBLE);
            } break;

            case R.id.deleteEstimate: {
                infoInput.setText("Видалення студента");
                editedLayout.setVisibility(View.GONE);
                students.remove(pos);
                updateDataApp();
            } break;

            case R.id.editEstimals: {
                edited = true;
                infoInput.setText("Редагування студента");
                button.setText("Редагування");
                editedLayout.setVisibility(View.VISIBLE);
                nameInput.setText(students.get(pos).getName());
                surnameInput.setText(students.get(pos).getSurname());
                entitiesInput.setText(students.get(pos).getEstimate().toString().replace("[", "").replace("]", ""));
            } break;

        }
    }
}
