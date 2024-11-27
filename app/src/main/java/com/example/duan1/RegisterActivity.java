package com.example.duan1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt1,edt2,edt3;
    private Button DangKi,backquaylai;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth=FirebaseAuth.getInstance();

        edt1 = findViewById(R.id.edtUser);
        edt2 = findViewById(R.id.edtPass);
        edt3 = findViewById(R.id.edtConfirm);
        backquaylai = findViewById(R.id.btnTroLai);
        DangKi = findViewById(R.id.btnDangKy);

        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dangki();
            }
        });
        backquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, Login_Activity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void Dangki() {
        String emailedit, passwordedt, passwordedt2;
        emailedit = edt1.getText().toString();
        passwordedt = edt2.getText().toString();
        passwordedt2 = edt3.getText().toString();

        if (emailedit.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailedit).matches()) {
            edt1.setError("Email không hợp lệ!");
            return;}
        if (passwordedt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordedt.length() < 6 ) {
            edt2.setError("Mật khẩu cần phải có ít nhất 6 ký tự!");
            return;}
        if (!passwordedt.equals(passwordedt2)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(emailedit, passwordedt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, Login_Activity.class);
                            startActivity(i);
                            finish(); // Đảm bảo không quay lại màn hình đăng ký
                        } else {
                            String errorMessage = task.getException() != null
                                    ? task.getException().getMessage()
                                    : "Đăng kí thất bại!";
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}