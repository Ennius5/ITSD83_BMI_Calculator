package com.main.bmi_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bmi_button_listener();
    }

    public void bmi_button_listener(){
        Button button = (Button)findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText height_text = (EditText)findViewById(R.id.height_input);
                final EditText weight_text = (EditText)findViewById(R.id.weight_input);

                String height_string = height_text.getText().toString();
                String weight_string = weight_text.getText().toString();
                double h = Double.parseDouble(height_string);
                double w = Double.parseDouble(weight_string);

                double bmi =  (w)/(h*h);

                final EditText bmi_result = (EditText)findViewById(R.id.bmi_result);
                bmi_result.setText(Double.toString(bmi));

                String bmi_cat;
                if (bmi < 15)
                    bmi_cat = "Very severely underweight";
                else if (bmi < 16)
                    bmi_cat = "Severely underweight";
                else if (bmi < 18.5)
                    bmi_cat = "Underweight";
                else if (bmi < 25)
                    bmi_cat = "Normal";
                else if (bmi < 30)
                    bmi_cat = "Overweight";
                else if (bmi < 35)
                    bmi_cat = "Obese Class 1 - Moderately Obese";
                else if (bmi < 40)
                    bmi_cat = "Obese Class 2 - Severely Obese";
                else
                    bmi_cat = "Obese Class 3 - Very Severely Obese";


                final TextView bmi_category = (TextView) findViewById(R.id.bmi_category);
                bmi_category.setText(bmi_cat);
            }

        });
    }
}