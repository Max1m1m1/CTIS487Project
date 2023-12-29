package com.orhanefegokdemir.project

import ExampleFragment
import ExampleFragment2
import android.app.NotificationManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.orhanefegokdemir.project.databinding.ActivityMainBinding
import com.orhanefegokdemir.project.syste.Supplement
import com.orhanefegokdemir.project.syste.SupplementSys


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var gestureDetector: GestureDetector

    lateinit var workManager: WorkManager
    lateinit var workRequest: OneTimeWorkRequest
    lateinit var customWorker: CustomWorker
    lateinit var btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        btn=findViewById(R.id.btnStartService)
        val buttonDoubleClick: FloatingActionButton = findViewById(R.id.buttonDoubleClick)

        // Initialize GestureDetector
        gestureDetector = GestureDetector(this, DoubleClickGestureListener())

        // Set onTouchListener for the floating action button
        buttonDoubleClick.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ExampleFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container2, ExampleFragment2()).commit()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)

        SupplementSys.prepareData()

        val adapter = MyAdapter(SupplementSys.supplementlist)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Cancel the notification that we started
        val intent = intent
        val notificationId = intent.getIntExtra("notificationID", 0)
        val message = intent.getStringExtra("msg")
        if (message != null) {
            mNotificationManager.cancel(notificationId)
            mNotificationManager.cancel(notificationId)
        }

        /*

        fetchData { supplementList ->
            supplementList?.let {
                // The supplementList is not null, initialize the adapter with the list
                val adapter = MyAdapter(it)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            } ?: run {
                // Handle the case where the supplementList is null (e.g., error occurred)
            }
        }
        */
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        /*
        STEP3: Create work policy: Schedule the work
        OneTimeWorkRequest: worker will be used for just one work
        PeriodicWorkRequest: Worker will be used for periodic work
         */
        //STEP 4 & STEP7, Create work policy and send data as input to the work. How input will be taken , check CustomWorker class
        workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
            .setInputData(Data.Builder().putInt("num", 10).putString("name", "nese").build())
            .build()
        //it will begin immeditaley in the background
        // if constraints are assigned, it will run immeditaley if constraits are met
        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).setConstraints(constraints).build()

        //STEP 5: To delay work for 10 minutes
        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).setInitialDelay(10, TimeUnit.MINUTES).build();

        //STEP 6: To retry and backoff policy: in each 10 seconds it will retry, after subsequent attempts exponential it will try as 20,40,80...

        //workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java).build();
        //var mperidicRequest = PeriodicWorkRequest.Builder(CustomWorker::class.java, 1, TimeUnit.HOURS).build(); // work scheduled to one hour
        //var mperidicRequest = PeriodicWorkRequest.Builder(CustomWorker::class.java, 1, TimeUnit.HOURS, 15, TimeUnit.MINUTES).build(); // work scheduled to one hour

        /*
        STEP 8_1: to un the work, create WorkManager object
         */
        workManager = WorkManager.getInstance(this)

        btn.setOnClickListener(View.OnClickListener {
            //STEP 4 & STEP7, Create work policy and send data as input to the work. How input will be taken , check MyWorker class
            workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setInputData(Data.Builder().putInt("num", 10).putString("name", "nese").build())
                .build()

            /*
            STEP 8-2: to run the work, assign request object to work manager.
           */
            workManager.enqueue(workRequest)
            Toast.makeText(this@MainActivity, "This will immediately executed. It will not wait for the worker result",
                Toast.LENGTH_SHORT).show()

            //at the end of background task what will be done
            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this@MainActivity,
                Observer{ workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val resultData: Data = workInfo.outputData//get output of worker
                        Toast.makeText(this@MainActivity, "SUCCEEDED " + resultData.getInt("result", 0),
                            Toast.LENGTH_LONG ).show()
                    }
                })

        })
    }
    inner class DoubleClickGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val DOUBLE_CLICK_TIME_DELTA: Long = 300 // Time between clicks for a double-click (in milliseconds)
        private var lastClickTime: Long = 0

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            // Handle single tap (if needed)
            return super.onSingleTapConfirmed(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double-click detected
                showToast("Double-click detected!")
            }
            lastClickTime = clickTime
            return true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun changeBackgroundColor(colorCode: String) {
        // Change the background color of the fragment container
        findViewById<View>(R.id.fragment_container).setBackgroundColor(Color.parseColor(colorCode))
    }

    fun loadFragment(fragment: Fragment) {
        // Load a new fragment into the container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }



    fun changeBackgroundColor2(colorCode: String) {
        // Change the background color of the fragment container
        findViewById<View>(R.id.fragment_container2).setBackgroundColor(Color.parseColor(colorCode))
    }

    fun loadFragment2(fragment: Fragment) {
        // Load a new fragment into the container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container2, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun fetchData(callback: (List<Supplement>?) -> Unit) {
        val call: Call<List<Supplement>> = RetrofitInstance.apiService.getData()

        call.enqueue(object : Callback<List<Supplement>> {
            override fun onResponse(call: Call<List<Supplement>>, response: Response<List<Supplement>>) {
                if (response.isSuccessful) {
                    val data: List<Supplement>? = response.body()
                    // Process the data
                    callback(data)
                } else {
                    // Handle error (e.g., non-2xx status code)
                    // You can access error details using response.errorBody()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Supplement>>, t: Throwable) {
                // Handle network failure (e.g., no internet connection)
                callback(null)
            }
        })
    }

}