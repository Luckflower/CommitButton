# CommitButton

##使用实例:
在布局文件中引入:
```xml
<com.example.lbe.commitbutton.AnimationButton
        android:id="@+id/btn"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center"/>
```

在java代码中设置监听事件:
```java
final AnimationButton animationButton = (AnimationButton) findViewById(R.id.btn);
        animationButton.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void OnClick() {
                animationButton.startAnimation();
            }
        });
```

##效果展示
![效果展示](http://img.blog.csdn.net/20180311164629513?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMDc4NDg4Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
