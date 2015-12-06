
$grammar{"NP VP"}  = ["S"];
$grammar{"V NP"}   = ["VP"];
$grammar{"VP PP"}  = ["VP"];
$grammar{"NP PP"}  = ["NP"];
$grammar{"P NP"}   = ["PP"];
$grammar{"John"}   = ["NP"];
$grammar{"plays"}  = ["V"];
$grammar{"soccer"} = ["NP"];
$grammar{"at"}     = ["P"];
$grammar{"school"} = ["NP"];

@start = ("S");

$prob{"S -> NP VP"}   = 1;
$prob{"VP -> V NP"}   = 0.5;
$prob{"VP -> VP PP"}  = 0.5;
$prob{"PP -> P NP"}   = 1;
$prob{"NP -> NP PP"}  = 0.25;
$prob{"NP -> John"}   = 0.25;
$prob{"NP -> soccer"} = 0.25;
$prob{"NP -> school"} = 0.25;
$prob{"V -> plays"}   = 1;
$prob{"P -> at"}      = 1;

@input = (John, plays);

1;

