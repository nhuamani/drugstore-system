
document.addEventListener('DOMContentLoaded', () => {

    const searchInput = document.getElementById('categorySearch');
    const categoryRows = document.getElementById('categoryRows');

    if (!searchInput || !categoryRows) return;

    let debounceTimer;
    let abortController;

    async function searchCategories(term) {

        if (abortController) {
            abortController.abort();
        }

        abortController = new AbortController();

        try {
            const response = await fetch(
                `/categories/search?term=${encodeURIComponent(term)}`,
                {
                    signal: abortController.signal
                }
            );

            if (!response.ok) {
                throw new Error('Error al buscar categorías');
            }

            const html = await response.text();

            // Obtener únicamente el tbody de la respuesta
            const template = document.createElement('template');
            template.innerHTML = html.trim();

            const newTbody = template.content.querySelector('#categoryRows');

            if (!newTbody) {
                throw new Error('No se encontró el tbody categoryRows');
            }

            // Reemplazar únicamente las filas
            categoryRows.innerHTML = newTbody.innerHTML;

        } catch (error) {

            if (error.name !== 'AbortError') {
                console.error(error);
            }
        }
    }

    searchInput.addEventListener('input', (event) => {

        clearTimeout(debounceTimer);

        debounceTimer = setTimeout(() => {
            searchCategories(event.target.value.trim());
        }, 300);

    });

});
