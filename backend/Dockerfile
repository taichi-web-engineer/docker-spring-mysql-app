# ステップ 1: ビルドステージ
FROM gradle:8.3.0-jdk17 AS build

# 作業ディレクトリを設定
WORKDIR /app

# 必要なファイルのみコピーしてDockerキャッシュを活用
COPY build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle

# 依存関係をダウンロード（Dockerキャッシュを活用）
RUN gradle build -x test --parallel --no-daemon || return 0

# 残りのソースコードをコピーしてアプリケーションをビルド
COPY src /app/src
RUN gradle build -x test --no-daemon

# ステップ 2: 実行ステージ
FROM eclipse-temurin:21-jdk

# アプリケーションを実行するユーザーを作成
RUN useradd -m -u 1001 spring

# 作業ディレクトリを設定
WORKDIR /app

# ビルドステージからビルド済みのアプリケーションをコピー
COPY --from=build /app/build/libs/*.jar app.jar

# アプリケーションファイルの所有権を変更
RUN chown -R spring:spring /app

# 作成したユーザーとしてアプリケーションを実行
USER spring

# アプリケーションを実行するコマンドを指定
ENTRYPOINT ["java", "-jar", "app.jar"]

# アプリケーションポートを公開
EXPOSE 8080
