# Programação com Objetos - Projeto

## Objetivo
O objectivo do projecto e desenvolver uma aplicação de gestão de uma rede de terminais de comunicação, denominada por prr. Genericamente, o programa permite o registo, gestão e consulta de clientes, terminais e comunicações.

## Clientes, terminais, comunicações, planos tarifários

Os clientes, terminais e comunicações possuem chaves únicas, cadeias de caracteres para os clientes e para os terminais e inteiros para as comunicações.

A noção de saldo é definida como a diferença entre os valores dos pagamentos efectuados e das dívidas por pagar. O saldo pode ser calculado globalmente (considerando todos os clientes), por cliente (considerando todos os terminais do cliente) ou por terminal (considerando todas as comunicações do terminal, pagas e por pagar).

## Propriedades e funcionalidade dos clientes

Cada cliente, para além da chave única, tem ainda o nome (cadeia de caracteres) e o número de identificação fiscal (inteiro). A cada cliente podem estar associados vários terminais.

O cliente mantém informação sobre os pagamentos efectuados (sobre comunicações passadas) e valores em dívida (comunicações cujo valor ainda não foi pago).

Existem três tipos de clientes: Normal (situação inicial, após o registo -- no entanto, ver a situação da leitura de dados textuais), Gold e Platinum. O tipo de cliente influencia o custo das comunicações que efectua (ver planos tarifários). O tipo do cliente evolui nas seguintes condições:
```
Antes 	Depois 	Condição
Normal 	Gold 	O saldo do cliente (após realizar um pagamento) é superior a 500 créditos.

Normal 	Platinum 	(não é possível)
Gold 	Normal 	O saldo do cliente (após realizar uma comunicação) é negativo.

Gold 	Platinum 	O cliente realizou 5 comunicações de vídeo consecutivas e não tem saldo negativo. A contabilização da 5ª comunicação ainda considera que o cliente é do tipo Gold.

Platinum 	Gold 	O cliente realizou 2 comunicações de texto consecutivas e não tem saldo negativo. A contabilização da 2ª comunicação ainda considera que o cliente é do tipo Platinum.

Platinum 	Normal 	O saldo do cliente (após realizar uma comunicação) é negativo.
```

## Propriedades e funcionalidade dos terminais

Cada terminal é identificado por uma cadeia de caracteres numérica (exactamente 6 dígitos) e está associado a um único cliente.

Os terminais podem realizar três tipos de comunicação: texto, voz e vídeo. As comunicações realizadas pelo terminal são contabilizadas de acordo com o tarifário associado ao cliente. O terminal tem contabilidade própria, sendo sempre possível saber os valores dos pagamentos efectuados e dos valores devidos.

Existem, pelo menos, dois tipos de terminal: básicos e sofisticados. Os terminais básicos só conseguem realizar comunicações de texto e de voz, não podendo nem iniciar nem receber comunicações de vídeo. Os terminais sofisticados podem realizar todos os tipos de comunicação.

Cada terminal tem uma lista de amigos (inicialmente vazia). Um terminal não pode ser amigo de si próprio.

Um terminal recém-criado fica no estado de espera (idle); tem os valores de pagamentos e dívidas ambos a zero. Existem outros estados, definidos a seguir.

### Estados dos terminais

Cada terminal pode estar em espera, em silêncio, ocupado ou desligado.

    Espera -- situação normal sem actividade (idle);
    Silêncio -- tal como em Espera, pode iniciar-se qualquer tipo de comunicação suportada pelo terminal, mas só podem ser recebidas comunicações de texto;
    Ocupado -- não podendo iniciar-se comunicações, mas podem ser recebidas mensagens de texto;
    Desligado -- não podem ser iniciadas ou recebidas comunicações.

### Transições entre estados de terminais

Um terminal pode chegar aos vários estados nas seguintes condições (outras transições não são possíveis):

    Espera -- de desligado (ao ligar em espera); de silêncio (ir para espera); de ocupado (final de comunicação);
    Silêncio -- de desligado (ao ligar em silêncio); de espera (colocar em silêncio); de ocupado (final de comunicação);
    Ocupado -- de espera ou de silêncio (início de comunicação);
    Desligado -- de espera ou de silêncio (ao desligar).

