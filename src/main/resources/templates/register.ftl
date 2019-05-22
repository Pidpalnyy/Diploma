<!doctype html>
<html lang="en">
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
            <h2>Registration</h2>
            <form action="/register" method="post"/>
            <label for="name">Username:</label>
            <input type="text" name="name" id="name"/>
            <label for="name">Password:</label>
            <input type="password" id="pass" name="pass">
            <label for="name">Repeat password:</label>
            <input type="password" id="pass2" name="pass2">
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <input type="submit" class="submit" value="Sign Up">
            </form>
        </div>
        <div class="login">Already registered? <a href="/login">Log in</a></div>
    </div>
</div>
</body>
</html>