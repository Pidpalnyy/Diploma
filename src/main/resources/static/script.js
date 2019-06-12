class Ajax {
    static get(url, onsuccess, onerror) {
        let request = new XMLHttpRequest();
        request.open("GET", url, true);
        request.onreadystatechange = function () {
            if (request.readyState !== 4) return;
            if (request.status === 200)
                onsuccess(request.responseText);
            else if (onerror)
                onerror(request.status, request.statusText);
        };
        request.send();
    }

    static post(url, params, onsuccess, onerror) {
        let request = new XMLHttpRequest();
        request.open("POST", url, true);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        request.onreadystatechange = function () {
            if (request.readyState !== 4) return;
            if (request.status === 200)
                onsuccess(request.responseText);
            else if (onerror)
                onerror(request.status, request.statusText);
        };
        let _params = [];
        for (let i in params) _params.push(i + "=" + encodeURIComponent(params[i]));
        request.send(_params.join("&"));
    }

    static postMultiPart(url, params, onsuccess, onerror) {
        let request = new XMLHttpRequest();
        request.open("POST", url, true);
        request.onreadystatechange = function () {
            if (request.readyState !== 4) return;
            if (request.status === 200)
                onsuccess(request.responseText);
            else if (onerror)
                onerror(request.status, request.statusText);
        };
        request.send(params);
    }
}

class Module {
    constructor(selector) {
        this.selector = selector;
    }

    get(selector) {
        return this.container.querySelector(selector);
    }

    init() {
        this.container = document.querySelector(this.selector);
        this.loadComponents();
        this.bindEvents();
        this.onCreate();
    }

    loadComponents() {
    }

    bindEvents() {
    }

    onCreate() {
    }
}

class Page {
    constructor() {
        this.modules = [];
    }

    registerModule(module) {
        this.modules.push(module);
    }

    init() {
        this.modules.forEach(m => m.init())
    }

    start() {
        window.addEventListener("load", e => this.init());
    }
}

class Animate{
   static animate(startvalue,endvalue,time,onframe,onend){
        const FRAME_TIME = 15;
        let frames = time/FRAME_TIME;
        let step = (endvalue-startvalue)/frames;

        let up = endvalue>startvalue;

        let inter = setInterval(function(){
            startvalue+=step;
            if((up&&startvalue>=endvalue)||(!up && startvalue<=endvalue)){
                clearInterval(inter);
                startvalue=endvalue;
            }
            onframe(startvalue);
            if(startvalue === endvalue && onend) onend();
        },FRAME_TIME);
    }
}

class ViewModule extends Module{
    constructor(selector) {
        super(selector);
        this.headerMenu="Кофе";
        this.paused = false;
        this.isAnimViewBlock = false;
    }
    loadComponents() {
        this.slideWrapperViewBlock = this.get(".sliderViewBlock-wrapper");
        this.leftArrowViewBlock=this.get(".sliderViewBlock-arrow-left");
        this.rightArrowViewBlock=this.get(".sliderViewBlock-arrow-right");
        this.nameCategoryForViewBlock=this.get(".nameCategoryForViewBlock");
        this.categoryMenu=this.get(".categoryMenu");
        this.viewMenu=this.get(".viewMenu");
    }
    bindEvents() {
        this.rightArrowViewBlock.addEventListener("click",()=>this.animateLeftViewBlock());
        this.leftArrowViewBlock.addEventListener("click",()=>this.animateRigthViewBlock());
        this.viewMenu.addEventListener("click", e => {
            if (e.target.matches('.categoryMenu')) {
                let categoryName = e.target;
                this.headerMenu=categoryName.dataset.name;
                this.updateProductPostList(this.headerMenu);
            }
        });
    }
    onCreate() {
        if(this.autoscrolltime>0) setInterval(()=>{
            if(this.paused) return;
            this.animateLeft();
        },this.autoscrolltime);
        this.products = [];
        this.updateProductPostList(this.headerMenu);
    }
    updateProductPostList(categoryName) {
        Ajax.post("/api/product/productPost", {categoryName: categoryName},
            resp => this.onLoadProducts(JSON.parse(resp).data)
        )
    }
    onLoadProducts(products) {
        this.products = products;
        this.updateView();
    }
    updateView() {
        this.slideWrapperViewBlock.innerHTML = "";
        this.nameCategoryForViewBlock.innerHTML = this.headerMenu;
        this.products.forEach(product => {
            let productBlock = document.createElement("div");
            productBlock.className = "productBlock";
            productBlock.dataset.id = product.id;

            let productImg = document.createElement("img");
            productImg.className = "productImg";
            productImg.setAttribute('src', '/images/'+product.page);
            // productImg.style.backgroundImage = "url('/images/'+product.page)";
            productBlock.appendChild(productImg);

            let productName = document.createElement("div");
            productName.className = "productName";
            productName.innerHTML = product.name;
            productBlock.appendChild(productName);

            let productText = document.createElement("div");
            productText.className = "productText";
            productText.innerHTML = product.text;
            productBlock.appendChild(productText);

            let productQuantity = document.createElement("div");
            productQuantity.className = "productQuantity";
            productQuantity.innerHTML = product.quantity;
            productBlock.appendChild(productQuantity);

            let productPrice = document.createElement("div");
            productPrice.className = "productPrice";
            productPrice.innerHTML = product.price;
            productBlock.appendChild(productPrice);

            let delbtn = document.createElement("button");
            delbtn.className = "del";
            delbtn.dataset.id = product.id;
            delbtn.innerHTML = "X";
            productBlock.appendChild(delbtn);

            this.slideWrapperViewBlock.appendChild(productBlock);
        });
    }
    animateLeftViewBlock(onend){
        if(this.isAnimViewBlock) return;
        this.isAnimViewBlock = true;
        Animate.animate(0,-100,1000,(value)=> {
            this.slideWrapperViewBlock.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapperViewBlock.appendChild(this.slideWrapperViewBlock.firstElementChild);
            this.slideWrapperViewBlock.appendChild(this.slideWrapperViewBlock.firstElementChild);
            this.slideWrapperViewBlock.style.marginLeft = "";
            if(onend) onend();
            this.isAnimViewBlock = false;
        })
    }
    animateRigthViewBlock(onend){
        if(this.isAnimViewBlock) return;
        this.isAnimViewBlock = true;
        this.slideWrapperViewBlock.style.marginLeft = "-100%";
        this.slideWrapperViewBlock.insertBefore(this.slideWrapperViewBlock.lastElementChild,this.slideWrapperViewBlock.firstElementChild);
        this.slideWrapperViewBlock.insertBefore(this.slideWrapperViewBlock.lastElementChild,this.slideWrapperViewBlock.firstElementChild);
        Animate.animate(-100,0,1000,(value)=> {
            this.slideWrapperViewBlock.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapperViewBlock.style.marginLeft = "";
            if(onend) onend();
            this.isAnimViewBlock = false;
        })
    }
}

