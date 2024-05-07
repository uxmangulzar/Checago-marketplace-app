package com.softarena.checagocoffee.Acitivity.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softarena.checagocoffee.R;

public class ConfirmPinPasswordAvtivity extends AppCompatActivity {
    private Button btn_submit_confirmpin;
    EditText ed_pin;
    private String code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin_password_avtivity);
        code=getIntent().getStringExtra("code");
        ed_pin = findViewById(R.id.ed_pin);
        ed_pin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_pin.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                return false;
            }
        });
        btn_submit_confirmpin = findViewById(R.id.btn_submit_confirmpin);
        btn_submit_confirmpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed_pin.getText().toString().equals("")){
                    if (code.equals(ed_pin.getText().toString())){
                        Intent intent = new Intent(ConfirmPinPasswordAvtivity.this, ChangePasswordActivity.class);
                        intent.putExtra("user_id",getIntent().getStringExtra("user_id"));
                        startActivity(intent);
                    }else {
                        Toast.makeText(ConfirmPinPasswordAvtivity.this, "Incorrect pin", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
