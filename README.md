# AE Modulator

Jde o program který provádí FM, MFM a RLL modulace dat tak jak je vyžadováno předmětem __Architektura Počítačů__. Moduluje
jakýkoliv ASCII string a má na výběr z obou verzí RLL modulace. Při sestavování ze zdroje si jednoduše můžete definovat svou
vlastní tabulku RLL.

Online verze se připravuje ale asi vám ji hostovat nebudu

- Postup kompletnosti
  - [x] FM modulace
  - [x] MFM modulace
  - [ ] RLL1 modulace
  - [ ] RLL2 modulace
  - [ ] Pokrytí testy
  - [ ] Základní možnosti výstupu (vyžádat si jen jednu konverzi)
  - [ ] Zachycení chyb (filtrovat ěščřžýáíéóúů a všechno mimo ASCII znaky)
  - [ ] Přepsat zdroják do angličtiny?

## Instalace

1. Ze zdroje
   - Nainstalujte si nějakou javu
   - Nainstalujte si clojure
   - Nainstalujte is leiningen
   - Naklonujte si repo ``git clone git@github.com:qespr/ae-utb-modulator.git``
   - Přejděte do složky ``cd ae-utb-modulator`` a spustit pomocí leiningen: ``lein run [váš text]``
2. Předsestavené
   - Vydání bude sestaveno až tam budou ubě RLL verze
3. Online verze (dobře to není úplně instalace)
   - Asi by to teoreticky šlo narvat do nějakého online interpreteru
   - Později to můžu zkusit podporovat
4. Emacs
   - Možná bude Elisp verze, pokud se na to nevyseru (pravděpodobně se na to vyseru)

## Možnosti

Zatím žádné

## Ukázky

```
$ java -jar modulator.jar Hello
Text:  Hello
  FM:  PNPPPNPNPPPNPNPNPNPPPPPNPNPPPNPPPNPPPPPNPPPPPNPNPNPPPPPNPPPPPNPNPNPPPPPNPPPPPPPP
 MFM:  PNNPNNPNNPNNPNPNPNNPNPNNPNNPNNNPNNNPNPNNNPNPNNPNPNNPNPNNNPNPNNPNPNNPNPNNNPNPNPNP
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
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html
