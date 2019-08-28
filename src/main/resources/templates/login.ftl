<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container">
    <div class="block">
        <div class="register">
            <h2>Авторизация</h2>
            <form action="/login" method="post">
                <label for="username">Имя пользователя:</label>
                <input type="text" id="username" name="username"/>
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password"/>
                <button type="submit" class="submit">Войти в систему</button>
            </form>
        </div>
        <div class="login">Плохой пароль? <a href="/updatePassword">Новый пароль</a></div>
    </div>
</div>
</body>
</html>