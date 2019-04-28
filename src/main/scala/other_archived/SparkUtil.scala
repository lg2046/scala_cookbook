package other_archived

object SparkUtil {
  //  import org.apache.spark.sql.DataFrame
  //  import org.joda.time.DateTime
  //  import sys.process._
  //
  //  def convertToPandasDF(df_spark: DataFrame): Unit = {
  //    //先存储到hdfs
  //    val hdfs_dir =
  //      s"""/user/mart_cd/cdliguang/sparkjupyterhub/data_${DateTime.now.toString("yyyyMMdd_HHmmss")}.parquet"""
  //
  //    //df_spark.repartition(1)
  //    //    .write
  //    //    .option("header", "true")
  //    //    .option("codec", "gzip")
  //    //    .csv(hdfs_dir)
  //
  //    df_spark.repartition(1).write.format("parquet").mode("overwrite").save(hdfs_dir)
  //
  //    val hdfs_file_path = s"""hdfs dfs -ls $hdfs_dir/part-00000*""".!!.split(' ').last.trim
  //
  //    //本地下载路径
  //    val local_dir = "/home/liguang109/sparkjupyterhub/"
  //    s"""hdfs dfs -get $hdfs_file_path $local_dir""".!!
  //
  //    val local_path = local_dir + hdfs_file_path.split('/').last
  //
  //    println(local_path)
  //
  //    // 2: 显示列名，直接拷到下面
  //    //把本地文件路径和列名进行存储，找印出pyspark中使用的代码
  //    val columns = "[\n" + df_spark.schema.map(f => s"""  '${f.name}'""").mkString(",\n") + ",\n]"
  //
  //    // 3: 本地直接读取生成pandas 数据框
  //    println()
  //    println("-------copy to jupyter start----------")
  //    println(
  //      s"""
  //         |data = pd.read_parquet("$local_path")
  //         |data.head()
  //     """.stripMargin)
  //    println("-------copy to jupyter end----------")
  //    println()
  //  }
}
