document.addEventListener('DOMContentLoaded', () => {
    loadOficineiros(); // Carregar os oficineiros ao carregar a página
    document.getElementById('formOficineiro').addEventListener('submit', cadastrarOficineiro);
    document.getElementById('formInstrumento').addEventListener('submit', cadastrarInstrumento);
});

// Função para carregar os oficineiros no select
async function loadOficineiros() {
    try {
        const response = await fetch('http://127.0.0.1:8080/oficineiros');
        const oficineiros = await response.json();

        const select = document.getElementById('selectOficineiro');
        // Limpa o select antes de adicionar novos itens
        select.innerHTML = ''; 

        // Adiciona uma opção padrão
        const optionDefault = document.createElement('option');
        optionDefault.value = '';
        optionDefault.textContent = 'Selecione um professor';
        select.appendChild(optionDefault);

        // Preenche o select com os oficineiros
        oficineiros.forEach(oficineiro => {
            const option = document.createElement('option');
            option.value = oficineiro.idOficineiro;
            option.textContent = oficineiro.nomeOficineiro;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar oficineiros:", error);
    }
}

// Função para cadastrar o oficineiro
async function cadastrarOficineiro(event) {
    event.preventDefault();

    const nomeOficineiro = document.getElementById('nomeOficineiro').value;
    const idadeOficineiro = document.getElementById('idadeOficineiro').value;
    const tempoEmpresa = document.getElementById('tempoEmpresa').value;
    const fotoOficineiro = document.getElementById('fotoOficineiro').value;

    const oficineiro = {
        nomeOficineiro,
        idadeOficineiro,
        tempoEmpresa,
        fotoOficineiro
    };

    try {
        const response = await fetch('http://127.0.0.1:8080/oficineiros', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(oficineiro),
        });

        if (response.ok) {
            alert('Oficineiro cadastrado com sucesso!');
            loadOficineiros(); // Recarregar oficineiros após o cadastro
            document.getElementById('cadastroOficineiro').style.display = 'none';
            document.getElementById('cadastroInstrumento').style.display = 'block';
        } else {
            alert('Erro ao cadastrar oficineiro.');
        }
    } catch (error) {
        console.error("Erro ao cadastrar oficineiro:", error);
    }
}

// Função para cadastrar o Instrumento
async function cadastrarInstrumento(event) {
    event.preventDefault();

    const nomeInstrumento = document.getElementById('nomeInstrumento').value;
    const tempoAprendizado = document.getElementById('tempoAprendizado').value;
    const fotoInstrumento = document.getElementById('fotoInstrumento').value;
    const oficineiroId = document.getElementById('selectOficineiro').value;

    const instrumento = {
        nomeInstrumento,
        tempoAprendizado,
        fotoInstrumento,
        oficineiroId
    };

    try {
        const response = await fetch('http://127.0.0.1:8080/instrumentos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(instrumento),
        });

        if (response.ok) {
            alert('Instrumento cadastrado com sucesso!');
            document.getElementById('cadastroInstrumento').style.display = 'none';
            document.getElementById('cadastroOficineiro').style.display = 'block';
            document.getElementById('formOficineiro').reset();
            document.getElementById('formInstrumento').reset();
        } else {
            alert('Erro ao cadastrar instrumento.');
        }
    } catch (error) {
        console.error("Erro ao cadastrar instrumento:", error);
    }
}
