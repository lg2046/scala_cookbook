package dp.d1_creational.d3_abstract_factory

//手记类产品线
trait Article {
  def produce(): Unit
}


class JavaArticle extends Article {
  override def produce(): Unit = println("编写Java手记")
}


class PythonArticle extends Article {
  override def produce(): Unit = println("编写Python手记")
}


//视频类产品线
trait Video {
  def produce(): Unit
}


class JavaVideo extends Video {
  override def produce(): Unit = println("录制Java视频")
}


class PythonVideo extends Video {
  override def produce(): Unit = println("录制Python视频")
}

//用于解决创建一组产品
trait CourseFactory {
  def getVideo: Video

  def getArticle: Article
}

class JavaCourseFactory extends CourseFactory {
  override def getVideo: Video = new JavaVideo

  override def getArticle: Article = new JavaArticle
}

class PythonCourseFactory extends CourseFactory {
  override def getVideo: Video = new PythonVideo

  override def getArticle: Article = new PythonArticle
}

object AbstractFactory {
  def main(args: Array[String]): Unit = {
    val javaCourseFactory = new JavaCourseFactory()
    val video = javaCourseFactory.getVideo
    val article = javaCourseFactory.getArticle

    video.produce()
    article.produce()
  }
}