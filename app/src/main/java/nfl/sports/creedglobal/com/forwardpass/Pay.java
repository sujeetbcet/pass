package nfl.sports.creedglobal.com.forwardpass;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Pay extends AppCompatActivity {
    TextView amounttxt;
    EditText cardtxt,cvv;
    Spinner selectedBank;
    int cardlength=16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        String amount = getIntent().getStringExtra("amount");
        selectedBank = (Spinner)findViewById(R.id.selectbank);
        amounttxt = (TextView)findViewById(R.id.amounttxt);
        Button btn_paynow = (Button) findViewById(R.id.paynowbtn);
        cvv = (EditText) findViewById(R.id.cvv);

        amounttxt.setText(amount + " $");
        cardtxt= (EditText) findViewById(R.id.cardedt);

        selectedBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    cardlength=15;

                }
                else {
                    cardlength=16;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Pay.this,"please select a card",Toast.LENGTH_SHORT).show();
            }
        });
        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    Dialog dialog = new Dialog(Pay.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("thanks for Donation...");
                    dialog.show();
                    startActivity(new Intent(getApplicationContext(), Team.class));
                    finish();
                    dialog.dismiss();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this,Team.class));
        finish();
    }

    public boolean validate(){
        boolean status=true;
        if (selectedBank.getSelectedItem().toString().equals("select card")){
            Toast.makeText(this,"please select a card",Toast.LENGTH_SHORT).show();
            status=false;
        }
        else if (cardtxt.getText().toString().length()!=cardlength){
            cardtxt.setError("please enter "+cardlength+"-digit card number");
            status=false;
        }
        else if (cvv.getText().toString().length()!=3){
            cvv.setError("please enter valid CVV number");
            status=false;
        }
        return status;
    }
}
