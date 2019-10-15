package cn.wangyan.game

import org.apache.commons.lang3.time.FastDateFormat

object FilterUtils {
  private val fdf = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")

  def filterByTime(fields: (String, String, String), startTime: Long, endTime: Long): Boolean = {
    val time = fields._2
    val time_long = fdf.parse(time).getTime
    time_long >= startTime && time_long <= endTime
  }


  def filterByTimeAndType(fields: (String, String, String), eventType: String, startTime: Long, endTime: Long): Boolean = {
    val event_type = fields._1
    filterByTime(fields,startTime,endTime) && event_type.equals(eventType)
  }




}
