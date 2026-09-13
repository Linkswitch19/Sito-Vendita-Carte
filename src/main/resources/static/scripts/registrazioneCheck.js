
const inputsContainer= document.querySelectorAll('#registrazioneID .infield'); // annidati
const reg=document.querySelector('#registrazioneID') ;
const valoreMaxCorrete=5;
const inputsContainerLogin= document.querySelectorAll('#loginID .infield');


/**
 * const validazioniMap = new Map([ // mappa invece di uno switch
    [0, value => /^[A-Za-z]+$/.test(value)],
    [2, value => /^[A-Za-z0-9_]+$/.test(value)],
    [3 ,value=> /^[A-Za-z0-9_]+$/.test(value) && value.length >=8]
   
  ]);
 * 
 */
//per non riutilizzare
const validazioniMap = new Map();

validazioniMap.set(0, value => /^[A-Za-z]+$/.test(value));
validazioniMap.set(2, value => /^[A-Za-z0-9_]+$/.test(value));
validazioniMap.set(3, value => validazioniMap.get(2)(value) && value.length >= 8 && value.length<15);
validazioniMap.set(4, value => validazioniMap.get(3)(value) && controlloPassword() );


const controlloPassword=()=>{
    return document.querySelectorAll('input.password')[1].value===document.querySelectorAll('input.password')[0].value ;

};

const creaCheck= padreCheck=>{
    const check =document.createElement("p");
    check.classList.add("check");
    padreCheck.appendChild(check);
        
};
// <input type="submit" value="Conferma" class="bottoneSubmit">
const creaSubmit= ()=>{
    const nCoretti=document.querySelectorAll('p.check.giusto').length;
    const bottone =document.querySelector('#registrazioneSub');
    if(nCoretti>=valoreMaxCorrete && bottone===null && controlloPassword()){
        const input =document.createElement("input");
        input.type="submit";
        input.value="conferma";
        input.id="registrazioneSub";
        input.classList.add("bottoneSubmit");
    
        reg.appendChild(input);

    }else if(bottone){
       
        bottone.remove();
        if(!controlloPassword()){ 
            controlli(document.querySelectorAll('input.password')[1],4);
            

        }

    }
    

}



const cambioCheck= (element,check,index,login) =>{
    const isValid=(validazioniMap.get(index) || validazioniMap.get(0));
    if (isValid(element.value)) {
        check.textContent="✅"
        check.classList.add("giusto");
        
       
    }else if(element.value===""){
        check.textContent="";
        check.classList.remove("giusto");
    }
    else{
        check.classList.remove("giusto");
        check.textContent="❌";
        
    }
    if (!login) creaSubmit();

};



const controlli = (element,index,login=false) => {
    console.log(index);
    let arrayDaUsare;
    if (!login) arrayDaUsare=inputsContainer;
    else arrayDaUsare=inputsContainerLogin;
    
    const check =arrayDaUsare[index].querySelector("p.check");

    if(check===null) {
        creaCheck(arrayDaUsare[index]);

        controlli(element,index);
    }
    else{
        cambioCheck(element,check,login ? index+2: index,login);
    }
    
    
    
};

//invece di fare gli even listener separati cosi tutto in uno
inputsContainer.forEach((element,index)=>{ //per ogni elemento che ha infield
    const inputPresente= element.querySelector("input"); //prendo il suo input
    inputPresente.addEventListener('keyup',()=>{
        controlli(inputPresente,index);//faccio il controllo

    });
});

inputsContainerLogin.forEach( (element, index)=>{
    const inputPresente=element.querySelector("input");
    inputPresente.addEventListener('keyup',()=>{ // solo eventi in ()

         controlli(inputPresente,index,true);
    });

});