### Notificações

Apenas são passíveis de notificação os clientes que tentaram comunicação com um terminal e a comunicação não foi possível nessa altura. Quando uma comunicação não se efectua, regista-se a tentativa de contacto, para que, assim que seja possível a realização do contacto pretendido, se enviarem notificações aos clientes associados aos terminais de origem. O registo da tentativa de contactos só tem lugar quando o cliente do terminal de origem tem activa a recepção de contactos falhados no instante em que se tentou efectuar a comunicação (por omissão, a recepção de notificações está activa).

São geradas notificações e é possível avisar um cliente nas seguintes circunstâncias:

    Um terminal desligado é colocado em silêncio (off-to-silent): notifica-se disponibilidade para receber comunicações de texto.
    Um terminal desligado ou em silêncio é colocado em espera (off-to-idle ou silent-to-idle): notifica-se disponibilidade para receber comunicações (qualquer suportada).
    Um terminal deixa de estar ocupado (busy-to-idle): notifica-se disponibilidade para receber comunicações (qualquer suportada).

## Propriedades e funcionalidade das comunicações

Cada comunicação tem um identificador único (número inteiro, no contexto de todos os clientes). A primeira tem como identificador “1”, sendo os identificadores subsequentes obtidos por incremento unitário do mais recente identificador utilizado. A comunicação contém ainda informação sobre os terminais de origem e de destino e o estado da comunicação: em curso ou terminada.

As comunicações de texto têm ainda a mensagem enviada. As comunicações interactivas (vídeo e voz) possuem informação sobre a duração da comunicação. O custo de uma comunicação depende do comprimento da mensagem de texto ou da duração das comunicações interactivas. O custo depende ainda do plano tarifário associado a cada cliente (calculado no final da comunicação). Todos os cálculos envolvendo os custos das comunicações devem ser realizados sem arredondamentos.

Um terminal não pode estabelecer uma comunicação interactiva consigo próprio.

## Planos tarifários

Cada plano tarifário define os custos para cada tipo de comunicação, baseado no nível do cliente, no tipo de comunicação, entre outras características. Os planos tarifários têm um nome único no contexto da rede de terminais a que estão associados. A rede de terminais pode oferecer vários planos tarifários mas em cada momento um cliente apenas tem um plano tarifário. A rede de terminais oferece pelo menos o plano tarifário designado como base. Este plano tarifário é o plano atribuído inicialmente a todos os clientes. O custos das comunicações é medido em créditos.

O custo (medido em créditos) de uma comunicação de texto com N caracteres no plano tarifário base está representado na tabela seguinte:
	Normal 	Gold 	Platinum
N < 50 caracteres 	10 	10 	0
50 caracteres <= N < 100 caracteres 	16 	10 	4
N >= 100 caracteres 	2 x N 	2 x N 	4

Quando é efectuada uma comunicação de voz ou de vídeo, o custo no plano tarifário base é proporcional ao tempo de conversação e, quando se comunica com um terminal amigo, é aplicado um desconto de 50%. O custo, em créditos por minuto, é o seguinte para terminais não amigos:
	Normal 	Gold 	Platinum
Comunicação de voz 	20 	10 	10
Comunicação de vídeo 	30 	20 	10

O custo de uma comunicação deve ser calculado quando a comunicação termina e guardado, por forma a garantir que o custo não é afectado por mudanças futuras dos planos tarifários.

## Entrega de notificações

Os clientes podem activar a recepção de notificações sobre eventos associados a terminais em algumas circunstâncias. Em qualquer momento, um cliente pode activar ou desactivar essas notificações. A entrega de notificações deve ser flexível e deve prever vários meios de entrega, e.g., correio postal, SMS, email, entre outras. O meio de entrega por omissão corresponde a registar a notificação na aplicação.

As notificações contêm informação acerca da sua natureza e do terminal a que dizem respeito. Um dado evento apenas produz uma notificação por cliente (o conjunto de clientes a notificar é limpo após o envio da notificação). 