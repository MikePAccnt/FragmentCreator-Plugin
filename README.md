# Fragment Creator

IntelliJ/AndroidStudio Plugin to auto create an android Fragment class from a provided xml.

* Only generates kotlin classes
* Only generates variables for views with an id
* Requires the following extension function to be in your project

```kotlin
fun <T: View> Fragment.findViewById(@IdRes id: Int): Lazy<T> {
	return lazy {
		requireView().findViewById<T>(id)
	}
}
```

## Example

### Input (`fragment_something.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/text_view_1"/>

    <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/layout_one">

        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/text_view_2"/>
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/text_view_3"/>
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/text_view_4"/>

    </LinearLayout>

</FrameLayout>
```

### Output
```kotlin
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

@SuppressLint("WrongViewCast")
class SomeFragment : Fragment() {
    val textView1: TextView by findViewById(R.id.text_view_1)


    val layoutOne: LinearLayout by findViewById(R.id.layout_one)


    val textView2: TextView by findViewById(R.id.text_view_2)


    val textView3: TextView by findViewById(R.id.text_view_3)


    val textView4: TextView by findViewById(R.id.text_view_4)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_testing, container, false)


}

```