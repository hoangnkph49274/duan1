package com.example.duan1.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.R;

public class WebViewFragment extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);

        // Initialize WebView
        webView = rootView.findViewById(R.id.webView);

        // Nhận đường dẫn từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String duongDan = bundle.getString("duongDan", "https://default.com"); // URL mặc định nếu không có
            setupWebView(duongDan);
        } else {
            setupWebView("https://default.com"); // Trường hợp không có bundle
        }

        return rootView;
    }

    private void setupWebView(String url) {
        // Enable JavaScript and other WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Cho phép JavaScript
        webSettings.setDomStorageEnabled(true); // Cho phép DOM Storage

        // Đặt WebViewClient để xử lý các liên kết trong WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                // Kiểm tra nếu URL là liên kết ngoài, vẫn mở trong WebView
                view.loadUrl(url);
                return true; // Trả về true để ngừng việc mở trong trình duyệt ngoài
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Cũng xử lý URL dưới dạng chuỗi ở đây để tương thích với các Android cũ
                view.loadUrl(url);
                return true; // Trả về true để ngừng việc mở trong trình duyệt ngoài
            }
        });


        // Load URL
        webView.loadUrl(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            webView.destroy(); // Giải phóng tài nguyên WebView khi fragment bị hủy
        }
    }
}
