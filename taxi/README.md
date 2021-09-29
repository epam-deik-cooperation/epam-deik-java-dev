# 1. házi feladat: Taxi

Ebben a feladatban lehetőségetek lesz gyakorolni a kódolvasást, különböző refaktorálási
módszereket, az egységtesztek írását és a mock-ok használatát.

## Domain

Egy taxi vállalat szoftverét fejleszted, ahol hamarosan nagyobb bővítés várható a
számlázást végző kódban. Sajnos azonban a kód elég régi, már egy fejlesztő sincs
a cégnél, aki részt vett az elkészítésében, ráadásul a commitokban említett ticketek
sem túl részletesek a követelményeket illetően. Emellett a kód minősége sem a legjobb,
nem könnyű olvasni, a teljes logika egyetlen osztályban található, a megoldás nehézkesen 
bővíthető.

## A feladat

1. Írj a jelenlegi kódot (src/main/java/com/epam/training/taxi/Calculator.java) teljesen lefedő teszt-eseteket
    1. Ez segít feltárni a követelményeket, amely alapján a jelenlegi program működik, illetve
    1. Biztosítja azt, hogy a rafaktorálás során nem változtatod meg a kód működését
1. Refaktoráld a jelenlegi kódot
    1. Igyekezz objektum-orientáltan megoldani a feladatot
    1. Figyelj a [Java kódolási konvenciókra](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)
    1. Ha új osztályt vezetsz be, ne felejts el egységtesztet is készíteni hozzá
    1. A fő fókusz legyen a felelősségek megfelelő elosztásán és a bővíthetőségen  
    
