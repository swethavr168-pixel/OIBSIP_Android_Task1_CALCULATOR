package com.swetha.calculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    TextView display, historyView;

    String expression = "";
    ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        historyView = findViewById(R.id.historyView);

        // Numbers
        int[] nums = {
                R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9
        };

        for (int id : nums) {
            findViewById(id).setOnClickListener(v ->
                    append(((TextView)v).getText().toString())
            );
        }

        // Operators (IMPORTANT FIX: real math symbols converted)
        findViewById(R.id.btnAdd).setOnClickListener(v -> append("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> append("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> append("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> append("/"));

        findViewById(R.id.btnDot).setOnClickListener(v -> append("."));

        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        findViewById(R.id.btnBack).setOnClickListener(v -> backspace());
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());
    }

    void append(String value) {
        expression += value;
        display.setText(expression);
    }

    void calculate() {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();

            String resultStr = String.valueOf(result);
            if (resultStr.endsWith(".0")) {
                resultStr = resultStr.replace(".0", "");
            }

            historyList.add(expression + " = " + resultStr);

            StringBuilder sb = new StringBuilder();
            for (String item : historyList) {
                sb.append(item).append("\n");
            }

            historyView.setText(sb.toString());

            display.setText(resultStr);
            expression = resultStr;

        } catch (Exception e) {
            display.setText("Error");
            expression = "";
        }
    }

    void clear() {
        expression = "";
        display.setText("0");
    }

    void backspace() {
        if (!expression.isEmpty()) {
            expression = expression.substring(0, expression.length() - 1);
            display.setText(expression.isEmpty() ? "0" : expression);
        }
    }
}