## Автомаг
### Сайт по продаже машин

Сайт по продаже машин.

Есть фильтр в виде регистрации пользователей.

Обьявления можно добавлять, редактировать, переводить с состояния продажи в состояние продан, удалять. 

Обьявлениях указывают марку машины,
год, описание, цену, владельца.

Взаимодествие производится по средствам WEB интерфейса. 

### Стек технологий:

![java](https://img.shields.io/badge/java-17-red)![java](https://img.shields.io/badge/AssertJ-3.22.0-red)
![java](https://img.shields.io/badge/Slf4j-1.7.36-red)
![Spring boot](https://img.shields.io/badge/Spring%20boot-2.7.4-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-2.7.4-green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-4-ff69b4)
![Postgresql](https://img.shields.io/badge/Postgresql-14-blue)
![Hibernate](https://img.shields.io/badge/Checkstylee-9.0-orange)
![Liquibase](https://img.shields.io/badge/Liquibase-4.15.0-red)
![H2](https://img.shields.io/badge/H2-1.4.200-blue)![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
![H2](https://img.shields.io/badge/lombok-1.18.24-blue)![Maven](https://img.shields.io/badge/Hibernate-5.6.14-red)

### Требования к окружению :

- ![java](https://img.shields.io/badge/java-17+-red)
- ![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
- ![Postgresql](https://img.shields.io/badge/Postgresql-14-blue)

### Запуск приложения

Запуск с помощью командной строки:

1. Создать базу данных cars.

```CREATE DATABASE cars;```

2. Перейти в папку с проектом.
3. Выполнить команду: mvn liquibase:update
4. Выполнить команду: mvn clean install
5. Выполнить команду: mvn spring-boot:run
6. Перейти по ссылке: http://localhost:8080

### Виды

#### Вид входа
![главный вид](imgs/log.png)

#### Вид регистрации
![регистрации вид](imgs/reg.png)

#### Вид добавления поста
![добавления поста вид](imgs/addPost.png)

#### Вид поста
![главный вид](imgs/post.png)

#### Вид редактирования обьявления
![Редактирования вид](imgs/editCar.png)

#### Главный вид
![главный вид](imgs/allPosts.png)

#### Вид поиска по марке
![поиск по марке](imgs/brand.png)

#### Вид поиска с фото
![главный вид](imgs/withPhoto.png)

### Расширения приложения

Обновить дизайн входа, главного вида, поменять навигационную панель.

Добавить личный кабинет пользователя, фильтр для редактирования обьявлений - пользователями которые их добавляли,
выбор региона при регистрации пользователя, отображение времени по часовому поясу пользователя.

Добавить кузов, коробку передач, мощность двигателя, пробег. 

Реализовать подписки, историю изменения цен с выводом на экран, обновив фронт.

### Контакты: @WhiteVax