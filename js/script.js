document.addEventListener('DOMContentLoaded', () => {
    loadInstrumentos();  // Carrega os instrumentos ao carregar a página
    document.getElementById('selectInstrumento').addEventListener('change', mostrarDetalhes);
});

// Função para carregar os instrumentos cadastrados
async function loadInstrumentos() {
    try {
        const response = await fetch('http://127.0.0.1:8080/instrumentos');
        const instrumentos = await response.json();

        const select = document.getElementById('selectInstrumento');
        instrumentos.forEach(instrumento => {
            const option = document.createElement('option');
            option.value = instrumento.idInstrumento;
            option.textContent = instrumento.nomeInstrumento;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar instrumentos:", error);
    }
}

// Função para mostrar os detalhes do instrumento selecionado
async function mostrarDetalhes(event) {
    const instrumentoId = event.target.value;

    // Limpa as informações caso nenhum instrumento seja selecionado
    if (!instrumentoId) {
        document.getElementById('detalhes').style.display = 'none';
        document.getElementById('fotoProfessorImg').src = '';
        document.getElementById('tempo').textContent = '';
        document.getElementById('fotoInstrumentoImg').src = '';
        return;
    }

    try {
        const response = await fetch(`http://127.0.0.1:8080/instrumentos/${instrumentoId}`);
        const instrumento = await response.json();

        // Exibe os detalhes
        document.getElementById('detalhes').style.display = 'block';
        document.getElementById('fotoProfessorImg').src = instrumento.oficineiro.fotoOficineiro;
        document.getElementById('tempo').textContent = instrumento.tempoAprendizado + ' meses';
        document.getElementById('fotoInstrumentoImg').src = instrumento.fotoInstrumento;
    } catch (error) {
        console.error("Erro ao carregar detalhes do instrumento:", error);
    }
}