class SliderModule extends Module{
    constructor(selector,autostop=true,right=false) {
        super(selector);
        // this.autoscrolltime = autoscrolltime;
        // this.right = right;
        this.autostop = autostop;
        this.paused = false;
        this.isAnim = false;
        // this.isAnimViewBlock = false;
        // this.timerId=null;
    }
    loadComponents() {
        this.slideWrapper = this.get(".slider-wrapper");
        this.leftArrow=this.get(".slider-arrow-left");
        this.rightArrow=this.get(".slider-arrow-right");
    }
    bindEvents() {
        this.rightArrow.addEventListener("click",()=>this.animateLeft());
        this.leftArrow.addEventListener("click",()=>this.animateRigth());
        this.slideWrapper.addEventListener("mouseenter",()=>this.paused = this.autostop);
        this.slideWrapper.addEventListener("mouseleave",()=>this.paused = false);
    }
    onCreate() {
        if(this.autoscrolltime>0) setInterval(()=>{
            if(this.paused) return;
            this.animateLeft();
        },this.autoscrolltime);
    }
    animateLeft(onend){
        if(this.isAnim) return;
        this.isAnim = true;
        Animate.animate(0,-100,1000,(value)=> {
            this.slideWrapper.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapper.appendChild(this.slideWrapper.firstElementChild);
            this.slideWrapper.style.marginLeft = "";
            if(onend) onend();
            this.isAnim = false;
        })
    }
    animateRigth(onend){
        if(this.isAnim) return;
        this.isAnim = true;
        this.slideWrapper.style.marginLeft = "-100%";
        this.slideWrapper.insertBefore(this.slideWrapper.lastElementChild,this.slideWrapper.firstElementChild);
        Animate.animate(-100,0,1000,(value)=> {
            this.slideWrapper.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapper.style.marginLeft = "";
            if(onend) onend();
            this.isAnim = false;
        })
    }
}

let page = new Page();
let viewModule = new ViewModule(".midlle");
let sliderModule = new SliderModule(".gallery");

page.registerModule(viewModule);
page.registerModule(sliderModule);

page.start();


document.addEventListener("DOMContentLoaded",function () {
    $(document).ready(function () {
        let cont = $('.container').height()+$('.top-menu-wrapper').height();
        console.log(cont);
        let flag = true;
        $(window).scroll(function () {
            if ($(this).scrollTop() >= cont && flag) {
                $('.top-menu-wrapper').addClass('top-menu-wrapper-fixed');
                // $('body').css("padding-top", "120px");
                flag = !flag;
            } else if ($(this).scrollTop() < cont && !flag) {
                flag = !flag;
                $('.top-menu-wrapper').removeClass('top-menu-wrapper-fixed');
                // $('body').css("padding-top", "0px");
            }
        }).trigger("scroll");

        $("a[href*=#]").on("click", function (event) {
            event.preventDefault();
            let id = $(this).attr('href'),
                top = $(id).offset().top;
            $('body,html').animate({scrollTop: top}, 1000);
        });

        // cod dlya prokrutki k yakory-------------------------------------------------------------------------
        // $("a[href*=#]").on("click", function (e) {
        //     e.preventDefault();
        //     let anchor = $(this);
        //     let oft = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset;
        //
        //     $('html, body').stop().animate({
        //         scrollTop: $(anchor.attr('href')).offset().top + oft
        //     }, 777);
        //
        //     return false;
        // });
        // -------------------------------------------------------------------------------------------------------

    });
});

