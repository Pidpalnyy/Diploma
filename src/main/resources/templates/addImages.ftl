<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/add.css">
</head>
<body>
<div class="container">
    <div class="mainMenu">
        <div><a href="/addCategory">Категории меню</a></div>
        <div><a href="/addProduct">Меню</a></div>
        <div><a href="/addImages">Галерея</a></div>
    </div>
    <#if error??><div class="error">${error}</div></#if>
    <div class="block">
        <div class="add">
            <h2>Галерея</h2>
            <form action="/api/image/addImages" method="post" enctype="multipart/form-data">

                <label for="image">Фото:</label>
                <input type="file" id="image" name="image">

                <#--<input type="hidden"-->
                       <#--name="${_csrf.parameterName}"-->
                       <#--value="${_csrf.token}"/>-->

                <input type="submit" class="submit" value="Загрузить">
            </form>
        </div>
    </div>
</div>


</body>
</html>