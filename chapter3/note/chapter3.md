## 本章目标

会使用Js的基本语法

1. css 样式
2.  Script标签
3. 变量的定义与赋值
4. 数据类型（与java基本相同,但是有多出来的）
5. 运算符和控制语句(与java相同)
6. 自定义函数
7. DOM对象和DOM元素的访问



## css（定义样式 ）

1. 内联式样式

   - 样式的属性内容直接跟在将要修饰的文字标记里

   - 不满足结构与表现分离的设计思想

2. 嵌入式样式

   - 样式的属性内容以代码的形式写在网页代码中

   - 在合适的地方使用 class="样式名" 调用具体的样式效

     ```css
      <style type="text/css">
     
     .main{ width:1002px; margin:0 auto;}
     
     </style>
     ```

     

3. 外联式样式

   - 有一个单独的CSS文件存在

   - 在合适的地方使用 class="样式名" 调用具体的样式效果

     ```css
     <link href="style.css" type="text/css" rel="Stylesheet"/>
     ```

   

样式优先级: 内联式 > 嵌入式 > 外部式   (就近原则)

但注意上面所总结的优先级是有一个前提：内联式、嵌入式、外部式样式表中css样式是在的相同权值的情况下。

## Script标签（javascript）

```javascript
<script>
  ...
</script>
```



## 变量的定义与赋值



变量是对“值”的引用，使用变量等同于引用一个值。每一个变量都有一个变量名

```
var a = 1; 
var a; a = 1; 
```

如果只是声明变量而没有赋值，则该变量的值是undefined 。 undefined是一个JavaScript关键字，表示"无定义"

```
var a; a // undefined 
```

可以在同一条var命令中声明多个变量。

```
var a, b; 
```

JavaScirpt是一种动态类型语言，也就是说，变量的类型没有限制，可以赋予各种类型的值。

```
var a = 1; 
a = 'hello'; 
```

如果使用var重新声明一个已经存在的变量，是无效的。

```
var x = 1;
var x; 
x // 1 
```

但是，如果第二次声明的同时还赋值了，则会覆盖掉前面的值。

```javascript
var x = 1; 
var x = 2; 

// 等同于
var x = 1; 
var x; x = 2; 
```

命名规则

>\>第一个字符，可以是任意Unicode字母（包括英文字母和其他语言的字母），以及美元符号（$）和下划线（_）。
>
>第二个字符及后面的字符，除了Unicode字母、美元符号和下划线，还可以用数字0-9



## 数据类型

- 数值（number）：整数和小数（比如1和3.14）

- 字符串（string）：字符组成的文本（比如”Hello World”）

- 布尔值（boolean）：true（真）和false（假）两个特定值

- undefined：表示“未定义”或不存在，即此处目前没有任何值

- null：表示空缺，即此处应该有一个值，但目前为空

- 对象（object）：各种值组成的集合

undefined和null，一般将它们看成两个特殊值。



## 运算符

- 两元逻辑运算符： && (And)，|| (Or)

- 前置逻辑运算符： ! (Not)
- 相等运算符：===，!==，==，!=
- 比较运算符：>，>=，<，<=
- 算术运算符：+， -，* ， /， %，++，--

## 控制语句

- if…… else…...
- switch……case…...
- (contidion) ? expr1 : expr2  三元运算符
- while
- for
- do …… while
- break
- continue



## 对象

对象（object）是JavaScript的核心概念，也是最重要的数据类型。JavaScript的所有数据都可以被视为对象。

简单说，所谓对象，就是一种无序的数据集合，由若干个“键值对” 构成。

```javascript
var o = { 
  p: 'Hello World' 
}; 
```

对象的生成方法，通常有三种方法。除了像上面那样直接使用大括号生成（{}），还可以用new命令生成一个Object对象的实例，或者使用Object.create方法生成。

```javascript
var o1 = {}; 
var o2 = new (); 
var o3 = Object.create(null); 
```

对象的所有键名都是字符串，所以加不加引号都可以。上面的代码也可以写成下面这样。

```javascript
var o = { 
  p: 'Hello World' 
}; 
```

如果键名是数值，会被自动转为字符串。

```javascript
var o ={ 
1: 'a', 
3.2: 'b', 
1e2: true
}; 

```

对象的每一个“键名”又称为“属性”（property），它的“键值”可以是任何数据类型。如果一个属性的值为函数，通常把这个属性称为“方法”，它可以像函数那样调用。

```javascript
var o = { 
p: function (x) { 
	return 2 * x; 
} 
}; 
o.p(1) // 2 

```

对象的属性之间用逗号分隔，最后一个属性后面可以加逗号（trailing comma），也可以不加。

## 数组

本质上，数组属于一种特殊的对象。typeof运算符会返回数组的类型是object。

```javascript
typeof [1, 2, 3] // "object" 
```

数组的特殊性体现在，它的键名是按次序排列的一组整数（0，1，2…）。

```javascript
var arr = ['a', 'b', 'c']; 
Object.keys(arr) 
// ["0", "1", "2"] 
```

数组的length属性，返回数组的成员数量。

```javascript
['a', 'b', 'c'].length // 3 
```

## 函数

function命令后面是函数名，函数名后面是一对圆括号，里面是传入函数的参数。函数体放在大括号里面。性，返回数组的成员数量。

```javascript
function print(s) { 
   console.log(s); 
} 
```

除了用function命令声明函数，还可以采用变量赋值的写法。

```javascript
var print = function(s) { 
   console.log(s); 
}; 
```

这种写法将一个匿名函数赋值给变量。这时，这个匿名函数又称函数表达式（Function Expression），因为赋值语句的等号右侧只能放表达式。 采用函数表达式声明函数时，function命令后面不带有函数名。如果加上函数名，该函数名只在函数体内部有效，在函数体外部无效。

##DOM对象和DOM元素的访问

**DOM**－Document Object Model,它是W3C国际组织的一套Web标准，它定义了访问HTML文档对象的一套属性、方法和事件 。

| 名称                 | 说明                                                         |
| -------------------- | ------------------------------------------------------------ |
| getElementByID( )    | 根据HTML元素指定的ID，获得唯一的一个HTML元素。如：访问DIV层对象、图片Img对象 |
| getElementsByName( ) | 根据HTML元素指定的name，获得相同名称的一组元素。如：访问表单元素(全选功能） |



获得对象

```
 document.getElementById("")
```



## jquery

> jQuery 是一个 JavaScript 库, 极大地简化了 JavaScript 编程, 很容易学习。



请注意，<script> 标签应该位于页面的 <head> 部分。

```javascript
<head>
<script type="text/javascript" src="jquery.js"></script>
</head>
```

---

jQuery 语法是为 HTML 元素的选取编制的，可以对元素执行某些操作。

基础语法是：$(selector).action()

- 美元符号定义 jQuery
- 选择符（selector）“查询”和“查找” HTML 元素
- jQuery 的 action() 执行对元素的操作 

**示例**

$("p").hide() - 隐藏所有段落



** 文档就绪函数 **

```javascript
$(document).ready(function(){

 //do something

});
```

```javascript
$(function(){
	//do something
})
```

```javascript
$().ready(function(){

//do something
})
```

