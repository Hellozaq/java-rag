### 安装 Elasticsearch（ES）教程

#### 1. 环境检查
在安装 Elasticsearch 之前，需要对系统环境进行一些检查，确保安装过程顺利进行。
- **查看端口占用情况**：
  端口占用情况会影响 Elasticsearch 服务的正常启动。可以使用以下命令查看当前系统的端口占用情况：
    ```bash
    netstat -tuln
    ```
  此命令将显示所有监听的 TCP 和 UDP 端口，你需要确保 9200 端口未被占用，因为 Elasticsearch 默认使用该端口提供服务。
- **查看内存情况**：
  Elasticsearch 对内存要求较高，因此需要根据系统内存情况合理分配 ES 的内存。使用以下命令查看系统内存信息：
    ```bash
    free -h
    ```
  根据输出结果，你可以了解系统的可用内存，以便后续为 ES 分配合适的内存。

#### 2. 设置虚拟内存区域的最大映射计数
Elasticsearch 在运行时需要足够的虚拟内存映射，因此需要设置虚拟内存区域的最大映射计数。执行以下命令：
```bash
sysctl -w vm.max_map_count=262144
```
该命令将 `vm.max_map_count` 设置为 262144，以满足 Elasticsearch 的运行需求。

#### 3. 创建 Docker 网络
为了让 Elasticsearch 和 Kibana 能够相互通信，需要创建一个 Docker 网络。使用以下命令创建名为 `elastic` 的 Docker 网络：
```bash
docker network create elastic
```

#### 4. 拉取 Elasticsearch 镜像
使用 Docker 拉取 Elasticsearch 8.11.4 版本的镜像，执行以下命令：
```bash
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.11.4
```
此命令将从 Docker Hub 下载 Elasticsearch 8.11.4 版本的镜像到本地。

#### 5. 运行 Elasticsearch 容器
下载完成镜像后，使用以下命令运行 Elasticsearch 容器：
```bash
docker run --name es01 --net elastic -p 9200:9200 -it -m 2GB docker.elastic.co/elasticsearch/elasticsearch:8.11.4
```
参数说明：
- `--name es01`：为容器指定名称为 `es01`。
- `--net elastic`：将容器加入到之前创建的 `elastic` 网络中。
- `-p 9200:9200`：将容器的 9200 端口映射到主机的 9200 端口。
- `-it`：以交互模式运行容器。
- `-m 2GB`：为容器分配 2GB 的内存。

#### 6. 重置密码和生成 Kibana 注册令牌
容器启动后，需要重置 Elasticsearch 的默认密码，并生成 Kibana 的注册令牌。执行以下两个命令：
```bash
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
```
第一个命令用于重置 `elastic` 用户的密码，第二个命令用于生成 Kibana 的注册令牌。

#### 7. 进入 Elasticsearch 容器环境
如果需要在容器内执行一些操作，可以使用以下命令进入 Elasticsearch 容器的环境：
```bash
docker exec -it es01 /bin/bash
```

#### 8. 安装 IK 中文分词器
为了支持中文分词，需要安装 IK 中文分词器。在容器内执行以下命令：
```bash
bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/8.11.4
```

#### 9. 重启 Elasticsearch 容器
安装完分词器后，需要重启 Elasticsearch 容器使配置生效，执行以下命令：
```bash
docker restart es01
```

#### 10. 拉取和运行 Kibana
Kibana 是 Elasticsearch 的可视化工具，方便对 Elasticsearch 进行管理和查询。
- **拉取 Kibana 镜像**：
    ```bash
    docker pull docker.elastic.co/kibana/kibana:8.11.4
    ```
- **运行 Kibana 容器**：
    ```bash
    docker run --name kib01 --net elastic -p 5601:5601 docker.elastic.co/kibana/kibana:8.11.4
    ```
  参数说明：
    - `--name kib01`：为容器指定名称为 `kib01`。
    - `--net elastic`：将容器加入到 `elastic` 网络中。
    - `-p 5601:5601`：将容器的 5601 端口映射到主机的 5601 端口，通过浏览器访问 `http://localhost:5601` 即可打开 Kibana 界面。

