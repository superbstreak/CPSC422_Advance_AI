#!/usr/bin/perl

#use strict;

my $verbose = 1;

my $print_spans = 0;
our (%grammar, %prob, @input, @start);

my $file = shift or die "$0 <grammar>\n";
require $file;


    my ($user1,$system1,$cuser1,$csystem1) = times;

    #my @input = (John, plays, soccer, at, school);

    #my @input = (John, plays, soccer);

    my $N = $#input + 1; #number of words
    my @A = ();  # len^2
    my %B = ();  # backpointers: len^2 * NTs
    my %P = ();  # prob: len^2 * NTs

    my $HUGE_VAL = 1048576;
    my $DEFAULT  = -$HUGE_VAL;
    for (my $i = 0; $i < $N; $i++) {
	# nt -> input[i]
	for my $nt (@{$grammar{"$input[$i]"}}) {
	    $j = $i+1;
	    if (!(defined $B{$i}{$j}{$nt})) {
		push @{$A[$i][$j]}, $nt;
	    }
	    $np = log($prob{"$nt -> $input[$i]"});
	    $op = (defined $P{$i}{$j}{$nt}) ? $P{$i}{$j}{$nt} : $DEFAULT;
	    if ($np >= $op) {
		$P{$i}{$j}{$nt} = $np;
		$B{$i}{$j}{$nt} = "/$input[$i]/";
	    }
	}
    }

    for (my $j = 2; $j <= $N; $j++) {
	for (my $i = $j-2; $i >= 0; $i--) {
	    for (my $k = $i+1; $k < $j; $k++) {
		for my $nt1 (@{$A[$i][$k]}) {
		    for my $nt2 (@{$A[$k][$j]}) {
			for my $nt (@{$grammar{"$nt1 $nt2"}}) {
			    if (!(defined $B{$i}{$j}{$nt})) {
				push @{$A[$i][$j]}, $nt;
			    } 
			    $np = $P{$i}{$k}{$nt1} + $P{$k}{$j}{$nt2} +
				log($prob{"$nt -> $nt1 $nt2"});
			    $op = (defined $P{$i}{$j}{$nt}) ? $P{$i}{$j}{$nt} : $DEFAULT;
			    if ($np >= $op) {
				$P{$i}{$j}{$nt} = $np;
				$B{$i}{$j}{$nt} = "$k $nt1 $nt2";
			    }
			}
		    }
		}
	    }
	}
    }

    my $bp = $DEFAULT;
    my $best = '';

    for my $s (@start) {
	my $rx = extractbest(\%B, \%P, 0, $N, $s);
	next if ($rx eq '');
	$np = $P{0}{$N}{$s};
	if ($np >= $bp) { $best = $rx; $bp = $np; }
    }
    print STDERR "BEST DERIV: $bp\n";
    if ($bp == $DEFAULT) {
	print defaultderiv(@input), "\n";
    } else {
	print "BEST PARSE: $best\n";
    }

    my ($user2,$system2,$cuser2,$csystem2) = times;

    print STDERR ("parsing time:",
		  " user=",    $user2-$user1, 
		  " system=",  $system2-$system1, "\n");


sub defaultderiv
{   my (@input) = @_;
    my @default = ();
    my $sz = $#input+1;
    for (my $i = 0; $i < $sz-1; $i++) {
	push (@default, "(k? $input[$i])");
    }
    return "(v @default $input[$sz-1])";
}

sub extractbest
{   my ($B, $P, $i, $j, $nt) = @_;
    my $rx = ();
    return '' if (!defined $$B{$i}{$j}{$nt});
    $back = $$B{$i}{$j}{$nt};
    #print STDERR "prob: $nt, $i, $j: $$P{$i}{$j}{$nt}\n";
    if ($back =~ m|^/[^/]*/|) {
       $rx = "($nt $back)";
    } else {
       my ($k, $nt1, $nt2) = split ' ', $back;
       my $r1 = extractbest($B, $P, $i, $k, $nt1);
       my $r2 = extractbest($B, $P, $k, $j, $nt2);
       if ($nt eq 'INS') {
	   $rx = " $r1 $r2 ";
       } else {
	   $rx = "($nt $r1 $r2)";
       }
    }
    return $rx;
}

