<!doctype html>
<html lang="en">
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
            <h2>Log in</h2>
            <form action="/login" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username"/>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password"/>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button type="submit" class="submit">Log in</button>
            </form>
        </div>
        <div class="login">Have no account yet? <a href="/register">Sign up</a></div>
    </div>
</div>
</body>
</html>