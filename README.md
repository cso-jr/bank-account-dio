# bank-account-dio

"Este projeto é uma aplicação Java orientada a objetos, desenvolvida com o objetivo de consolidar conceitos fundamentais da programação orientada a objetos (POO), como herança, encapsulamento, polimorfismo, abstração e reuso de código. A aplicação simula um sistema bancário básico que permite a criação de contas, depósitos, saques, transferências via PIX, criação de investimentos e acompanhamento de histórico de transações."

Essa aplicação foi desenvolvida na IDE Eclipse e utlizando Maven como builder e gerenciador de dependências, lombok para automação de código.

## Atividades proporcionadas pela execução desse projeto:

 - Aplicação de conceitos de orientação a objetos;
 - Implementação da estrutura de entidades com herança e composição;
 - Criação de repositórios simulando persistência em memória;
 - Pratica de boas práticas com uso de Lombok, enums e records;
 - Exercício da construção de menus e fluxos interativos via console;
 - Documentação de processos técnicos de forma clara e estruturada; 
 - Utilização o GitHub como ferramenta para compartilhamento de documentação técnica.

## Conceitos praticados e pontos de atenção:

  - Tratamento de exceções: ao estender a classe RuntimeException para customizar um erro em determinado ponto do fluxo da aplicação, deve-se ter em mente que essa exceção lançada em um ponto do programa será capturada em outro ponto, pelo método que fez a chamada do método que lançou a exceção. É importante porque caso não ocorra a captura da exceção lançada o programa continua e pode quebrar após lançar a exceção.
  - Interfaces, repositories e enums: recursos importantes e valiosos que devem ser mais explorados, especialmente para o desenho da estrutura da aplicação, com divisão em camadas e atribuição de responsabilidades em diferentes partes da aplicação.
  - O uso do lombok para reduzir a quantidade de código merece atenção, pois um mal funcionamento do lombok pode não ficar explícito, gerando erros durante o desenvolvimento do código que não tem relação direta com a lógica ou com os objetos envolvidos, e sim com o lombok que não está trabalhando corretamente. Nesse caso, a ide não deixa de ter razão, o código tem problema, mas ela não diz que o problema é no lombok, ja que as annotations estão lá, o que leva ao outro ponto importante de atenção:
  - Cuidado com os imports! As annotations feitas no código podem coincidir com as de outras bibliotecas e fazer o import errado gera além de muito aprendizado, tempo perdido.

## 🚀 Instalando bank-account-dio do Carlos

Para instalar o bank-account-dio do Carlos, siga estas etapas:

Windows:
- Tenha o Maven instalado na máquina (lembre-se de configurar a variável de ambiente para executar o comando mvn em qualquer pasta)
- faça o clone do projeto e na pasta principal do projeto execute o comando do Maven;
- Na pasta do projeto, via terminal, execute os comandos:
```
mvn package

```
isso vai gerar um arquivo .jar na pasta \target
Em seguida, use o comando:
```
java -jar target\java-bank-account-0.0.1-SNAPSHOT.jar
```
## ☕ Usando o bank-account-dio

Se tudo certo, esse menu aparece na tela:

<img width="404" height="263" alt="image" src="https://github.com/user-attachments/assets/b221dd15-b931-4bf2-9c4a-d8e48be1cdd8" />


Atenção a alguns detalhes: 

- O valor de entrada é sempre colocado em quantidade de centavos, ou seja, entrada de 100 equivale a 1,00 real e 10000 equivale a 100,00 reais;
- Chave pix é o identificador da conta, em String;
- É preciso criar uma conta, depois um investimento. Uma conta pode ter uma carteira de investimento, desde que o investimento já exista antes de a carteira de investimento ser criada.
- A conta usa uma String "pix" para identificá-la, e o investimento usa um ID numérico sequencial e o primeiro investimento criado assumirá o valor 1 (é necessári lembrar isso quando for criar a carteira de investimento).




