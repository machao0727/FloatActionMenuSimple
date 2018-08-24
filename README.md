# FloatActionMenuSimple
![](https://raw.githubusercontent.com/machao0727/FloatActionMenuSimple/master/simplegif/GIF.gif)
</br>
</br>
USE
====
```java
FloatActionMenu menu = findViewById(R.id.left);
        menu.setMenuDrawable(R.mipmap.ic_add
                ,R.mipmap.icon_merchant_alpay
                ,R.mipmap.icon_merchant_bankpay
                ,R.mipmap.icon_merchant_cashpay
                ,R.mipmap.icon_merchant_wxpay
        ).setOnItemClickListener(new FloatActionMenu.OnItemClick() {
            @Override
            public void itemClick(int item) {

            }
        });
```
```xml
<com.machao.floatactionlibrary.FloatActionMenu
            android:id="@+id/left"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_gravity="left"
            android:layout_margin="30dp"
            android:background="#00000000"
            app:duration="200"
            app:spreadDirection="spread_bottom"
            app:viewOffset="20dp">

        </com.machao.floatactionlibrary.FloatActionMenu>
```
PS
===
布局文件必须设置背景颜色，否则不会显示
