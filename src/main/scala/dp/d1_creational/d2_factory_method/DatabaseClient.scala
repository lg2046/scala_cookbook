package dp.d1_creational.d2_factory_method

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

//暴露给用户的一组对象, 由用户选择子类来选择创建具体的类
trait DatabaseClient {
  def connect(): SimpleConnection

  def executeQuery(query: String): Unit = {
    connect().executeQuery(query)
  }
}

class MysqlDatabaseClient extends DatabaseClient {
  override def connect(): SimpleConnection = new MysqlConnection
}

class PgDatabaseClient extends DatabaseClient {
  override def connect(): SimpleConnection = new PgConnection
}

object Demo {
  def main(args: Array[String]): Unit = {
    val mysqlClient: DatabaseClient = new MysqlDatabaseClient
    val pgClient: DatabaseClient = new PgDatabaseClient

    mysqlClient.executeQuery("select * from users")
    pgClient.executeQuery("select * from users")
  }
}
