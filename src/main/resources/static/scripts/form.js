//value
const inputs= document.querySelectorAll('fieldset .nome')
const UpperLowerNumber_ = /^[A-Za-z0-9_]+$/;



const isValidInput = (value) => UpperLowerNumber_.test(value) && value.length <= 10 && value.length>=3;

const controlloNomi = (element) => {
    if (isValidInput(element.value)) {
        element.style.backgroundColor = "#DAF7A6"; // Reset colore se valido
       
    }else{
        element.removeAttribute("style"); /**Toglie gli stili */
          
    }
    
    
};


// <p class="invia"><input id="inviaInput" type="submit" value="Crea Annuncio!!"></p>
const submit= ()=>{
   
    //let nCorrette = Array.from(inputs).filter(input => isValidInput(input.value)).length;
    let nCorrette=0;
    for (let i = 0; i < inputs.length; i++) {
        if (isValidInput(inputs[i].value)) {
            nCorrette++;
        }
    }

   
    
    
    let ps=document.getElementsByClassName('invia');
    let submitBot=document.getElementById('inviaInput');
    if(nCorrette===inputs.length ){

        if(!submitBot){
            // Creazione del pulsante <input type="submit">
            let input = document.createElement("input");
            input.id = "inviaInput"; // Imposta l'ID
            input.type = "submit"; // Tipo di input
            input.value = "Crea Annuncio!!"; // Testo del pulsante

            // Aggiungere il pulsante dentro il <p>
            ps[0].appendChild(input);

        }
        

    }else if (submitBot)submitBot.remove();
        
};

inputs.forEach(element => {
    element.addEventListener('keyup',()=>{
        controlloNomi(element);
        submit();


    });
    
});