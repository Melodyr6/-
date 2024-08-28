# 使用官方 Node.js 镜像作为基础镜像
FROM node:alpine

# 设置工作目录
WORKDIR /usr/src/app

# 将当前目录的内容复制到容器中的 /usr/src/app
COPY build /usr/src/app/build

# 暴露端口
EXPOSE 80

# 使用 Nginx 或其他轻量级服务器来提供静态文件
# 这里我们使用简单的 HTTP 服务器 `nginx` 作为示例
RUN apk add --no-cache nginx && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# 启动命令
CMD ["nginx", "-g", "daemon off;"]