# roboFacebook
###Funcionalidades do robo:
 - Percorre grupos do facebook e curti postagens
 - Compartilhar postagem em grupos do facebook

###Requisitos do projeto:
- Baixar o webdriver em: https://www.selenium.dev/downloads e colocar como dependência do projeto
- Baixar o CromeDriver em: https://chromedriver.chromium.org/downloads e colocar no diretorio /opt.
```
    Isso mesmo, /opt. Não fiz pensando em windows, mas é facil mudar isso.
    No metodo inicializarNavegador da classe App, toque:
System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
    Por:
System.setProperty("webdriver.chrome.driver", "PastaDesejadaNoWindos/chromedriver");
``` 