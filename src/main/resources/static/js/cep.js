const options = {
    method: "GET",
}

const cep = document.getElementById("cep")
cep.addEventListener("blur", (e) => {
    let Cep = document.getElementById("cep").value;
    console.log(Cep)
    let search = cep.value.replace("-", "")
    fetch(`https://viacep.com.br/ws/${search}/json/`, options).then((response) => {
        response.json().then(data => {
            console.log(data)
            document.getElementById("bairro").value = data.bairro;
            document.getElementById("localidade").value = data.localidade;
            document.getElementById("logradouro").value = data.logradouro;
            document.getElementById("uf").value = data.uf;
        })
    })
})