### 安装 MySQL 教程

#### 1. 运行 MySQL 容器
使用 Docker 运行 MySQL 容器，执行以下命令：
```bash
sudo docker run --name some-mysql \
  -e MYSQL_ROOT_PASSWORD=my-secret-pw \
  -p 3306:3306 \
  -d mysql/mysql-server
```
参数说明：
- `--name some-mysql`：为容器指定名称为 `some-mysql`。
- `-e MYSQL_ROOT_PASSWORD=my-secret-pw`：设置 MySQL 的 `root` 用户密码为 `my-secret-pw`。
- `-p 3306:3306`：将容器的 3306 端口映射到主机的 3306 端口。
- `-d`：以守护进程模式运行容器。

#### 2. 进入 MySQL 容器环境
使用以下命令进入 MySQL 容器的环境：
```bash
sudo docker exec -it some-mysql /bin/bash
```

#### 3. 登录 MySQL 并创建数据库和用户
在容器内执行以下命令登录 MySQL：
```bash
mysql -u root -p
```
输入之前设置的 `root` 用户密码后，即可登录 MySQL。登录成功后，执行以下 SQL 语句创建数据库和用户：
```sql
CREATE DATABASE pkb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'some-mysql'@'%' IDENTIFIED WITH mysql_native_password BY 'my-secret-pw';
GRANT ALL PRIVILEGES ON *.* TO 'some-mysql'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```
- `CREATE DATABASE pkb ...`：创建名为 `pkb` 的数据库，使用 `utf8mb4` 字符集和 `utf8mb4_unicode_ci` 排序规则。
- `CREATE USER ...`：创建名为 `some-mysql` 的用户，允许从任何主机连接，密码为 `my-secret-pw`。
- `GRANT ALL PRIVILEGES ...`：授予 `some-mysql` 用户所有数据库和表的全部权限。
- `FLUSH PRIVILEGES`：刷新权限使设置生效。

### 安装 MinIO 教程

#### 1. 创建数据存储目录
为 MinIO 容器创建数据存储目录，执行以下命令：
```bash
mkdir -p ~/minio/data
```
该命令将在用户主目录下创建 `minio/data` 目录，用于存储 MinIO 的数据。

#### 2. 运行 MinIO 容器
使用以下命令运行 MinIO 容器：
```bash
docker run -p 9000:9000 -p 9090:9090 --name minio -v ~/minio/data:/data -e "MINIO_ROOT_USER=ROOTNAME" -e "MINIO_ROOT_PASSWORD=CHANGEME123" quay.io/minio/minio server /data --console-address ":9090"
```
参数说明：
- `-p 9000:9000`：将容器的 9000 端口映射到主机的 9000 端口，用于 MinIO 的 API 服务。
- `-p 9090:9090`：将容器的 9090 端口映射到主机的 9090 端口，用于 MinIO 的控制台服务。
- `--name minio`：为容器指定名称为 `minio`。
- `-v ~/minio/data:/data`：将主机的 `~/minio/data` 目录挂载到容器的 `/data` 目录，实现数据持久化。
- `-e "MINIO_ROOT_USER=ROOTNAME"`：设置 MinIO 的根用户名为 `ROOTNAME`。
- `-e "MINIO_ROOT_PASSWORD=CHANGEME123"`：设置 MinIO 的根用户密码为 `CHANGEME123`。
- `quay.io/minio/minio server /data --console-address ":9090"`：启动 MinIO 服务，指定数据存储目录为 `/data`，控制台地址为 `:9090`。

启动成功后，通过浏览器访问 `http://localhost:9090` 即可打开 MinIO 的控制台界面，使用设置的用户名和密码登录。 