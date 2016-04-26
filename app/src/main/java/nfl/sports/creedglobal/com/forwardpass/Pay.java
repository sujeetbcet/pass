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
import android.widget.EditText;
import android.widget.TextView;

public class Pay extends AppCompatActivity {
    TextView amounttxt;
    EditText cardtxt,cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        String amount = getIntent().getStringExtra("amount");
        amounttxt = (TextView)findViewById(R.id.amounttxt);
        amounttxt.setText(amount + " $");
        cardtxt= (EditText) findViewById(R.id.cardedt);
        cardtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(cardtxt.getText().toString().length()==16)){
                    cardtxt.setError("Enter 16-digit valid card number");
                }

            }
        });
        cvv=(EditText)findViewById(R.id.cvv);
        cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(cvv.getText().toString().length() == 3)) {
                    cvv.setError("Enter valid cvv ");
                }
            }
        });
    }
    public void logout(View view){
        Log.i("my_info : ", "logout>Payment");
//        if (AccessToken.getCurrentAccessToken()!=null){
//            Log.i("my_info : ", "logout>Payment"+AccessToken.getCurrentAccessToken());
//            LoginManager.getInstance().logOut();
//            Log.i("my_info : ", String.valueOf("after  logout Access Token is :"+ AccessToken.getCurrentAccessToken()==null));
//        }
        if (cvv.getText().toString().length()==3&&cardtxt.getText().toString().length()==16){
            Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            dialog.setTitle("thanks for Donation...");
            dialog.show();
            startActivity(new Intent(this, Team.class));
            finish();
            dialog.dismiss();
        }
        else{
            cardtxt.setError("Enter valid card number");
            cvv.setError("Enter valid cvv");

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this,Team.class));
        finish();
    }

    public boolean isValid(String str){
        return Patterns.PHONE.matcher(str).matches();
    }
}
