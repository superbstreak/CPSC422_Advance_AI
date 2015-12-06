
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

$prob{"S -> NP VP"}   = 1.000000000;
$prob{"VP -> V NP"}   = 0.769230769;
$prob{"VP -> VP PP"}  = 0.230769231;
$prob{"PP -> P NP"}   = 1.000000000;
$prob{"NP -> NP PP"}  = 0.040000000;
$prob{"NP -> John"}   = 0.400000000;
$prob{"NP -> soccer"} = 0.400000000;
$prob{"NP -> school"} = 0.160000000;
$prob{"V -> plays"}   = 1.000000000;
$prob{"P -> at"}      = 1.000000000;

@input = (John, plays);

1;

