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
        this.modules.forEach(m => m.init());
    }

    start() {
        window.addEventListener("load", e => this.init());
    }
}

// class HeaderModule extends Module {
//     constructor(selector) {
//         super();
//         this.selector = selector;
//     }
//
//     loadComponents() {
//         // this.arrowBlock = this.get(".arrow");
//     }
//
//     bindEvents() {
//         // this.arrowBlock.addEventListener("click", e => {
//         //     alert("Yes");
//         // });
//     }
//
//     onCreate() {
//     }
//
// }

class ViewModule extends Module{

    constructor(selector,autoscrolltime=0,autostop=true,right=false) {
        super(selector);
        this.autoscrolltime = autoscrolltime;
        this.right = right;
        this.autostop = autostop;
        this.paused = false;
        this.isAnim = false;
        this.isAnimViewBlock = false;
        this.timerId=null;
    }
    /*Override*/
    loadComponents() {
        // this.arrowBlock = this.get(".arrow");
        this.slideWrapper = this.get(".slider-wrapper");
        this.leftArrow=this.get(".slider-arrow-left");
        this.rightArrow=this.get(".slider-arrow-right");
        this.slideWrapperViewBlock = this.get(".sliderViewBlock-wrapper");
        this.leftArrowViewBlock=this.get(".sliderViewBlock-arrow-left");
        this.rightArrowViewBlock=this.get(".sliderViewBlock-arrow-right");
        // this.leap=this.get(".id");
    }
    /*Override*/
    bindEvents() {
        this.rightArrowViewBlock.addEventListener("click",()=>this.animateLeftViewBlock());
        this.leftArrowViewBlock.addEventListener("click",()=>this.animateRigthViewBlock());
        this.rightArrow.addEventListener("click",()=>this.animateLeft());
        this.leftArrow.addEventListener("click",()=>this.animateRigth());
        this.slideWrapper.addEventListener("mouseenter",()=>this.paused = this.autostop);
        this.slideWrapper.addEventListener("mouseleave",()=>this.paused = false);

        // this.arrowBlock.addEventListener("click", e => {
        //     alert("Yes");
        // });

        // this.leap.addEventListener("click",(e)=> {
        //     console.log("Yes");
        //     if (!e.target.matches(".id")) {
        //         this.timerId = setInterval(()=>{
        //             if(this.slideWrapper.firstElementChild.classList.contains(e.target.className)){
        //                 clearInterval(this.timerId);
        //                 return;
        //             }
        //             this.animateLeap();
        //         },10);
        //     }
        // });
    }
    /*Override*/
    onCreate() {
        if(this.autoscrolltime>0) setInterval(()=>{
            if(this.paused) return;
            this.animateLeft();
        },this.autoscrolltime);

        // this.products = [];
        // this.updateProductsList();
    }

    updateProductsList() {
        Ajax.get("/api/product/all",
            resp => this.onLoadProducts(JSON.parse(resp).data)
        )
    }
    onLoadProducts(products) {
        this.products = products;
        this.updateView();

    }
    updateView() {
        // this.userlist.innerHTML = "";
        // this.users.forEach(user => {
        //     let item = document.createElement("div");
        //     item.className = "user";
        //     item.innerHTML = `<div class="data">${user.name}</div>`;
        //     item.dataset.id = user.id;
        //
        //     let delbtn = document.createElement("button");
        //     delbtn.className = "del";
        //     delbtn.dataset.id = user.id;
        //     delbtn.innerHTML = "del";
        //     item.appendChild(delbtn);
        //
        //     let morebtn = document.createElement("button");
        //     morebtn.className = "more";
        //     morebtn.dataset.id = user.id;
        //     morebtn.innerHTML = "more";
        //     item.appendChild(morebtn);
        //
        //     this.userlist.appendChild(item);
        // })
        console.log(this.products);
    }


    animateLeftViewBlock(onend){
        if(this.isAnimViewBlock) return;
        this.isAnimViewBlock = true;
        this.animate(0,-100,1000,(value)=> {
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
        this.animate(-100,0,1000,(value)=> {
            this.slideWrapperViewBlock.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapperViewBlock.style.marginLeft = "";
            if(onend) onend();
            this.isAnimViewBlock = false;
        })
    }

    animateLeft(onend){
        if(this.isAnim) return;
        this.isAnim = true;
        this.animate(0,-100,1000,(value)=> {
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
        this.animate(-100,0,1000,(value)=> {
            this.slideWrapper.style.marginLeft = value+"%";
        },()=>{
            this.slideWrapper.style.marginLeft = "";
            if(onend) onend();
            this.isAnim = false;
        })
    }
    // animateLeap(onend){
    //     if(this.isAnim) return;
    //     this.isAnim = true;
    //     this.animate(0,-100,100,(value)=> {
    //         this.slideWrapper.style.marginLeft = value+"%";
    //     },()=>{
    //         this.slideWrapper.appendChild(this.slideWrapper.firstElementChild);
    //         this.slideWrapper.style.marginLeft = "";
    //         if(onend) onend();
    //         this.isAnim = false;
    //     })
    // }
    animate(startvalue,endvalue,time,onframe,onend){
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

let page = new Page();

let viewModule = new ViewModule(".block",5000);

page.registerModule(viewModule);

page.start();

window.addEventListener("load",()=>page.init());


$(document).ready(function() {

    $("a[href*=#]").on("click", function (e) {
        e.preventDefault();
        let anchor = $(this);
        let oft = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset;

        $('html, body').stop().animate({
            scrollTop: $(anchor.attr('href')).offset().top + oft
        }, 777);

        return false;
    });
});

    // window.onscroll=function() {
    //     console.log("sdasd")
    //     let oft = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset;
    //     console.log(oft)
    //     // if(oft >= 120) {
    //     //     $('.top-menu').addClass('sticky-menu');
    //     // }
    //     // else{
    //     //     $('.top-menu').removeClass('sticky-menu');
    //     // }
    // };

// document.addEventListener("DOMContentLoaded",function () {
//     console.log("loaded");
//     window.addEventListener("scroll",function () {
//         console.log("scroll");
//
//     })
// })


// $(function(){
//     let flag = true;
//     $(window).scroll(function() {
//         if($(this).scrollTop() >= 200 && flag) {
//             $('.top-menu').addClass('sticky-menu');
//             $('body').css("padding-top", "70px");
//             flag = !flag;
//         }
//     else if($(this).scrollTop() < 200 && !flag){
//             flag = !flag;
//             $('.top-menu').removeClass('sticky-menu');
//             $('body').css("padding-top", "0px");
//         }
//     }).trigger("scroll");
// });
