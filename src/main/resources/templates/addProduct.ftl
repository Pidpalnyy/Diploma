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
    <#if yes>
        <div class="add">

        <h2>Add Product</h2>
        <form action="/product/addProduct" method="post" enctype="multipart/form-data">

        <label for="category">Категория:</label>
        <select name="category" id="category">
        <#list categoryes as category>
            <option value="${category.name}">${category.name}</option>
        </#list>
        </select>

        <label for="page">Изображение:</label>
        <input type="file" id="page" name="page">

        <label for="username">Название:</label>
        <input type="text" id="name" name="name"/>

        <label for="text">Описание:</label>
        <input type="text" id="text" name="text"/>

        <label for="quantity">Колличество:</label>
        <input type="text" id="quantity" name="quantity"/>

        <label for="price">Цена:</label>
        <input type="text" id="price" name="price"/>

        <input type="hidden"
        name="${_csrf.parameterName}"
        value="${_csrf.token}"/>

        <input type="submit" class="submit" value="Загрузить">

        </form>

        </div>
    </#if>
</div>


</body>
</html>