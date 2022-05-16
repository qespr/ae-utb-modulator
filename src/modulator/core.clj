(ns modulator.core
  (:require [modulator.number-utils :as nu]
            [clojure.string :as cstr])
  (:gen-class))

(def RLL-1
  "První verze RLL modulace jak je definováno v návodu na cvičení"
  {"00" "PNNN",
   "01" "NPNN",
   "100" "NNPNNN",
   "101" "PNNPNN",
   "111" "NNNPNN",
   "1100" "NNNNPNNN",
   "1101" "NNPNNPNN"})

(def RLL-2
  "Druhá verze RLL modulace jak je definováno v návodu na cvičení"
  {"11" "PNNN",
   "10" "NPNN",
   "011" "NNPNNN",
   "010" "PNNPNN",
   "000" "NNNPNN",
   "0010" "NNPNNPNN",
   "0011" "NNNNPNNN"})

(defn- rll-match
  "Zjistí který segment se nachází jako další v binárním řetězci
  Vrací délku segmentu aby jste si mohli udělat substring"
  ;;Musíme si ještě udržovat kopii všech klíčů pro případ
  ;;že by poslední sada nebyla tabulková hodnota
  ([klice bin-string] (rll-match klice klice bin-string))
  ;;kk - klíč kopie, vždy předávat nezměněné
  ([klice kk bin-string]
  ;;vyzkoušet match pro první first z klicu
  ;;pokud ne tak recur s rest klicu
  ;;Tady MUSÍ nejdřív být check jestli klíče nejsou nulové -> stane se když už netrefíme nic, v tom případě se musí přidávat nuly
  (cond
    ;;pokud jsou klíče už prázdné - žádná kombinace se nenašla, musíme přidávat nuly;
    ;;můžou se přidat max 2 nuly ale zkusíme to ignorovat a uvidíme jestli z toho bude někdy bug
    (= klice '()) (recur kk kk (str bin-string "0"))
    ;;Kombinace se našla - vracíme délku sady aby si volající funkce mohla ukrojit substring
    (cstr/starts-with? bin-string (first klice)) (first klice)
    :else (recur (rest klice) kk bin-string))))

;;Todo: tohle taky nějak musí deketovat že došlo k doplnění
;;      třeba tak že to vždy zkontroluje že segment není delší než zbývající string
(defn rll-encode
  "Zakóduje binární string pomocí specifikované RLL tabulky"
  ([tabulka bin-string] (rll-encode tabulka bin-string nil))
  ;;Tady se musí dávat bacha na to kddyž se nic nematchne
  ([tabulka string-rest ret]))



;;Komplet pravidla
;;0 = PN if předtím 00
;;0 = NN if předtím 10
;;1 = NP
(defn mfm-encode
  "Zakóduje binární text pomocí MFM modulace"
  ([bin-text] (mfm-encode (first bin-text) \0 (rest bin-text) nil))
  ([bit prev tail ret]
   (if (not= bit nil) ;;Dokud máme bity ke kontrole
     (recur
      (first tail) ;;Předáváme další bit na průzkum
      bit ;;Předchozí bude současný bit
      (rest tail) ;;Ocas bude ocas ocasu
      (str ret
           ;;Hlavní logika kde se rozhoduje o tom jaká modulace se použije
           (if (= bit \1)
             "NP"
             (if (= prev \0)
               "PN"
               "NN"))))
     ret)))

(defn fm-encode
  "Zakóduje text pomocí FN modulace"
  [bin-text]
  (cstr/replace
   (cstr/replace bin-text #"0" "PN")
   #"1" "PP"))

(defn -main
  "Vstupní bod, bere 1 argument - text k modulaci"
  [& args]
  (let [text-bin (nu/text-to-bin (first args))
        text-fm (fm-encode text-bin)
        text-mfm (mfm-encode text-bin)
        text-rll1 "nil"
        text-rll2 "nil"]
    (println "Text: " (first args))
    (println " Bin: " text-bin)
    (println "  FM: " text-fm)
    (println " MFM: " text-mfm)))
