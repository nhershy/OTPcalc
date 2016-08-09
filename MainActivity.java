package com.example.nicholas.otpcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //controls
    private EditText txtResult;
    private EditText txtOrigPrice;
    private EditText txtPercentDisc;
    private Switch switchEmp;
    private Switch switchMilit;
    private EditText txtTax;
    private Button btnTaxMinus;
    private Button btnTaxPlus;
    private Button btnCalc;

    //variables
    private double result;
    private double originalPrice;
    private double percentDiscount;
    private boolean employeeDiscount;
    private double empDisc;
    private boolean militaryDiscount;
    private double militDisc;
    private double tax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking everything
        txtResult = (EditText)findViewById(R.id.txtResult);
        txtResult.setFocusable(false); //makes it not-editable
        txtResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30); //this makes text size bigger
        //text should also be centered (I put this in xml: android:gravity="center")
        //and text should be bold (you can change that from gui in properties)
        txtResult.setText("0.0");

        txtOrigPrice = (EditText)findViewById(R.id.txtOrigPrice);

        txtPercentDisc = (EditText)findViewById(R.id.txtPercentDisc);

        switchEmp = (Switch)findViewById(R.id.switchEmp);
        switchMilit = (Switch)findViewById(R.id.switchMilit);

        txtTax = (EditText)findViewById(R.id.txtTax);
        tax = 9.0;
        txtTax.setText(String.valueOf(tax));
        txtTax.setFocusable(false); //makes it not-editable

        btnTaxMinus = (Button)findViewById(R.id.btnTaxMinus);
        btnTaxPlus = (Button)findViewById(R.id.btnTaxPlus);
        btnCalc = (Button)findViewById(R.id.btnCalc);

        //add padding to "Employee Disc"


        //end linking things

        //btnTaxMinus onClick event
        btnTaxMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tax -= 0.5;
                txtTax.setText(String.valueOf(tax));
            }
        });

        //btnTaxPlus onClick event
        btnTaxPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tax += 0.5;
                txtTax.setText(String.valueOf(tax));
            }
        });

        //btnCalc onClick event
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    //set originalPrice from EditText and validate
                    if (txtOrigPrice.getEditableText() == null) return;
                    originalPrice = Double.parseDouble(txtOrigPrice.getEditableText().toString());
                    if (originalPrice < 0) return;

                    //set percentDiscount from EditText and validate
                    if (txtPercentDisc.getEditableText() == null) return;
                    percentDiscount = Double.parseDouble(txtPercentDisc.getEditableText().toString());
                    if (percentDiscount < 0 || percentDiscount > 99) return;
                    if (percentDiscount == 0)
                        percentDiscount = 1.0;
                    else
                    {
                        percentDiscount = 100 - percentDiscount;
                        percentDiscount /= 100;
                    }


                    //set employeeDiscount from Switch
                    employeeDiscount = switchEmp.isChecked();


                    //set militaryDiscount from Switch
                    militaryDiscount = switchMilit.isChecked();

                    //validate tax
                    if (tax < 0 || tax > 25) return;
                    if (tax == 0)
                        tax = 1.0;
                    else
                    {
                        tax /= 100;
                        tax += 1;
                    }

                    //if both are checked
                    if (employeeDiscount && militaryDiscount)
                    {
                        result = originalPrice * percentDiscount * 0.80 * 0.90 * tax;
                    }
                    else if (employeeDiscount)
                    {
                        result = originalPrice * percentDiscount * 0.80 * tax;
                    }
                    else if (militaryDiscount)
                    {
                        result = originalPrice * percentDiscount * 0.90 * tax;
                    }
                    else
                    {
                        result = originalPrice * percentDiscount * tax;
                    }

                    //sets result to two decimal places and displays it in EditText
                    DecimalFormat resultFormat = new DecimalFormat("##.##");
                    String resultToString = resultFormat.format(result);
                    txtResult.setText(resultToString);

                    //resets tax to normal
                    tax = 9.0;
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "No fields can be left blank",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
