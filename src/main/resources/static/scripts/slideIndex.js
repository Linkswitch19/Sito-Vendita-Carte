const mainImg = document.querySelectorAll('img.card-image'); 
const wallpaper = document.querySelectorAll('img.background-image');
const navPallini=document.querySelectorAll('.slider-nav a');
const slideContainer=document.querySelector('div.SlideShow');

const tempo=1000;
let index = 0; /*globale */
const interval=10;

const cambiaImg = () => {
    
    mainImg.forEach(img => img.classList.remove('active'));
    wallpaper.forEach(wall => wall.classList.remove('active'));
    navPallini.forEach(pal => pal.classList.remove('active'));

    mainImg[index].classList.add('active');
    wallpaper[index].classList.add('active');
    navPallini[index].classList.add('active');
   
};

function cambioImgSlide(){
    index = (index + 1) % mainImg.length;
    cambiaImg();

}

let slideShow= setInterval(() => {
    cambioImgSlide(); 
    
}, interval*tempo);

navPallini.forEach((pallino, i) => {
    pallino.addEventListener('click', () => { 
        
        
        index = i; 
        cambiaImg(); 

    });
});



   
slideContainer.addEventListener('mouseleave', () => {
       slideShow=setInterval(() => {
        cambioImgSlide(); },interval*tempo);
});

slideContainer.addEventListener('mouseover', () => {
    clearInterval(slideShow);
});






