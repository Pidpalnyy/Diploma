<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="/script.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo"></div>
        <div class="title">Shu Shu</div>
        <div class="tagline">Ваша кофейня.</div>
        <div class="arrowBlock">
            <a href="#main">
                <div class="arrow"></div>
            </a>
        </div>
    </div>
</div>
<div class="top-menu-wrapper">
    <div class="top-menu" id="main">
        <a href="#menu">
            <div class="menu">Меню</div>
        </a>
        <div class="logo_1"></div>
        <a href="#gallery">
            <div class="galleryMenu">Галлерея</div>
        </a>
    </div>
</div>
<div class="midlle" id="menu">
    <div class="viewMenu">
        <h2>Меню</h2>
        <div class="mainMenu">
            <#list categoryMenu as ctgr>
                <div class="categoryMenu" data-name="${ctgr.getName()}">
                ${ctgr.getName()}
                <form action="/api/category/delCategory" method="post">
            <input type="hidden" name="id" value="${ctgr.getId()}">
                <input type="submit" class="del" value="X">
                </form>
                </div>
            </#list>
        </div>
        <h2>Детское меню</h2>
        <div class="childrenMenu">
            <#list categoryChildrensMenu as ctgr>
                <div class="categoryMenu" data-name="${ctgr.getName()}">
                ${ctgr.getName()}
                <form action="/api/category/delCategory" method="post">
            <input type="hidden" name="id" value="${ctgr.getId()}">
                <input type="submit" class="del" value="X">
                </form>
                </div>
            </#list>
        </div>
    </div>
    <div class="viewBlock">
        <div class="sliderViewBlock">
            <div class="sliderViewBlock-arrowBlock">
                <div class="nameCategoryForViewBlock"></div>
                <div class="sliderViewBlockForArrowBlock">
                    <div class="sliderViewBlock-arrow sliderViewBlock-arrow-left"></div>
                    <div class="sliderViewBlock-arrow sliderViewBlock-arrow-right"></div>
                </div>
            </div>
            <div class="sliderViewBlock-wrapper"></div>
        </div>


    </div>
</div>
<div class="gallery" id="gallery">
    <div class="slider myslider1">
        <div class="sliderViewBlock-arrowBlock">
            <div class="nameForGallery">Галлерея</div>
            <div class="sliderViewBlockForArrowBlock">
                <div class="slider-arrow slider-arrow-left"></div>
                <div class="slider-arrow slider-arrow-right"></div>
            </div>
        </div>

        <div class="slider-wrapper">
            <#list images as image>
                <div style="background-image: url('/images/${image.getImage()}') ;" class="imageDiv">
                <form action="/api/image/delImage" method="post">
            <input type="hidden" name="id" value="${image.getId()}">
                <input type="submit" class="del" value="X">
                </form>
                </div>
            </#list>
        </div>
    </div>
</div>
<div class="map">
    <figure>
        <iframe
                width="100%"
                height="100%"
                frameborder="0" style="border:0"
                src="https://www.google.com/maps/embed/v1/view?key=AIzaSyDnTm87fLLXlk4353j1ojiW-7VHIVcJOmg
			    &center=48.486194,34.9346461&zoom=18" allowfullscreen>
        </iframe>
    </figure>
</div>
<div class="bottom">
    <div class="address">
        <div>г.Днепр</div>
        <div>ул.Коробова 2к</div>
    </div>
    <div class="workingHours">
        <div>Часы работы:</div>
        <br>
        <div>Понедельник-Пятница</div>
        <div>с 7:00 до 22:00</div>
        <br>
        <div>Суббота-Воскресенье</div>
        <div>с 9:00 до 22:00</div>
    </div>
    <div class="phone">
        <div>Телефон:</div>
        <br>
        <div>+380509405168</div>
    </div>
    <div class="social">
        <a href="https://www.facebook.com/profile.php?id=100025931262654">
            <div class="fb"></div>
        </a>
        <a href="https://instagram.com/shushudnepr?utm_source=ig_profile_share&igshid=l8fuwlm1xp0u">
            <div class="inst"></div>
        </a>
    </div>
</div>
</body>
</html>