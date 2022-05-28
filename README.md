# AE Modulator

Jde o program který provádí FM, MFM a RLL modulace dat tak jak je vyžadováno předmětem __Architektura Počítačů__. Moduluje
jakýkoliv ASCII string a má na výběr z obou verzí RLL modulace. Při sestavování ze zdroje si jednoduše můžete definovat svou
vlastní tabulku RLL. Program v plné verzi bude umět převádět jak __z__ tak __do__ jednotlivých modulací, počítat jejich
efektivitu a to vše bez limitu 5ti znaků který má oficiální kontrolní stránka. Cílem je možnost tento program použít jako přímou
náhradu kontrolní stránky zamčené za univerzitní VPN.

Online verze se připravuje ale asi vám ji hostovat nebudu, možná ji bude hostovat github v každém případě půjde o plně soběstačný
ZIP s .html souborem který vám pojede lokálně.

- Postup kompletnosti
  - [x] FM modulace
  - [x] MFM modulace
  - [x] RLL1 modulace
  - [x] RLL2 modulace
  - [ ] Pokrytí testy
  - [x] Reverzní převod RLL
  - [ ] Reverzní převod MFM
  - [ ] Reverzní převod FM
  - [ ] Zachycení chyb (filtrovat ěščřžýáíéóúů a všechno mimo ASCII znaky)
  - [ ] Základní možnosti výstupu (možnost vyžádat si jen jednu konverzi)
  - [ ] Kompletní kontrola nad výstupem a převody
  - [ ] Přepsat zdroják do angličtiny?
  - [ ] Spočítat efektivitu každého převodu (počet pulzů na string)
  - [ ] Online verze (možná bude v samostatném projektu)

## Instalace/použití

1. Ze zdroje
   - Nainstalujte si nějakou javu
   - Nainstalujte si clojure
   - Nainstalujte is leiningen
   - Naklonujte si repo ``git clone git@github.com:qespr/ae-utb-modulator.git``
   - Přejděte do složky ``cd ae-utb-modulator`` a spustit pomocí leiningen: ``lein run [váš text]``
2. Předsestavené
   - Stáhněte si binárku ze stránky [vydání](https://github.com/qespr/ae-utb-modulator/releases)
   - Otevřete terminál tam kde jste to stáhli
   - Pomocí ``$ java -jar utb-modulator-1.0.jar [vas text]"`` získáte na standartním výstupu všechny převody
3. Online verze (dobře to není úplně instalace)
   - Asi by to teoreticky šlo narvat do nějakého online interpreteru
   - Později to můžu zkusit podporovat
4. Emacs
   - Možná bude Elisp verze, pokud se na to nevyseru (pravděpodobně se na to vyseru)

## Ukázky

```
$ java -jar utb-modulator-1.0.jar Luna
Text:  Luna
   Bin:  01001100011101010110111001100001
    FM:  PNPPPNPNPPPPPNPNPNPPPPPPPNPPPNPPPNPPPPPNPPPPPPPNPNPPPPPNPNPNPNPP
   MFM:  PNNPNNPNNPNPNNPNPNNPNPNPNNNPNNNPNNNPNPNNNPNPNPNNPNNPNPNNPNPNPNNP
 RLL-1:  NPNNPNNNNNNNPNNNNPNNNNPNNPNNNPNNNPNNPNNPNNNNNNPNNNNNNNPNNNPNNNNNPNNN
 RLL-2:  PNNPNNNNPNNNNNNPNNPNNNNPNNNPNNNPNNPNNNNNPNNNNPNNNNPNNNNNNPNNPNNPNN
```

### Bugs

- Bug: Pokud vložíte něco mimo ASCII tak to vyplivne úplný bordel
  - Řešení: Nedělejte to

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
