package mikep.fragmentcreator

import com.squareup.kotlinpoet.ClassName



object AndroidClasses {

    private val packageRegex = Regex("[A-Za-z]+\\.*")
    //TODO Get all of the possible classes from xml that don't contain their package in their name

    val view = ClassName("android.view.View", "View")

    //Layouts
    val linearLayout = ClassName("android.widget.LinearLayout", "LinearLayout")
    val frameLayout = ClassName("android.widget.FrameLayout", "FrameLayout")
    val tableLayout = ClassName("android.widget.TableLayout", "TableLayout")
    val tableRow = ClassName("android.widget.TableRow", "TableRow")
    val viewGroup = ClassName("android.view.ViewGroup", "ViewGroup")


    //Views
    val button = ClassName("android.widget.Button", "Button")
    val imageView = ClassName("android.widget.ImageView", "ImageView")
    val scrollView = ClassName("android.widget.ScrollView", "ScrollView")
    val switch = ClassName("android.widget.Switch", "Switch")
    val imageButton = ClassName("android.widget.ImageButton", "ImageButton")
    val checkBox = ClassName("android.widget.CheckBox", "CheckBox")
    val radioGroup = ClassName("android.widget.RadioGroup", "RadioGroup")
    val radioButton = ClassName("android.widget.RadioButton", "RadioButton")
    val webView = ClassName("android.webkit.WebView", "WebView")
    val videoView = ClassName("android.widget.VideoView", "VideoView")
    val calendarView = ClassName("android.widget.CalendarView", "CalendarView")
    val progressBar = ClassName("android.widget.ProgressBar", "ProgressBar")
    val seekBar = ClassName("android.widget.SeekBar", "SeekBar")
    val ratingBar = ClassName("android.widget.RatingBar", "RatingBar")
    val searchView = ClassName("android.widget.SearchView", "SearchView")
    val textureView = ClassName("android.view.TextureView", "TextureView")
    val surfaceView = ClassName("android.view.SurfaceView", "SurfaceView")
    val spinner = ClassName("android.widget.Spinner", "Spinner")


    fun classNameFromNodeName(nodeName: String) : ClassName {
        val nodeNameHasPackage = packageRegex.matches(nodeName)

        if(nodeNameHasPackage) {
            val parts = nodeName.split(".")
            val simpleName = parts.last()
            val packageName = nodeName.replace(".$simpleName", "")

            return ClassName(packageName, simpleName)
        } else {
            return classNameFromNoPackage(nodeName)
        }
    }

    private fun classNameFromNoPackage(viewName: String) : ClassName {
        return when(viewName) {
            "LinearLayout" -> linearLayout
            "FrameLayout" -> frameLayout
            "TableLayout" -> tableLayout
            "TableRow" -> tableRow
            "ViewGroup", "include" -> viewGroup
            "Button" -> button
            "ImageView" -> imageView
            "ScrollView" -> scrollView
            "Switch" -> switch
            "ImageButton" -> imageButton
            "CheckBox" -> checkBox
            "RadioGroup" -> radioGroup
            "RadioButton" -> radioButton
            "WebView" -> webView
            "VideoView" -> videoView
            "CalendarView" -> calendarView
            "ProgressBar" -> progressBar
            "SeekBar" -> seekBar
            "RatingBar" -> ratingBar
            "SearchView" -> searchView
            "TextureView" -> textureView
            "SurfaceView" -> surfaceView
            "Spinner" -> spinner
            else -> throw RuntimeException("No class for $viewName!")
        }
    }
}




