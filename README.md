# AE Modulator

Jde o program který provádí obousměrné FM, MFM a RLL modulace dat tak jak je vyžadováno předmětem __Architektura Počítačů__. Moduluje
jakýkoliv ASCII string a má na výběr z obou verzí RLL modulace. Při sestavování ze zdroje si jednoduše můžete definovat svou
vlastní tabulku RLL.

Program nemá limit na počet znaků a upozorňuje na chyby v chybné části modulace pro případ že by jste jej využívaly jen pro
kontrolu. __NERUČÍM ZA SPRÁVNOST__ - Program je samozřejmě testován a je funkční, nicméně vždy doporučuji kontrolu přes
univerzitní stránku a případné neshody nahlásit.

Online verze se připravuje ale asi vám ji hostovat nebudu, možná ji bude hostovat github, v každém případě půjde o plně soběstačný
ZIP s .html souborem který vám pojede lokálně.

- Postup kompletnosti
  - [x] FM modulace
  - [x] MFM modulace
  - [x] RLL1 modulace
  - [x] RLL2 modulace
  - [ ] Pokrytí testy
  - [x] Reverzní převod RLL
  - [x] Reverzní převod MFM
  - [x] Reverzní převod FM
  - [x] Zachycení chyb (Omezit na rozsah ASCII tabulky)
  - [x] Detekovat když se někdo snaží demodulovat nemodulovaný text
  - [x] Základní možnosti výstupu (možnost vyžádat si jen jednu konverzi)
  - [x] Kompletní kontrola nad výstupem a převody
  - [x] Spočítat efektivitu každého převodu (počet pulzů na string)
  - [ ] Online verze (bude v samostatném projektu a zde link)
  - [x] Kompletní README

## Instalace/použití

1. Ze zdroje
   - Nainstalujte si nějakou Javu 8+
   - Nainstalujte si clojure
   - Nainstalujte is leiningen (nebo si to sestavte manuálně ale to vám popisovat nebudu)
   - Naklonujte si repo ``git clone git@github.com:qespr/ae-utb-modulator.git``
   - Přejděte do složky ``cd ae-utb-modulator`` a spustit pomocí leiningen: ``lein run [váš text]``
2. Předsestavené
   - Stáhněte si binárku ze stránky [vydání](https://github.com/qespr/ae-utb-modulator/releases)
   - Otevřete terminál tam kde jste to stáhli
   - Pomocí ``$ java -jar utb-modulator-1.0.jar [vas text]"`` získáte na standartním výstupu všechny převody
   - Pro další možnosti výstupu přejděte do další sekce
3. Online verze - ZATÍM NENÍ
   - Asi by to teoreticky šlo narvat do nějakého online interpreteru (gl hf)
   - Bude online verze v Clojure scriptu

## Možnosti výstupu z nápovědy

Základní použití:
modulator [text] - Vypíše všechny modulované verze [text]u, pokud text obsahuje mezery je nutné jej dát do "uvozovek"
V tomto případě vypíše, počet pulzů, převod do všech modulací + binární reprezentaci textu

Pokročilé možnosti:
-e [text] - Text k modulování
-d [modulovaný text] - text k demodulování, je potřeba upřesnit o jakou modulaci se jedná pomocí -t
-t {fm, mfm, rll1, rll2} - Specifikuje o jaký typ modulace se jedná (při -d) nebo jaká se žádá (při -e)
-q - Tichý režim, jen vypsat modulaci/demodulaci bez statistik a dalších převodů, funguje pouze pokud je použit '-t'

Každé další opakování jakékoliv možnosti přepíše tu předchozí (-t fm -t mfm => platí pouze '-t mfm'

## Ukázky

```
$ java -jar utb-modulator-1.3.jar Luna
Text:  Luna
   Bin:  01001100011101010110111001100001
    FM:  PNPPPNPNPPPPPNPNPNPPPPPPPNPPPNPPPNPPPPPNPPPPPPPNPNPPPPPNPNPNPNPP
 Pulzu:  48
   MFM:  PNNPNNPNNPNPNNPNPNNPNPNPNNNPNNNPNNNPNPNNNPNPNPNNPNNPNPNNPNPNPNNP
 Pulzu:  24
 RLL-1:  NPNNPNNNNNNNPNNNNPNNNNPNNPNNNPNNNPNNPNNPNNNNNNPNNNNNNNPNNNPNNNNNPNNN
 Pulzu:  14
 RLL-2:  PNNPNNNNPNNNNNNPNNPNNNNPNNNPNNNPNNPNNNNNPNNNNPNNNNPNNNNNNPNNPNNPNN
 Pulzu:  15
$ java -jar utb-modulator-1.3.jar -e Purple -t rll1
NPNNNPNNPNNNPNNNNPNNNNPNNPNNNPNNNPNNNNNNPNNNNNPNNNNNNPNNPNNNPNNNNPNNPNNPNNNNPNNNNPNNNNPNNNPNNPNN
Vstup:  Purple
Binárně:  010100000111010101110010011100000110110001100101
Pulzu:  22
$
$ java -jar utb-modulator-1.3.jar -q -t rll2 -e PNNPNNNPNNNNPNNNNNPNNNNNPNNNPNNPNNPNNNNNNPNNPNNPNNPNNNNPNNPNNPNNNNPNNNNPNNNPNNNNNPNN
Smart
```

## Známé Bugy

- Zatím žádné

## License

Copyright © 2022 qespr

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) version 3, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html
