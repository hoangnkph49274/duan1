package com.example.duan1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity {

    private EditText edt1, edt2;
    private Button DangNhap;
    private TextView DangKi;
    private CheckBox chkLuu;
    private FirebaseAuth firebaseAuth;

    public static String user;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firebaseAuth = FirebaseAuth.getInstance();

        edt1 = findViewById(R.id.edtUser);
        edt2 = findViewById(R.id.edtPass);
        DangNhap = findViewById(R.id.btnLogin);
        DangKi = findViewById(R.id.dangki);
        chkLuu = findViewById(R.id.chkLuu);

        pref = getSharedPreferences("tai_khoan_du_an_1",MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("tai_khoan_du_an_1",MODE_PRIVATE);
        edt1.setText(sharedPreferences.getString("user",""));
        edt2.setText(sharedPreferences.getString("pass",""));

        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });
        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKi();
            }
        });
    }
    private void DangKi() {
        Intent i  = new Intent(Login_Activity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void DangNhap() {
        String emailedt, passwordedt;
        emailedt = edt1.getText().toString();
        passwordedt = edt2.getText().toString();

        if (TextUtils.isEmpty(emailedt)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordedt)) {
            Toast.makeText(this, "Vui lòng nhập pass", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(emailedt, passwordedt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = emailedt;

                    // Lưu thông tin đăng nhập nếu CheckBox được chọn
                    SharedPreferences.Editor editor = pref.edit();
                    if (chkLuu.isChecked()) {
                        editor.putString("user", emailedt);
                        editor.putString("pass", passwordedt);
                        editor.putBoolean("remember", true);
                    } else {
                        editor.clear(); // Xóa thông tin đăng nhập nếu không chọn lưu
                    }
                    editor.apply();

                    // Tiếp tục xử lý đăng nhập thành công
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public List<User> readUser(Context context, String fileName){
        List<User> objectList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectList = (List<User>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return  objectList;
    }
}