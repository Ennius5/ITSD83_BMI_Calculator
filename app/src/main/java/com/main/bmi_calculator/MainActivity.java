package com.main.bmi_calculator;

import android.os.Bundle;
import android.preference.EditTextPreference;
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
                final EditText bmi_result = (EditText)findViewById(R.id.bmi_result);
                final EditText height_text = (EditText)findViewById(R.id.height_input);
                final EditText weight_text = (EditText)findViewById(R.id.weight_input);
                final TextView bmi_category = (TextView) findViewById(R.id.bmi_category);
                final TextView to_prev_category = (TextView) findViewById(R.id.prev_category);
                final TextView to_next_category = (TextView) findViewById(R.id.next_category);

                double w;
                double h;
                String height_string = height_text.getText().toString();
                String weight_string = weight_text.getText().toString();
                try {
                    w = Double.parseDouble(weight_string);
                    h = Double.parseDouble(height_string);
                } catch (Exception e){
                    System.out.println("Failed to parse");
                    bmi_category.setText("Please enter an appropriate value!");
                    to_prev_category.setText("");
                    to_next_category.setText("");
                    return;
                }

                double bmi = w / (h * h);
                bmi_result.setText(String.format("%.1f", bmi));

                // Define BMI category thresholds
                double[] bmiThresholds = {15, 16, 18.5, 25, 30, 35, 40, Double.MAX_VALUE};
                String[] categories = {
                        "Very severely underweight",
                        "Severely underweight",
                        "Underweight",
                        "Normal",
                        "Overweight",
                        "Obese Class 1 - Moderately Obese",
                        "Obese Class 2 - Severely Obese",
                        "Obese Class 3 - Very Severely Obese"
                };

                //current category index
                int currentCategoryIndex = -1;
                for (int i = 0; i < bmiThresholds.length; i++) {
                    if (bmi < bmiThresholds[i]) {
                        currentCategoryIndex = i;
                        break;
                    }
                }

                if (currentCategoryIndex == -1) {
                    currentCategoryIndex = categories.length - 1;
                }

                String currentCategory = categories[currentCategoryIndex];
                bmi_category.setText(currentCategory);

                // Calculate weight changes for previous and next categories
                //previous
                if (currentCategoryIndex > 0) {
                    double targetBMI = bmiThresholds[currentCategoryIndex - 1];
                    double targetWeight = targetBMI * h * h;
                    double weightChange = w - targetWeight;

                    to_prev_category.setText(String.format("Lose %.1f kg to reach %s",
                            weightChange, categories[currentCategoryIndex - 1]));
                } else {
                    to_prev_category.setText("Already at lowest category");
                }
                //next
                if (currentCategoryIndex < categories.length - 1) {
                    double targetBMI = bmiThresholds[currentCategoryIndex];
                    double targetWeight = targetBMI * h * h;
                    double weightChange = targetWeight - w;
                    to_next_category.setText(String.format("Gain %.1f kg to reach %s",
                            weightChange, categories[currentCategoryIndex + 1]));
                } else {
                    to_next_category.setText("Already at highest category");
                }
            }
        });
    }
//    public void bmi_button_listener(){
//        Button button = (Button)findViewById(R.id.calculate);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText bmi_result = (EditText)findViewById(R.id.bmi_result);
//                final EditText height_text = (EditText)findViewById(R.id.height_input);
//                final EditText weight_text = (EditText)findViewById(R.id.weight_input);
//                final TextView bmi_category = (TextView) findViewById(R.id.bmi_category);
//                final TextView to_prev_category =(TextView) findViewById(R.id.prev_category); //weight to be lost to get to previous category
//                final TextView to_next_category =(TextView) findViewById(R.id.next_category); // //weight to be lost to get to next category
//
//
//                double w;
//                double h;
//                String height_string = height_text.getText().toString();
//                String weight_string = weight_text.getText().toString();
//                try {
//                    w = Double.parseDouble(weight_string);
//                    h = Double.parseDouble(height_string);
//                } catch (Exception e){
//                    System.out.println("Failed to parse");
//                    bmi_category.setText("Please enter an appropriate value!");
//                    return;
//                }
//
//
//                double bmi =  (w)/(h*h);
//
//                bmi_result.setText(Double.toString(bmi));
//
//                String[] categs = new String[]{
//                        "Very severely underweight",
//                        "Severely underweight",
//                        "Underweight",
//                        "Normal",
//                        "Overweight",
//                        "Obese Class 1 - Moderately Obese",
//                        "Obese Class 2 - Severely Obese",
//                        "Obese Class 3 - Very Severely Obese"
//                };
//                String bmi_cat;
//                if (bmi < 15)
//                    bmi_cat = categs[0];
//                else if (bmi < 16)
//                    bmi_cat = categs[1];
//                else if (bmi < 18.5)
//                    bmi_cat = categs[2];
//                else if (bmi < 25)
//                    bmi_cat = categs[3];
//                else if (bmi < 30)
//                    bmi_cat = categs[4];
//                else if (bmi < 35)
//                    bmi_cat = categs[5];
//                else if (bmi < 40)
//                    bmi_cat = categs[6];
//                else
//                    bmi_cat = categs[7];
//
//
//                bmi_category.setText(bmi_cat);
//            }
//
//        });
//    }
}