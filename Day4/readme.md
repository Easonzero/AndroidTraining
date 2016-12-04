# 阿里巴巴俱乐部Android训练营{Day4}

### 培训内容

## android数据存储的方式

1. 文件存储--api简单，底层，功能基本，想要复杂的数据存储需要自己实现  
2. preference--api简单，功能基本，适合简单的key、value形式的数据存储  
3. sqlite--api复杂，功能强大，适合复杂的数据存储场景  
4. contentprovider--app与app之间的数据存储方式  

## 数据读写权限

在`AndroidManifest.xml`文件中添加如下权限

```xml
<!-- 在SDCard中创建与删除文件权限 -->
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS">
<!-- 往SDCard写入数据权限 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE">
```

## 文件存储

java语言提供了对文件进行操作的类，比如File、FileOutputStream、FileInputStream等等。

1.`File`类是对文件的封装类，创建实例的方式是
```java
File file = new File('文件路径');
```
File类提供了文件的创建、删除、重命名等针对文件的操作。

2.`FileOutputStream`及`FileInputStream`是文件内容的操作类，可以将文件内容读入内存中或者将在文件中写入任意内容。创建实例的方式是
```java
FileInputStream fis = new FileInputStream(file);
```
二者使用之后都需要通过`close`方法关闭流，避免内存泄露。

3.文件写入的api是通过`FileOutputStream`类的`write`方法写入的，参数是字节数组，字节数组可以直接通过字符串的`getBytes`方法获得

文件读取的方法如下：
```java
	FileInputStream in;
        try {
            in = new FileInputStream(file);
            byte[] bbuf = new byte[1024];  
            int hasRead = 0;  
            while((hasRead = in.read(bbuf))>0)  
            {  
                System.out.println(new String(bbuf,0,hasRead));  
            }  
            in.close();  
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
```

## preference

preference提供了在app中持久保存简单数据的能力。preference本质上是一个xml文件，保存在android系统为app分配的专门的文件夹中。我们可以通过preference提供的方法来轻松的写入读出各种格式的数据。

preference保存的数据格式是(key，value)的形式，即key是索引，value是索引对应的值，所以我们可以key索引来找到对应的value。

1.preference读取数据：

```java
SharedPreferences sp = getSharedPreferences('name', MODE_WORLD_READABLE);  
//从SharedPreferences获得内容  
String content = sp.getString("key", "默认值");  
```
preference的创建方式如代码的第一行所示，有两个参数，第一个是preference的文件名，第二个则是操作模式。

preference的操作模式有三种：

- MODE_PRIVATE(私有)
- MODE_WORLD_READABLE(可读)
- MODE_WORLD_WRITEABLE（可写）

获取preference的内容只需要通过`getString`等api即可。  

2.preference写入数据：

```java
SharedPreferences.Editor editor = getSharedPreferences('name', MODE_WORLD_WRITEABLE).edit();  
//将数据添加到editor  
editor.putString("key", myEditText.getText().toString());  
//提交editor内容到preference  
editor.commit(); 
```

写入数据时要注意的是写入操作需要先创建`Editor`类才能操作，调用`putString`等写入api后，只是将数据写入了缓存区，需要通过`commit`方法提交到`preference`

## sqlite

SQLite是一款轻型的数据库，可以应对复杂的数据存储情况。

1.SQLite数据类型

NULL: 这个值为空值

INTEGER: 值被标识为整数,依据值的大小可以依次被存储为1,2,3,4,5,6,7,8.

REAL: 所有值都是浮动的数值,被存储为8字节的IEEE浮动标记序号.

TEXT: 值为文本字符串,使用数据库编码存储(TUTF-8, UTF-16BE or UTF-16-LE).

BLOB: 值是BLOB数据块，以输入的数据格式进行存储。如何输入就如何存储,不改变格式。

2.SQLiteOpenHelper

在android中，代表数据库的类是SQLiteDatabase，创建数据库则需要通过android提供的数据库辅助类`SQLiteOpenHelper`来完成。

```java
public class StuDBHelper extends SQLiteOpenHelper {  
  
private static final String TAG = "TestSQLite";  
public static final int VERSION = 1;  
  
//必须要有构造函数  
public StuDBHelper(Context context, String name, CursorFactory factory,  
int version) {  
    super(context, name, factory, version);  
}  
  
// 当第一次创建数据库的时候，调用该方法   
public void onCreate(SQLiteDatabase db) {  
    String sql = "create table stu_table(id int,sname varchar(20),sage int,ssex varchar(10))";  
//输出创建数据库的日志信息  
    Log.i(TAG, "create Database------------->");  
//execSQL函数用于执行SQL语句  
    db.execSQL(sql);  
}  
  
//当更新数据库的时候执行该方法  
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
//输出更新数据库的日志信息  
     Log.i(TAG, "update Database------------->");  
}  
}  
```

如同上例所示，SQLiteOpenHelper有两个生命周期方法，一个是`onCreate`，在第一次创建数据库时回调，一个是`onUpgrade`，在数据库更新时回调。

我们可以在onCreate方法中发射创建数据库的sql语句，即可完成数据库的创建。

```java
StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this,"stu_db",null,1);  
//得到一个可写的数据库  
SQLiteDatabase db =dbHelper.getWritableDatabase();  
```

使用时像这样即可获得一个数据库的实例。

之后我们只需要通过数据库`SQLiteDatabase`的`execSQL`方法发射sql语句即可完成各种数据操作。当数据库使用结束时，需要调用`SQLiteDatabase`的`close`方法，防止内存泄露。

3.sql语句

- 创建数据库

```sql
CREATE TABLE 表名称
(
列名称1 数据类型,
列名称2 数据类型,
列名称3 数据类型,
....
)
```

- 插入数据

```sql
INSERT INTO 表名称 VALUES (值1, 值2,....)
```

- 删除数据

```sql
DELETE FROM 表名称 WHERE 列名称 = 值
```

- 修改数据

```sql
UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
```

- 查询数据

```sql
SELECT 列名称 FROM 表名称 WHERE 条件语句
```

更多详细的sql语法，请自行搜索解决。
