package com.example.clockkotlin


import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import java.io.File

//В этом классе будут записываться будильники в массив
class AlarmReceiver : BroadcastReceiver() {

    //Alarms array
    lateinit var clockDataBase: SQLiteDatabase
    lateinit var dbHelper: DataBaseOpenHelper


    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(
            context,
            "Будильник добавлен на ${intent.getStringExtra("time")}",
            Toast.LENGTH_SHORT
        ).show()


        dbHelper = DataBaseOpenHelper(context, "clock_table_data_base", null, 1)

        addClock(intent.getStringExtra("time"))


    }


    /**
     * Method that adds new alarm clock.
     *
     * @param time alarm time
     */
    private fun addClock(time: String) {

        //New clock object
        val cv = ContentValues()

        //Alarm time, user chose it
        cv.put("time", time)

        //default switch value
        cv.put("switch", 1)

        clockDataBase = dbHelper.writableDatabase
        clockDataBase.insert("clock_table_data_base", null, cv)
        Log.d("clock_table_data_base", "add new clock to data base")

        clockDataBase.close()
        dbHelper.close()
    }
}
