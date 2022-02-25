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
data:     添加新数据

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

## 需求分析与类关系设计

### 需求

- 学生基本信息、联系方式、入学前信息、家庭信息、社会关系等基本信息的管理
- 学习信息管理，包括课程基本信息，课程中心（教材、课件、参考资料等）选课信息、考勤信息、作业信息、成绩信息等
- 学生社会实践、学科竞赛、科技成果、培训讲座、创新项目、校外实习等创新实践信息管理
- 学生荣誉信息管理，包括获得的各种称号奖励等
- 学生体育活动、外出旅游、文艺演出、聚会、等日常活动管理。
- 学生外出请假信息和生活学习消费等日志信息管理
- 学生个人信息的统计汇入统计数据库
- 学生各种信息的查询统计、综合绩分的计算（可自行设计公式）和学生个人画像、个人简历的生成打印

## 部分运行时问题的解释

### 服务启动失败

#### 端口占用

端口号在文件`src/main/resources/application.properties`中由`server.port`配置 出现端口占用情况，可以运行指令`lsof -i:port`查看占用情况，可以的话直接运行`kill`
杀掉目标进程即可.

**不要不要不要改端口号**

## 工程解读

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

### 前端页面的生成

#### uims.yml前端配置文件

文件位于`src/main/resources/uims.yml`

服务启动时会将这个文件，包括ajax、form等传到前端生成页面

#### 封装后的yaml语言

封装后提供了如下几种标签