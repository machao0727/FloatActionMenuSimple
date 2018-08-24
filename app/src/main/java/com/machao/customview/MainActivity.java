package com.machao.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.machao.floatactionlibrary.FloatActionMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatActionMenu left = findViewById(R.id.left);
        left.setMenuDrawable(R.mipmap.ic_add
                ,R.mipmap.icon_merchant_alpay
                ,R.mipmap.icon_merchant_bankpay
                ,R.mipmap.icon_merchant_cashpay
                ,R.mipmap.icon_merchant_wxpay
        ).setOnItemClickListener(new FloatActionMenu.OnItemClick() {
            @Override
            public void itemClick(int item) {
                Toast.makeText(MainActivity.this,"点击位置"+item,Toast.LENGTH_SHORT).show();
            }
        });

        FloatActionMenu right = findViewById(R.id.right);
        right.setMenuDrawable(R.mipmap.ic_add
                ,R.mipmap.icon_merchant_alpay
                ,R.mipmap.icon_merchant_bankpay
                ,R.mipmap.icon_merchant_cashpay
                ,R.mipmap.icon_merchant_wxpay
        ).setOnItemClickListener(new FloatActionMenu.OnItemClick() {
            @Override
            public void itemClick(int item) {
                Toast.makeText(MainActivity.this,"点击位置"+item,Toast.LENGTH_SHORT).show();
            }
        });

        FloatActionMenu bot_left = findViewById(R.id.bottom_left);
        bot_left.setMenuDrawable(R.mipmap.ic_add
                ,R.mipmap.icon_merchant_alpay
                ,R.mipmap.icon_merchant_bankpay
                ,R.mipmap.icon_merchant_cashpay
                ,R.mipmap.icon_merchant_wxpay
        ).setOnItemClickListener(new FloatActionMenu.OnItemClick() {
            @Override
            public void itemClick(int item) {
                Toast.makeText(MainActivity.this,"点击位置"+item,Toast.LENGTH_SHORT).show();
            }
        });

        FloatActionMenu bot_right = findViewById(R.id.bottom_right);
        bot_right.setMenuDrawable(R.mipmap.ic_add
                ,R.mipmap.icon_merchant_alpay
                ,R.mipmap.icon_merchant_bankpay
                ,R.mipmap.icon_merchant_cashpay
                ,R.mipmap.icon_merchant_wxpay
        ).setOnItemClickListener(new FloatActionMenu.OnItemClick() {
            @Override
            public void itemClick(int item) {
                Toast.makeText(MainActivity.this,"点击位置"+item,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
