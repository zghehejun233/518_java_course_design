# 518 Java课设项目

## 仓库规范（建议）

### 版本管理规范

项目代码release包括三类：

- 大版本(x.0.0)
- 小版本(x.x.0)
- 补丁(x.x.x)

### Git Commit代码提交规范

#### commit message格式

git commit 提交样式规范：

```
<类型>: <标题>
<空一行>
<内容>
```

#### 1.3.2 格式说明

##### <类型>

用于说明 commit 的类别(type)，只允许使用下面7个标识。

```
# 主要type
feat:     增加新功能
fix:      修复bug

# 特殊type
docs:     只改动了文档相关的内容
style:    不影响代码含义的改动，例如去掉空格、改变缩进、增删分号
build:    构造工具的或者外部依赖的改动，例如webpack，npm
refactor: 代码重构时使用,重构（即不是新增功能，也不是修改bug的代码变动）
revert:   执行git revert打印的message

# 暂不使用type
test:     添加测试或者修改现有测试
perf:     提高性能的改动
ci:       与CI（持续集成服务）有关的改动
chore:    不修改src或者test的其余修改，例如构建过程或辅助工具的变动
```

当一次改动包括`主要type`与`特殊type`时，统一采用`主要type`。

##### <标题>

commit 目的的简短描述，不超过50个字符

##### <内容>

对本次 commit 的详细描述，可以分成多行，可详细说明代码变动的动机。

#### 示例

```
feat: 添加了xxx功能

对本次 commit 的详细描述，可以分成多行，可详细说明代码变动的动机
```

### 版本发布格式

版本发布时git commit提交规范：

```
<类型>: <版本>
<空一行>
feat: 
<新功能>
<空一行>
fix: 
<修复的bug>
```

#### <类型>

这里允许使用三个类型

```
alpha: 内部开发测试版本，对应版本号0.0.x
beta: 公开测试版本，对应版本号0.x.x
release: 正式部署版本，对应版本号x.x.x
```

## 需求分析

### 需求

- 学生基本信息、联系方式、入学前信息、家庭信息、社会关系等基本信息的管理
  > 由Student、StudentRepository和StudentController类实现
  > 除crud外，没有其他需要特别注意的功能
  > 查询可以通过学号和姓名查询
  > 难度1/5
- 学习信息管理，包括课程基本信息，课程中心（教材、课件、参考资料等）选课信息、考勤信息、作业信息、成绩信息等
  > 课程基本信息的实现并不困难，但这项需求的牵涉范围比较大
  > 比如考勤信息、作业信息，都是一个包含多个记录的记录
  > 具体实现方式有待思考
  > 需要特别注意的是可能设计较多的修改等操作
  > 对于查询功能可能并不需要实现
  > 难度4/5
- 学生社会实践、学科竞赛、科技成果、培训讲座、创新项目、校外实习等创新实践信息管理
  > 考虑对这几项内容进行抽象，再进行进一步的实现
  > 最好加入下拉菜单式的分类查询
  > 难度3/5
- 学生荣誉信息管理，包括获得的各种称号奖励等
  > 由Achievement、AchievementRepository和AchievementController实现
  > 根据类型进行查询
  > 难度2/5
- 学生体育活动、外出旅游、文艺演出、聚会、等日常活动管理。
  > 同样可能需要进行进一步抽象
  > 基本的crud操作
  > 难度3/5
- 学生外出请假信息和生活学习消费等日志信息管理
  > 感觉不应该在一起啊。。。
  > 写两个类吧
- 学生个人信息的统计汇入统计数据库
- 学生各种信息的查询统计、综合绩分的计算（可自行设计公式）和学生个人画像、个人简历的生成打印

### 分包和类关系

#### 学生模块
- 学生基本信息（Student）：姓名、学号、性别、年龄、生日、手机、邮箱
- 教育经历信息（EducationExperience）：学校名称、学段、开始时间、结束时间、备注
- 家庭信息(Family)：姓名、年龄、性别、关系、备注
- 社会关系(SocialRelation)：备注
对应`student_basic`包，其中学生基本信息对应多个教育经历信息、家庭信息和社会关系

