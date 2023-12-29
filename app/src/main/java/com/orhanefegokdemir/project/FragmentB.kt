import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.orhanefegokdemir.project.MainActivity
import com.orhanefegokdemir.project.R

class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_example2, container, false)

        val buttonChangeColor: Button = view.findViewById(R.id.buttonChangeColor2)
        buttonChangeColor.setOnClickListener {
            // Communicate with the activity to change the background color
            (activity as? MainActivity)?.changeBackgroundColor2("#33FF57") // Example color code
        }

        return view
    }
}