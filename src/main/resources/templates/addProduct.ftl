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
        <div><a href="/">Главная</a></div>
        <div><a href="/addCategory">Добавить категории меню</a></div>
        <div><a href="/addImages">Добавить фото в Галлерею</a></div>
    </div>
    <#if yes>
        <div class="add">
        <h2>Добавить продукт</h2>
        <form action="/api/product/addProduct" method="post" enctype="multipart/form-data">

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
        <div>
            <input class="inputQuantity_Price" type="text" id="quantity" name="quantity"/>
            <select class="selectQuantity_Price" name="selectQuantity">
                <option>г.</option>
                <option>мл.</option>
                <option>шт.</option>
                <option>ед.</option>
                <option>кг.</option>
                <option>мг.</option>
                <option>л.</option>
            </select>
        </div>

        <label for="price">Цена:</label>
        <div>
            <input class="inputQuantity_Price" type="text" id="price" name="price"/>
            <select class="selectQuantity_Price" name="selectPrice">
                <option>грн</option>
                <option>$</option>
                <option>руб</option>
            </select>
        </div>
        <input type="submit" class="submit" value="Добавить">
        </form>
        </div>
    </#if>
</div>
</body>
</html>