package cn.wangyan.game

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.rdd.EsSpark

object GameKPI {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("GameKPI")
      .setMaster("local[2]")
      .set("es.nodes", "hadoop0001,hadoop0002,hadoop0003")
      .set("es.port", "9200")
      .set("es.index.auto.create", "true")
      .set("es.nodes.wan.only", "true")

    val sc = new SparkContext(conf)

    //es查询的条件
    val query =
      """
        {"query":{"match_all":{}}}
      """.stripMargin
    //获取es中的数据
    val queryRDD: RDD[(String, collection.Map[String, AnyRef])] = EsSpark.esRDD(sc,"gamelogs",query)

    //过滤掉id
    val fieldsRDD: RDD[collection.Map[String, AnyRef]] = queryRDD.map(_._2)

    //获取事件类型，时间，用户字段
    val getFieldsRDD = fieldsRDD.map(line => {
      val event_type: String = line.getOrElse("event_type","-1").toString
      val current_time: String = line.getOrElse("current_time","-1").toString
      val user: String = line.getOrElse("user","").toString
      //Array(event_type,current_time,user)
      (event_type,current_time,user)
    })

    val queryTime = "2016-02-01 00:00:00"
    val startTime = TimeUtils(queryTime)
    val endTime = TimeUtils.updateCalendar(1)

    //日新增
    val dnu = getFieldsRDD.filter(fields => FilterUtils.filterByTimeAndType(fields,EventType.REGISTER,startTime,endTime))
    println(dnu.collect().toBuffer)


  }
}
