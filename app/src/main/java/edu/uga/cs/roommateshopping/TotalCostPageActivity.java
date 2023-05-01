package edu.uga.cs.roommateshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TotalCostPageActivity extends AppCompatActivity {

    private double totalCost;
    private TextView averageCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_cost_page);


        averageCost = findViewById(R.id.avgCost);

        Intent intent = getIntent();
        totalCost = intent.getDoubleExtra("cost", 0);
        DecimalFormat f = new DecimalFormat("##.00");
        averageCost.setText("Average Per Roommate: $" + f.format(totalCost));
    }
}