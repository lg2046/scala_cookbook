package dp.creational

//要创建的一组对象
trait SimpleConnection {
  def executeQuery(query: String): Unit
}

class MysqlConnection extends SimpleConnection {
  def executeQuery(query: String): Unit = {
    println(s"""mysql execute $query""")
  }
}

class PgConnection extends SimpleConnection {
  def executeQuery(query: String): Unit = {
    println(s"""pg execute $query""")
  }
}

//对用户暴露一个类, 使用组合，从而将继承关系进行隔离 用户可以自由选择传递  //如何比较简单的，直接传递一个函数逻辑
class DatabaseClient(connectorFactory: DatabaseConnectorFactory) {
  def executeQuery(query: String): Unit = {
    connectorFactory.connect().executeQuery(query)
  }
}

trait DatabaseConnectorFactory {
  def connect(): SimpleConnection
}

class MysqlFactory extends DatabaseConnectorFactory {
  override def connect(): SimpleConnection = new MysqlConnection
}

class PgFactory extends DatabaseConnectorFactory {
  override def connect(): SimpleConnection = new PgConnection
}

object Demo {
  def main(args: Array[String]): Unit = {
    val mysqlClient: DatabaseClient = new DatabaseClient(new MysqlFactory)
    val pgClient: DatabaseClient = new DatabaseClient(new PgFactory)

    mysqlClient.executeQuery("select * from users")
    pgClient.executeQuery("select * from users")
  }
}