其中对教育经历信息、家庭信息和社会关系分别设计超链接，使用二级菜单安排内容细节

#### 教研活动模块
- 社会实践：实践名称、内容、负责人、组织机构、地点、时间
- 学科竞赛：竞赛名称、竞赛类型、主办方、奖项名称、时间
- 科技成果：成果名称、简介、作者、级别、时间
- 培训讲座：主题、主讲人、内容、地点、时间
- 创新项目：名称、简介、负责人、指导、挂靠组织、备注
- 校外实习：实习机构、工作内容、地点、开始时间、结束时间、备注

#### 课程模块
- 开设课程管理
- 选课信息
- 作业信息
- 成绩信息
- 考勤信息

#### 日常信息模块
- 活动信息
- 校外活动信息
- 请假信息
- 消费信息
- 荣誉信息
- 称号奖励

## 版本

### alpha版本

### beta版本

#### beta: 0.1.0

feat：
实现学生模块的功能

#### alpha: 0.1.1

fix:
修复了详情页面显示内容缺少和格式错误的bug
修复了相关信息查询错误的bug

### release版本

## 部分运行时问题的解释

### 服务启动失败

#### 端口占用

端口号在文件`src/main/resources/application.properties`中由`server.port`配置 出现端口占用情况，可以运行指令`lsof -i:port`查看占用情况，可以的话直接运行`kill`
杀掉目标进程即可.

**不要不要不要改端口**

## 工程解读

### Spring和Spring boot简介

#### 什么是Spring？

Spring是一个由Java开发的后端框架

随着Spring的发展，逐渐形成一个庞大的体系。包括但不限于Spring boot（对Spring的精简和封装） ，Spring cloud（分布式开发）等等。在Spring官网或者使用IDEA建立Spring项目时，
会显示出可以选择的组件

后端框架的产生，大大简化了代码的编写量。一个后端框架通常包含类管理、数据库操作的封装、日志 、接口的封装之类的功能

Spring在开发时一般会基于MVC或者MVVC结构

#### Maven

Maven是一个管理和构建工具

在实际开发时，我们常常会调用大量依赖关系，配置JDK版本和文件路径。 在Maven和Gradle等构建工具出现之前，这些操作都是非常繁琐的， 在不同环境下常常会出现混乱。 构建工具的出现使得项目能够在不同操作系统、IDE下实现相同的配置，
比如一次性完成第三方以来的安装和调用、JDK的选择和文件目录的配置

#### IoC容器

Inversion of Control，控制反转

举个例子，对于多个Controller来讲，我们可能会反复生成某个工具对象
这样会带来内存和代码量的浪费。
一个比较简单的思路就是把要用到的对象扔到代码外面，直接拿现成的对象

IoC可以简单的理解为这种操作

Spring拖过注释，可以将这个操作交给框架实现，再通过`@autowired`注释
注入获得的对象

#### AOP



### 后端结构简介

#### controllers、models和repository

这一部分的设计严格基于MVC模型设计，开发一个功能时，首先在mdoels里设计一个javaBean实体类，比如Student

然后在repository里设计对应的操作接口，实现具体的SQL语句

最后在controllers里，定义相应的api和操作

#### api的对应关系

通过观察，我们首先列出如下表格(api首先定义`path=api/teach`)

| uims.yml         | api                    |
|------------------|------------------------|
| ～/student        | path/studentInit       |
| ~/student/query  | path/studentQuery      |
| ~/student/delete | path/studentDelete     |
| ~/student/edit   | path/studentEdit       |
| ~/studentEdit    | path/studentEditInit   |
| ~/studentEdit/?  | path/studentEditSubmit |

可以发现，api由前端描述自动生成

其基本原理就是对page下的name，name下的opers操作根据驼峰命名法进行组合

特别的，一些type的page会自带query、submit等操作，也会自动生成相应的操作

#### controller语法简介

### 前端页面的生成

#### uims.yml前端配置文件

文件位于`src/main/resources/uims.yml`

服务启动时会将这个文件，包括ajax、form等传到前端生成页面

#### 封装后的yaml语言

封装后提供了如下几种标签