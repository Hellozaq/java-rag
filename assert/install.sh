安装es脚本
# 看一下端口占用情况
netstat -tuln
# 看一下内存情况,再决定 ES 内存分配情况
free -h
# 设置 虚拟内存区域的最大映射计数
sysctl -w vm.max_map_count=262144
#  创建 docker network
docker network create elastic
#  拉 ES
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# 运行 ES
docker run --name es01 --net elastic -p 9200:9200 -it -m 2GB docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# 重置 password and enrollment token
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
# 进入ES 环境
docker exec -it es01 /bin/bash
# 安装ik中文分词器
bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/8.11.4
# 重启 ES配置生效
docker restart es01
# 拉取 kibana
docker pull docker.elastic.co/kibana/kibana:8.11.4
# 运行 kibana
docker run --name kib01 --net elastic -p 5601:5601 docker.elastic.co/kibana/kibana:8.11.4


安装mysql 脚本
sudo docker run --name some-mysql \
  -e MYSQL_ROOT_PASSWORD=my-secret-pw \
  -p 3306:3306 \
  -d mysql/mysql-server

sudo docker exec -it some-mysql /bin/bash
mysql -u root -p
CREATE DATABASE pkb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'some-mysql'@'%' IDENTIFIED WITH mysql_native_password BY 'my-secret-pw';
GRANT ALL PRIVILEGES ON *.* TO 'some-mysql'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;


安装minio脚本
mkdir -p ~/minio/data
docker run \ -p 9000:9000 \ -p 9090:9090 \ --name minio \ -v ~/minio/data:/data \ -e "MINIO_ROOT_USER=ROOTNAME" \ -e "MINIO_ROOT_PASSWORD=CHANGEME123" \ quay.io/minio/minio server /data --console-address ":9090"