package com.example.clockkotlin.databaseClockAlarm

/**
 * This class used for creating clock alarms that stores: alarm [time], [enable] can enable or disable, and it position in data base as [id]
 *
 * @param time alarm time must have a look like this 00:05, 14:39 etc.
 * @param enable enable that represents for enable or disable clock signal
 * @param id position in [LocalDataBase]
 */
class AlarmSignal(val id: Long, val time: String, var enable: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlarmSignal

        if (time != other.time) return false
        if (enable != other.enable) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.hashCode()
        result = 31 * result + enable.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "AlarmSignal(time=$time, enable=$enable, id=$id)"
    }
}
