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
    <div class="block">
        <div class="add">
            <h2>Добавить меню</h2>
            <form action="/category/addCategory" method="post">

                <label for="category">Категория:</label>
                <input type="text" id="category" name="name"/>

                <label for="menu">Меню:</label>
                <select name="menu" id="menu">
                        <option value="menu">Меню</option>
                        <option value="childrensMenu">Детское меню</option>
                </select>

                <#--<input type="hidden"-->
                       <#--name="${_csrf.parameterName}"-->
                       <#--value="${_csrf.token}"/>-->

                <input type="submit" class="submit" value="Добавить">
            </form>
        </div>
    </div>
</div>


</body>
</html>