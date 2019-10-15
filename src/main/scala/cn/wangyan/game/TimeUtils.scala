package cn.wangyan.game

import java.util.{Calendar, Locale}

import org.apache.commons.lang3.time.FastDateFormat

object TimeUtils {
  private val fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  private val calendar = Calendar.getInstance()

  def apply(time: String): Long = {
    calendar.setTime(fdf.parse(time))
    calendar.getTimeInMillis
  }

//  def apply(time: String,pattern: FastDateFormat): Long = {
//    calendar.setTime(pattern.parse(time))
//    calendar.getTimeInMillis
//  }

  def updateCalendar(a: Int): Long = {
    calendar.add(Calendar.DATE,a)
    val time = calendar.getTimeInMillis
    calendar.add(Calendar.DATE,-a)
    time
  }

}
