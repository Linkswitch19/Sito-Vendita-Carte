const mainImg= document.querySelectorAll('.default .main-img img'); // seleziona le immagini
const thumbnails= document.querySelectorAll('.default .thumb-list div');


const cambiaImg = (index)=> {
    //rimuovi
    //poi aggiungi
    mainImg.forEach((img)=>{
        img.classList.remove('active')
    });

    thumbnails.forEach((thumb)=>{
        thumb.classList.remove('active')
    });
    mainImg[index].classList.add('active');
    thumbnails[index].classList.add('active');
}


thumbnails.forEach( (thumb,index)=> { //click listener
    thumb.addEventListener('click',()=>{
        cambiaImg(index)

    });

});