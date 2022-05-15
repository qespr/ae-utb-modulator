# AE Modulator

Jde o program který provádí FM, MFM a RLL modulace dat tak jak je vyžadováno předmětem __Architektura Počítačů__. Moduluje
jakýkoliv ASCII string a má na výběr z obou verzí RLL modulace. Při sestavování ze zdroje si jednodušše můžete definovat svou
vlastní.

Online verze se připravuje ale asi vám ji hostovat nebudu

## Instalace

1. Ze zdroje
   - Nainstalujte si nějakou javu
   - Nainstalujte si clojure
   - Nainstalujte is leinegen
2. Předsestavené
   - Jděte na stránku s vydanými verzemi
   - Stáhněte si nejnovější
   - Stačí vám java 8+
   - Více info v užití
3. Online verze (dobře to není úplně instalace)
   - Asi by to teoreticky šlo narvat do nějakého online interpreteru
   - Později to můžu zkusit podporovat
4. Emacs
   - Ano bude Elisp verze, pokud se na to nevyseru

## Užití

``$ java -jar modulator-0.1.0-standalone.jar [Text]``

Tohle vám vyplivne váš text zakódovaný do veškerých podporovaných modulací

## Možnosti

Zatím žádné

## Ukázky

```
$ java -jar modulator.jar Hello
Text:  Hello
FM:  PNPPPNPNPPPNPNPNPNPPPPPNPNPPPNPPPNPPPPPNPPPPPNPNPNPPPPPNPPPPPNPNPNPPPPPNPPPPPPPP
```

### Bugs

Zatím žádné známé, klidně nahlašujte, patchujte

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
