const container = document.getElementById('container');
const overlayCon = document.getElementById('overlayCon');
const overlayBtn = document.getElementById('overlayBtn');

overlayBtn.addEventListener(
    'click',()=>{
        container.classList.toggle('righ-panel-active'); // TOGLE se la lista è presente lòa rimuove se no la toglie
    });
