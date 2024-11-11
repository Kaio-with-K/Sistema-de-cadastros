document.addEventListener('DOMContentLoaded', () => {
    loadOficineiros(); // Carregar oficineiros ao carregar a página
    loadInstrumentos(); // Carregar instrumentos ao carregar a página

    // Verifique se o formulário de edição está presente no DOM antes de adicionar o evento
    const formEditOficineiro = document.getElementById('formEditOficineiro');
    const formEditInstrumento = document.getElementById('formEditInstrumento');

    if (formEditOficineiro) {
        formEditOficineiro.addEventListener('submit', updateOficineiro);
    }

    if (formEditInstrumento) {
        formEditInstrumento.addEventListener('submit', updateInstrumento);
    }
});


// Função para carregar oficineiros
async function loadOficineiros() {
    try {
        const response = await fetch('http://127.0.0.1:8080/oficineiros');
        const oficineiros = await response.json();

        const tableBody = document.querySelector('#oficineirosTable tbody');
        tableBody.innerHTML = ''; // Limpa o corpo da tabela antes de adicionar novos dados

        oficineiros.forEach(oficineiro => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${oficineiro.nomeOficineiro}</td>
                <td>${oficineiro.idadeOficineiro}</td>
                <td>${oficineiro.tempoEmpresa}</td>
                <td><img src="${oficineiro.fotoOficineiro}" alt="Foto do Professor" width="50"></td>
                <td>
                    <button onclick="editOficineiro('${oficineiro.idOficineiro}')">Atualizar</button>
                    <button onclick="deleteOficineiro('${oficineiro.idOficineiro}')">Excluir</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error("Erro ao carregar oficineiros:", error);
    }
}

// Função para carregar os Oficineiros no select do formulário de edição
async function loadOficineirosForEdit() {
    try {
        const response = await fetch('http://127.0.0.1:8080/oficineiros');
        const oficineiros = await response.json();

        const select = document.getElementById('editSelectOficineiro');
        select.innerHTML = ''; // Limpar opções anteriores

        // Adicionar o valor de cada oficineiro ao select
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

// Função para carregar instrumentos
async function loadInstrumentos() {
    try {
        const response = await fetch('http://127.0.0.1:8080/instrumentos');
        const instrumentos = await response.json();

        const tableBody = document.querySelector('#instrumentosTable tbody');
        tableBody.innerHTML = ''; // Limpa o corpo da tabela antes de adicionar novos dados

        instrumentos.forEach(instrumento => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${instrumento.nomeInstrumento}</td>
                <td>${instrumento.tempoAprendizado}</td>
                <td><img src="${instrumento.fotoInstrumento}" alt="Foto do Instrumento" width="50"></td>
                <td>${instrumento.oficineiro.nomeOficineiro}</td>
                <td>
                    <button onclick="editInstrumento('${instrumento.idInstrumento}')">Atualizar</button>
                    <button onclick="deleteInstrumento('${instrumento.idInstrumento}')">Excluir</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error("Erro ao carregar instrumentos:", error);
    }
}

// Função para abrir o formulário de edição de oficineiro
async function editOficineiro(idOficineiro) {
    try {
        const response = await fetch(`http://127.0.0.1:8080/oficineiros/${idOficineiro}`);
        const oficineiro = await response.json();

        // Preencher os campos do formulário com os dados do oficineiro
        document.getElementById('editNomeOficineiro').value = oficineiro.nomeOficineiro;
        document.getElementById('editIdadeOficineiro').value = oficineiro.idadeOficineiro;
        document.getElementById('editTempoEmpresa').value = oficineiro.tempoEmpresa;
        document.getElementById('editFotoOficineiro').value = oficineiro.fotoOficineiro;

        // Exibir o formulário de edição
        document.getElementById('editOficineiro').style.display = 'block';
        document.getElementById('listagemOficineiros').style.display = 'none';

        // Armazenar o ID do oficineiro para atualização
        document.getElementById('formEditOficineiro').dataset.idOficineiro = oficineiro.idOficineiro;
    } catch (error) {
        console.error("Erro ao carregar oficineiro:", error);
    }
}

// Função para atualizar o oficineiro
async function updateOficineiro(event) {
    event.preventDefault();

    const idOficineiro = event.target.dataset.idOficineiro;
    const nomeOficineiro = document.getElementById('editNomeOficineiro').value;
    const idadeOficineiro = document.getElementById('editIdadeOficineiro').value;
    const tempoEmpresa = document.getElementById('editTempoEmpresa').value;
    const fotoOficineiro = document.getElementById('editFotoOficineiro').value;

    const oficineiro = {
        nomeOficineiro,
        idadeOficineiro: parseInt(idadeOficineiro),  // Certifique-se de que é um número
        tempoEmpresa: parseInt(tempoEmpresa),        // Certifique-se de que é um número
        fotoOficineiro
    };

    console.log('Requisição de atualização:', JSON.stringify(oficineiro));

    try {
        const response = await fetch(`http://127.0.0.1:8080/oficineiros/${idOficineiro}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(oficineiro),
        });

        if (response.ok) {
            alert('Oficineiro atualizado com sucesso!');
            loadOficineiros();
            document.getElementById('editOficineiro').style.display = 'none';
            document.getElementById('listagemOficineiros').style.display = 'block';
        } else {
            // Aqui você captura o erro detalhado
            const errorDetails = await response.json();  // Captura o corpo do erro, se houver
            console.error("Erro ao atualizar oficineiro:", errorDetails);
            alert('Erro ao atualizar oficineiro.');
        }
    } catch (error) {
        console.error("Erro ao atualizar oficineiro:", error);
    }
}


// Função para excluir um oficineiro
async function deleteOficineiro(idOficineiro) {
    if (confirm('Tem certeza que deseja excluir este oficineiro?')) {
        try {
            const response = await fetch(`http://127.0.0.1:8080/oficineiros/${idOficineiro}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Oficineiro excluído com sucesso!');
                loadOficineiros(); // Recarregar a lista de oficineiros após exclusão
            } else {
                alert('Erro ao excluir oficineiro.');
            }
        } catch (error) {
            console.error("Erro ao excluir oficineiro:", error);
        }
    }
}

// Função para atualizar o instrumento
async function updateInstrumento(event) {
    event.preventDefault();

    const idInstrumento = event.target.dataset.idInstrumento;
    const nomeInstrumento = document.getElementById('editNomeInstrumento').value;
    const tempoAprendizado = document.getElementById('editTempoAprendizado').value;
    const fotoInstrumento = document.getElementById('editFotoInstrumento').value;
    const oficineiroId = document.getElementById('editSelectOficineiro').value;

    const instrumento = {
        nomeInstrumento,
        tempoAprendizado,
        fotoInstrumento,
        oficineiroId
    };

    try {
        const response = await fetch(`http://127.0.0.1:8080/instrumentos/${idInstrumento}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(instrumento),
        });

        if (response.ok) {
            alert('Instrumento atualizado com sucesso!');
            loadInstrumentos();
            document.getElementById('editInstrumento').style.display = 'none';
            document.getElementById('listagemInstrumentos').style.display = 'block';
        } else {
            alert('Erro ao atualizar instrumento.');
        }
    } catch (error) {
        console.error("Erro ao atualizar instrumento:", error);
    }
}

// Função para abrir o formulário de edição de instrumento
async function editInstrumento(idInstrumento) {
    try {
        const response = await fetch(`http://127.0.0.1:8080/instrumentos/${idInstrumento}`);
        const instrumento = await response.json();

        // Preencher os campos do formulário com os dados do instrumento
        document.getElementById('editNomeInstrumento').value = instrumento.nomeInstrumento;
        document.getElementById('editTempoAprendizado').value = instrumento.tempoAprendizado;
        document.getElementById('editFotoInstrumento').value = instrumento.fotoInstrumento;

        // Carregar os Oficineiros no select do formulário de edição
        await loadOficineirosForEdit();

        // Selecionar o professor associado ao instrumento
        const selectOficineiro = document.getElementById('editSelectOficineiro');

        // Verificar se o campo de seleção existe antes de tentar definir seu valor
        if (selectOficineiro) {
            selectOficineiro.value = instrumento.oficineiro.idOficineiro;
        } else {
            console.error('Campo de seleção de oficineiro não encontrado');
        }

        // Exibir o formulário de edição
        document.getElementById('editInstrumento').style.display = 'block';
        document.getElementById('listagemInstrumentos').style.display = 'none';

        // Armazenar o ID do instrumento para atualização
        document.getElementById('formEditInstrumento').dataset.idInstrumento = instrumento.idInstrumento;
    } catch (error) {
        console.error("Erro ao carregar instrumento:", error);
    }
}


// Função para excluir um instrumento
async function deleteInstrumento(idInstrumento) {
    if (confirm('Tem certeza que deseja excluir este instrumento?')) {
        try {
            const response = await fetch(`http://127.0.0.1:8080/instrumentos/${idInstrumento}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Instrumento excluído com sucesso!');
                loadInstrumentos();
            } else {
                alert('Erro ao excluir instrumento.');
            }
        } catch (error) {
            console.error("Erro ao excluir instrumento:", error);
        }
    }
}
