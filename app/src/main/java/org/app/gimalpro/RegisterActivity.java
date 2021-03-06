package org.app.gimalpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText et_id,et_pass,et_name,et_age,et_pass_ok;
    Button bt_register1,bt_validate;
    RadioGroup rg_gender;
    private AlertDialog dialog;
    private boolean validate=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_age=findViewById(R.id.et_age);
        bt_register1=findViewById(R.id.bt_register1);
        rg_gender=findViewById(R.id.rg_gender);
        bt_validate=findViewById(R.id.bt_validate);
        et_pass_ok=findViewById(R.id.et_pass_ok);


        //아이디 중복 확인
        bt_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=et_id.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                et_id.setEnabled(false);
                                validate=true;
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Validate validateRequest=new Validate(userID,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });



        //회원가입 버튼 클릭시 실행

            bt_register1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (et_id.length()==0||et_name.length()==0||et_age.length()==0||rg_gender.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "값을 입력하시오", Toast.LENGTH_SHORT).show();
                    }

                    else{
                    String userID=et_id.getText().toString();
                    String userPass=et_pass.getText().toString();
                    String userPass_ok=et_pass_ok.getText().toString();
                    String userName=et_name.getText().toString();
                    int userAge=Integer.parseInt(et_age.getText().toString());
                    int gender_id=rg_gender.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(gender_id);
                    String gender=rb.getText().toString();



                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if(success){//회원등록에 성공한 경우
                                    if(validate){
                                        if(password_injection(userPass)){
                                            if (password_correct(userPass,userPass_ok)){
                                                Toast.makeText(getApplicationContext(), "회원등록에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }

                                            else{
                                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                        }
                                        else {Toast.makeText(getApplicationContext(), "비밀번호에 숫자, 문자, 특수문자를 모두 포함하세요(8~15자)", Toast.LENGTH_SHORT).show();
                                            return;}
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                else{//회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "회원등록에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                            }

                        }
                    };
                    //서버로 volley를 이용해 요청
                        RegisterRequest registerRequest = new RegisterRequest (userID,userPass,userName,userAge,gender,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                }}
            });


    }

    public boolean password_injection(String str) {
        String Passwrod_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{7,15}.$";
        Pattern pattern = Pattern.compile(Passwrod_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean password_correct(String str1,String str2){
        if (str1.length()==0||str2.length()==0){
            return false;
        }
        else if (str1.equals(str2)){
            return true;
        }
        else {
            return false;
        }
    }


}