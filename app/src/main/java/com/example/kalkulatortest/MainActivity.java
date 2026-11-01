package com.example.kalkulatortest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1, editTextNumber2;
    private Button buttonAdd, buttonSubtract, buttonMultiply, buttonDivide, buttonClear;
    private TextView textViewResult, textViewHistory;

    private ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonClear = findViewById(R.id.buttonClear);
        textViewResult = findViewById(R.id.textViewResult);
        textViewHistory = findViewById(R.id.textViewHistory);

        // Enable scrolling for history log
        textViewHistory.setMovementMethod(new ScrollingMovementMethod());

        buttonAdd.setOnClickListener(v -> calculateResult('+'));
        buttonSubtract.setOnClickListener(v -> calculateResult('-'));
        buttonMultiply.setOnClickListener(v -> calculateResult('*'));
        buttonDivide.setOnClickListener(v -> calculateResult('/'));

        buttonClear.setOnClickListener(v -> {
            editTextNumber1.setText("");
            editTextNumber2.setText("");
            textViewResult.setText("0");
            historyList.clear();
            textViewHistory.setText("No history");
            Toast.makeText(MainActivity.this, "Calculator cleared", Toast.LENGTH_SHORT).show();
        });
    }

    private void calculateResult(char operator) {
        String input1 = editTextNumber1.getText().toString().trim();
        String input2 = editTextNumber2.getText().toString().trim();

        if (input1.isEmpty() || input2.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double num1 = Double.parseDouble(input1);
        double num2 = Double.parseDouble(input2);
        String resultStr;

        String opSymbol = String.valueOf(operator);
        if (operator == '*') opSymbol = "×";
        if (operator == '/') opSymbol = "÷";

        // Handle division by zero
        if (operator == '/' && num2 == 0) {
            resultStr = "Undefined";
            textViewResult.setText(resultStr);

            String currentCalculation = num1 + " " + opSymbol + " " + num2 + " = " + resultStr;
            historyList.add(0, currentCalculation);
            updateHistoryDisplay();
            return;
        }

        double result = 0;
        switch (operator) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/': result = num1 / num2; break;
        }

        // Format decimal places cleanly if whole number
        if (result == (long) result) {
            resultStr = String.valueOf((long) result);
        } else {
            resultStr = String.valueOf(result);
        }

        textViewResult.setText(resultStr);

        // Save to history log
        String currentCalculation = num1 + " " + opSymbol + " " + num2 + " = " + resultStr;
        historyList.add(0, currentCalculation);
        updateHistoryDisplay();
    }

    private void updateHistoryDisplay() {
        if (historyList.isEmpty()) {
            textViewHistory.setText("No history");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String item : historyList) {
            sb.append("• ").append(item).append("\n");
        }
        textViewHistory.setText(sb.toString().trim());
    }
}