import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.orhanefegokdemir.project.MainActivity
import com.orhanefegokdemir.project.R

class ExampleFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_example2, container,false)

        val buttonShowToast: Button = view.findViewById(R.id.buttonChangeColor2)

        buttonShowToast.setOnClickListener{
            Toast.makeText(context,"Button Clicked in Fragment",Toast.LENGTH_SHORT).show()

            (activity as? MainActivity)?.changeBackgroundColor2("#33FF57")
        }
        return view
        // Inflate the layout for this fragment

    }

}