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
  Vrací segment, takže si buď můžete udělat substring pomocí jeho délky;
  nebo jej přímo použít pro lookup"
  ([klice bin-string] (rll-match klice klice bin-string))
  ;;obsahuje navíc kopii klíčů [kk] aby jsme mohli obnovit hledání po přidání nul
  ([klice kk bin-string]
  ;;Je potřeba pohlídat jestli klíče nejsou nenenulové, pak se přidávají až dvě nuly
  (cond
    ;;pokud jsou klíče už prázdné - žádná kombinace se nenašla, musíme přidávat nuly;
    ;;můžou se přidat max 2 nuly ale zde na to sereme, zatím to funguje
    (= klice '()) (recur kk kk (str bin-string "0"))
    (cstr/starts-with? bin-string (first klice)) (first klice)
    :else (recur (rest klice) kk bin-string))))


(defn rll-encode
  "Zakóduje binární string pomocí specifikované RLL tabulky"
  ([tabulka bin-string] (rll-encode tabulka bin-string nil))
  ([tabulka string-rest ret]
   (if (> (count string-rest) 0)
     (let [mezi-klic (rll-match (keys tabulka) string-rest)]
     (recur
      tabulka
      (if (< (count mezi-klic) (count string-rest))
        (subs string-rest (count mezi-klic)) ;;Pokračuje v rekzurzi protože je pořád co převádět
        "") ;;Ukončí rekurzy v dalším kole
      (str ret (tabulka mezi-klic))))
     ret)))

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
  (when (= nil args)
    (println "Použití: modulator [text k převedení]")
    (System/exit 1))
  (let [text-bin (nu/text-to-bin (first args))
        text-fm (fm-encode text-bin)
        text-mfm (mfm-encode text-bin)
        text-rll1 (rll-encode RLL-1 text-bin)
        text-rll2 (rll-encode RLL-2 text-bin)]
    (println "Text: " (first args))
    (println "   Bin: " text-bin)
    (println "    FM: " text-fm)
    (println "   MFM: " text-mfm)
    (println " RLL-1: " text-rll1)
    (println " RLL-2: " text-rll2)))
