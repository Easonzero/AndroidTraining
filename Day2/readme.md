# 阿里巴巴俱乐部Android训练营`Day2`

### 培训内容

- Theme|Style([demo](./res/styles.xml))

1. Style和Theme的写法见[样例文件](./res/styles.xml)。
2. 在主题和风格的编写中，style标签可以通过parent指定该风格继承于谁。
3. item标签表示一个属性键值对，其中`name`属性代表属性名称，item标签内容则是属性的值。
4. Style文件可以在item标签内部直接调用color，dimen等常量值，调用语法为@常量类型/常量名称，例如@color/colorName。
5. Theme由`AndroidManifest.xml`中的application标签中的android:theme属性指定，@style/主题名称。
6. Style由任意需要指定样式的控件的style属性指定，指定格式为@style/样式名称。


- Activity([demo](./app/MainActivity.java))

1. ![生命周期](http://hi.csdn.net/attachment/201109/1/0_1314838777He6C.gif)
- 启动Activity：系统会先调用onCreate方法，然后调用onStart方法，最后调用onResume，Activity进入运行状态。
- 当前Activity被其他Activity覆盖其上或被锁屏：系统会调用onPause方法，暂停当前Activity的执行。
- 当前Activity由被覆盖状态回到前台或解锁屏：系统会调用onResume方法，再次进入运行状态。
- 当前Activity转到新的Activity界面或按Home键回到主屏，自身退居后台：系统会先调用onPause方法，然后调用onStop方法，进入停滞状态。
- 用户后退回到此Activity：系统会先调用onRestart方法，然后调用onStart方法，最后调用onResume方法，再次进入运行状态。
- 当前Activity处于被覆盖状态或者后台不可见状态，即第2步和第4步，系统内存不足，杀死当前Activity，而后用户退回当前Activity：再次调用onCreate方法、onStart方法、onResume方法，进入运行状态。
- 用户退出当前Activity：系统先调用onPause方法，然后调用onStop方法，最后调用onDestory方法，结束当前Activity。

2. Activity继承于AppCompatActivity(推荐，这是向前兼容新特性版的activity)或者Activity

3. 在Activity中可以通过findViewById(id)方法获得布局文件中的控件，注意获得的控件需要强转。

4. finish()函数可以直接使Activity进入destory期，终止Activity。

5. 通过构造Intent可以创建新的Activity实例。

6. 写新的Activity需要在`AndroidManifest.xml`文件中进行配置

- Java基础

1. 类，关键字是`class`，用于描述某一事物，具有行为和属性。

```java
class Book{
	int pageNum = 100;//属性

	int getPageNum(){//行为
		return pageNum;
	}
}
```

2. 继承:子类继承父类的所有属性和行为,使用`extends`关键字。

```java
class Humen{
	int getAge(){//获得年龄
		return age;
	}
}
//Women类继承于Humen类
class Women extends Humen{
	int age = 30;//年龄
}
```

3. 重写(Override):子类对父类允许访问的方法进行重新编写, 返回值和形参都不能改变,可以通过`super`方法调用父类的方法。

```java
//Women类继承于Humen类,并重写了getAge方法
class Women extends Humen{
	int age = 30;//年龄

	int getAge(){
		//super.getAge();
		return age-10;
	}
}
```

4. 重载(Overload):在一个类里面，方法名字相同，而参数不同

```java
//两个getAge方法，由于参数不同可以共存。
class Women extends Humen{
	int age = 30;//年龄

	int getAge(){
		return age-10;
	}

	int getAge(int offset){
		return age+offset;
	}
}
```

5. 修饰符：public\private\protected表示了访问权限。
<table>
    <tr>
        <td>`public`</td>
        <td>公有权限，允许任意类访问</td>
    </tr>
    <tr>
        <td>`private`</td>
        <td>私有权限，只允许本类访问</td>
    </tr>
    <tr>
        <td>`protected`</td>
        <td>保护权限，允许同包类、本类、子类访问</td>
    </tr>
</table>
