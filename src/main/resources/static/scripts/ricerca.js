/*=============== SEARCH BAR JS ===============*/
const toggleSearch = (search, button) =>{
    const searchBar = document.getElementById(search),
        searchButton = document.getElementById(button)

    searchButton.addEventListener('click', () =>{
        // We add the show-search class, so that the search bar expands
        searchBar.classList.toggle('show-search')
    })
}

// Funzione di debounce per evitare troppe richieste durante la digitazione
function debounce(fn, delay) {
    let timer = null;
    return function(...args) {  //posso togliere args perchè è sempre void
        clearTimeout(timer);
        timer = setTimeout(() => fn.apply(this, args), delay);
    };
}


// dato che il listener spariva una volta che sostituito ora non piu nel caso dovevo modificare il then risettando tutti i listner
function attachSearchListener() {


    const form = document.getElementById('search-bar');
    if (!form) return;

    const input = form.querySelector('input[name="query"]');
    if (!input) return;
    const query = new FormData(form).get('query');
    const performSearch = ()=>{

        const query = input.value.trim();
        if (query.length < 3) return; // invia solo se ci sono almeno 3 caratteri
        const url = `/card/search?query=${encodeURIComponent(query)}`;
        fetch(url, {
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.text();
            })
            .then(html => {


                const cardListContainer = document.getElementById('card-list-container');
                cardListContainer.innerHTML = html;


            })
            .catch(console.error);

    };// opzionale: evita più listener sovrapposti { once: true }
    // debounce opzionale: evita troppe richieste rapide
    input.addEventListener('keyup', debounce(performSearch, 300)); // function(event) de non usavo arrow function
    form.addEventListener('submit', (event)=> {
        event.preventDefault(); // impedisce l’invio del form
    });
}

// Attacca subito il listener all’avvio
document.addEventListener('DOMContentLoaded', () => {
    toggleSearch('search-bar', 'search-button');
    attachSearchListener();
});