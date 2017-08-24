## Mybatis 中 特殊符号 写法

##### 第一种写法（1）：

|原符号  |     <    |    <=  |    >   |    >=   |    &    |    '   |     "|
|---|---|---|---|---|---|---|---|
|替换符号   | \&lt;   | \&lt;=  | \&gt;   | \&gt;=  | \&amp;  | \&apos;|  \&quot;|

例如：sql如下：
create_date_time \&gt;= #{startTime} and  create_date_time \&lt;= #{endTime}

##### 第二种写法（2）：
大于等于
<![CDATA[ >= ]]>
小于等于
<![CDATA[ <= ]]>
例如：sql如下：
create_date_time <![CDATA[ >= ]]> #{startTime} and  create_date_time <![CDATA[ <= ]]> #{endTime}
