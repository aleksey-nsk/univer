# Info

- Тестовое задание **Университет**. Подробное условие задачи в файле files/**task.pdf**.

- Бэкенд реализован в виде **Spring Boot REST API**.

- В dev-профиле используется БД **PostgreSQL** в контейнере **Docker**. Настройки контейнера указываем
в файле docker/dev/**docker-compose.yaml**. Настройки подключения к БД прописываем
в файле src/main/resources/**application-dev.yaml**.

- Для тестирования используем **in-memory базу данных H2**. Настройки test-профиля прописываем
в файле src/test/resources/**application-test.yaml**. Далее над всеми тестовыми классами пишем
аннотацию **@ActiveProfiles("test")** для активации тестового профиля.  
Тесты (**интеграционные** и **unit**) создаём в директории **src/test/java**.
  
- Для миграций используем **Liquibase**. В том числе добавляем **миграцию** создающую **индексы**.

- Документацию к API генерируем с помощью **Swagger**. Для просмотра документации открыть адрес:
    - в dev-профиле: http://localhost:8084/swagger-ui/index.html
    - в проде: http://localhost:8086/swagger-ui/index.html

- Реализована валидация данных с помощью **spring-boot-starter-validation**.

- Фронтенд реализован на **AngularJS**. Структура фронтенда:  
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/00_front_struc.png)  
  
- Запущенное приложение в dev-профиле доступно по адресу http://localhost:8084/:  
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/01_all_groups.png)    
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/02_one_group.png)  

- Развернул приложение в проде с использованием **Docker-контейнеров**. В итоге поднял  
3 контейнера (БД, Бэкенд, Фронтенд). Все необходимые файлы находятся в папке **docker**:  
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/03_1_docker_struc.png)  

- Файл **build_and_push.sh** в корне проекта имеет вид:  
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/03_2_build.png)  
  
# Как запустить приложение

1. Файл для запуска docker/prod/**docker-compose.yaml** выглядит так:  
![](https://github.com/aleksey-nsk/univer/blob/master/screenshots/04_prod.png)  

2. Возьмите машину, на которой установлены **Docker** и утилита **docker-compose**. Скопируйте на эту
машину файл docker/prod/**docker-compose.yaml**. Далее откройте в терминале папку с этим файлом и
выполните команду `docker-compose up --build`. После этого скачаются нужные образы, создадутся контейнеры,
и затем приложение будет запущено.

3. Приложение доступно в браузере по адресу: http://localhost:8080/

4. Документация к API доступна по адресу: http://localhost:8086/swagger-ui/index.html

5. Чтобы остановить приложение, нажмите в консоли `Ctrl + C`. Контейнеры будут остановлены.

6. Чтобы **удалить контейнеры**, выполните команду `docker-compose down`. В результате контейнеры будут удалены.

7. Если нужно **удалить контейнеры, тома и образы**, выполните   
   команду `docker-compose down --rmi all --volume`
