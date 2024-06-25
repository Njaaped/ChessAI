# Njål's Sjakkspill

## SjakkSpill
Dette er et sjakkspill hvor du kan spille mot en datamaskinmotstander på tre forskjellige dybdenivåer. Du kan velge å spille som hvit eller svart, og velge dybden på datamaskinens tenking, som kan være 4, 5 eller 6. Jo høyere dybde, jo lengre tid vil datamaskinen bruke på å ta sin tur.

## Installasjon og kjøring

For å kjøre spillet, må du først laste ned filene fra dette git-repositoriet. Deretter kan du kjøre chessMain via din IDE

## Spilleregler

Sjakk er et brettspill for to spillere som spilles på et 8x8 rutenett. Hver spiller starter med 16 brikker: en konge, en dronning, to tårn, to springere, to løpere og åtte bønder. Målet med spillet er å ta motstanderens konge.

Spillerne bytter på å flytte brikker, og hver spiller prøver å ta motstanderens brikker ved å flytte sin egen brikke til en rute som okkuperes av motstanderens brikke. Hver type brikke har sin egen måte å flytte på:

Kongen kan flytte én rute i hvilken som helst retning.
Dronningen kan flytte et ubegrenset antall ruter diagonalt, horisontalt eller vertikalt.
Tårnene kan flytte et ubegrenset antall ruter horisontalt eller vertikalt.
Springerne kan flytte i en L-form: to ruter horisontalt og en rute vertikalt, eller to ruter vertikalt og en rute horisontalt.
Løperne kan flytte et ubegrenset antall ruter diagonalt.
Bøndene kan flytte én rute diagonalt framover for å ta en motstanders brikke, eller én eller to ruter rett fram hvis de ikke tidligere har beveget seg fra sin startposisjon.
En spiller kan ikke flytte en brikke til en rute som allerede okkuperes av en av sine egne brikker, men kan erobre en motstanders brikke ved å flytte sin egen brikke til den ruten.

Hvis en spillers konge er truet av en motstanders brikke, kalles det sjakk, og spilleren må gjøre et trekk for å unngå at kongen blir tatt. Hvis en spiller ikke kan gjøre et gyldig trekk for å unngå sjakk, kalles det sjakk matt, og spilleren taper partiet.

## Hvordan spille

Når spillet starter, vil du bli bedt om å velge hvilken farge du ønsker å spille som, samt dybden på algoritmens. Deretter vil spillet starte, og du kan begynne å spille ved å klikke på brikken du ønsker å flytte, og deretter klikke på ruten du ønsker å flytte brikken til. Hvis trekket ditt er gyldig, vil brikken flyttes til den valgte ruten. Hvis trekket ditt ikke er gyldig, vil du bli bedt om å gjøre et annet trekk.

Datamaskinen vil deretter tenke på sitt neste trekk og gjøre det, og det vil bli din tur. Du kan velge å bytte "View" ved å trykke på knappen "View" da vil brettet få flere forskjellige farger. Jeg har også en settings knapp, men den gjør ikke stort enda.


Lykke til med spillet!

https://youtu.be/mMapoEdR29Y