package library_demo

import java.net.URL
import java.nio.file.{Path, StandardWatchEventKinds, WatchEvent}

import active_support._

import scala.concurrent.ExecutionContext.Implicits.global

object BetterFileDemo {
  def main(args: Array[String]): Unit = {
    import better.files._
    import File._
    import java.io.{File => JFile}

    /** 1: 实例化File */
    val f = File("/Users/liguang") // /User/liguang
    val f1 = home / "Desktop" // home解析为当前用户目录/Users/liguang/Desktop
    val f2 = home / "Desktop" / ".." //可以使用..进行导航 /Users/liguang
    val f3 = ".".toFile // .为当前项目的目录/Volumes/data2/github/scala_cookbook
    val f4 = File("/Users/liguang/..") // /User
    // new JFile("/Users/liguang/Documents").toScala

    println(f.path)
    println(f1.path)
    println(f2.path)
    println(f3.path)
    println(f4.path)


    /** 2: 简单文件读写 overwrite appendLine append contentAsString */
    val file = File("/tmp/test.txt")
    file.overwrite("hello 地球")

    val file11 = File("/tmp/test.txt").deleteOnExit()
    file11.overwrite("hello") //writeByteArray
    //appendLine会附加换行符
    file11.appendLine("google").append("地球")
    assert(file11.contentAsString == "hellogoogle\n地球")


    /** 3: 弹性接口 */
    println("\n---- FluentApi ------")
    File("/tmp/test2.txt")
      .createFileIfNotExists()
      .appendLine()
      .appendLines("My name is", "Inogo Montoya")
      .moveToDirectory(home / "Desktop")
      .renameTo("princess_diary.txt")
      .changeExtensionTo(".md")
      .deleteOnExit()
      .lines
      .mkString("\n")
      .tap(println)

    /** 4: Resource API  从resources中加载文件 */
    println("\n---- Resource API -----")
    val resourceAsStr = Resource.getAsString("people.json")
    println(resourceAsStr.slice(0, 10))

    println("\n---- Resource API getAsStream ------")
    val resourceAsStream = Resource.getAsStream("people.json")
    //Resource.at[MyClass].getAsStream("foo.txt") 自定义ClassLoader
    resourceAsStream tap println

    /** 5: Stream  -- 均返回迭代器 不会常驻内存 */
    println("\n---- Stream API------")
    val bytes: Iterator[Byte] = file.bytes
    val chars: Iterator[Char] = file.chars
    // file.lines会加载所有行到内存
    val lines: Iterator[String] = file.lineIterator

    bytes.take(10).mkString(",") tap println
    chars.take(10).mkString(",") tap println
    lines.take(10) foreach println

    //bytes.toStream允许多次traverse 上面的只能traverse一次

    //写Iterator到文件
    //file.writeBytes(bytes)
    //file.printLines(lines)

    //tee 多个OutputStream 共同 输出
    //    val s3 = file.newOutputStream tee file.newOutputStream
    //    s3.printWriter().println("Hello")

    /** 6: 基于java.io的便捷方法 */
    val file2: File = File("/tmp/hello.txt").createFileIfNotExists()
    val javaFile: java.io.File = file2.toJava
    val uri: java.net.URI = file2.uri
    val url: java.net.URL = file2.url
    val reader: java.io.BufferedReader = file2.newBufferedReader
    val outputstream: java.io.OutputStream = file2.newOutputStream
    val writer: java.io.BufferedWriter = file2.newBufferedWriter
    val inputstream: java.io.InputStream = file2.newInputStream
    val path: java.nio.file.Path = file2.path
    val fs: java.nio.file.FileSystem = file2.fileSystem
    val channel: java.nio.channels.FileChannel = file2.newFileChannel
    val ram: java.io.RandomAccessFile = file2.newRandomAccess()
    val fr: java.io.FileReader = file2.newFileReader
    val fw: java.io.FileWriter = file2.newFileWriter(append = true)
    val printer: java.io.PrintWriter = file2.newPrintWriter()

    /** 7: 对java的stream与reader writer的一些扩展隐式方法 */
    //file1.reader > file2.writer       // pipes a reader to a writer
    //System.in > file2.out             // pipes an inputstream to an outputstream
    //src.pipeTo(sink)                  // if you don't like symbols

    //val bytes   : Iterator[Byte]        = inputstream.bytes
    //val bis     : BufferedInputStream   = inputstream.buffered
    //val bos     : BufferedOutputStream  = outputstream.buffered
    //val reader  : InputStreamReader     = inputstream.reader
    //val writer  : OutputStreamWriter    = outputstream.writer
    //val printer : PrintWriter           = outputstream.printWriter
    //val br      : BufferedReader        = reader.buffered
    //val bw      : BufferedWriter        = writer.buffered
    //val mm      : MappedByteBuffer      = fileChannel.toMappedByteBuffer
    //val str     : String                = inputstream.asString  //Read a string from an InputStream
    //val in      : InputStream           = str.inputStream
    //val reader  : Reader                = str.reader
    //val lines   : Seq[String]           = str.lines


    //    对java对象序列化的支持
    //    case class Person(name: String, age: Int)
    //    val person = Person("Chris", 24)
    //
    //    val objfile = File("/tmp/person.txt")
    //    objfile.writeSerialized(person)
    //    assert(objfile.readDeserialized[Person] == person)
    //
    //    //调用in的read 不停的往out里面写 当读的字节为0时返回
    //    File("/tmp/test.txt").newInputStream.pipeTo(System.out)

    /** 8: glob */
    val dir = home / "Desktop"
    dir.glob("*.{sql,htm,jpg}") foreach println //globRegex
    dir.glob("**/*.{sql,htm,jpg}") foreach println

    //打印所有目录
    dir.collectChildren(_.isDirectory) foreach println

    //dir.list dir.walk 简单便捷方法

    /** 9: 文件系统操作 */
    //file.touch()
    //file.delete()     // unlike the Java API, also works on directories as expected (deletes children recursively)
    //file.clear()      // If directory, deletes all children; if file clears contents
    //file.renameTo(newName: String)
    //file.moveTo(destination)
    //file.moveToDirectory(destination)
    //file.copyTo(destination)       // unlike the default API, also works on directories (copies recursively)
    //file.copyToDirectory(destination)
    //file.linkTo(destination)                     // ln destination file
    //file.symbolicLinkTo(destination)             // ln -s destination file

    //file.setOwner(user: String)      // chown user file
    //file.setGroup(group: String)     // chgrp group file
    //file.isReadLocked; file.isWriteLocked; file.isLocked
    //File.numberOfOpenFileDescriptors        // number of open file descriptors

    //file.{checksum, md5, sha1, sha256, sha512, digest}   // also works for directories  (it recursively computes for each file in the directory).
    //file.md5 // equivalent to file.checksum("md5")
    //For input/output streams:
    //val md5: String = inputstream.md5.hexDigest()
    //val md5: String = outputstream.md5.hexDigest()
    //The above consumes and closes the inputstream. If you want to write it to a file AND also compute the sha512, you can do:

    //val md5: String = inputstream.sha512.hexDigest(drainTo = someFile)

    /** 10:临时文件 */
    //    File.newTemporaryDirectory() //增加deleteOnExit在退出时删除
    //    File.newTemporaryFile()

    //在上下文结束时会删除，包括产生异常时也会删除
    //    for (tempFile <- File.temporaryFile()) { //Dispose[File]
    //      println(tempFile.path)
    //      tempFile.overwrite("google")
    //      println(tempFile.contentAsString)
    //    }

    //下面也会自动删除
    //    File.usingTemporaryDirectory() { tmpFile =>
    //      println(tmpFile.path)
    //    }

    //val foo = File.home / "Downloads" / "foo.txt"
    //for {
    //  temp <- foo.toTemporary  转化为临时文件  在退时block时会删除
    //} doSomething(temp) // foo is deleted at the end of this block - even if an exception happens


    /** 11:查询文件属性 */
    //    file.name // simpler than java.io.File#getName
    //    file.extension
    //    file.contentType
    //    file.lastModifiedTime // returns JSR-310 time
    //    file.owner
    //    file.group
    //    file.isDirectory
    //    file.isDirectory(File.LinkOptions.noFollow)
    //    file.isSymbolicLink
    //    file.isRegularFile
    //    file.isHidden
    //    file.isOwnerExecutable
    //    file.isGroupReadable // etc. see file.permissions
    //    file.size // for a directory, computes the directory size
    //    file.posixAttributes
    //    file.isEmpty // true if file has no content (or no children if directory) or does not exist
    //    file.isParentOf(file)
    //    file.isChildOf(file)
    //    file.isSiblingOf(file)
    //    file.siblings

    //    import java.nio.file.attribute.PosixFilePermission
    //    file.addPermission(PosixFilePermission.OWNER_EXECUTE) // chmod +X file
    //    file.removePermission(PosixFilePermission.OWNER_WRITE) // chmod -w file
    //    assert(file.permissionsAsString == "r-xr--r--")

    // The following are all equivalent:
    //    assert(file.permissions contains PosixFilePermission.OWNER_EXECUTE)
    //    assert(file.testPermission(PosixFilePermission.OWNER_EXECUTE))
    //    assert(file.isOwnerExecutable)

    /** 12:文件比较 */
    //Use == to check for path-based equality and === for content-based equality:
    //file1 == file2 // equivalent to `file1.isSamePathAs(file2)`
    //file1 === file2 // equivalent to `file1.isSameContentAs(file2)` (works for regular-files and directories)
    //file1 != file2 // equivalent to `!file1.isSamePathAs(file2)`
    //file1 !== file2 // equivalent to `!file1.isSameContentAs(file2)`

    //内置Ordering[File]多项实例
    //val files = myDir.list.toSeq
    //files.sorted(File.Order.byName)
    //files.max(File.Order.bySize)
    //files.min(File.Order.byDepth)
    //files.max(File.Order.byModificationTime)
    //files.sorted(File.Order.byDirectoriesFirst)


    /** 13:zip api */
    //解压
    //val zipFile = home / "Desktop" / "test.zip"
    //zipFile.unzipTo(home / "Desktop" / "test")

    //压缩
    //    val zipFile2 = (home / "Desktop" / "Title_files").zipTo(home / "Desktop" / "Title_files.zip").deleteOnExit()

    //压缩指定的多个文件
    //(home / "Desktop" / "test2.zip").zipIn(Iterator(zipFile, zipFile2))

    // Zipping/Unzipping to temporary files/directories:
    //val someTempZipFile: File = directory.zip()
    //val someTempDir: File = someTempZipFile.unzip()
    //assert(directory === someTempDir)

    //获取zip文件中的文件列表
    //val fileNames = zipFile.newZipInputStream.mapEntries(_.getName) // gets the file names inside the zip file

    //GZIP 处理
    //File("big-data.csv").gzipTo(File("big-data.csv.gz"))
    //File("big-data.csv.gz").unGzipTo(File("big-data.csv"))

    //// GZIP stream handling:
    //File("countries.gz").newInputStream.asGzipInputStream().lines.take(10).foreach(println)

    //def write(out: OutputStream, countries: Seq[String]) =
    //  out.asGzipOutputStream().printWriter().printLines(countries).close()


    /** 14: ARM  auto close */
    //for {
    //  reader <- file.bufferedReader    // returns Dispose[BufferedReader]
    //} foo(reader)

    // or simply:
    //file.bufferedReader.foreach(foo)

    //for {
    //  reader <- file.bufferedReader
    //} yield foo(reader)

    // Simpler
    //file.bufferedReader.map(foo).get()

    // Even simpler
    //file.bufferedReader.apply(foo)

    //    val lines2: List[String] = file.newInputStream.autoClosed { stream =>
    //      stream.lines.toList // Must be eager so .toList
    //    }

    /** 14: 文件监控 使用directory-watcher-better-files+2.12效率高些 */
    //    https://github.com/gmethvin/directory-watcher#better-files-integration-scala
    //    val watcher = new FileMonitor(home / "Desktop", recursive = true) {
    //      override def onEvent(eventType: WatchEvent.Kind[Path], file: File, count: Int): Unit = eventType match {
    //        case StandardWatchEventKinds.ENTRY_CREATE => println(s"$file created")
    //        case StandardWatchEventKinds.ENTRY_MODIFY => println(s"$file modified")
    //        case StandardWatchEventKinds.ENTRY_DELETE => println(s"$file delete")
    //      }
    //    }
    //    watcher.start()


    /** 扩展进程处理 scala自带 */

    //    import sys.process._
    //    "ls -al".! //阻塞 返回退出码
    //    "ls -al".!! //阻塞 捕获输出 非0退出码暴异常  标准错误信息打印到控制台

    //    "which google".lineStream_!.headOption tap println //输出以流返回  但不会暴异常

    //单独截获退出码 标准输出与标准错误
    //    val (stdout, stderr) = (new StringBuilder, new StringBuilder)
    //    val status = "ls -al FRED" ! ProcessLogger(stdout append _, stderr append _)
    //
    //    println(status)
    //    println(stdout)
    //    println(stderr)

    //管道命令
    //    val numProcs = ("ps auxw" #| "wc -l").!!.trim
    //    println(s"#procs = $numProcs")

    // #&&第一个命令成功才运行第二个
    // #||第一个命令失败才运行第二个
    // ### 依次运行命令，退出码为最后一个命令的退出码
    //    ("ls -alh" #| "wc -l").!!.trim tap println

    //重定义扩展命令的stdin stdout
    // #> stdout重定向  #< 重定向stdin
    //    ("ls -alh" #> File("files.txt").toJava).!

    //    scala.io.Source.fromURL("http://www.google.com").mkString tap println
    //    (new URL("http://www.google.com") #> File("google.txt").toJava).!

    //    "ls *.txt".! //无法处理命令中的通配符 使用下面的才行
    //    Seq("/bin/sh", "-c", "ls *.txt").!

    //在指定的目录下运行进程
    //    Process("ls -al", File("/tmp").toJava).!

    //运行命令时指定环境变量
    //    Process("env", File("/tmp").toJava, "PATH" -> ".:/usr/bin:/opt/scala/bin").!
  }
}
