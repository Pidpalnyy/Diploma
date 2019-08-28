<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/register.css">
</head>
<body>
<div class="container">
    <div class="block">
        <div class="register">
            <h2>Обновление пароля</h2>
            <form action="/updatePasswordPost" method="post"/>
            <label for="name">Введите действующий пароль:</label>
            <input type="password" id="oldPass" name="oldPass">
            <label for="name">Введите новый пароль:</label>
            <input type="password" id="newPass" name="newPass">
            <label for="name">Повторите новый пароль:</label>
            <input type="password" id="newPass2" name="newPass2">
            <input type="submit" class="submit" value="Обновить пароль">
            </form>
        </div>
        <div class="login">Пароль устраивает?<a href="/login">Авторизация</a></div>
    </div>
</div>
</body>
</html>