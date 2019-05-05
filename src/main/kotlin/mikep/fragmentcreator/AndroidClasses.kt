package mikep.fragmentcreator

import com.squareup.kotlinpoet.*


object AndroidClasses {

    private val packageRegex = Regex("[A-Za-z]+[.].*")
    //TODO Get all of the possible classes from xml that don't contain their package in their name

    val view = ClassName("android.view", "View")

    //Layouts
    val linearLayout = ClassName("android.widget", "LinearLayout")
    val frameLayout = ClassName("android.widget", "FrameLayout")
    val tableLayout = ClassName("android.widget", "TableLayout")
    val tableRow = ClassName("android.widget", "TableRow")
    val viewGroup = ClassName("android.view", "ViewGroup")


    //Views
    val button = ClassName("android.widget", "Button")
    val imageView = ClassName("android.widget", "ImageView")
    val scrollView = ClassName("android.widget", "ScrollView")
    val switch = ClassName("android.widget", "Switch")
    val imageButton = ClassName("android.widget", "ImageButton")
    val checkBox = ClassName("android.widget", "CheckBox")
    val radioGroup = ClassName("android.widget", "RadioGroup")
    val radioButton = ClassName("android.widget", "RadioButton")
    val webView = ClassName("android.webkit", "WebView")
    val videoView = ClassName("android.widget", "VideoView")
    val calendarView = ClassName("android.widget", "CalendarView")
    val progressBar = ClassName("android.widget", "ProgressBar")
    val seekBar = ClassName("android.widget", "SeekBar")
    val ratingBar = ClassName("android.widget", "RatingBar")
    val searchView = ClassName("android.widget", "SearchView")
    val textureView = ClassName("android.view", "TextureView")
    val surfaceView = ClassName("android.view", "SurfaceView")
    val spinner = ClassName("android.widget", "Spinner")
    val textView = ClassName("android.widget", "TextView")


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
            "TextView" -> textView
            else -> throw RuntimeException("No class for $viewName!")
        }
    }

    fun createFragmentClass(className: String, layoutId: String) : TypeSpec.Builder {
        val clazz = TypeSpec.classBuilder(className).superclass(ClassName("androidx.fragment.app", "Fragment"))
        clazz.addAnnotation(AnnotationSpec.builder(ClassName("android.annotation", "SuppressLint")).addMember(""""WrongViewCast"""").build())
        val funOnCreateView = FunSpec.builder("onCreateView")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("inflater", ClassName("android.view", "LayoutInflater"))
            .addParameter("container", viewGroup.copy(nullable = true))
            .addParameter("savedInstanceState", ClassName("android.os", "Bundle").copy(nullable = true))
            .addCode("""return inflater.inflate($layoutId, container, false)""".trimIndent())
            .returns(view.copy(nullable = true))
            .build()

        return clazz.addFunction(funOnCreateView)
    }
}




